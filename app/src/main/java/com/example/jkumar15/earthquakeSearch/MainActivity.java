package com.example.jkumar15.earthquakeSearch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button submitButton;
    Button calButton;

    EditText eqView;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        eqView = findViewById(R.id.eqView);
        spinner = findViewById(R.id.spinner);

        //spinner
        List<String> categories = Arrays.asList("magnitude", "date");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.simple_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        calButton = findViewById(R.id.calButton);
        calButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate=Calendar.getInstance();
                int year = mcurrentDate.get(Calendar.YEAR);
                int month = mcurrentDate.get(Calendar.MONTH);
                int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                        int fixmonth = selectedMonth + 1;
                        calButton.setText(selectedYear + "-" + fixmonth + "-" + selectedDay);
                    }
                },year, month, day);
                mDatePicker.show();
            }
        });

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp1 = String.valueOf(eqView.getText());
                String temp2 = String.valueOf(calButton.getText());
                String temp3 = String.valueOf(spinner.getSelectedItem());

                Intent intent = new Intent(MainActivity.this, list.class);
                intent.putExtra("eqNo", temp1);
                intent.putExtra("orderBy", temp3);
                intent.putExtra("date", temp2);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-M-dd");
        String dateString = sdf.format(date);
        calButton.setText(dateString);
    }
}
