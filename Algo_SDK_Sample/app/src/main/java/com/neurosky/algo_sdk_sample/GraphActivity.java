package com.neurosky.algo_sdk_sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class GraphActivity extends Activity {

    Button back_button;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference Root_reference=firebaseDatabase.getReference();
    Button save_button;
    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
    public float gamma;
    public float deltha;
    public float beta;
    public float theta;
    public float alpha;

    public void average()
    {
        
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        back_button=(Button)findViewById(R.id.see_graph_btn);
        save_button=(Button)findViewById(R.id.Save_button);
        GraphView graph = (GraphView) findViewById(R.id.graph);
        Intent graph_intent=this.getIntent();
        if(graph_intent==null){
            Log.d("ERROR FROM INTENT","");
        }
        else
        {
            Log.d("Intent is not NULL","");
        }

        final float gamma_test=graph_intent.getExtras().getFloat("gamma");
        final float deltha_test=graph_intent.getExtras().getFloat("deltha");
        final float beta_test=graph_intent.getExtras().getFloat("beta");
        final float theta_test=graph_intent.getExtras().getFloat("theta");
        final float alpha_test=graph_intent.getExtras().getFloat("alpha");

        gamma=gamma_test;
        deltha=deltha_test;
        beta=beta_test;
        theta=theta_test;
        alpha=alpha_test;

        Log.d("deltha",String.valueOf(deltha));
        Log.d("theta",String.valueOf(theta));
        Log.d("alpha",String.valueOf(alpha));
        Log.d("beta",String.valueOf(beta));
        Log.d("gamma",String.valueOf(gamma));


        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {



                new DataPoint(0, deltha),
                new DataPoint(1, theta),
                new DataPoint(2, alpha),
                new DataPoint(3, beta),
                new DataPoint(4, gamma)
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
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GraphActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    DatabaseReference Deltha_ref = Root_reference.child(date).child("Deltha: ");
                    DatabaseReference Theta_ref = Root_reference.child(date).child("Theta: ");
                    DatabaseReference Alpha_ref = Root_reference.child(date).child("Alpha: ");
                    DatabaseReference Beta_ref = Root_reference.child(date).child("Beta: ");
                    DatabaseReference Gamma_ref = Root_reference.child(date).child("Gamma: ");

                    Deltha_ref.setValue(deltha);
                    Theta_ref.setValue(theta);
                    Alpha_ref.setValue(alpha);
                    Beta_ref.setValue(beta);
                    Gamma_ref.setValue(gamma);

                }
                catch(Exception e){
                    Toast.makeText(getApplication(),"well this is embarrasing",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
