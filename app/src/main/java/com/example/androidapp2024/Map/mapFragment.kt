package com.example.androidapp2024.Map

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.appcompat.widget.SearchView
import com.example.androidapp2024.R


class mapFragment : Fragment() {
    private val FINE_PERMISSION_CODE = 1
    private var currentLocation: android.location.Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null // Declare googleMap as a class-level variable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            googleMap = map

            getLastLocation()

            val israel = LatLng(31.0461, 34.8516)
            googleMap?.addMarker(MarkerOptions().position(israel).title("Israel"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(israel, 6f)) // Adjust the zoom level as needed

            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true

        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                FINE_PERMISSION_CODE
            )
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    val currentLatLng = LatLng(location.latitude, location.longitude)

                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    googleMap?.addMarker(MarkerOptions().position(currentLatLng).title("Your Location"))
                }
            }
    }
}