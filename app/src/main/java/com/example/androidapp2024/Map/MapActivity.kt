package com.example.androidapp2024.Map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidapp2024.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap:GoogleMap?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap

        // Set the initial camera position to Israel
        val israel = LatLng(31.0461, 34.8516) // Replace with the desired coordinates
        mGoogleMap?.addMarker(MarkerOptions().position(israel).title("Israel"))
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(israel, 6f)) // Adjust the zoom level as needed

        // Enable other map features as needed
        mGoogleMap?.uiSettings?.isZoomControlsEnabled = true
        mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = true
    }
}