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
import com.example.pillnotes.domain.model.ContactDoctor
import com.example.pillnotes.domain.model.NoteTask
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val CAMERA_ZOOM = 18f

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private var contactDoctor: ContactDoctor? = null
    private var locationClicked = Location(LocationManager.GPS_PROVIDER)

    private val callbackMap = OnMapReadyCallback { googleMap ->
        val yourLocation = LatLng(
            contactDoctor!!.location.latitude,
            contactDoctor!!.location.longitude
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(yourLocation)
                .title(getString(R.string.you_are_here))
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(yourLocation, CAMERA_ZOOM))

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
    private val callbackBack: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val bundle = bundleOf(
                Constants.CONTACT_CODE to arguments?.getParcelable<ContactDoctor>(Constants.CONTACT_CODE)
            )
            findNavController().navigate(R.id.googleMapsCheck_to_newContact, bundle)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            if (bundle.getParcelable<NoteTask>(Constants.CONTACT_CODE) != null) {
                contactDoctor = bundle.getParcelable(Constants.CONTACT_CODE)!!
            }
        }
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
        requireActivity().onBackPressedDispatcher.addCallback(callbackBack)
    }
}