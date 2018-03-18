package com.tom.metropricecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
    public static final String wenhu = "文湖線";
    public static final String danshui = "淡水信義線";
    public static final String songshan = "松山新店線";
    public static final String zhonghe = "中和新盧線";
    public static final String bannan = "板南線";

    private static final int metroStartPrice = 16;

    private String metro ;
    private String stations [] ;
    private String startStation;
    private String endStation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner transportSelect = (Spinner) findViewById(R.id.transport_spinner);   //transport spinner
        final Spinner metroSpinner = (Spinner) findViewById(R.id.metro_spinner);    //metro route spinner
        final Spinner startSpinner = (Spinner) findViewById(R.id.starting_spinner);       //starting station spinner
        final Spinner arrivalSpinner = (Spinner) findViewById(R.id.arrival_spinner);    //ending station spinner

        //adapter for transport chosen
        ArrayAdapter<CharSequence> nAdapeter = ArrayAdapter.createFromResource(
                this, R.array.transportSelectArray, android.R.layout.simple_spinner_item);

        //adapter for metro route select
        final ArrayAdapter<CharSequence> routeAdapter = ArrayAdapter.createFromResource(
                this,R.array.metroRoute, android.R.layout.simple_spinner_item);

        //adapter for different metro stations
        final ArrayAdapter<CharSequence> wenhuAdapter = ArrayAdapter.createFromResource(
                this,R.array.wenhuRoute, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> danshuiAdapter = ArrayAdapter.createFromResource(
                this,R.array.danshuiRoute, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> songshanAdapter = ArrayAdapter.createFromResource(
                this,R.array.songshanRoute, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> zhongheAdapter = ArrayAdapter.createFromResource(
                this,R.array.zhongheRoute, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> bannanAdapter = ArrayAdapter.createFromResource(
                this,R.array.bannanRoute, android.R.layout.simple_spinner_item);


        transportSelect.setAdapter(nAdapeter);  //set the transport spinner

        //transport select listener, select different transport vehicle gives different spinner to set up
        transportSelect.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        String item = adapterView.getItemAtPosition(i).toString();
                        String []a = getResources().getStringArray(R.array.transportSelectArray);



                        //if choose metro shows the metro route spinner
                        if (item.equals(a[1])){
                            Toast.makeText(adapterView.getContext(), item.toString(), Toast.LENGTH_SHORT).show();
                            metroSpinner.setAdapter(routeAdapter);
                            metroSpinner.setVisibility(View.VISIBLE);
                        }
                        else{
                            metroSpinner.setVisibility(View.GONE);  //if choose none metro, let metro route spinner be gone
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        //metro spinner selected listener, choose different route gives different starting and ending stations
        metroSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        metro = adapterView.getItemAtPosition(i).toString();
                        //Toast.makeText(adapterView.getContext(), metro.toString(), Toast.LENGTH_SHORT).show();
                        //String []route = getResources().getStringArray(R.array.metroRoute);

                        switch (metro){
                            case wenhu: startSpinner.setAdapter(wenhuAdapter);
                                        arrivalSpinner.setAdapter(wenhuAdapter);
                                        stations = getResources().getStringArray(R.array.wenhuRoute);

                                break;
                            case danshui:   startSpinner.setAdapter(danshuiAdapter);
                                            arrivalSpinner.setAdapter(danshuiAdapter);
                                            stations = getResources().getStringArray(R.array.danshuiRoute);
                                break;
                            case songshan:  startSpinner.setAdapter(songshanAdapter);
                                            arrivalSpinner.setAdapter(songshanAdapter);
                                            stations = getResources().getStringArray(R.array.songshanRoute);
                                break;
                            case zhonghe:   startSpinner.setAdapter(zhongheAdapter);
                                            arrivalSpinner.setAdapter(zhongheAdapter);
                                            stations = getResources().getStringArray(R.array.zhongheRoute);
                                break;
                            case bannan:    startSpinner.setAdapter(bannanAdapter);
                                            arrivalSpinner.setAdapter(bannanAdapter);
                                            stations = getResources().getStringArray(R.array.bannanRoute);
                                break;

                            default:
                                    break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );
        startSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        startStation = startSpinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        arrivalSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        endStation = arrivalSpinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );


        }

    //calculating the ticket price
    public void calculateTicketPrice (View v){
        int arrive = 0;
        int arrival = 0;
        int numberOfStationsDifference = 0;
        int price = metroStartPrice;


        for (int i = 0; i < stations.length; i++){
            if (startStation.equals(stations[i]))
            {
                arrive = i;
            }
            if (endStation.equals(stations[i])){
                arrival = i;
            }
        }



        numberOfStationsDifference = Math.abs(arrival - arrive);

        if (numberOfStationsDifference > 5)
        {
            price = metroStartPrice + 4 * (numberOfStationsDifference - 4);
        }


        Toast.makeText(this,"本次乘車需花費 " + Integer.toString(price) + " 元",Toast.LENGTH_LONG).show();


    }

    //@Override
    //public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //String item = adapterView.getItemAtPosition(i).toString();
    //    Toast.makeText(this, "yolo", Toast.LENGTH_SHORT).show();
    //}

    //@Override
    //public void onNothingSelected(AdapterView<?> adapterView) {
//
  //  }
}





