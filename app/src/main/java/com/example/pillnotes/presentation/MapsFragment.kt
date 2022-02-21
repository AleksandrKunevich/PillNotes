package com.example.pillnotes.presentation

import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pillnotes.R
import com.example.pillnotes.databinding.FragmentMapsBinding
import com.example.pillnotes.domain.Constants
import com.example.pillnotes.domain.contactdoctor.location.SharedPreferenceLocationImpl
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    companion object {
        private const val CAMERA_ZOOM = 18f
    }

    private lateinit var binding: FragmentMapsBinding
    private var contactDoctor: ContactDoctor? = null
    private var locationClicked = Location(LocationManager.GPS_PROVIDER)
    private val yourLocation = Location(LocationManager.GPS_PROVIDER)

    private val callbackMap = OnMapReadyCallback { googleMap ->
        val prefs = SharedPreferenceLocationImpl(requireContext())
        if (contactDoctor!!.isLocation) {
            yourLocation.latitude = contactDoctor!!.location.latitude
            yourLocation.longitude = contactDoctor!!.location.longitude
        } else {
            yourLocation.latitude = prefs.getLocation(
                Constants.KEY_LOCATION, Constants.KEY_LOCATION_2
            ).first
            yourLocation.longitude = prefs.getLocation(
                Constants.KEY_LOCATION, Constants.KEY_LOCATION_2
            ).second
        }
        locationClicked.latitude = yourLocation.latitude
        locationClicked.longitude = yourLocation.longitude
        val latLng = LatLng(yourLocation.latitude, yourLocation.longitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(getString(R.string.you_are_here))
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, CAMERA_ZOOM))

        googleMap.setOnMarkerClickListener { marker ->
            Toast.makeText(requireContext(), "${marker.title}", Toast.LENGTH_LONG).show()
            true
        }

        googleMap.setOnMapClickListener { latLng ->
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            locationClicked.latitude = latLng.latitude
            locationClicked.longitude = latLng.longitude
            markerOptions.title("${contactDoctor!!.name} ${getString(R.string.here)}")
            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            googleMap.addMarker(markerOptions)
        }
    }
    private val callbackBackPress: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val bundle = bundleOf(
                Constants.CONTACT_CODE to arguments?.getParcelable<ContactDoctor>(Constants.CONTACT_CODE)
            )
            findNavController().navigate(R.id.googleMapsCheck_to_newContact, bundle)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            if (bundle.getParcelable<NoteTask>(Constants.CONTACT_CODE) != null) {
                contactDoctor = bundle.getParcelable(Constants.CONTACT_CODE)!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapGoogle) as SupportMapFragment?
        mapFragment?.getMapAsync(callbackMap)
    }

    override fun onStart() {
        super.onStart()
        binding.btnLocationOk.setOnClickListener {
            contactDoctor!!.location = locationClicked
            contactDoctor!!.isLocation = true
            val bundle = bundleOf(Constants.CONTACT_CODE to contactDoctor)
            findNavController().navigate(R.id.googleMapsCheck_to_newContact, bundle)
        }
        requireActivity().onBackPressedDispatcher.addCallback(callbackBackPress)
    }
}