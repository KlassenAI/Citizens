package com.android.citizens.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.citizens.R;
import com.android.citizens.model.Citizen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class CitizenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);
        setTitle("Гражданин");

        TextView txtViewName = findViewById(R.id.txtViewCitizenName);
        TextView txtViewAge = findViewById(R.id.txtViewCitizenAge);
        TextView txtViewWorkPlace = findViewById(R.id.txtViewCitizenWorkPlace);
        TextView txtViewGender = findViewById(R.id.txtViewCitizenGender);
        TextView txtViewLivingArea = findViewById(R.id.txtViewCitizenLivingArea);
        TextView txtViewCar = findViewById(R.id.txtViewCitizenCar);

        Intent intent = getIntent();
        if (intent != null) {
            Citizen citizen = intent.getParcelableExtra("citizen");

            txtViewName.setText(String.format("%s %s", citizen.getLastName(), citizen.getFirstName()));
            txtViewAge.setText(String.format("Возраст: %s", citizen.getAge()));
            txtViewWorkPlace.setText(String.format("Место работы: %s", citizen.getWorkPlace()));
            txtViewGender.setText(String.format("Пол: %s", citizen.getGender()));
            txtViewLivingArea.setText(String.format("Район проживания: %s", citizen.getLivingArea()));
            txtViewCar.setText(String.format("Автомобиль: %s", citizen.getCar() ? "Да" : "Нет"));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}