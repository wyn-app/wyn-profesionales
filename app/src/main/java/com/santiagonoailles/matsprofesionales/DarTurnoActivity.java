package com.santiagonoailles.matsprofesionales;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.santiagonoailles.matsprofesionales.entity.Informe;
import com.santiagonoailles.matsprofesionales.helper.FirebaseHelper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class DarTurnoActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, OnDateSetListener {

    public static Informe currentInforme;
    private Calendar cal;

    private boolean hourstate = false;
    private boolean daystate = false;

    private TextView detallesTextView;
    private ImageView detallesImageView;
    private TextView fechaTitleTextView;
    private Button fechaButton;
    private TextView horaTitleTextView;
    private Button horaButton;
    private FloatingActionButton doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dar_turno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cal = Calendar.getInstance();

        detallesTextView = (TextView) findViewById(R.id.informeItem_detalleTextView);
        detallesImageView = (ImageView) findViewById(R.id.informeItem_detalleImageView);
        fechaTitleTextView = (TextView) findViewById(R.id.dateTitle);
        fechaButton = (Button) findViewById(R.id.dar_turno_fechaButton);
        horaTitleTextView = (TextView) findViewById(R.id.horaTitle);
        horaButton = (Button) findViewById(R.id.dar_turno_horaButton);
        doneButton = (FloatingActionButton) findViewById(R.id.fab);

        fechaButton.setOnClickListener(this);
        horaButton.setOnClickListener(this);
        doneButton.setOnClickListener(this);

        int userMinHour = currentInforme.getDisponibilidad().getFromHour24();
        int userMaxHour = currentInforme.getDisponibilidad().getToHour24();
        horaTitleTextView.setText(String.format(getString(R.string.margen_horario), userMinHour, userMaxHour));

        String dias = currentInforme.getDisponibilidad().getDiasText();
        fechaTitleTextView.setText(String.format(getString(R.string.margen_dias), dias));

        detallesTextView.setText(currentInforme.getDetalle());
        String imageUrl = currentInforme.getUrlImage();
        if (imageUrl != null){
            detallesImageView.setVisibility(View.VISIBLE);
            Picasso.with(this).load(currentInforme.getUrlImage()).into(detallesImageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dar_turno_horaButton:

                TimePickerDialog timePickerDialog = new TimePickerDialog(this, this, currentInforme.getDisponibilidad().getFromHour24(), 0,
                        DateFormat.is24HourFormat(DarTurnoActivity.this));
                timePickerDialog.show();
                break;
            case R.id.dar_turno_fechaButton:
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
                datePickerDialog.show();
                break;
            case R.id.fab:
                if (hourstate && daystate) {
                    FirebaseHelper.setTurnoToInforme(currentInforme.getId(), cal.getTime());
                    this.finish();
                } else {
                    Toast.makeText(this, getString(R.string.turno_error), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        int userMinHour = currentInforme.getDisponibilidad().getFromHour24();
        int userMaxHour = currentInforme.getDisponibilidad().getToHour24();

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);

        if (hour >= userMinHour && hour <= userMaxHour) {
            hourstate = true;
            this.horaButton.setText(hour + ":" + (minute < 10 ? "0" + String.valueOf(minute): String.valueOf(minute)));
        } else {
            Toast.makeText(this, getString(R.string.hour_error), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if (cal.get(Calendar.DAY_OF_YEAR) - Calendar.getInstance().get(Calendar.DAY_OF_YEAR) >= 0) {

            int dia = cal.get(Calendar.DAY_OF_WEEK);

            if (currentInforme.getDisponibilidad().compareDays(dia)) {
                daystate = true;
                this.fechaButton.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            } else {
                Toast.makeText(this, getString(R.string.day_error), Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, getString(R.string.date_error), Toast.LENGTH_SHORT).show();

        }
    }
}
