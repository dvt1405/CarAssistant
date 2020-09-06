package ai.kitt.vnest.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

public class KeyboardUtil {

    public static void hideSoftKeyboard(Activity activity, View view) {
        if (activity == null) return;
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(view!=null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        hideSoftKeyboard(activity,activity.getCurrentFocus());
    }

    public static void hideSoftKeyboard(AppCompatDialogFragment dialogFragment) {
        if (dialogFragment == null) return;
        FragmentActivity activity = dialogFragment.getActivity();
        if (activity == null) return;
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = dialogFragment.getView();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setupHideKeyboarOutTouchScreen(Activity activity, View view) {
        if (activity == null) return;
        if (view == null) return;
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                KeyboardUtil.hideSoftKeyboard(activity);
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupHideKeyboarOutTouchScreen(activity, innerView);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setupHideKeyboarOutTouchScreen(AppCompatDialogFragment dialogFragment, View view) {
        if (dialogFragment == null) return;
        if (view == null) return;
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                KeyboardUtil.hideSoftKeyboard(dialogFragment);
                return false;
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupHideKeyboarOutTouchScreen(dialogFragment, innerView);
            }
        }
    }
}
