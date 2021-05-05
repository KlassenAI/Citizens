package com.android.citizens.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.citizens.R;
import com.android.citizens.adapter.CitizenAdapter;
import com.android.citizens.model.Citizen;
import com.android.citizens.service.CitizenService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "Logs";
    private FloatingActionButton mFloatingActionButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private CitizenAdapter mCitizenAdapter;
    private List<Citizen> mCitizenList;
    private ServiceConnection mConnection;
    boolean mBound = false;
    private CitizenService mCitizenService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Граждане");

        Intent intent = new Intent(this, CitizenService.class);

        startService(intent);

        mFloatingActionButton = findViewById(R.id.fab);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = findViewById(R.id.rclView);

        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getApplicationContext(),
                R.color.design_default_color_primary));
        mSwipeRefreshLayout.setOnRefreshListener(this::getNewCitizens);

        mFloatingActionButton.setOnClickListener(view -> {
            getNewCitizens();
        });

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                CitizenService.CitizenBinder binder = (CitizenService.CitizenBinder) iBinder;
                mCitizenService = binder.getService();
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                mBound = false;
            }
        };

        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        mBound = false;
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void getNewCitizens() {
        if (mBound) {
            mSwipeRefreshLayout.setRefreshing(true);
            mCitizenList = mCitizenService.getCitizenList();
            Runnable runnable = () -> runOnUiThread(() -> {
                mCitizenAdapter = new CitizenAdapter(MainActivity.this, mCitizenList);
                mRecyclerView.setAdapter(mCitizenAdapter);
            });
            Thread thread = new Thread(runnable);
            thread.start();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
}