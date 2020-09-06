package ai.kitt.vnest.feature.screenspeech;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import ai.kitt.snowboy.service.TriggerOfflineService;
import ai.kitt.vnest.App;
import ai.kitt.vnest.R;
import ai.kitt.vnest.feature.activitymain.MainActivity;
import ai.kitt.vnest.feature.activitymain.ViewModel;
import ai.kitt.vnest.feature.screenspeech.adapters.AdapterAssistantMessage;
import ai.kitt.vnest.feature.screenspeech.model.ItemAssistant;
import ai.kitt.vnest.feature.screenspeech.model.ItemListResult;
import ai.kitt.vnest.feature.screenspeech.model.ResultItem;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentResult extends Fragment {
    private final static String LOG_TAG = "Vnest Fragment Result";
    public final static int SPEECH_TIME_OUT = 101;
    public final static int START_SPEECH_TIME_COUNT = 102;
    public final static int STOP_SPEECH_TIME_COUNT = 103;
    private static int MAX_SPEECH_TIME_OUT = 20;

    private int speechCountTime = MAX_SPEECH_TIME_OUT;
    private RecyclerView mListResult;
    private AdapterAssistantMessage adapter;
    private ViewModel viewModel;
    Toolbar toolbar;
    public Button btnVoice;
    public static Boolean isPlayingRecognition = false;
    private RecognitionProgressView recognitionProgressView;
    private DataSource.Factory mediaSourceFactory;
    private PlayerView playerView;
    private ExoPlayer exoPlayer;
    private TrackSelector trackSelector;
    private ImageView btnClosePlayerView;
    public Handler timer = new Handler();
    public Runnable timerSpeech = this::notifySpeechTimeOut;
    public Handler handlerSpeechRecordTimeManager = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SPEECH_TIME_OUT:
                    if (isPlayingRecognition) {
                        viewModel.getLiveDataStartRecord().postValue(false);
                        getMainActivity().getTextToSpeech().speak("Xin lỗi, không thể phát hiện giọng nói của bạn!", false);
                    }
                    break;
                case START_SPEECH_TIME_COUNT:
                    timer.postDelayed(timerSpeech, MAX_SPEECH_TIME_OUT * 1000);
                    break;
                case STOP_SPEECH_TIME_COUNT:
                    timer.removeCallbacks(timerSpeech);
                    speechCountTime = MAX_SPEECH_TIME_OUT;
                    break;
            }

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        intAction(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }


    public void initView(View view) {
        btnVoice = view.findViewById(R.id.btnVoice);
        recognitionProgressView = view.findViewById(R.id.recognition_view);
        playerView = view.findViewById(R.id.playerView);
        btnClosePlayerView = view.findViewById(R.id.btnClosePlayerView);
        mListResult = view.findViewById(R.id.mRecyclerView);
        toolbar = view.findViewById(R.id.toolbar);
        setupToolbar();
        initRecognitionProgressView();
    }
    public void setupToolbar() {
        ((AppCompatActivity)requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(requireContext(),R.drawable.ic_round_arrow_back_24));

    }

    public void intAction(View view) {
        setUpRecognitionsUi();
        adapter = new AdapterAssistantMessage();
        mListResult.setAdapter(adapter);
        mListResult.setLayoutManager(new LinearLayoutManager(getContext()));
        btnVoice.setOnClickListener(view1 -> {
            if (isPlayingRecognition) {
                finishRecognition();
            } else {
                if (App.isActivated) {
                    startRecognition();
                }
            }
        });
        btnClosePlayerView.setOnClickListener(v -> {
            btnClosePlayerView.setVisibility(View.GONE);
            playerView.setVisibility(View.GONE);
            exoPlayer.stop();
            toolbar.setVisibility(View.VISIBLE);
            btnVoice.setVisibility(View.VISIBLE);
            recognitionProgressView.setVisibility(View.INVISIBLE);
        });
        toolbar.setOnClickListener(v -> requireActivity().onBackPressed());
        viewModel.getListMessLiveData().observe(getViewLifecycleOwner(), list -> {
            ArrayList<ResultItem> listResultItem = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                listResultItem.add(new ItemAssistant(list.get(i).getMessage(), list.get(i).isSender()));
            }
            adapter.setListItem(listResultItem);
            mListResult.scrollToPosition(adapter.getItemCount() - 1);
        });
        viewModel.getLiveDataProcessText().observe(getViewLifecycleOwner(), message -> {
            viewModel.saveMessage(message);
            adapter.addItem(new ItemAssistant(message.getMessage(), message.isSender()));
            mListResult.scrollToPosition(adapter.getItemCount() - 1);
            finishRecognition();
        });

        viewModel.getLiveListPoi().observe(getViewLifecycleOwner(), pois -> {
            adapter.addItem(new ItemListResult(pois));
            mListResult.scrollToPosition(adapter.getItemCount() - 1);
        });
        viewModel.getLiveDataStartRecord().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean == null) return;
            if (aBoolean) {
                if (App.isActivated) {
                    startRecognition();
                }
            } else {
                finishRecognition();
            }
            viewModel.getLiveDataStartRecord().postValue(null);
        });
        mediaSourceFactory = new DefaultDataSourceFactory(requireContext(), Util.getUserAgent(requireContext(), "vnest"));

        viewModel.getLiveDataOpenVTV().observe(getViewLifecycleOwner(), this::playVideo);
        viewModel.getLiveDataRebindRecognitionsView().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean == null) return;
            if (aBoolean) {
                setUpRecognitionsUi();
            }
            viewModel.getLiveDataRebindRecognitionsView().postValue(null);
        });
    }

    private void setUpRecognitionsUi() {
        recognitionProgressView.setOnClickListener(view1 -> {
            finishRecognition();
        });
        recognitionProgressView.setSpeechRecognizer(getMainActivity().getSpeechRecognizerManager().getSpeechRecognizer());
        recognitionProgressView.setRecognitionListener(getMainActivity().getSpeechRecognizerManager().getSpeechListener());
    }

    private void initRecognitionProgressView() {
        int[] colors = {
                ContextCompat.getColor(requireContext(), R.color.color1),
                ContextCompat.getColor(requireContext(), R.color.color2),
                ContextCompat.getColor(requireContext(), R.color.color3),
                ContextCompat.getColor(requireContext(), R.color.color4),
                ContextCompat.getColor(requireContext(), R.color.color5)
        };

        int[] heights = {60, 76, 58, 80, 55};

        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.setCircleRadiusInDp(6); // kich thuoc cham tron
        recognitionProgressView.setSpacingInDp(2); // khoang cach giua cac cham tron
        recognitionProgressView.setIdleStateAmplitudeInDp(8); // bien do dao dong cua cham tron
        recognitionProgressView.setRotationRadiusInDp(0); // kich thuoc vong quay cua cham tron
        recognitionProgressView.play();

    }

    @SuppressLint("LogNotTimber")
    public void startRecognition() {
        Log.e(LOG_TAG, "start listener....");
        TriggerOfflineService.stopService(requireContext());
        isPlayingRecognition = true;
        btnVoice.setVisibility(View.GONE);
        setMarginListResult(120);
        recognitionProgressView.play();
        recognitionProgressView.setVisibility(View.VISIBLE);
        getMainActivity().getSpeechRecognizerManager().startListening();
        startSpeechCountDown();
    }

    public void startSpeechCountDown() {
        Message message = new Message();
        message.what = START_SPEECH_TIME_COUNT;
        message.setTarget(handlerSpeechRecordTimeManager);
        message.sendToTarget();
    }

    public void stopSpeechTimeCount() {
        Message message = new Message();
        message.what = STOP_SPEECH_TIME_COUNT;
        message.setTarget(handlerSpeechRecordTimeManager);
        message.sendToTarget();
    }

    public void notifySpeechTimeOut() {
        Message message = new Message();
        message.what = SPEECH_TIME_OUT;
        message.setTarget(handlerSpeechRecordTimeManager);
        message.sendToTarget();
    }

    /**
     * Finish Speech Recognition
     */
    public void finishRecognition() {
        Log.d(LOG_TAG, "stop listener....");
        btnVoice.setVisibility(View.VISIBLE);
        setMarginListResult(37);
        isPlayingRecognition = false;
        recognitionProgressView.setVisibility(View.INVISIBLE);
        recognitionProgressView.stop();
        getMainActivity().getSpeechRecognizerManager().stopListening();
        stopSpeechTimeCount();
    }

    private void setMarginListResult(int topMargin) {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mListResult.getLayoutParams();
        layoutParams.topMargin = (int) (getResources().getDisplayMetrics().scaledDensity * topMargin);
        mListResult.scrollToPosition(adapter.getItemCount() - 1);
        mListResult.setLayoutParams(layoutParams);
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }


    @Override
    public void onResume() {
        super.onResume();
        getMainActivity().bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void initializePlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    requireContext(),
                    new DefaultRenderersFactory(requireContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());
            playerView.setPlayer(exoPlayer);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    private void playVideo(String url) {
        if (url == null) return;
        if (url.equalsIgnoreCase("-1")) {
            btnClosePlayerView.performClick();
            return;
        }
        viewModel.getLiveDataOpenVTV().postValue(null);
        finishRecognition();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri videoUri = Uri.parse(url);
            intent.setDataAndType( videoUri, "application/x-mpegURL" );
            intent.setPackage( "com.mxtech.videoplayer.pro" );
            if(intent.resolveActivity(requireContext().getPackageManager()) ==  null) {
                intent.setPackage( "com.mxtech.videoplayer.ad" );
            }
            startActivity( intent );
        }catch (Exception ex) {
            toolbar.setVisibility(View.INVISIBLE);
            btnVoice.setVisibility(View.GONE);
            playerView.setVisibility(View.VISIBLE);
            recognitionProgressView.setVisibility(View.INVISIBLE);
            btnClosePlayerView.setVisibility(View.VISIBLE);
            MediaSource mediaSource = buildMediaSource(Uri.parse(url));
            exoPlayer.prepare(mediaSource, true, false);
        }
    }

    private MediaSource buildMediaSource(@NonNull Uri uri) {
        String userAgent = "exoplayer-vnest";
        if (Objects.requireNonNull(uri.getLastPathSegment()).contains("mp3") || uri.getLastPathSegment().contains("mp4")) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains("m3u8")) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(userAgent))
                    .createMediaSource(uri);
        } else {
            DefaultDashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                    new DefaultHttpDataSourceFactory(userAgent));
            DefaultHttpDataSourceFactory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri);
        }
    }
    @Override
    public void onDestroyView() {
        finishRecognition();
        super.onDestroyView();
    }
}
