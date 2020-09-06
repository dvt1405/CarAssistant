package ai.kitt.vnest.feature.screenspeech;

import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import ai.kitt.vnest.R;
import ai.kitt.vnest.feature.screenspeech.adapters.AdapterAddressResult;

public class FragmentResult extends BaseFragmentResult {

    protected void setMarginListResult(int topMargin) {
    }


    @Override
    public void initView(View view) {
        super.initView(view);
        binding.mRecyclerViewResult.setLayoutManager(new GridLayoutManager(requireContext(),2));
    }

    @Override
    public void onPoisResult() {
        viewModel.getLiveListPoi().observe(getViewLifecycleOwner(), pois -> {
            showListResult();
            binding.mRecyclerViewResult.setAdapter(new AdapterAddressResult(pois,false));
        });
    }

    @Override
    public void showListResult(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(requireContext(), R.layout.fragment_result_state_end);
        Transition transition =(Transition) new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(1200);
        TransitionManager.beginDelayedTransition(((ConstraintLayout) binding.getRoot()), transition);
        constraintSet.applyTo((ConstraintLayout) binding.getRoot());
    }
    @Override
    public void hideListResult(){
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(requireContext(), R.layout.fragment_result_state_start);
        Transition transition =(Transition) new ChangeBounds();
        transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        transition.setDuration(1200);
        TransitionManager.beginDelayedTransition(((ConstraintLayout) binding.getRoot()), transition);
        constraintSet.applyTo((ConstraintLayout) binding.getRoot());
    }

    @Override
    public void onDestroyView() {
        hideListResult();
        super.onDestroyView();
    }
}
