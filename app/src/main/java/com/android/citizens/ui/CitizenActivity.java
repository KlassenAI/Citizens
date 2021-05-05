package com.android.citizens.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.citizens.R;
import com.android.citizens.model.Citizen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class CitizenActivity extends AppCompatActivity {

    private TextInputEditText txtEditName;
    private TextInputEditText txtEditAge;
    private TextInputEditText txtEditWorkPlace;
    private TextInputEditText txtEditGender;
    private TextInputEditText txtEditLivingArea;
    private TextInputEditText txtEditCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);
        setTitle("Гражданин");

        txtEditName = findViewById(R.id.txtEditName);
        txtEditAge = findViewById(R.id.txtEditAge);
        txtEditWorkPlace = findViewById(R.id.txtEditWorkPlace);
        txtEditGender = findViewById(R.id.txtEditGender);
        txtEditLivingArea = findViewById(R.id.txtEditLivingArea);
        txtEditCar = findViewById(R.id.txtEditCar);

        Intent intent = getIntent();
        if (intent != null) {
            Citizen citizen = intent.getParcelableExtra("citizen");

            txtEditName.setText(String.format("%s %s", citizen.getLastName(), citizen.getFirstName()));
            txtEditAge.setText(String.valueOf(citizen.getAge()));
            txtEditWorkPlace.setText(citizen.getWorkPlace());
            txtEditGender.setText(citizen.getGender());
            txtEditLivingArea.setText(citizen.getLivingArea());
            txtEditCar.setText(citizen.getCar() ? "Присутствует" : "Отсутствует");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}