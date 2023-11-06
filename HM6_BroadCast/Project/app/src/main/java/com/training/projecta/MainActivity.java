package com.training.projecta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Replace the container with the GridFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new GridFragment())
                .commit();

//        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(ImageWorker.class)
//                .setConstraints(new Constraints.Builder()
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
//                        .build())
//                .build();
//
//        WorkManager.getInstance(context).enqueue(workRequest);

    }
}
