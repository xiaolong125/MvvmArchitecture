package com.turdroid.library.loading;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by Yang on 2019/4/10.
 * Email 15674798147@163.com .
 *
 * @class describe
 */
public abstract class BaseDialogFragment extends DialogFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDialog();
    }

    private void initDialog() {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        setCancelable(getCancelable());

        // 设置背景透明
        Window w = getDialog().getWindow();
        if (null != w) {
            w.setBackgroundDrawable(new ColorDrawable(0));

            // 设置Window默认大小
            getDialog().show();
            WindowManager.LayoutParams params = w.getAttributes();
            params.width = getWindowWidth(getResources().getDisplayMetrics().widthPixels);
            params.height = getWindowHeight(getResources().getDisplayMetrics().heightPixels);
            w.setAttributes(params);

        }

    }

    protected abstract int getWindowWidth(int screenWidth);

    protected int getWindowHeight(int screenHeight) {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected abstract boolean getCancelable();

    /**
     * All try-catch to avoid the java.lang.IllegalStateException:
     * Can not perform this action after onSaveInstanceState.
     */
    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            super.dismissAllowingStateLoss();
        }
    }

    /**
     * All try-catch to avoid the java.lang.IllegalStateException:
     * Can not perform this action after onSaveInstanceState.
     */
    @Override
    public void show(FragmentManager manager, String tag) {
        if (isAdded()) {
            return;
        }

        try {
            super.show(manager, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int mBackStackId = -1;

    /**
     * All try-catch to avoid the java.lang.IllegalStateException:
     * Can not perform this action after onSaveInstanceState.
     */
    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if (isAdded()) {
            return mBackStackId;
        }

        try {
            return mBackStackId = super.show(transaction, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBackStackId;
    }
}