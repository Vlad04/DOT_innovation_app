package com.neurosky.algo_sdk_sample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class Calendario extends Activity {
    public String current_user_level;
    Button Done;
    CalendarView calendar;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        Done=(Button)findViewById(R.id.Done_button);


        calendar=(CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMont) {

                //Toast.makeText(getApplicationContext(),dayOfMont+"/"+month+"/"+year,Toast.LENGTH_LONG).show();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMont);
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);
                int Month = calendar.get(Calendar.MONTH) + 1;

                int Year=calendar.get(Calendar.YEAR);

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String DATE_FORMAT = "EE, MMM dd, yyyy";
                String formattedDate = sdf.format(calendar.getTime());
                String selected_Date=(Day+"-"+Month+"-"+Year);

                //Toast.makeText(getApplicationContext(),dayOfWeek+"/",Toast.LENGTH_SHORT).show();
                //Log.d("day of week", String.valueOf(dayOfWeek));


                String day="";
                if(dayOfWeek==1)
                {
                    day="Sunday ";
                }
                else if(dayOfWeek==2)
                {
                    day="Monday ";
                }
                else if(dayOfWeek==3)
                {
                    day="Tuesday ";
                }
                else if(dayOfWeek==4)
                {
                    day="Wednesday ";
                }
                else if(dayOfWeek==5)
                {
                    day="Thursday ";
                }
                else if(dayOfWeek==6)
                {
                    day="Friday ";
                }
                else if(dayOfWeek==7)
                {
                    day="Saturday ";
                }
                Log.d("current date",day);

                if(dayOfWeek==7 || dayOfWeek==1)
                {
                    Toast.makeText(getApplicationContext(),"Rest for this day",Toast.LENGTH_SHORT).show();
                }
                else {


                    Intent intent = new Intent(Calendario.this, Progress_graph.class);
                    intent.putExtra("current day", formattedDate);
                    Log.d("month selected",String.valueOf(formattedDate));
                    Toast.makeText(getApplication(),formattedDate,Toast.LENGTH_SHORT).show();

                    startActivity(intent);


                }
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Calendario.this, MainActivity.class);
                intent.putExtra("current_user_level_from_calendar_2", current_user_level);

                startActivity(intent);
            }
        });


    }


    @Override
    public void onBackPressed() {
        // do nothing.
        Toast.makeText(getApplicationContext(), "Please select one day", Toast.LENGTH_SHORT).show();
    }


}
