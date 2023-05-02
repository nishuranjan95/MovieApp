package com.example.myapplication.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMapsBinding

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map:GoogleMap
    private val appPerms = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private var mapFragment: SupportMapFragment? =null
    private var locationManager: LocationManager? =null

    // globally declare LocationRequest
    private lateinit var locationRequest: LocationRequest

    // globally declare LocationCallback
    private lateinit var locationCallback: LocationCallback
    private var latitude=17.34
    private var longitude=28.84345
    private var country:String?=null


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        getLastKnownLocation()
        map=googleMap
        val currlocation = LatLng(latitude, longitude)
        map.addMarker(MarkerOptions().position(currlocation).title("Marker in $country"))
        map.moveCamera(CameraUpdateFactory.newLatLng(currlocation))
        map.setOnMapClickListener {
            map.clear()
            map.animateCamera(CameraUpdateFactory.newLatLng(it));
            //val location = currlocation
            map.addMarker(MarkerOptions().position(it))
            Toast.makeText(context,"${it.latitude} & ${it.longitude} ",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient=LocationServices.getFusedLocationProviderClient(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         mapFragment= (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
         requestPermission()
    }

    private var activityResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = false
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }
            if(allAreGranted) {
                //capturePhoto()
                //Toast.makeText(context,"permission granted",Toast.LENGTH_SHORT).show()
                mapFragment?.getMapAsync(callback)
            }
            else{
                alertBuilder()
//                requestPermission()
                //Toast.makeText(context,"permission not granted",Toast.LENGTH_SHORT).show()
            }
        }


    private fun checkPermission(): Boolean {
        return context?.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
                &&
                context?.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED

    }
    private fun alertBuilder(){
        val builder = AlertDialog.Builder(requireContext()).setTitle("permission requested")
            .setMessage("location permission required")
        builder.setPositiveButton("Ok") { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts(
                    "package",
                    requireContext().packageName, null
                )
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

        }
        builder.setNegativeButton("No") { _, _ ->
            run {
                builder.setCancelable(true)
            }

        }
        builder.create()
        builder.show()
    }

    private fun requestPermission() {
        locationManager =
            context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?

        if (!checkPermission()) activityResultLauncher.launch(appPerms)
        else {
            val gpsEnabled: Boolean =
                locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val networkEnabled: Boolean =
                locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!networkEnabled && !gpsEnabled) {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            } else {

                mapFragment?.getMapAsync(callback)
            }
        }
    }


    /**
     * call this method in onCreate
     * onLocationResult call when location is changed
     */

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        if(ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnCompleteListener(requireContext() as Activity) { task ->
                val location: Location? = task.result
                if (location != null) {
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val list: List<Address> =
                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        ) as List<Address>
                    longitude = list[0].longitude
                    latitude = list[0].latitude
                    country = list[0].countryName
                    Toast.makeText(
                        requireContext(),
                        "Lat: $latitude, Long: $longitude",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            activityResultLauncher.launch(appPerms)
        }
        }

//        fusedLocationClient.lastLocation.addOnSuccessListener {
//            latitude=it.latitude
//            longitude=it.longitude
//        }
}