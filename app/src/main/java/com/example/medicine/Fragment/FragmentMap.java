package com.example.medicine.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.medicine.Hospital.Hospital;
import com.example.medicine.R;
import com.example.medicine.SQLConnect;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class FragmentMap extends Fragment implements OnMapReadyCallback {
    GoogleMap map;
    public final int REQUEST_LOCATION = 101;
    private long UPDATE_INTERVAL = 5 * 1000; /* 5 secs */
    private long FASTEST_INTERVAL = 3000; /* 3 sec */
    Activity activity;
    Connection connection = null;
    ArrayList<Hospital> hospitals;
    public FragmentMap() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

         activity = getActivity();

        // Set up google map
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.frgMap);

        mapFragment.getMapAsync(this);

        connection = SQLConnect.setConnection();
        hospitals = SQLConnect.getAllHospital();

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (googleMap != null) {
            map = googleMap;
            startLocationUpdates();
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(-34, 151);
            map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            map.getUiSettings().setZoomControlsEnabled(true);


            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));
            addMarkerHospital();
            getLastLocation();
        }
    }


    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION && grantResults.length == 2 && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void getLastLocation() {
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        if (checkPermissions()) return;
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
    }

    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        placeMarkerOnMap(latLng);

    }

    private void placeMarkerOnMap(LatLng location) {


        if(isAdded() && activity != null){
            MarkerOptions options = new MarkerOptions().position(location);
            options.icon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(),
                            R.drawable.ic_location_user)));
            String address = getAddress(location);
            options.title(address);

            // etc ...
            map.addMarker(options);
        }

    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> addresses;
        Address address;
        String addressText = "";
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses.get(0);
                if (address.getMaxAddressLineIndex() > 0) {
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        addressText += (i == 0) ? address.getAddressLine(i) : "\n" +
                                address.getAddressLine(i);
                    }
                } else {
                    addressText = address.getAddressLine(0);
                }
            }
        } catch (IOException e) {
            Log.e("CSC", Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return addressText;
    }

    protected void startLocationUpdates() {
        // Tạo đối tượng LocationRequest để bắt đầu cập nhật vị trí
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        // Tạo đối tượng LocationSettingsRequest sử dụng đối tượng LocationRequest trên
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        // Kiểm tra cấu hình Location của người dùng
        SettingsClient settingsClient = LocationServices.getSettingsClient(Objects.requireNonNull(getActivity()));
        settingsClient.checkLocationSettings(locationSettingsRequest);
        if (checkPermissions()) return;
        // Thực hiện lấy thông tin vị trí hiện tại của người dùng với một vòng lặp liên tục
        FusedLocationProviderClient locationClient = getFusedLocationProviderClient(getActivity());


        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        locationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // cập nhật lại vị trí của người dùng, thay đổi vị trí marker
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void addMarkerHospital() {
        for(int i = 0; i < hospitals.size(); i ++) {
            LatLng sydney = new LatLng(Double.parseDouble(hospitals.get(i).vido) , Double.parseDouble(hospitals.get(i).kinhdo) );
            map.addMarker(new MarkerOptions()
                    .position(sydney)
                    .title(hospitals.get(i).ten))
                    .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_hospital));
        }
    }


}