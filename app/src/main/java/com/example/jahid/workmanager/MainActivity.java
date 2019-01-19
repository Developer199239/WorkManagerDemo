package com.example.jahid.workmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.work.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    TextView statusText;
    Button fireBtn, getStatusBtn, getDataListBtn;
    private final String PEROIDIC_TAG = "PEROIDIC_TAG";
    private final String SINGLE_TAG = "SINGLE_TAG";

    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;
    List<DataModel> data = new ArrayList<>();

    DataBaseManager dataBaseManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        statusText = findViewById(R.id.jobStatus);
        fireBtn = findViewById(R.id.fireBtn);
        getStatusBtn = findViewById(R.id.getStatus);
        getDataListBtn = findViewById(R.id.getDataList);

        fireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                oneTimeRequest();
                perodicRequest();
            }
        });

        getStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                taskStatus(SINGLE_TAG);
                taskStatus(PEROIDIC_TAG);
            }
        });

        getDataListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataList();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, data);
        recyclerView.setAdapter(adapter);

        dataBaseManager = new DataBaseManager(this);
    }

    private void oneTimeRequest() {
        OneTimeWorkRequest request =
                // Tell which work to execute
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        // If you want to delay the start of work by 60 seconds
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        // Set a backoff criteria to be used when retry-ing
                        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 30000, TimeUnit.MILLISECONDS)
                        .addTag(SINGLE_TAG)
                        .build();

        WorkManager.getInstance()
                .enqueueUniqueWork(SINGLE_TAG, ExistingWorkPolicy.REPLACE, request);
    }

    private void perodicRequest() {
        PeriodicWorkRequest request =
                // Executes MyWorker every 15 minutes
                new PeriodicWorkRequest.Builder(MyWorker.class, 15, TimeUnit.MINUTES)
                        .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10000, TimeUnit.MILLISECONDS)
                        .addTag(PEROIDIC_TAG)
                        .build();

        WorkManager.getInstance()
                .enqueueUniquePeriodicWork(PEROIDIC_TAG, ExistingPeriodicWorkPolicy.REPLACE, request);
    }

    void taskStatus(String tag) {
        try {
            List<WorkInfo> workInfos = WorkManager.getInstance().getWorkInfosByTag(tag).get();
            if (workInfos != null && workInfos.size() > 0) {
                WorkInfo workInfo = workInfos.get(0);
                if (workInfo.getState() == WorkInfo.State.BLOCKED) {
                    statusText.setText("BLOCKED");
                } else if (workInfo.getState() == WorkInfo.State.RUNNING) {
                    statusText.setText("RUNNING");
                } else if (workInfo.getState() == WorkInfo.State.ENQUEUED) {
                    statusText.setText("ENQUEUED");
                } else if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    statusText.setText("SUCCEEDED");
                } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                    statusText.setText("FAILED");
                } else if (workInfo.getState() == WorkInfo.State.CANCELLED) {
                    statusText.setText("CANCELLED");
                }
            } else {
                statusText.setText("no status");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            statusText.setText("" + e.getMessage().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            statusText.setText("" + e.getMessage().toString());
        }
    }


    void getDataList() {
        ArrayList<DataModel> data1 = dataBaseManager.getDataList();
        data.clear();
        data.addAll(data1);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
