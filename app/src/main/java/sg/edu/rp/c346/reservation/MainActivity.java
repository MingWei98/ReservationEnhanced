package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvName; EditText etName;
    TextView tvMobile; EditText etMobile;
    TextView tvSize; EditText etSize;
    TextView tvSmoking; ToggleButton tbtnSmoking;
    TextView tvDate; EditText etDate;
    TextView tvTime; EditText etTime;
    Button btnReserve; Button btnReset;

    int CurrentYear = 0;
    int CurrentMonth = 0;
    int CurrentDay = 0;

    // Additional Challenge - 4
    @Override
    protected void onPause() {
        super.onPause();

        String date = etDate.getText().toString();
        String time = etTime.getText().toString();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();
        prefEdit.putString("date", date);
        prefEdit.putString("time", time);
        prefEdit.commit();
    }

    // Additional Challenge - 4
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String date = prefs.getString("date", "");
        String time = prefs.getString("time", "");

        etDate.setText(date);
        etTime.setText(time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.textViewName);
        etName = findViewById(R.id.editTextName);

        tvMobile = findViewById(R.id.textViewMobile);
        etMobile = findViewById(R.id.editTextMobile);

        tvSize = findViewById(R.id.textViewSize);
        etSize = findViewById(R.id.editTextSize);

        tvSmoking = findViewById(R.id.textViewSmoke);
        tbtnSmoking = findViewById(R.id.toggleButtonSmoke);

        tvDate = findViewById(R.id.textViewDay);
        etDate = findViewById(R.id.editTextDay);

        tvTime = findViewById(R.id.textViewTime);
        etTime = findViewById(R.id.editTextTime);

        btnReserve = findViewById(R.id.buttonReserve);
        btnReset = findViewById(R.id.buttonReset);

        // Handle User Event: On Click "RESERVE" Button
        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // IF editText is not empty
                if (etName.getText().toString().trim().length() != 0
                        && etMobile.getText().toString().trim().length() != 0
                        && etSize.getText().toString().trim().length() != 0) {

                    String smoking = "";
                    if(tbtnSmoking.isChecked()){
                        smoking = "Yes";
                    }
                    else {
                        smoking = "No";
                    }

                    AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);

                    myBuilder.setTitle("Confirm your order");
                    // Postitive / Negative Button always on the left
                    myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    "Your Reservation Has Been Confirmed", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                    myBuilder.setMessage("New Reservation\n" +
                            "Name: " + etName.getText().toString() +
                            "\nSmoking: " + smoking +
                            "\nSize: " + etSize.getText().toString() +
                            "\nDate: " + etDate.getText().toString() +
                            "\nTime: " + etTime.getText().toString());

                    myBuilder.setCancelable(false);
                    // Neutral Button always on the right
                    myBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(),
                                    "Your Reservation Has Been Cancelled", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                    AlertDialog myDialog = myBuilder.create();
                    myDialog.show();

                }

                // IF editText is empty, show Toast msg instead of crashing
                else {
                    Toast.makeText(MainActivity.this, "Please input all fields", Toast.LENGTH_LONG).show();
                }

            }
        });

        etDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create the Listener to set the date
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        CurrentYear = year + CurrentYear;
                        CurrentMonth = (monthOfYear+1);
                        CurrentDay = dayOfMonth;

                        etDate.setText("Date: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                    }
                };

                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this, R.style.Theme_AppCompat_DayNight, myDateListener, year,month,day);
                myDateDialog.show();


            }
        });

        etTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Create the Listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        etTime.setText("Time: " + String.format("%02d",hourOfDay) + ":" + String.format("%02d",minutes));
                    }
                };

                // Create the TimePicker Dialog - Additional Challenge 1
                Calendar now = Calendar.getInstance();
                int Hour = now.get(Calendar.HOUR);
                int Mins = now.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, R.style.Theme_AppCompat_DayNight, myTimeListener, Hour,Mins,true);
                myTimeDialog.show();
            }
        });



        // Handle User Event: On Click "RESET" Button
        // Additional Challenge - 3
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                int Hour = now.get(Calendar.HOUR);
                int Mins = now.get(Calendar.MINUTE);
                etName.setText("");
                etMobile.setText("");
                etSize.setText("");
                etDate.setText("Date: " + day + "/" + (month + 1) + "/" + year);
                etTime.setText("Time: " + String.format("%02d",Hour) + ":" + String.format("%02d",Mins));

            }
        });


    }
}