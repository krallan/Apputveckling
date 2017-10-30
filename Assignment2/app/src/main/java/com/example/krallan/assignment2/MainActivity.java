package com.example.krallan.assignment2;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private LatLng myLocation;
    private Marker myMarker;
    private FloatingActionButton joinGroup;
    private FloatingActionButton createGroup;
    private FloatingActionButton disconnectGroup;
    private Connection connection;
    private Thread connectionThread;
    private JoinGroup joinGroupDialog;
    private CreateGroup createGroupDialog;
    private String name;
    private TextView currentGroup;
    private FloatingActionsMenu fab;

    public JoinGroup getJoinGroupDialog() {
        return joinGroupDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        joinGroup = findViewById(R.id.fabJoin);
        createGroup = findViewById(R.id.fabCreate);
        currentGroup = findViewById(R.id.textViewCurrentGroup);
        disconnectGroup = findViewById(R.id.fabDisconnect);
        fab = findViewById(R.id.fab);
        connection = new Connection(this);
        connectionThread = new Thread(connection);
        connectionThread.start();
        joinGroup.setOnClickListener(this);
        createGroup.setOnClickListener(this);
        disconnectGroup.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("USER", 0);
        name = sharedPreferences.getString("USER", "");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setLocation();
            }
        }
    }

    private void setLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                if (!connection.isJoined()) {
                    if (myMarker != null) {
                        myMarker.remove();
                    }
                    myMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title(name));
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        if (lastKnownLocation == null) {
            return;
        }
        double latitude = lastKnownLocation.getLatitude();
        double longitude = lastKnownLocation.getLongitude();
        myLocation = new LatLng(latitude, longitude);
        myMarker = mMap.addMarker(new MarkerOptions().position(myLocation).title(name));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation));
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        setLocation();
    }

    public Connection getConnection() {
        return connection;
    }
    public void updateCurrentGroup(final String group){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                currentGroup.setText(group);
            }
        });
    }

    public void updateMarkers(final List<Member> members) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMap.clear();
                for (Member member : members) {
                    mMap.addMarker(
                            new MarkerOptions().position(member.getLatLng()).title(member.getMember())
                    );
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fabJoin) {
            joinGroupDialog = new JoinGroup();
            joinGroupDialog.show(getFragmentManager(), "JoinGroup");
        } else if (view.getId() == R.id.fabCreate) {
            createGroupDialog = new CreateGroup();
            createGroupDialog.show(getFragmentManager(), "CreateGroup");
        } else if (view.getId() == R.id.fabDisconnect) {
            connection.disconnect();
        }
        fab.collapse();
    }

    public LatLng getMyLocation() {
        return myLocation;
    }
}
