package com.turdroid.library.loading;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.turdroid.library.R;

public class LoadingDialog extends BaseDialogFragment {



    @Override
    protected int getWindowWidth(int screenWidth) {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected boolean getCancelable() {
        return false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.loading_dialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        LinearLayout l = new LinearLayout(getActivity());
        LoadingView mLoadingView = new LoadingView(getActivity());

//        mLoadingView = new ThreeBallAnimation(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
//
        l.addView(mLoadingView, lp);

        mLoadingView.setOnClickListener(v-> {
            if (Build.getInstance().isShowing) {
                //Build.getInstance().mLoadingDialog.dismiss();
                //Build.getInstance().isShowing = false;
            }
        });

        return l;
    }

    public static class Build {
        private LoadingDialog mLoadingDialog;
        private boolean isShowing;

        private static class Holder {
            private static final Build INSTANCE = new Build();
        }

        private Build() {
        }

        public static Build getInstance() {
            return Holder.INSTANCE;
        }

        /**
         * Loading animation dialog
         *
         * @param show true to show or false to hide.
         */
        public void show(FragmentManager manager, boolean show) {
            if (isShowing == show) {
                return;
            }
            isShowing = show;

            if (show) {
                mLoadingDialog = new LoadingDialog();
                mLoadingDialog.show(manager, "Loading");
            } else {
                mLoadingDialog.dismiss();
                mLoadingDialog = null;
            }
        }

    }

}
