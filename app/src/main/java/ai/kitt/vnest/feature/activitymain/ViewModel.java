package ai.kitt.vnest.feature.activitymain;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.AppUtils;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import ai.api.model.AIResponse;
import ai.kitt.vnest.base.BaseViewModel;
import ai.kitt.vnest.basedata.api.model.ActiveCode;
import ai.kitt.vnest.basedata.api.model.CarInfo;
import ai.kitt.vnest.basedata.api.model.CarResponse;
import ai.kitt.vnest.basedata.api.model.UpdateInfo;
import ai.kitt.vnest.basedata.api.repository.ActiveRepo;
import ai.kitt.vnest.basedata.api.repository.CarRepo;
import ai.kitt.vnest.basedata.database.VNestDB;
import ai.kitt.vnest.basedata.entity.Audio;
import ai.kitt.vnest.basedata.entity.Message;
import ai.kitt.vnest.basedata.entity.Poi;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

//import kun.kt.vtv.VtvFetchLinkStream;
import ai.kitt.vnest.basedata.entity.Youtube;
import ai.kitt.vnest.util.LogUtil;
import ai.kitt.vnest.util.PhoneUtils;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kun.kt.vtv.Stream;
import kun.kt.vtv.VtvFetchLinkStream;
import timber.log.Timber;

@SuppressLint("LogNotTimber")
public class ViewModel extends BaseViewModel {

    private static final String LOG_TAG = "Main view model";
    private static final String KEY_SEARCH = "tìm";

    public CarResponse carInfoResponse;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private CarRepo carRepo = new CarRepo();
    private ActiveRepo activeRepo = new ActiveRepo();

    private MutableLiveData<Boolean> liveDataStartRecord = new MutableLiveData<>();
    private MutableLiveData<Message> liveDataProcessText = new MutableLiveData<>();
    private MutableLiveData<CarResponse> liveDataUpdateResponse = new MutableLiveData<>();
    private MutableLiveData<List<Poi>> liveListPoi = new MutableLiveData<>();
    private MutableLiveData<List<Message>> listMessLiveData = new MutableLiveData<>();
    private MutableLiveData<String> liveDataOpenVTV = new MutableLiveData<>();
    private MutableLiveData<Boolean> liveDataRebindRecognitionsView = new MutableLiveData<>();


    public ViewModel(Context context) {
        this.context = context;
    }

    public MutableLiveData<List<Poi>> getLiveListPoi() {
        return liveListPoi;
    }

    public MutableLiveData<CarResponse> getLiveDataUpdateResponse() {
        return liveDataUpdateResponse;
    }

    public MutableLiveData<String> getLiveDataOpenVTV() {
        return liveDataOpenVTV;
    }

    public MutableLiveData<Boolean> getLiveDataRebindRecognitionsView() {
        return liveDataRebindRecognitionsView;
    }

    public LiveData<Boolean> getLiveDataStartRecord() {
        return liveDataStartRecord;
    }

    public MutableLiveData<List<Message>> getListMessLiveData() {
        return listMessLiveData;
    }

    public MutableLiveData<Message> getLiveDataProcessText() {
        return liveDataProcessText;
    }

    public void saveMessage(Message message) {
        new Thread(() -> {
            try {
                VNestDB.getInstances(context)
                        .messageDao()
                        .insert(message);
            } catch (Exception e) {
                LogUtil.log(e);
                ;
            }
        }).start();
    }

    public void deleteMessages(DeleteHistoryListener listener) {
        addDisposable(Completable.fromAction(VNestDB.getInstances(context)
                .messageDao()::delete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.single())
                .subscribe(listener::onSuccess));
    }

    public void startRecord() {
        liveDataStartRecord.postValue(true);
    }

    public void stopRecord() {
        liveDataStartRecord.postValue(false);
    }

    public void resetRecordState() {
        liveDataStartRecord.postValue(null);
    }

    public void getMessage() {
        new Thread(() -> {
            List<Message> listMessage = VNestDB.getInstances(context)
                    .messageDao()
                    .getAll();
            if (listMessage != null && !listMessage.isEmpty()) {
                listMessLiveData.postValue(listMessage);
            }
        }).start();
    }

    public void sendCarInfo(String deviceId, String imei) {
        carRepo.sendCarInfo(CarInfo.getDefault(deviceId, imei), new CarRepo.OnResponseListener() {
            @Override
            public void onResponse(CarResponse carResponse) {
                carInfoResponse = carResponse;
                liveDataUpdateResponse.postValue(carResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                if (throwable instanceof UnknownHostException || throwable instanceof TimeoutException || throwable instanceof ConnectException) {
                    liveDataUpdateResponse.postValue(null);
                }
            }
        });
    }

    public void sendCarBks(String deviceId, String bks) {
        carRepo.sendBks(new UpdateInfo(deviceId, bks));
    }

    public void activeDevice(ActiveCode activeCode, ActiveRepo.ActiveListener activeListener) {
        activeRepo.activeDevice(activeCode, activeListener);
    }


    public void getVtvLink(int channel, VtvFetchLinkStream.OnSuccessListener onSuccessListener) {
        new VtvFetchLinkStream(channel, onSuccessListener).execute();
    }

    public void openVtv(AIResponse response, String code, OnOpenVtvListener onOpenVtvListener) {
        if (code.endsWith("0")) {
            Timber.e(code);
        } else {
            Timber.e(new Gson().toJson(response.getResult().getFulfillment().getData()));
            try {
                JsonElement element = response.getResult().getFulfillment().getData().get("channel");
                int channel = element.getAsInt();
                getVtvLink(channel, new VtvFetchLinkStream.OnSuccessListener() {
                    @Override
                    public void onGetSuccess(Stream stream) {
                        getLiveDataOpenVTV().postValue(stream.getLink());
                        onOpenVtvListener.onGetVtvLinkSuccess(channel);
                    }

                    @Override
                    public void onGetError() {
                        onOpenVtvListener.onNoVtvLinkFound(channel);
                    }
                });
            } catch (Exception ex) {
                onOpenVtvListener.onGetVtvLinkError();
            }

        }
    }

    public void callTo(Context context, AIResponse aiResponse, String code, OnCallListener onCallListener) {
        if (code.endsWith("0")) {

        } else {
            Timber.e(new Gson().toJson(aiResponse.getResult().getFulfillment().getData()));
            JsonElement element = aiResponse.getResult().getFulfillment().getData().get("who");
            String name = element.getAsString();
            PhoneUtils.callToContact(context, name, new PhoneUtils.OnCallListener() {
                @Override
                public void onSuccess() {
                    onCallListener.onCallSuccess();
                }

                @Override
                public void onError(Exception ex) {
                    if (ex instanceof NullPointerException) {
                        onCallListener.onCallError(ex);
                    }
                    LogUtil.log(ex);
                    ;
                }
            });
        }
    }

    public void searchMp3(String code, AIResponse aiRes, OnSearchMp3Listener onSearchMp3Listener) {
        final String KEY_JSON = "audios";
        String textSpeech;
        if (code != null) {
            Timber.e("======= code:%s", code);
            if (code.equals("1")) {
                /**
                 * @HadData
                 * Go to zing mp3
                 * **/
                Gson gson = new Gson();
                Audio audio = gson.fromJson(aiRes.getResult().getFulfillment().getData().get(KEY_JSON).getAsJsonArray().get(0).toString(), Audio.class);
                onSearchMp3Listener.onSearchMp3Success(audio);

            } else {
                /**
                 * @ListenAgainSongName
                 * **/
                textSpeech = aiRes.getResult().getFulfillment().getSpeech();
                onSearchMp3Listener.onRequestSongNameAgain(textSpeech);
                Timber.e("===== textSpeech:%s", textSpeech);
            }
        } else {
            textSpeech = aiRes.getResult().getFulfillment().getSpeech();
            onSearchMp3Listener.onSearchMp3Error(textSpeech);
            Timber.e("===== textSpeech:%s", textSpeech);

        }
    }

    public void searchYoutube(AIResponse aiRes, String code, OnSearchYoutubeListener onSearchYoutubeListener) {
        final String KEY_JSON = "videos";
        if (code.trim().equals("0")) {
            String speech = aiRes.getResult().getFulfillment().getSpeech();
            onSearchYoutubeListener.onRequestSearchYoutube(speech);
        } else {
            try {
                Youtube video = new Gson().fromJson(aiRes.getResult().getFulfillment().getData().get(KEY_JSON).getAsJsonArray().get(0).toString(), Youtube.class);
                onSearchYoutubeListener.onSearchYoutubeSuccess(video);
            } catch (IndexOutOfBoundsException ex) {
                onSearchYoutubeListener.onNoYoutubeDataFound();
            }

        }
    }

    public void searchPlaces(String key, AIResponse aiResponse, OnSearchPlacesListener onSearchPlacesListener) {
        try {
            JsonArray dataResponse = aiResponse.getResult().getFulfillment().getData().get("pois").getAsJsonArray();
            if (dataResponse.isJsonNull() || dataResponse.size() < 1) {
                onSearchPlacesListener.onSearchPlacesNoDataFoundTryAgain();
            } else if (dataResponse.size() >= 1) {
                Gson gson = new Gson();
                final ArrayList<Poi> poiArrayList = new ArrayList<>();
                for (JsonElement element : dataResponse) {
                    poiArrayList.add(gson.fromJson(element, Poi.class));
                }
                if (key.toLowerCase().equals("openmapto")) {
                    try {
                        onSearchPlacesListener.onOpenMapToPoi(poiArrayList.get(0));
                    } catch (Exception e) {
                        onSearchPlacesListener.onSearchPlacesNoDataFound();
                        LogUtil.log(e);
                        Log.e("Error", e.getMessage(), e);

                    }
                } else {
                    onSearchPlacesListener.onSelectPlacesToNavigate(poiArrayList);
                }


            } else {
                Poi poi = new Gson().fromJson(dataResponse.get(0), Poi.class);
                onSearchPlacesListener.onOpenMapToPoi(poi);
            }
        } catch (Exception e) {
            LogUtil.log(e);
            Log.e("Error", e.getMessage(), e);
        }

    }

    public void search(String text, OnOtherSearchListener onOtherSearchListener) {
        Timber.e("Key %s", text);
        if (text.contains("đường")) {
            String string_start = "đến";
            int start = text.indexOf(string_start) + string_start.length();
            int end = text.length();
            String location = text.substring(start, end).replace(" ", "+");
            Timber.e("Key %s", location);
            onOtherSearchListener.onSearchRoadFromToSuccess(location);


        } else if (text.contains("gần")) {
            String string_start = KEY_SEARCH;
            String string_end = "gần";
            int start = text.indexOf(string_start) + string_start.length();
            int end = text.indexOf(string_end);
            String location = text.substring(start, end);
            onOtherSearchListener.onSearchNearestSuccess(location);
        } else {
            String string_start = KEY_SEARCH;
            int start = text.indexOf(string_start) + string_start.length();
            int end = text.length();
            String key = text.substring(start, end);
            onOtherSearchListener.onGoogleSearch(key);
        }
    }

    public void checkCarViolations(AIResponse aiResponse, String code, CheckCarViolationsListener listener) {
        try {
            String message = aiResponse.getResult().getFulfillment().getSpeech();
            String bks = (aiResponse.getResult().getFulfillment().getData().get("bks") == null) ? "" : Objects.requireNonNull(aiResponse.getResult().getFulfillment().getData().get("bks")).getAsString();
            if (bks != null && !bks.trim().isEmpty()) {
                bks = bks.replaceAll(" ", "");
                listener.onCheckCarViolationsSuccess("http://www.csgt.vn/tra-cuu-phuong-tien-vi-pham.html?&LoaiXe=1&BienKiemSoat=" + bks);
                return;
            }
            if (message != null && !message.trim().isEmpty()) {
                listener.onAskCarLicensePlates(message);
            }
        } catch (Exception ex) {
            Log.e("Error", ex.getMessage(), ex);
            LogUtil.log(ex);
            listener.onCheckCarViolationsFail();
        }

    }
    public void openApp(AIResponse aiResponse, String code,OnOpenAppListener listener) {
        try {
            String appId = aiResponse.getResult().getParameters().get("AppId").getAsString();
            if(AppUtils.isAppInstalled(appId)) {
                listener.onReceivedAppId(appId);
            } else {
                listener.onAppNotInstalled();
            }
        }catch (Exception ex) {
            listener.onOpenAppError(ex);
        }
    }

    public interface OnOpenAppListener{
        void onReceivedAppId(String appId);
        void onOpenAppError(Exception ex);
        void onAppNotInstalled();
    }

    public interface OnCallListener {
        void onCallSuccess();

        void onCallError(Exception ex);
    }

    public interface OnOpenVtvListener {
        void onGetVtvLinkSuccess(int chanel);

        void onNoVtvLinkFound(int chanel);

        void onGetVtvLinkError();
    }

    public interface OnSearchMp3Listener {
        void onSearchMp3Success(Audio audio);

        void onRequestSongNameAgain(String textSpeech);

        void onSearchMp3Error(String textSpeech);
    }

    public interface OnSearchYoutubeListener {
        void onRequestSearchYoutube(String textSpeech);

        void onSearchYoutubeSuccess(Youtube video);

        void onNoYoutubeDataFound();
    }

    public interface OnSearchPlacesListener {
        void onSearchPlacesNoDataFoundTryAgain();

        void onSelectPlacesToNavigate(ArrayList<Poi> poiArrayList);

        void onOpenMapToPoi(Poi poi);

        void onSearchPlacesNoDataFound();
    }

    public interface OnOtherSearchListener {
        void onSearchRoadFromToSuccess(String location);

        void onSearchNearestSuccess(String location);

        void onGoogleSearch(String keySearch);
    }

    public interface CheckCarViolationsListener {
        void onCheckCarViolationsSuccess(String url);

        void onAskCarLicensePlates(String speech);

        void onCheckCarViolationsFail();
    }

    public interface DeleteHistoryListener {
        void onSuccess();
    }
}
