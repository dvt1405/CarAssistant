package ai.kitt.vnest.feature.screenhome;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.List;

import ai.kitt.vnest.App;
import ai.kitt.vnest.R;
import ai.kitt.vnest.basedata.entity.Message;
import ai.kitt.vnest.feature.activitymain.MainActivity;
import ai.kitt.vnest.feature.activitymain.ViewModel;

public class FragmentHome extends Fragment {
    private static final String LOG_TAG = "VNest";
    private static final String HELLO_ASSISTANT = "Chào bạn, tôi có thể giúp gì cho bạn";

    private Button btnListener;
    private ViewModel viewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initAction(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {
        btnListener = view.findViewById(R.id.btnVoice);
    }

    private void initAction(View view) {
        viewModel.getMessage();
        btnListener.setOnClickListener(view1 -> {
            getMainActivity().startResultFragment();
            viewModel.getLiveDataStartRecord().postValue(true);
        });

        viewModel.getListMessLiveData().observe(getViewLifecycleOwner(), list -> {
            if (list.size() > 1) {
                getMainActivity().startResultFragment();
            } else {
                viewModel.saveMessage(new Message(HELLO_ASSISTANT, false, Calendar.getInstance().getTimeInMillis()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}
