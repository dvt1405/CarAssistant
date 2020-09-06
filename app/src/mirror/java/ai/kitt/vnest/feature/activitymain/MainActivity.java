package ai.kitt.vnest.feature.activitymain;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

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
import ai.kitt.vnest.feature.screenhome.FragmentHome;


public class MainActivity extends BaseMainActivity {
    private FrameLayout fragmentContainer;
    private RecyclerView mRecyclerViewDefaultAssistant;
    private RecyclerView mRecyclerViewDefaultMainItem;
    private View mCollapseView;
    private ConstraintLayout mDrawer;
    private DrawerLayout mDrawerLayout;
    private CoordinatorLayout mainLayout;

    @Override
    protected void initView() {
        fragmentContainer = findViewById(R.id.fragment_container);
        mRecyclerViewDefaultAssistant = findViewById(R.id.recycler_view_def_assistant);
        mCollapseView = findViewById(R.id.view_collapse);
        mDrawer = findViewById(R.id.drawer);
        mDrawerLayout = findViewById(R.id.drawerLayout);
        mainLayout = findViewById(R.id.mainLayout);
        initLeftNav();
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
            mRecyclerViewDefaultAssistant.setAdapter(getAssistantAdapter(3, mDrawerLayout));
            mRecyclerViewDefaultAssistant.setLayoutManager(new GridLayoutManager(this, 2));
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewDefaultAssistant.getContext(),
                    ((GridLayoutManager) Objects.requireNonNull(mRecyclerViewDefaultAssistant.getLayoutManager())).getOrientation());
            mRecyclerViewDefaultAssistant.addItemDecoration(dividerItemDecoration);

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


    private void setUiRecognition(Context context) {
        weather = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        weather.setUnits(Units.METRIC);
        weather.setLang(Lang.VIETNAMESE);
    }
}
