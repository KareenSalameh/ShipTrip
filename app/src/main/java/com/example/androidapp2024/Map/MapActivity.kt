//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.androidapp2024.R
//import com.mapbox.maps.MapView
//import com.mapbox.maps.Style
//import com.mapbox.maps.plugin.animation.camera
//import com.mapbox.maps.plugin.gestures.gestures
//import com.mapbox.maps.plugin.locationcomponent.location
//import com.mapbox.maps.plugin.annotation.annotations
//
//class MapActivity : AppCompatActivity() {
//    private lateinit var mapView: MapView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_map)
//
//        mapView = findViewById(R.id.mapView)
//        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS)
//        mapView.gestures.focalPoint = mapView.getMapboxMap().pixelForCoordinate(mapView.getMapboxMap().cameraState.center)
//        mapView.location.apply {
//            this.updateSettings {
//                this.enabled = true
//            }
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        mapView.onStart()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView.onStop()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//}