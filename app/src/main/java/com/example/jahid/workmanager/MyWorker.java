package com.example.jahid.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    Context context;
    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters params) {
        super(appContext, params);
        this.context = appContext;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("fire","here fire");
        return Result.success();
    }

    @Override
    public void onStopped() {
        // Cleanup because you are being stopped.
    }
}
