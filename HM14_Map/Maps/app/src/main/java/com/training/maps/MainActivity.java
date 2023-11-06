package com.training.maps;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;
    private String imagepath;
    private Context mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mContent = this.getApplicationContext();



        ActionBar actionBar = getSupportActionBar();


        // providing subtitle for the ActionBar
        //actionBar.setSubtitle("   Design a custom Action Bar");

        // adding icon in the ActionBar


        // methods to display the icon in the ActionBar
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // Zoom to the user's current location
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            googleMap.addMarker(new MarkerOptions().position(currentLocation));
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            TakePictureActivityResultLauncher.launch(takePictureIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.camera) {
            openCamera();
            return true;
        }
        if (item.getItemId() == R.id.form) {
            goToFormActivity();
            return true;
        }
        if (item.getItemId() == R.id.detail) {
            goToDetailActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    ActivityResultLauncher<Intent> TakePictureActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    int requestCode = REQUEST_IMAGE_CAPTURE;
                    int resultCode = result.getResultCode();
                    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        imageBitmap = (Bitmap) extras.get("data");
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        imagepath =  timeStamp + "_IMG";
                        try  {
                            FileOutputStream out = openFileOutput(imagepath, MODE_PRIVATE);
                            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void goToFormActivity(){
        try {
            Intent k = new Intent(MainActivity.this, FormActivity.class);
            k.putExtra("image", imagepath);
            k.putExtra("bitmap", imageBitmap);
            startActivity(k);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void goToDetailActivity(){
        try {
            Intent k = new Intent(MainActivity.this, DetailActivity.class);
            k.putExtra("bitmap", imageBitmap);
            startActivity(k);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


}
