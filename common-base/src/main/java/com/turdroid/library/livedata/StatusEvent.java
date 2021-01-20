package com.turdroid.library.livedata;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 这里可以放一些全局状态,可以做成状态机
 */
public class StatusEvent extends SingleLiveEvent<Integer> {


    public void observe(LifecycleOwner owner, final StatusEvent.StatusObserver observer) {
        super.observe(owner, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer t) {
                if (t != null) {
                    observer.onStatusChanged(t);
                }
            }
        });
    }

    public interface StatusObserver{
        void onStatusChanged(@Status int status);
    }

    /**
     * 状态
     */
    @IntDef({Status.LOADING, Status.SUCCESS, Status.FAILURE, Status.ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
        int LOADING = 0;
        int SUCCESS = 1;
        int FAILURE = 2;
        int ERROR = 3;
    }
}

