package com.earthquakemanager.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.earthquakemanager.Model.GeometryValue;
import com.earthquakemanager.Model.RowItem;
import com.earthquakemonitor.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class DetailActivity extends AppCompatActivity {


    RowItem item;
    GeometryValue geometryValue;
    // Google Map
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent = getIntent();
        item = (RowItem) intent.getExtras().getSerializable("item");
        geometryValue = (GeometryValue) intent.getExtras().getSerializable("detail");

        TextView mag = (TextView) findViewById(R.id.detailMagnitude);
        TextView date = (TextView) findViewById(R.id.detailDate);
        TextView loc = (TextView) findViewById(R.id.detailLocation);

        mag.setText(item.getMagnitude());
        date.setText(item.getDateTime());
        loc.setText(item.getLocation());
        initilizeMap();


    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();


            // create marker
            MarkerOptions marker = new MarkerOptions().position(new LatLng(geometryValue.getLatitude(), geometryValue.getLongitude())).title(item.getLocation());
            //Moving Camera to a Location
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(geometryValue.getLatitude(), geometryValue.getLongitude())).zoom(8).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            // adding marker
            googleMap.addMarker(marker);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
}
