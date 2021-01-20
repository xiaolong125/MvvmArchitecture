package com.turdroid.library.livedata;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class MessageEvent extends SingleLiveEvent<String> {

    public void observe(LifecycleOwner owner, final MessageObserver observer) {
        super.observe(owner, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String t) {
                //过滤空消息
                if (TextUtils.isEmpty(t)) {
                    return;
                }
                observer.onNewMessage(t);
            }
        });
    }

    public interface MessageObserver{
        void onNewMessage(@NonNull String message);
    }
}

