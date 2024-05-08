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
            googleMap = map // Assign the map to the class-level variable

            setUpSearch()

            getLastLocation()

            // Set the initial camera position to Israel
            val israel = LatLng(31.0461, 34.8516) // Replace with the desired coordinates
            googleMap?.addMarker(MarkerOptions().position(israel).title("Israel"))
            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(israel, 6f)) // Adjust the zoom level as needed

            // Enable other map features as needed
            googleMap?.uiSettings?.isZoomControlsEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true

        }
    }
    private fun setUpSearch(){
        val searchView = view?.findViewById<SearchView>(R.id.searchView)
        searchView?.isFocusable = true

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Perform search operations based on the query
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Perform search operations as the text changes
                return true
            }
        })
    }

    private fun performSearch(query: String?) {
        // Implement your search logic here
        if (!query.isNullOrBlank()) {
            // Perform search operations based on the query
            // For example, you might want to search for the location on the map
            // Here, I'm just logging the search query to the console
            println("Search query: $query")
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
                    // Now you have the current location, you can do whatever you want with it.
                    // For example, move the camera to the current location:
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    // Add a marker at the current location:
                    googleMap?.addMarker(MarkerOptions().position(currentLatLng).title("Your Location"))
                }
            }
    }
}