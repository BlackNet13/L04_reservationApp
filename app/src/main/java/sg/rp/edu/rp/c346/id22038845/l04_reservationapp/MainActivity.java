package sg.rp.edu.rp.c346.id22038845.l04_reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatePicker dp; //datepicker
    TimePicker tp; //timepicker
    Button rBtn; //reset
    Button sBtn; //submit
    ToggleButton sTgl; //smoking area or not
    Spinner dropD; //dropDown for salutations
    EditText nText; //name
    EditText cText; //contact
    EditText pText; //pax
    TextView ast1;
    TextView ast2;
    TextView ast3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link var to id
        dp=findViewById(R.id.dPicker);
        tp=findViewById(R.id.tPicker);
        rBtn=findViewById(R.id.reset);
        sBtn=findViewById(R.id.subReserve);
        sTgl=findViewById(R.id.smokeTgl);
        nText=findViewById(R.id.nameInpt);
        cText=findViewById(R.id.contactInpt);
        pText=findViewById(R.id.paxInpt);
        dropD=findViewById(R.id.dropDown);
        ast1=findViewById(R.id.ast1);
        ast2=findViewById(R.id.ast2);
        ast3=findViewById(R.id.ast3);

        //special
        ast1.setVisibility(View.GONE);
        ast2.setVisibility(View.GONE);
        ast3.setVisibility(View.GONE);

        //date/time picker settings:
        Calendar cal= Calendar.getInstance();
        dp.setMinDate(System.currentTimeMillis()); //set min date to current date
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);

        //has a limit, if a month does not have certain day it will over show till the next mth
        int maxDay = 30;
        int maxMonth = 7;
        int maxYear = 2023;
        cal.set(maxYear, maxMonth-1 , maxDay);
        dp.setMaxDate(cal.getTimeInMillis());

        //spinner array
        String[] salu = {"Mr.","Ms.","Mdm.","None"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,salu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropD.setAdapter(adapter);

        //checks if time selection change
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                if(hourOfDay<8){
                    tp.setCurrentHour(8);
                    Toast jusToast = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Reservations are only from: 8am-8.59pm" + "</b><br/></font>"), Toast.LENGTH_LONG);
                    jusToast.show();
                }else if(hourOfDay>=21){
                    tp.setCurrentHour(20);
                    Toast jusToast = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Reservations are only from: 8am-8.59pm" + "</b><br/></font>"), Toast.LENGTH_LONG);
                    jusToast.show();
                }
            }
        });

        //check if date selection change
        dp.getCalendarView().setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                dp.init(year,month,day,null);

            }
        });


        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String smoke = "Non-Smoking Area";
                if(sTgl.isChecked()==true){
                    smoke = "Smoking Area";
                }
                String dd=dropD.getSelectedItem().toString();
                String dPost = dd;
                if(dd=="None"){
                    dPost = " ";
                }


                /*boolean checks = false;
                while(checks==false){*/

                    if(nText.getText().length()<=0||cText.getText().length()<=0||pText.getText().length()<=0){
                        ast1.setVisibility(View.VISIBLE);
                        ast2.setVisibility(View.VISIBLE);
                        ast3.setVisibility(View.VISIBLE);
                        Toast jusToast3 = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Invalid reservation input. All fields must be filled." + "</b><br/></font>"), Toast.LENGTH_LONG);
                        jusToast3.show();

                    }else{
                        if(cText.getText().length()!=8){
                            Toast jusToast = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Invalid contact number. Numbers should contain 8 digits" + "</b><br/></font>"), Toast.LENGTH_LONG);
                            jusToast.show();

                        }else{
                            int num =Integer.parseInt(pText.getText().toString());
                            char[] cArray = cText.getText().toString().toCharArray();
                            char firstC = cArray[0];

                            if(firstC=='9'||firstC=='8'){
                                if(num>12){
                                    Toast jusToast2 = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Max pax allowed is 12 only" + "</b><br/></font>"), Toast.LENGTH_LONG);
                                    jusToast2.show();

                                }else{
                                    ast1.setVisibility(View.GONE);
                                    ast2.setVisibility(View.GONE);
                                    ast3.setVisibility(View.GONE);
                                    String toPrint=dPost+"Name: "+nText.getText().toString()+"<br/>Contact: "+cText.getText().toString()+"<br/>Pax: "+pText.getText().toString()+"<br/>Area Pref: "+smoke+"<br/>Time is " + timeStr(tp.getCurrentHour(),tp.getCurrentMinute())+ "<br/>Date is " + dateStr(dp.getDayOfMonth(), dp.getMonth()+1, dp.getYear());
                                    Toast jusToast = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Reservations Successful" + "</b><br/>"+toPrint+"</font>"), Toast.LENGTH_LONG);
                                    jusToast.show();
                                }
                            }else{
                                Toast jusToast4 = Toast.makeText(getBaseContext() , Html.fromHtml("<font color='#ff0303' ><b>" + "Invalid contact number. Numbers should start with 8 or 9." + "</b><br/></font>"), Toast.LENGTH_LONG);
                                jusToast4.show();
                                }
                            }
                        }
            }
        });

        rBtn.setOnClickListener(new View.OnClickListener() { //sets values to default when it senses a click on reset btn
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int rDay = 1;
                int rMonth = 5;
                int rYear = 2023;
                dp.updateDate(rYear,rMonth,rDay);
                tp.setCurrentHour(8);
                tp.setCurrentMinute(0);
                nText.getText().clear();
                cText.getText().clear();
                pText.getText().clear();
                sTgl.setChecked(false);
                dropD.setSelection(0);
            }
        });

    }

    //converts time into string
    private String timeStr(int hour, int min){
        //allows you to show AM and PM, works when return string ampm with the hour and min in desired string format.
        String ampm ="AM";
        if(hour>=12){
            if(hour!=12){
                hour-=12;
            }
            ampm="PM";
        }else if(hour==0){
            hour=12;
        }

        return String.format("%02d:%02d %s",hour,min,ampm);
    }

    //converts date into string
    private String dateStr(int day, int month, int year){
        return day+" / "+getMth(month)+" / "+year;
    }

    //

    //converts month into mth in words to be used with dateStr when returning month: getMth(month)
    private String getMth(int mth){
        String strMth[]={"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
        return strMth[mth-1];
    }
}