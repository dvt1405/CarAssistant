package ai.kitt.vnest.feature.activitymain;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kwabenaberko.openweathermaplib.constants.Lang;
import com.kwabenaberko.openweathermaplib.constants.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;

import java.util.Objects;

import ai.kitt.vnest.R;
import ai.kitt.vnest.feature.activitymain.adapters.DefaultAssistantAdapter;
import ai.kitt.vnest.feature.screenhome.AdapterHomeItemDefault;
import ai.kitt.vnest.feature.screenhome.FragmentHome;
import ai.kitt.vnest.feature.screensettings.FragmentSettings;

public class MainActivity extends BaseMainActivity {
    private View bottomSheetLayout;
    public BottomSheetBehavior bottomSheetBehavior;
    private FrameLayout fragmentContainer;
    private RecyclerView mRecyclerViewDefaultAssistant;
    private RecyclerView mRecyclerViewDefaultMainItem;
    private View mCollapseView;
    private ConstraintLayout mDrawer;
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void initView() {
        fragmentContainer = findViewById(R.id.fragment_container);
        mRecyclerViewDefaultAssistant = findViewById(R.id.recycler_view_def_assistant);
        mCollapseView = findViewById(R.id.view_collapse);
        initLeftNav();
        initBottomSheet();

    }

    @Override
    protected void initAction() {
        if (fragmentContainer != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new FragmentHome())
                    .addToBackStack(MainActivity.class.getName())
                    .commit();
        }
        if (mCollapseView != null) {
            mCollapseView.setOnClickListener(view -> {
                if (mDrawerLayout.isDrawerOpen(mDrawer)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void initLeftNav() {
        if (mRecyclerViewDefaultAssistant != null) {
            mRecyclerViewDefaultAssistant.setAdapter(new DefaultAssistantAdapter((text, position) -> {
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
                            mDrawerLayout.closeDrawers();
                            bottomSheetBehavior.setHideable(true);
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
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
                        viewModel.getLiveDataStartRecord().postValue(false);
                        sendMessage(textSpeech, true);
                        processing_text(textSpeech, true);
                        startResultFragment();
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }
            }));
            mRecyclerViewDefaultAssistant.setLayoutManager(new GridLayoutManager(this, 2));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewDefaultAssistant.getContext(),
                    ((GridLayoutManager) Objects.requireNonNull(mRecyclerViewDefaultAssistant.getLayoutManager())).getOrientation());
            mRecyclerViewDefaultAssistant.addItemDecoration(dividerItemDecoration);

            mDrawer = findViewById(R.id.drawer);
            mDrawerLayout = findViewById(R.id.drawerLayout);
            mainLayout = findViewById(R.id.mainLayout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    float slideX = drawerView.getWidth() * slideOffset;
                    mainLayout.setTranslationX(slideX);
                }
            };
            toggle.syncState();
            mDrawerLayout.addDrawerListener(toggle);
        }
    }

    @Override
    public void startResultFragment() {
        super.startResultFragment();
        bottomSheetBehavior.setHideable(false);
    }

    private void initBottomSheet() {
        bottomSheetLayout = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        mRecyclerViewDefaultMainItem = bottomSheetLayout.findViewById(R.id.mRecyclerView);
        AdapterHomeItemDefault adapter = new AdapterHomeItemDefault(this, getTextToSpeech(), text -> {

        });
        adapter.setItemClickListener((position, name) -> {
            startResultFragment();
            viewModel.getLiveDataStartRecord().postValue(false);
            contexts = null;
            sendMessage(name, true);
            processing_text(name, true);
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });
        mRecyclerViewDefaultMainItem.setAdapter(adapter);
        mRecyclerViewDefaultMainItem.setLayoutManager(new GridLayoutManager(this, 3));
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheetLayout.getLayoutParams();
        layoutParams.leftMargin = (int) (17 * getResources().getDisplayMetrics().scaledDensity);
        bottomSheetLayout.setLayoutParams(layoutParams);
        bottomSheetLayout.requestLayout();
    }


    private void setUiRecognition(Context context) {
        weather = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        weather.setUnits(Units.METRIC);
        weather.setLang(Lang.VIETNAMESE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheetLayout.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY()))
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.e("User"," On User LeaveHint");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
    }
}
