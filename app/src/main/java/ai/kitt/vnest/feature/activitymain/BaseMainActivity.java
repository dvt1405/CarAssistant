package ai.kitt.vnest.feature.activitymain;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.blankj.utilcode.util.AppUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callbacks.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.models.currentweather.CurrentWeather;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIContext;
import ai.api.model.AIOutputContext;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.kitt.snowboy.service.TriggerBroadCast;
import ai.kitt.vnest.App;
import ai.kitt.vnest.BuildConfig;
import ai.kitt.vnest.R;
import ai.kitt.vnest.base.LocationActivity;
import ai.kitt.vnest.basedata.api.model.ActiveCode;
import ai.kitt.vnest.basedata.api.model.ActiveResponse;
import ai.kitt.vnest.basedata.api.model.CarResponse;
import ai.kitt.vnest.basedata.api.repository.ActiveRepo;
import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference;
import ai.kitt.vnest.basedata.entity.Audio;
import ai.kitt.vnest.basedata.entity.Message;
import ai.kitt.vnest.basedata.entity.MyAIContext;
import ai.kitt.vnest.basedata.entity.Poi;
import ai.kitt.vnest.basedata.entity.Youtube;
import ai.kitt.vnest.databinding.ActivityMainBinding;
import ai.kitt.vnest.feature.activitymain.adapters.DefaultAssistantAdapter;
import ai.kitt.vnest.feature.activitymain.adapters.ItemNavigationAdapter;
import ai.kitt.vnest.feature.screencheckcarvilolations.WebViewFragment;
import ai.kitt.vnest.feature.screenhome.FragmentHome;
import ai.kitt.vnest.feature.screensettings.FragmentSettings;
import ai.kitt.vnest.feature.screenspeech.FragmentResult;
import ai.kitt.vnest.receiver.OpenNavigationReceiver;
import ai.kitt.vnest.speechmanager.speechonline.OnResultReady;
import ai.kitt.vnest.speechmanager.speechonline.SpeechRecognizerManager;
import ai.kitt.vnest.speechmanager.texttospeech.TextToSpeechManager;
import ai.kitt.vnest.util.AppUtil;
import ai.kitt.vnest.util.LogUtil;
import ai.kitt.vnest.util.NavigationUtil;
import ai.kitt.vnest.util.ConfirmDialog;
import ai.kitt.vnest.util.DialogActiveControl;
import ai.kitt.vnest.util.DialogUtils;
import kun.ktupdatelibrary.DownLoadBroadCast;
import kun.ktupdatelibrary.UpdateChecker;
import timber.log.Timber;

@SuppressLint("LogNotTimber")

public abstract class BaseMainActivity extends LocationActivity implements TextToSpeechManager.TextToSpeechListener, ViewModel.OnOpenVtvListener,
        ViewModel.OnCallListener, ViewModel.OnSearchMp3Listener, ActiveRepo.ActiveListener, ViewModel.OnSearchYoutubeListener, ViewModel.OnSearchPlacesListener,
        ViewModel.OnOtherSearchListener, ViewModel.CheckCarViolationsListener,ViewModel.OnOpenAppListener {
    protected static int callDeviceInfoTimes = 0;
    protected static final String LOG_TAG = "VNest";
    protected static final int REQUEST_PERMISSION_CODE = 101;
    protected static final int PICK_CONTACT_CODE = 102;

    protected static final String TEXT_TO_SPEECH_RESTART_VOICE_RECORD = "restart_voice";
    protected static final String NOT_CHANGE_SESSION_ID = "notchangesessionid";
    protected static final String NO_DATA_FOUND = "Hiện không tìm thấy thông tin";
    protected static final String KEY_WEATHER = "thời tiết";
    protected static final String KEY_SEARCH = "tìm";
    protected static final String NO_DATA_FOUND_TRY_AGAIN = "Không tìm thấy kết quả bạn mong muốn! Vui lòng thử lại";
    protected static final String NAVIGATE_TO = "Di chuyển tới ";
    protected static final String SELECT_YOUR_PLACE = "Vui lòng chọn nơi bạn muốn đến";
    protected static final String CAN_NOT_FIND_PLACE = "Không tìm thấy điểm tới";
    protected static final String DELETE_DATA_SUCCESS = "Xóa dữ liệu thành công";
    protected static final String FIND_WAY_TO = "Tìm đường đi đến ";
    protected static final String KEY_OPEN_YOUTUBE = "Mở youtube";
    protected static final String KEY_NAVIGATION = "Chỉ đường";
    protected static final String KEY_MAINTAIN_SCHEDULE = "Lịch sử sửa chữa";
    protected static final String KEY_GASOLINE_HISTORY = "Lịch sử đổ xăng";
    protected static final String AI_CONFIG_ACCESS_TOKEN = BuildConfig.AI_CONFIG_ACCESS_TOKEN;
    /**
     * Open un_know place
     *
     * @Search_unknow
     **/
    protected static final String OPEN_BANK_PLACE_UN_KNOW_SPEECH = "OpenBankPlaceUnknownSpeech";
    protected static final String OPEN_DRINK_PLACE_UN_KNOW_SPEECH = "OpenDrinkPlaceUnknownSpeech";
    protected static final String OPEN_EAT_PLACE_UN_KNOW_SPEECH = "OpenEatPlaceUnknownSpeech";
    protected static final String OPEN_BANK_PLACE_WHATEVER = "OpenBankPlaceWhatever";
    protected static final String OPEN_MAP_TO_SPEECH = "OpenMapToSpeech";
    /**
     * search a place
     *
     * @Search_place
     **/
    protected static final String OPEN_BANK_PLACE = "OpenBankPlace";
    protected static final String OPEN_BANK_PLACE_UN_KNOW = "OpenBankPlaceUnknown";
    protected static final String OPEN_DRINK_PLACE = "OpenDrinkPlace";
    protected static final String OPEN_DRINK_PLACE_UN_KNOW = "OpenDrinkPlaceUnknown";
    protected static final String OPEN_EAT_PLACE = "OpenEatPlace";
    protected static final String OPEN_EAT_PLACE_UN_KNOW = "OpenEatPlaceUnknown";
    protected static final String OPEN_EAT_PLACE_WHATEVER = "OpenEatPlaceWhatever";
    protected static final String OPEN_PLACE = "OpenPlace";
    protected static final String OPEN_MAP_TO = "OpenMapTo";
    protected ConfirmDialog noNetworkDialog;
    /**
     * Search: input_un_know
     **/
    protected static final String INPUT_UN_KNOW = "input.unknown";
    /**
     * Search: Audio
     */
    protected static final String OPEN_MP3 = "Mp3";

    /**
     * Search: Video
     */
    protected static final String OPEN_YOU_TUBE = "Youtube";

    /**
     * Call from contact
     **/
    protected static final String ACTION_CALL_FROM_CONTACT = "CallFromContact";

    /**
     * Open VTV
     **/
    protected static final String ACTION_OPEN_VTV = "PlayVTV";

    /**
     * Kiểm tra thông tin phạt nguội
     **/
    protected static final String ACTION_CHECK_CAR_VIOLATIONS = "TRACUUPHATNGUOI";

    /**
     * Open App
     **/
    protected static final String ACTION_OPEN_APP = "OpenApp";

    protected String[] permissions = {Manifest.permission.INTERNET,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.VIBRATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.SET_ALARM,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    protected ViewModel viewModel;
    protected ActivityMainBinding binding;
    protected AlertDialog progressDialog;
    protected DialogActiveControl dialogActiveControl;
    protected List<AIOutputContext> contexts;
    public TextToSpeechManager textToSpeech;
    protected String deviceId;
    public SpeechRecognizer speechRecognizer;
    public SpeechRecognizerManager speechRecognizerManager;
    protected ProgressDialog downloadDialog;
    protected OpenWeatherMapHelper weather;
    protected AIService aiService;
    protected boolean notchangesessionid = false;
    protected String currentSessionId;
    protected long processTime = 0;
    public static boolean isExcecuteText = false;
    protected Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    protected Boolean isStartRecognizer;
    public DownLoadBroadCast downLoadBroadCast;
    private TriggerBroadCast triggerBroadCast;
    private OpenNavigationReceiver openNavigationReceiver;

    public SpeechRecognizerManager getSpeechRecognizerManager() {
        return speechRecognizerManager;
    }

    public TextToSpeechManager getTextToSpeech() {
        return textToSpeech;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAIMasterService();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this, new ViewModelFactory(this)).get(ViewModel.class);
        LogUtil.log("Test");
        if (checkPermission()) {
            initIfPermissionGranted();
        } else {
            requestPermission();
        }
    }

    @Override
    public void onProviderDisabled(@org.jetbrains.annotations.Nullable String s) {
        super.onProviderDisabled(s);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void initIfPermissionGranted() {
        startHomeFragment();
        initView();
        initAction();
        initBaseAction();
    }

    protected abstract void initView();

    protected abstract void initAction();

    private void initBaseAction() {
        sendCarInfo();
        initSpeechRecognizerManager();
        viewModel.getLiveDataUpdateResponse().observe(this, this::updateApp);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        getLastLocation();
        requestLocationUpdate();
        initTextToSpeech();
    }

    protected void initAIMasterService() {
        final AIConfiguration config = new AIConfiguration(AI_CONFIG_ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
    }

    protected AIService getChildAIService(String config) {
        final AIConfiguration clientConfig = new AIConfiguration(config,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        return AIService.getService(this, clientConfig);
    }

    protected void initProgressDialog() {
        if (downloadDialog == null) {
            downloadDialog = new ProgressDialog(this);
            downloadDialog.setMax(100);
            downloadDialog.setMessage("Downloading...");
            downloadDialog.setCancelable(false);
            downloadDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        downloadDialog.show();
//        BuildConfig.DEBUG
    }

    protected DefaultAssistantAdapter getAssistantAdapter(int numItems, final DrawerLayout mDrawerLayout) {
        return new DefaultAssistantAdapter((text, position) -> {
            try {
                contexts = null;
                String textSpeech = null;
                switch (position) {
                    case 0:
                        textSpeech = KEY_OPEN_YOUTUBE;
                        break;
                    case 1:
                        textSpeech = KEY_NAVIGATION;
                        break;
                    case 2:
                        textSpeech = KEY_GASOLINE_HISTORY;
                        break;
                    case 3:
                        if (mDrawerLayout != null) {
                            mDrawerLayout.closeDrawer(Gravity.LEFT);
                        }
                        viewModel.stopRecord();
                        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentSettings.TAG);
                        if (fragment == null) {
                            getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.grow_from_bottom, R.anim.shrink_from_top, R.anim.grow_from_bottom, R.anim.shrink_from_top)
                                    .replace(R.id.fragment_container, new FragmentSettings(), FragmentSettings.TAG)
                                    .addToBackStack("1")
                                    .commit();
                        }
                        break;
                    default:
                        break;
                }
                if (textSpeech != null) {
                    viewModel.stopRecord();
                    sendMessage(textSpeech, true);
                    processing_text(textSpeech, true);
                    startResultFragment();
                    if (mDrawerLayout != null) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                }
            } catch (Exception e) {
                Timber.e(e);
            }
        }, numItems);
    }

    /**
     * Compare current time ti previous time to reset context
     **/
    protected Boolean calculateResetContext(long previousProcessTime, long currentProcessTime) {
        if (previousProcessTime == 0) return false;
        if (previousProcessTime == -1) return true;
        long distance = currentProcessTime - previousProcessTime;
        return TimeUnit.MILLISECONDS.toMinutes(distance) > 1;
    }

    /**
     * Check permission
     */

    protected void requestPermission() {
        List<String> remainingPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    remainingPermissions.add(permission);
                }
            }
        }
        if (remainingPermissions.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(remainingPermissions.toArray(new String[remainingPermissions.size()]), REQUEST_PERMISSION_CODE);
            }
        }
    }

    protected boolean checkPermission() {
        for (String permission : permissions) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }

        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (checkPermission()) {
                initIfPermissionGranted();
            } else {
                requestPermission();
            }
        }
    }

    protected void initTextToSpeech() {
        textToSpeech = TextToSpeechManager.getInstance(this);
        textToSpeech.setTextToSpeechListener(this);
    }

    protected void initSpeechRecognizerManager() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        speechRecognizerManager = SpeechRecognizerManager.getInstance(this, speechRecognizer, new OnResultReady() {
            @Override
            public void onResults(@NotNull ArrayList<String> results) {
                if (isExcecuteText) {
                    return;
                }
                speechRecognizerManager.muteVolume(false);
                finishRecognition();
//                speechRecognizerManager.stopListening();
                String text = results.get(0);
                isExcecuteText = true;
                sendMessage(text, true);
                Log.e("onResults", text);
                processing_text(text, false);
            }

            @Override
            public void onStreamResult(@NotNull ArrayList<String> partialResults) {
                for (String st : partialResults) {
                    Timber.e(st);
                }
            }
        }, new SpeechRecognizerManager.SpeechRecognizerManagerCallBack() {
            @Override
            public void onNoNetWork() {
                viewModel.stopRecord();
                textToSpeech.speak(getString(R.string.alert_no_internet_connection_message), false);
            }

            @Override
            public void onErrorTimeOut() {
                try {
                    FragmentResult fragmentResult = (FragmentResult) getSupportFragmentManager().findFragmentByTag(FragmentResult.class.getName());
                    fragmentResult.notifySpeechTimeOut();
                } catch (Exception e) {
                    Timber.e(e);
                }
            }

            @Override
            public void onRebindToSpeechRecognitionView() {
                viewModel.getLiveDataRebindRecognitionsView().postValue(true);
            }
        });
    }

    public void finishRecognition() {
        isStartRecognizer = false;
        FragmentResult.isPlayingRecognition = false;
        if (speechRecognizerManager != null) {
            speechRecognizerManager.stopListening();
        }
    }

    @SuppressLint({"LongLogTag", "LogNotTimber"})
    public void processing_text(final String text, Boolean resetContext) {
        Log.e("=================processing_text", text);
        Thread thread = new Thread(() -> {
            try {
                AIRequest aiRequest = new AIRequest(text);
                if (!notchangesessionid || currentSessionId == null) {
                    currentSessionId = deviceId + "#" + getLatitude() + "-" + getLongitude();
                }
                aiRequest.setSessionId(currentSessionId);
                long currentProcessTime = System.currentTimeMillis();
                if (resetContext || calculateResetContext(processTime, currentProcessTime) || contexts == null) {
                    Timber.e("============Reset context=============");
                    contexts = null;
                    aiRequest.setResetContexts(true);
                    aiRequest.setContexts(null);
                }
                processTime = currentProcessTime;
                if (contexts != null) {
                    List<AIContext> rqContexts = new ArrayList<>();
                    for (AIOutputContext oc : contexts) {
                        rqContexts.add(new MyAIContext(oc));
                    }
                    aiRequest.setContexts(rqContexts);
                }
                Log.e("===== aiRequest:%s", gson.toJson(aiRequest));
                try {
                    AIResponse aiRes;
                    aiRes = aiService.textRequest(aiRequest);
                    Log.e("Response", gson.toJson(aiRes));
                    String action = aiRes.getResult().getAction();
                    Log.e("===== action:%s", action);
                    contexts = aiRes.getResult().getContexts();
                    String code = null;
                    try {
                        notchangesessionid = aiRes.getResult().getFulfillment().getData().get(NOT_CHANGE_SESSION_ID).getAsBoolean();
                        code = aiRes.getResult().getFulfillment().getData().get("code").toString().replace("\"", "");
                        Log.e("Sesions id", "===== " + NOT_CHANGE_SESSION_ID + ": " + notchangesessionid);
                    } catch (Exception e) {
                        Log.e(LOG_TAG, e.getMessage(), e);
                    }
                    Log.e(LOG_TAG, aiRes.getResult().toString());
                    switch (action) {
                        case OPEN_BANK_PLACE_UN_KNOW_SPEECH:
                        case OPEN_DRINK_PLACE_UN_KNOW_SPEECH:
                        case OPEN_EAT_PLACE_UN_KNOW_SPEECH:
                        case OPEN_MAP_TO_SPEECH:
                        case OPEN_BANK_PLACE_WHATEVER:
                            searchPlaceUnknown(text, aiRes);
                            break;
                        case OPEN_BANK_PLACE:
                        case OPEN_DRINK_PLACE:
                        case OPEN_DRINK_PLACE_UN_KNOW:
                        case OPEN_EAT_PLACE:
                        case OPEN_EAT_PLACE_UN_KNOW:
                        case OPEN_EAT_PLACE_WHATEVER:
                        case OPEN_BANK_PLACE_UN_KNOW:
                        case OPEN_PLACE:
                        case OPEN_MAP_TO:
                            viewModel.searchPlaces(action, aiRes, this);
                            break;
                        case ACTION_OPEN_VTV:
                            viewModel.openVtv(aiRes, code, this);
                            break;
                        case ACTION_CHECK_CAR_VIOLATIONS:
                            viewModel.checkCarViolations(aiRes, code, this);
                            break;
                        case ACTION_OPEN_APP:
                            viewModel.openApp(aiRes, code, this);
                            break;
                        case INPUT_UN_KNOW:
                            searchInputUnknown(aiRes);
                            break;
                        case OPEN_MP3:
                            viewModel.searchMp3(code, aiRes, this);
                            break;
                        case OPEN_YOU_TUBE:
                            viewModel.searchYoutube(aiRes, code, this);
                            break;
                        case ACTION_CALL_FROM_CONTACT:
                            viewModel.callTo(this, aiRes, code, this);
                            break;
                        default:
                            if (text.toLowerCase().contains(KEY_WEATHER)) {
                                weather();
                            } else if (text.toLowerCase().contains(KEY_SEARCH)) {
                                viewModel.search(text, this);
                            } else {
                                sendMessage(NO_DATA_FOUND, false);
                                speak(NO_DATA_FOUND, false);
                            }
                    }


                } catch (AIServiceException e) {
                    Timber.e(e);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isExcecuteText = false;
            }
        });

        thread.start();
    }

    protected void resetContext() {
        contexts = null;
    }

    protected void weather() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please waiting....");
        progressDialog.show();

        weather.getCurrentWeatherByGeoCoordinates(getLatitude(), getLongitude(), new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {

                progressDialog.dismiss();

                Date timeSunrise = new Date(currentWeather.getSys().getSunrise() * 1000);
                Date timeSunset = new Date(currentWeather.getSys().getSunset() * 1000);

                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                String sunrise = dateFormat.format(timeSunrise);
                String sunset = dateFormat.format(timeSunset);

                Timber.tag("Weather").v("Coordinates: " + currentWeather.getCoord().getLat() + ", " + currentWeather.getCoord().getLon() + "\n"
                        + "Weather Description: " + currentWeather.getWeather().get(0).getDescription() + "\n"
                        + "Temperature: " + currentWeather.getMain().getTempMax() + "\n"
                        + "Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                        + "City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry() + "\n"
                        + "Time sunrise: " + sunrise + "\n"
                        + "Time sunset: " + sunset + "\n");

                String location = currentWeather.getName() + ", " + currentWeather.getSys().getCountry();
                String description = currentWeather.getWeather().get(0).getDescription();
                String wind = String.valueOf(currentWeather.getWind().getSpeed());
                String tempMax = String.valueOf(currentWeather.getMain().getTempMax());
                String humidity = String.valueOf(currentWeather.getMain().getHumidity());

                show_weather(location, description, tempMax, wind, humidity, sunrise, sunset);
            }

            @Override
            public void onFailure(Throwable throwable) {
                progressDialog.dismiss();
                sendMessage("Lỗi, Không thế tìm kiếm thời tiết tại vị trí của bạn.", false);
                Timber.e(throwable);
            }
        });

    }


    /**
     * Show dialog weather in location
     *
     * @param location
     * @param description
     * @param tempMax
     * @param wind
     * @param humidity
     * @param sunrise
     * @param sunset
     */
    protected void show_weather(String location, String description, String tempMax, String
            wind, String humidity, String sunrise, String sunset) {

        String w = "Thời tiết " + location + " : " + description + " " + tempMax + "\u2103";

        sendMessage(w, false);

        String textSpeech = "Thời tiết " + location + " : " + description + " " + tempMax + "độ xê";
        textToSpeech.speak(textSpeech, false);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.weather);

        dialog.setContentView(R.layout.weather);

        TextView tvLocation, tvDescription, tvTempMax, tvWind, tvHumidity, tvSunrise, tvSunset;
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvDescription = dialog.findViewById(R.id.tvDescription);
        tvTempMax = dialog.findViewById(R.id.tvTempMax);
        tvWind = dialog.findViewById(R.id.tvWind);
        tvHumidity = dialog.findViewById(R.id.tvHumidity);
        tvSunrise = dialog.findViewById(R.id.tvSunrise);
        tvSunset = dialog.findViewById(R.id.tvSunset);

        tvLocation.setText(location);
        tvDescription.setText(description);
        tvTempMax.setText(tempMax);
        tvWind.setText(wind);
        tvHumidity.setText(humidity);
        tvSunrise.setText(sunrise);
        tvSunset.setText(sunset);

        dialog.show();
    }

    public void sendMessage(String text, boolean isUser) {
        viewModel.getLiveDataProcessText().postValue(new Message(text, isUser, Calendar.getInstance().getTimeInMillis()));
    }

    public void speak(String key, boolean shouldRestartRecord) {
        textToSpeech.speak(key, shouldRestartRecord);
    }

    public void startHomeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentHome.class.getName());
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome(), FragmentHome.class.getName())
                    .commit();
        } else {
            int index = getSupportFragmentManager().getFragments().indexOf(fragment);
            for (int i = getSupportFragmentManager().getFragments().size() - 1; i > index; i--) {
                getSupportFragmentManager().popBackStack();
            }
        }

    }

    public void startResultFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FragmentResult.class.getName());
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(FragmentResult.class.getName())
                    .setCustomAnimations(R.anim.grow_from_bottom, R.anim.shrink_from_top, R.anim.grow_from_bottom, R.anim.shrink_from_top)
                    .add(R.id.fragment_container, new FragmentResult(), FragmentResult.class.getName())
                    .commit();
        } else {
            int index = getSupportFragmentManager().getFragments().indexOf(fragment);
            for (int i = getSupportFragmentManager().getFragments().size() - 1; i > index; i--) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    public void startWebViewFragment(String url) {
        Fragment fragment = WebViewFragment.startThis(url);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.grow_from_bottom, R.anim.shrink_from_top, R.anim.grow_from_bottom, R.anim.shrink_from_top)
                .add(R.id.fragment_container, fragment, WebViewFragment.class.getName())
                .commit();
    }

    @SuppressLint("HardwareIds")
    protected void sendCarInfo() {
        deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        // Get phone's location
        if (callDeviceInfoTimes < 1 || !App.isActivated) {
            viewModel.sendCarInfo(deviceId, NavigationUtil.getImei(this));
        }
    }

    public void updateApp(CarResponse carResponse) {
        if (carResponse == null) {
            if (noNetworkDialog == null) {
                noNetworkDialog = new ConfirmDialog.Builder(this, false)
                        .setOnDismissListener(dialog -> {
                            sendCarInfo();
                        })
                        .title(getString(R.string.alert_no_internet_connection_title))
                        .message(getString(R.string.alert_no_internet_connection_message))
                        .build();
            }
            noNetworkDialog.show();
            return;
        }
        callDeviceInfoTimes++;
        CarResponse.UpdateVersion updateVersion = carResponse.getVersion();
        boolean forceUpdate = updateVersion.getForced() == 1;
        boolean update = updateVersion.getUpdate() == 1;

        if (update) {
            UpdateChecker.checkForDialog(this, updateVersion.getUrl(), forceUpdate, updateVersion.getDescription());
            return;
        }
        App.isActivated = carResponse.isActivatedApp();
        if (App.isForTest) {
            App.isActivated = true;
        }
        if (!App.isActivated) {
            activeApp();
        } else {
            startResultFragment();
            startRecordWhileGpsEnabled();
        }
    }

    public void activeApp() {
        dialogActiveControl = new DialogActiveControl(this, new DialogActiveControl.OnActiveListener() {
            @Override
            public void onAccept(String phone, String activeCode) {
                if (progressDialog == null) {
                    progressDialog = DialogUtils.showProgressDialog(BaseMainActivity.this, false);
                } else {
                    progressDialog.show();
                    dialogActiveControl.dismiss();
                }
                viewModel.activeDevice(new ActiveCode(phone, activeCode,
                                NavigationUtil.getImei(BaseMainActivity.this),
                                NavigationUtil.getDeviceId(BaseMainActivity.this)),
                        BaseMainActivity.this);
            }

            @Override
            public void onFail() {

            }
        });
        dialogActiveControl.show();
    }

    protected void searchPlaceUnknown(String text, AIResponse aiRes) {
        String textSpeech = aiRes.getResult().getFulfillment().getSpeech();
        sendMessage(textSpeech, false);
        speak(textSpeech, true);
    }

    protected void searchInputUnknown(AIResponse aiRes) {
        String textSpeech = aiRes.getResult().getFulfillment().getSpeech();
        Timber.d("===== textSpeech:%s", textSpeech);
        sendMessage(textSpeech, false);
        speak(textSpeech, true);
        resetContext();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }


    @Override
    public void onRestartVoice() {
        viewModel.startRecord();

    }

    @Override
    public void onStopVoiceRecord() {
        viewModel.stopRecord();

    }

    @Override
    public void onUpdateMessageAfterProcessText(android.os.Message msg) {
        ArrayList<Poi> poiArrayList = (ArrayList<Poi>) msg.obj;
        viewModel.getLiveListPoi().postValue(poiArrayList);
    }

    @Override
    public void onGetVtvLinkSuccess(int chanel) {
        sendMessage(getString(R.string.open_vtv_success_title) + chanel + "...", false);
        startResultFragment();
        resetContext();
    }

    @Override
    public void onNoVtvLinkFound(int chanel) {
        String text = getString(R.string.open_vtv_can_not_find_link_title);
        sendMessage(text + chanel, false);
        speak(text + chanel, false);
    }

    @Override
    public void onGetVtvLinkError() {
        speak(NO_DATA_FOUND, false);
        sendMessage(NO_DATA_FOUND, false);
    }

    @Override
    public void onCallSuccess() {
        resetContext();
        Log.e(LOG_TAG, "Call success");
    }

    @Override
    public void onCallError(Exception ex) {
        String message = getString(R.string.find_contact_fail_title);
        sendMessage(message, false);
        speak(message, false);
    }

    @Override
    public void onSearchMp3Success(Audio audio) {
        sendMessage(audio.getAlias(), false);
        resetContext();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(audio.getLink()));
        intent.setPackage("com.zing.mp3");
        if (intent.resolveActivity(getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(audio.getLink()));
        }
        startActivity(intent);
    }

    @Override
    public void onRequestSongNameAgain(String textSpeech) {
        sendMessage(textSpeech, false);
        speak(textSpeech, true);
    }

    @Override
    public void onSearchMp3Error(String textSpeech) {
        sendMessage(textSpeech, false);
        speak(textSpeech, true);
    }


    @Override
    public void onActiveAppSuccess(ActiveResponse activeCode) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            String code = activeCode.getCode();
            if (code == null) {
                code = "";
            }
            if (code.toLowerCase().endsWith("0")) {
                onActiveAppError();
            } else {
                VnestSharePreference.getInstance(BaseMainActivity.this).saveActiveCode(code + "Vnest");
                App.isActivated = true;
                dialogActiveControl.dismiss();
                startResultFragment();
                if (isLocationEnabled()) {
                    viewModel.startRecord();
                }
            }
        }
    }

    @Override
    public void onActiveAppError() {
        if (progressDialog != null) {
            dialogActiveControl.dismiss();
            progressDialog.dismiss();
            new ConfirmDialog.Builder(BaseMainActivity.this, true)
                    .message(getString(R.string.active_code_fail))
                    .setOnAllowClick(DialogInterface::dismiss)
                    .setOnDismissListener(dialog -> dialogActiveControl.show())
                    .show();
        }
    }

    @Override
    public void onRequestSearchYoutube(String textSpeech) {
        sendMessage(textSpeech, false);
        speak(textSpeech, true);
    }

    @Override
    public void onSearchYoutubeSuccess(Youtube video) {
        NavigationUtil.openYoutube(this, video.getHref());
        sendMessage(video.getTitle(), false);
        resetContext();
    }

    @Override
    public void onNoYoutubeDataFound() {
        sendMessage(NO_DATA_FOUND, false);
        speak(NO_DATA_FOUND, false);
        resetContext();
    }

    @Override
    public void onSearchPlacesNoDataFoundTryAgain() {
        sendMessage(NO_DATA_FOUND_TRY_AGAIN, false);
        speak(NO_DATA_FOUND_TRY_AGAIN, false);
        resetContext();
    }

    @Override
    public void onSelectPlacesToNavigate(ArrayList<Poi> poiArrayList) {
        sendMessage(SELECT_YOUR_PLACE, false);
        speak(SELECT_YOUR_PLACE, false);
        contexts = null;
        ItemNavigationAdapter adapter = new ItemNavigationAdapter(poi -> NavigationUtil.navigationToPoint(poi, BaseMainActivity.this));
        adapter.setData(poiArrayList);
        android.os.Message message = TextToSpeechManager.mHandler.obtainMessage(TextToSpeechManager.UPDATE_AFTER_PROCESS_TEXT);
        message.obj = poiArrayList;
        message.sendToTarget();
        resetContext();
    }

    @Override
    public void onOpenMapToPoi(Poi poi) {
        sendMessage(NAVIGATE_TO + poi.getTitle() + "...", false);
        NavigationUtil.navigationToPoint(poi, BaseMainActivity.this);
        resetContext();
    }

    @Override
    public void onSearchPlacesNoDataFound() {
        sendMessage(CAN_NOT_FIND_PLACE, false);
        speak(CAN_NOT_FIND_PLACE, false);
    }

    @Override
    public void onSearchRoadFromToSuccess(String location) {
        NavigationUtil.navigationToLocation(location, this);
        sendMessage(FIND_WAY_TO + location, false);
        resetContext();
    }

    @Override
    public void onSearchNearestSuccess(String location) {
        sendMessage("Tìm " + location + "gần nhất", false);
        NavigationUtil.navigationToLocation(location, this);
        resetContext();
    }

    @Override
    public void onGoogleSearch(String keySearch) {
        sendMessage("Search: " + keySearch, false);
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, keySearch);
        startActivity(intent);
        resetContext();
    }

    @Override
    public void onCheckCarViolationsSuccess(String url) {
        String text = getString(R.string.check_car_ciolation_enter_capcha_title);
        sendMessage(text, false);
        speak(text, false);
        startWebViewFragment(url);
    }

    @Override
    public void onCheckCarViolationsFail() {
        String text = getString(R.string.check_car_ciolation_fail);
        sendMessage(text, false);
        speak(text, false);
    }

    @Override
    public void onAskCarLicensePlates(String speech) {
        sendMessage(speech, false);
        speak(speech, true);
    }

    @Override
    public void onReceivedAppId(String appId) {
        AppUtils.launchApp(appId);
    }

    @Override
    public void onOpenAppError(Exception ex) {
        LogUtil.log(ex);
    }

    @Override
    public void onAppNotInstalled() {
        String text = getString(R.string.alert_app_not_installed);
        sendMessage(text,false);
        speak(text,false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Timber.d("stop TRIGGER");
        registerTriggerBroadCast();
        registerDownloadBroadCast();
        registerOpenNavigationBroadCast();
        if (checkPermission()) {
            if (NavigationUtil.checkInternetConnection(this) && App.isActivated) {
                startResultFragment();
                startRecordWhileGpsEnabled();
            }
        }
    }

    protected void startRecordWhileGpsEnabled() {
        if (isLocationEnabled()) {
            viewModel.startRecord();
            dismissMessageNoGps();
        } else {
            viewModel.stopRecord();
            buildAlertMessageNoGps();
        }
    }

    public void registerTriggerBroadCast() {
        if (triggerBroadCast == null) {
            triggerBroadCast = TriggerBroadCast.initBroadCast(this, new TriggerBroadCast.OnHandleTrigger() {
                @Override
                public void onActionTurnOn() {
                    if (App.isActivated) {
                        startResultFragment();
                        viewModel.getLiveDataOpenVTV().postValue("-1");
                        viewModel.startRecord();
                    }
                }

                @Override
                public void onActionTurnOff() {

                }

                @Override
                public void onActionStartApp() {

                }

                @Override
                public void onNetWorkAvailable() {
                    Timber.e("available");
                    if (noNetworkDialog != null) {
                        noNetworkDialog.dismiss();
                    }
                }
            }, MainActivity.class);
        }
    }

    public void registerDownloadBroadCast() {
        if (downLoadBroadCast == null) {
            ProgressDialog waitingDialog = DialogUtils.showProgressDialog(this);
            downLoadBroadCast = DownLoadBroadCast.initBroadCast(this, new DownLoadBroadCast.OnReceive() {
                @Override
                public void onReceiveProgress(int progress) {
                    waitingDialog.dismiss();
                    initProgressDialog();
                    downloadDialog.setMessage("Downloading... ");
                    downloadDialog.setProgress(progress);
                    downloadDialog.show();
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onWaiting() {
                    waitingDialog.show();
                }

                @Override
                public void onRetry() {
                    updateApp(viewModel.carInfoResponse);
                }
            });
        }
    }

    public void registerOpenNavigationBroadCast() {
        if (openNavigationReceiver == null) {
            openNavigationReceiver = OpenNavigationReceiver.startThis(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
            }
        };
    }

    /**
     * Stop the recognizer.
     * Since cancel() does trigger an onResult() call,
     * we cancel the recognizer rather then stopping it.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Timber.d("start TRIGGER");
        resetContext();
        if (checkPermission()) {
            finishRecognition();
            if (speechRecognizer != null) {
                speechRecognizer.stopListening();
            }
            try {
                FragmentResult fragmentResult = (FragmentResult) getSupportFragmentManager().findFragmentByTag(FragmentResult.class.getName());
                fragmentResult.stopSpeechTimeCount();
            } catch (Exception e) {
                Timber.e(e);
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.shutDown();
            textToSpeech = null;
        }
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
        //Start service
        if (downLoadBroadCast != null) {
            unregisterReceiver(downLoadBroadCast);
        }
        TriggerBroadCast.unregisterBroadCast(this, triggerBroadCast);
        if (downloadDialog != null) {
            downloadDialog.dismiss();
        }
        if (dialogActiveControl != null) {
            dialogActiveControl.dismiss();
        }
        if (openNavigationReceiver != null) {
            unregisterReceiver(openNavigationReceiver);
        }
    }
}
