package ai.kitt.vnest.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import ai.kitt.vnest.R;

public class DialogActiveControl {
    private Activity context;
    private View view;
    private AlertDialog alertDialog;
    private TextView btnAccept;
    private EditText editTextPhone;
    private EditText editTextActiveCode;
    private OnActiveListener onActiveListener;

    public DialogActiveControl(Activity context, OnActiveListener onActiveListener) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_active_code, null, false);
        initView(view);
        initAction(view);
        alertDialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create();
        this.onActiveListener = onActiveListener;
    }

    private void initView(View view) {
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextActiveCode = view.findViewById(R.id.editTextActiveCode);
        btnAccept = view.findViewById(R.id.acceptBtn);
    }

    private void initAction(View view) {
        btnAccept.setOnClickListener(v -> {
//            if (checkInputNull(editTextPhone)) return;
            if (checkInputNull(editTextActiveCode)) return;
            activeCode(editTextPhone.getText().toString(), editTextActiveCode.getText().toString());
        });
        editTextActiveCode.requestFocus();
        editTextPhone.setOnEditorActionListener((v, actionId, event) -> {
            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    KeyboardUtil.hideSoftKeyboard(context, v);
                    return btnAccept.performClick();
            }
            return false;
        });
    }

    public void show() {
        Window window = alertDialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        editTextActiveCode.requestFocus();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }


    public void activeCode(String phone, String activeCode) {
//        onActiveListener.onSuccess(activeCode);
        onActiveListener.onAccept(phone, activeCode);

    }

    private boolean checkInputNull(EditText editText) {
        if (editText.getText() == null || editText.getText().length() == 0) {
            editText.requestFocus();
            return true;
        }
        return false;
    }

    public interface OnActiveListener {
        void onAccept(String phone, String activeCode);

        void onFail();
    }
}
