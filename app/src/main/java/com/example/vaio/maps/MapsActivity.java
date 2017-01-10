package com.example.vaio.maps;


import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationChangeListener, View.OnClickListener {
    public static final int WHAT = 1;
    private static final int REQUEST = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location myLocation;
    private Marker myMarker;
    private Geocoder geocoder;
    private Marker markerClick;
    private MyInfoWindowAdapter myInfoWindowAdapter;

    private EditText edtStart;
    private EditText edtEnd;
    private Button btnOk;
    private ArrayList<LatLng> arrLatLng = new ArrayList<>();
    private Polyline polyline;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            arrLatLng.addAll((Collection<? extends LatLng>) msg.obj);
            arrLatLng = (ArrayList<LatLng>) msg.obj;
            if (polyline != null) {
                polyline.remove();
            }
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.color(Color.RED);
            polylineOptions.width(10);
            polylineOptions.addAll(arrLatLng);
            polyline = mMap.addPolyline(polylineOptions);
            if(markerStart!=null){
                markerStart.remove();
            }
            if(markerEnd!=null){
                markerEnd.remove();
            }
            markerStart  = drawMarker(arrLatLng.get(0),BitmapDescriptorFactory.HUE_GREEN,"Start Location",getLocationName(arrLatLng.get(0)));
            markerEnd  = drawMarker(arrLatLng.get(arrLatLng.size()-1),BitmapDescriptorFactory.HUE_GREEN,"End Location",getLocationName(arrLatLng.get(arrLatLng.size()-1)));
        }
    };
    private Marker markerStart;
    private Marker markerEnd;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, REQUEST);
        }
        initViews();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void initViews() {
        edtStart = (EditText) findViewById(R.id.edtStart);
        edtEnd = (EditText) findViewById(R.id.edtEnd);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, this);
        mMap.setOnMyLocationChangeListener(this);
        geocoder = new Geocoder(this);
        initMaps();
//         mMap.setOnMyLocationChangeListener(this);
//         Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    private Marker drawMarker(LatLng latLng, float hue, String titile, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(titile);
        markerOptions.snippet(snippet);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(hue));
        return mMap.addMarker(markerOptions);
    }

    @Override
    public void onLocationChanged(Location location) {
//        myLocation = location;
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        myMarker = drawMarker(latLng, BitmapDescriptorFactory.HUE_RED, "MyLocation", "MyStreet");
//        CameraPosition cameraPosition = new CameraPosition(latLng,0,0,0);
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//        myLocation = location;
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        if (myMarker == null) {
//            myMarker = drawMarker(latLng, BitmapDescriptorFactory.HUE_RED, "MyLocation", "MyStreet");
//            CameraPosition cameraPosition = new CameraPosition(latLng, 17, 0, 0);
//            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//        } else {
//            myMarker.setPosition(latLng);
//        }
//
//        myMarker.setSnippet(getLocationName(latLng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void initMaps() {
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        myInfoWindowAdapter = new MyInfoWindowAdapter(this);
        mMap.setInfoWindowAdapter(myInfoWindowAdapter);
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public String getLocationName(LatLng latLng) {
        String name = "Unknown";
        try {
            List<Address> address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (address.size() > 0) {
                name = address.get(0).getAddressLine(0);
                name += "-" + address.get(0).getAddressLine(1);
                name += "-" + address.get(0).getAddressLine(2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        if (markerClick != null) {
            markerClick.remove();
        }
        markerClick = drawMarker(latLng, BitmapDescriptorFactory.HUE_BLUE, "DrawMarker", getLocationName(latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (markerClick != null) {
            markerClick.remove();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        myLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (myMarker == null) {
            myMarker = drawMarker(latLng, BitmapDescriptorFactory.HUE_RED, "MyLocation", "MyStreet");
            CameraPosition cameraPosition = new CameraPosition(latLng, 17, 0, 0);
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            myMarker.setPosition(latLng);
        }
    }

    @Override
    public void onClick(View v) {
        String start = edtStart.getText().toString();
        String end = edtEnd.getText().toString();
        LatLng lnStart = getLocationFromName(start);
        LatLng lnEnd = getLocationFromName(end);
        if (lnStart == null || lnEnd == null) {
            Toast.makeText(this, "Position not found", Toast.LENGTH_SHORT).show();
            return;
        }
        MyAsyncTask myAsyncTask = new MyAsyncTask(lnStart, lnEnd, handler);
        myAsyncTask.execute();

    }

    private LatLng getLocationFromName(String name) {
        LatLng latlng = null;
        try {
            List<Address> addresses = geocoder.getFromLocationName(name, 1);
            if (addresses.size() > 0) {
                latlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latlng;
    }
}
