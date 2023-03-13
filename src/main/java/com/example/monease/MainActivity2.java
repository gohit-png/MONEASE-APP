package com.example.monease;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    Button left;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setTitle("Summary");




        barChart=findViewById(R.id.barChart);
        ArrayList<BarEntry> info = new ArrayList<>();
        for (int i=1 ;i<=30 ;i++){
            info.add(new BarEntry(i,(i*1000)%13));
        }
        BarDataSet dset=new BarDataSet(info,"summary");
        dset.setColor(Color.WHITE);
        dset.setValueTextColor(Color.BLACK);
        dset.setValueTextSize(10f);


        BarData bdata = new BarData(dset);
        barChart.setFitBars(true);
        barChart.setData(bdata);
        barChart.getDescription().setText("this month");
        barChart.animateY(2000);
        barChart.setTouchEnabled(true);
        barChart.setClickable(false);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);

        barChart.setDrawBorders(false);
        barChart.setDrawGridBackground(false);

        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawAxisLine(false);

        barChart.getXAxis().setDrawGridLines(false);
        barChart.getXAxis().setDrawLabels(false);
        barChart.getXAxis().setDrawAxisLine(false);

        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawAxisLine(false);



    }







}