package com.example.jahid.workmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {

    Context context;
    DataBaseManager dataBaseManager;
    public MyWorker(@NonNull Context appContext, @NonNull WorkerParameters params) {
        super(appContext, params);
        this.context = appContext;
        dataBaseManager = new DataBaseManager(appContext);
    }

    @NonNull
    @Override
    public Result doWork() {
        long time= System.currentTimeMillis();
        boolean isAddSuccess = dataBaseManager.addData(""+time,"n/a");
        Log.d("MyWorker",""+isAddSuccess);
        return Result.success();
    }

    @Override
    public void onStopped() {
        // Cleanup because you are being stopped.
    }
}
