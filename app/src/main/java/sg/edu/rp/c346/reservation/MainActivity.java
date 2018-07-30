package sg.edu.rp.c346.reservation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    EditText name;
    EditText pax;
    EditText phone;
    EditText etDay;
    EditText etTime;

    CheckBox smoking;
    Button  btnReserve;
    Button btnReset;

    Calendar c = Calendar.getInstance();
    int month2 = c.get(Calendar.MONTH);
    int year2 = c.get(Calendar.YEAR);
    int day2 = c.get(Calendar.DAY_OF_MONTH);

    int hour2 = c.get(Calendar.HOUR_OF_DAY);
    int min2 = c.get(Calendar.MINUTE);

    @Override
    protected void onPause() {
        super.onPause();
        String day = etDay.getText().toString();
        String time = etTime.getText().toString();
        String name2 = name.getText().toString();
        String number2 = pax.getText().toString();
        String phone2 = phone.getText().toString();

        boolean confirm;
        if(smoking.isChecked()){
            confirm = true;
        }
        else{
            confirm = false;
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("Day",day);
        prefEdit.putString("Time",time);
        prefEdit.putString("Pax",number2);
        prefEdit.putString("Name",name2);
        prefEdit.putString("Phone",phone2);
        prefEdit.putBoolean("Smoking",confirm);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String day3 = prefs.getString("Day","");
        String time3 = prefs.getString("Time","");
        String pax3 = prefs.getString("Pax","");
        String name3 = prefs.getString("Name","");
        String phone3 = prefs.getString("Phone","");
        boolean smoking3 = prefs.getBoolean("Smoking",true);

        name.setText(name3);
        etTime.setText(time3);
        etDay.setText(day3);
        pax.setText(pax3);
        phone.setText(phone3);
        smoking.setChecked(smoking3);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etName);
        pax = findViewById(R.id.etPax);
        phone = findViewById(R.id.etPhone);

        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);

        smoking = findViewById(R.id.checkBoxSmoking);
        btnReserve = findViewById(R.id.buttonReserve);
        btnReset = findViewById(R.id.btnReset);



        Calendar c = Calendar.getInstance();
        final int month = c.get(Calendar.MONTH) +1;
        etDay.setText("Date:"+c.get(Calendar.YEAR)+"/"+month+"/"+c.get(Calendar.DAY_OF_MONTH));
        etTime.setText("Time: "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));



        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDay.setText("Date: "+ dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                       month2 = monthOfYear;
                       year2 = year;
                       day2 = dayOfMonth;
                    }
                };
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                        ,(calendar.get(Calendar.DAY_OF_MONTH)));
                myDateDialog.updateDate(year2,month2,day2);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        etTime.setText("Time: "+ hourOfDay+":"+minute);
                        min2 = minute;
                        hour2 = hourOfDay;


                    }
                };
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minute = now.get(Calendar.MINUTE);
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,myTimeListener,hour,minute,true);
                myTimeDialog.updateTime(hour2,min2);
                myTimeDialog.show();

            }
        });


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                String text;

                if(name.getText().toString().trim().length()!=0 && pax.getText().toString().trim().length()!=0 &&phone.getText().toString().trim().length()!=0){
                    if(smoking.isChecked()){
                          text = "No smoking area";
                    }
                    else{
                         text = "Smoking Area";
                    }
                    myBuilder.setMessage("New Reservation\n"+"Name:"+name.getText()+"\n"+"Smoking:"+text+"\n"+"Size:"+pax.getText()+"\n"+"Date:"+etDay.getText()+"\n"+"Time:"+etTime.getText()+"\n");
                }
                else{
                    myBuilder.setMessage("Every blank has to be filled");
                }

                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();





            }

        });


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pax.setText(null);
                name.setText(null);
                phone.setText(null);

                Calendar c = Calendar.getInstance();
                int month = c.get(Calendar.MONTH) +1;
                etDay.setText("Date:"+c.get(Calendar.YEAR)+"/"+month+"/"+c.get(Calendar.DAY_OF_MONTH));
                etTime.setText("Time: "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));



                smoking.setChecked(false);

            }
        });






    }
}
