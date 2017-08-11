package com.neurosky.algo_sdk_sample;

import android.app.Activity;
import android.app.Activity;
import android.app.job.JobInfo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class Progress_graph extends Activity {
    //String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    String key;
    String key_value;
    Intent i;
    TextView gamma, beta, alpha, deltha,theta;
    TextView getGamma,getBeta,getAlpha,getDeltha,getTheta;
    ArrayList<TextView> textViewsVal;
    ArrayList<TextView> textViewsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_graph);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Intent date_intent=this.getIntent();
        String date=date_intent.getExtras().getString("current day");
        reference = database.getReference(date);
        i = getIntent();


        gamma=(TextView)findViewById(R.id.gamma);
        beta=(TextView)findViewById(R.id.beta);
        alpha=(TextView)findViewById(R.id.alpha);
        deltha=(TextView)findViewById(R.id.deltha);
        theta=(TextView)findViewById(R.id.theta);

        getGamma=(TextView)findViewById(R.id.get_gamma);
        getBeta=(TextView)findViewById(R.id.get_beta);
        getAlpha=(TextView)findViewById(R.id.get_alpha);
        getDeltha=(TextView)findViewById(R.id.get_deltha);
        getTheta=(TextView)findViewById(R.id.get_theta);

        textViewsName = new ArrayList<TextView>();
        textViewsName.add(gamma);
        textViewsName.add(beta);
        textViewsName.add(alpha);
        textViewsName.add(deltha);
        textViewsName.add(theta);

        textViewsVal = new ArrayList<TextView>();
        textViewsVal.add(getGamma);
        textViewsVal.add(getBeta);
        textViewsVal.add(getAlpha);
        textViewsVal.add(getDeltha);
        textViewsVal.add(getTheta);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d("hey guy","im here");
                int i = 0;
                Log.d("number of childs",""+dataSnapshot.getChildrenCount());

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    if (i < 5) {
                        //Log.d("hey guy","im here 2");
                        textViewsName.get(i).setText(child.getKey());
                        textViewsVal.get(i).setText(child.getValue().toString());
                        Log.d("User key", child.getKey());
                        Log.d("User ref", child.getRef().toString());
                        Log.d("User val", child.getValue().toString());
                        i++;
                    }
                   // Log.d("user key", child.getKey());
                   // Log.d("User value",child.getValue().toString());
                    key=child.getKey();
                    key_value=child.getValue().toString();
                    //Log.d("FIREBASE KEY",key);
                    //Log.d("FIREBASE KEY_VALUE",key_value);


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*
        String gamma_graph_Test=getGamma.getText().toString();
        float gamma_value_graph_test=Float.parseFloat(gamma_graph_Test);
        Log.d("KEY_gamma",gamma.toString());
        Log.d("VALUE_gamma", String.valueOf(gamma_value_graph_test));
        /*
        String deltha_graph=getDeltha.getText().toString();
        String theta_graph=getTheta.getText().toString();
        String alpha_graph=getAlpha.getText().toString();
        String beta_graph=getBeta.getText().toString();
        String gamma_graph=getGamma.getText().toString();

        float deltha_value_graph=Float.parseFloat(deltha_graph);
        float gamma_value_graph=Float.parseFloat(gamma_graph);
        float theta_value_graph=Float.parseFloat(theta_graph);
        float alpha_value_graph=Float.parseFloat(alpha_graph);
        float beta_value_graph=Float.parseFloat(beta_graph);
        */

/*
        float deltha_graph=Float.parseFloat(String.valueOf(getDeltha));
        float theta_graph=Float.parseFloat(String.valueOf(getTheta));
        float alpha_graph=Float.parseFloat(String.valueOf(getAlpha));
        float beta_graph=Float.parseFloat(String.valueOf(getBeta));
        float gamma_graph=Float.parseFloat(String.valueOf(getGamma));
*/
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {



                new DataPoint(0, 1),
                new DataPoint(1, 2),
                new DataPoint(2, 3),
                new DataPoint(3, 4),
                new DataPoint(4, 5)
        });
        graph.addSeries(series);

// styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
    }
}
