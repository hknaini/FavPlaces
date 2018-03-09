package com.example.c5253694.favplaces;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    boolean addNew= true ;
    boolean newAdded = false ;
    LocationManager locationManager;
    LocationListener locationListener;
    String  favInfo[] = new String[3];
    Double  dInLat ;
    Double  dInLong ;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.i("25-- " , "25");
            if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng newPosition = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition , 14));

                Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());

                try {
                    List<Address> addresses  = geocoder.getFromLocation(lastKnownLocation.getLatitude() , lastKnownLocation.getLongitude() , 1);
                    Log.i("r in addresses" , String.valueOf(addresses.size()));
                    if ( addresses != null && addresses.size() > 0)
                    {
                        Log.i("Place ifo-- " , addresses.get(0).toString());
                        mMap.addMarker(new MarkerOptions().position(newPosition).title(addresses.get(0).getFeatureName().toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        }
    }

    @Override
        public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        if ( newAdded == true) {
            setResult(2, intent);
            intent.putExtra("favData", favInfo);
        }
        else
        {
            setResult(3, intent);
            intent.putExtra("favData", new String[]{});
        }

        finish();
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Intent intent = getIntent();
        newAdded = false ;
       // favInfo = null ;

        String[] favData = intent.getStringArrayExtra("favData");

        Log.i("Add- in map- ", intent.getStringExtra("Add"));

        if ( intent.getStringExtra("Add").equals("Add"))
        {
            Log.i("ADDDDDDDDDDDD- in map- ", intent.getStringExtra("Add"));
            addNew = true ;
        }
        else
        {
            Log.i("NOOOOOOOOOO- in map- ", intent.getStringExtra("Add"));
            addNew = false ;
            dInLat = Double.parseDouble(favData[1]);
            dInLong = Double.parseDouble(favData[2]);

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                LatLng newPosition = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(newPosition).title("location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition , 10));
                Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());

                try {
                    List<Address> addresses  = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() , 1);
                    Log.i("r in addresses" , String.valueOf(addresses.size()));
                    if ( addresses != null && addresses.size() > 0)
                    {
                        Log.i("Place ifo-- " , addresses.get(0).toString());
                        mMap.addMarker(new MarkerOptions().position(newPosition).title(addresses.get(0).getFeatureName().toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
        };
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        //  mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (Build.VERSION.SDK_INT < 23)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {

                mMap.clear();
                Log.i("r in" , "pin");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                LatLng newPosition ;

                if (addNew == true) {
                    newPosition = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                }
                else
                {
                    newPosition = new LatLng(dInLat, dInLong);

                }


                //  if ( firstTime)
                //{
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newPosition , 14));
                //firstTime = false ;
                // }
                Log.i("r in lastKnownLocation" , lastKnownLocation.toString());
                Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());

                try {
                    List<Address> addresses  = geocoder.getFromLocation(lastKnownLocation.getLatitude() , lastKnownLocation.getLongitude() , 1);
                    Log.i("r in addresses" , String.valueOf(addresses.size()));
                    if ( addresses != null && addresses.size() > 0)
                    {

                        Log.i("Place ifo-- " , addresses.get(0).toString());

                        mMap.addMarker(new MarkerOptions().position(newPosition).title(addresses.get(0).getFeatureName().toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Add a marker in Sydney and move the camera

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick (LatLng latLng)
            {
                newAdded = true ;
                Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());

                try {
                    List<Address> addresses  = geocoder.getFromLocation(latLng.latitude , latLng.longitude , 1);
                    Log.i("r in addresses" , String.valueOf(addresses.size()));
                    if ( addresses != null && addresses.size() > 0)
                    {
                        Log.i("Place ifo-- " , addresses.get(0).getFeatureName());
                        if ( addresses != null && addresses.size() > 0)
                        {
                            googleMap.clear();
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude,
                                    latLng.longitude)).title(addresses.get(0).getFeatureName().toString()));
                            favInfo[0] = addresses.get(0).getFeatureName().toString() ;
                            favInfo[1] =  Double.toString(latLng.latitude) ;
                            favInfo[2] =  Double.toString(latLng.longitude) ;
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
