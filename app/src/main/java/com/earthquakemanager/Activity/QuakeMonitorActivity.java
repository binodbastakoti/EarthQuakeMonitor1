package com.earthquakemanager.Activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.earthquakemanager.Adapter.QuakeAdapter;
import com.earthquakemanager.Model.GeometryValue;
import com.earthquakemanager.Model.RowItem;
import com.earthquakemonitor.R;

public class QuakeMonitorActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    List<RowItem> rowItems;
    List<String> detail;
    List<GeometryValue> geometryList;
    RowItem item;
    GeometryValue geometryValue;
    final String URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson";
    QuakeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quakemonitor);

        detail = new ArrayList<String>();
        rowItems = new ArrayList<RowItem>();
        geometryList = new ArrayList<GeometryValue>();

        listView = (ListView) findViewById(R.id.quakeList);
        adapter = new QuakeAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        getData();


    }

    public void getData() {

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

//                        Log.d("Response", response.toString());
                        try {
                            JSONArray features = response.getJSONArray("features");


                            String time;
                            String place;
                            String mag;

                            // Toast.makeText(getApplicationContext(), "length" + features.length(), Toast.LENGTH_LONG).show();
                            for (int i = 0; i < features.length(); i++) {
                                JSONObject row = features.getJSONObject(i);
                                //  Log.d("Row ", "" + row);
                                JSONObject item1 = row.getJSONObject("properties");
                                // Log.d("Item ", "" + item1);

                                time = item1.getString("time");
                                place = item1.getString("place");
                                mag = item1.getString("mag");
                                detail.add(item1.getString("detail"));
                                item = new RowItem();
                                item.setDateTime(time);
                                item.setLocation(place);
                                item.setMagnitude(mag);
                                rowItems.add(item);
                                JSONObject geometry = row.getJSONObject("geometry");
                                Log.d("geometry ", "" + geometry);;
                                JSONArray coordinates = geometry.getJSONArray("coordinates");

                                Log.d("longitude ", "" + coordinates.getDouble(0));
                                Log.d("latitude ", "" + coordinates.getDouble(1));
                                geometryValue= new GeometryValue(coordinates.getDouble(1),coordinates.getDouble(0));

                                geometryList.add(geometryValue);
                                adapter.notifyDataSetChanged();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        queue.add(jsObjRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quakemonitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("item", rowItems.get(position));
//        intent.putExtra("date",rowItems.get(position).getDateTime());
//        intent.putExtra("mag",rowItems.get(position).getMagnitude());
//        intent.putExtra("loc",rowItems.get(position).getLocation());
        intent.putExtra("detail", geometryList.get(position));
        startActivity(intent);
    }
}
