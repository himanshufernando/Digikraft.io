package digikraft.project.digikraftbikestation.ui.fragment.bikestationdetails

import android.app.Activity
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import digikraft.project.digikraftbikestation.R
import digikraft.project.digikraftbikestation.data.Feature
import digikraft.project.digikraftbikestation.databinding.FragmentBikeStationDetailsBinding


class BikeStationDetailsFragment : Fragment() {


    lateinit var binding: FragmentBikeStationDetailsBinding


    private val feature: Feature by lazy {
        arguments?.getParcelable<Feature>(FEATURE)
            ?: throw IllegalArgumentException("Missing arg deliveryDate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bike_station_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feature=feature

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                context as Activity,
                R.raw.new_map_style
            )
        )

        val mapLocation =  LatLng(feature.geometry.coordinates[1],feature.geometry.coordinates[0])
        googleMap.addMarker(
            MarkerOptions()
                .position(mapLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_bike))
        )

        val countMarkerLon = (feature.geometry.coordinates[0]+0.000400)

         googleMap.addMarker(
             MarkerOptions()
                 .position(LatLng(feature.geometry.coordinates[1], countMarkerLon))
                 .icon(writeOnDrawable(feature.properties.free_racks)?.let {
                     BitmapDescriptorFactory.fromBitmap(
                         it
                     )
                 })
         )

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapLocation, MAP_ZOOM))


    }


    private fun writeOnDrawable( text: String?): Bitmap? {

        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color =ContextCompat.getColor(requireContext(),R.color.light_green)
        paint.textSize = MAP_TEXT_SIZE
        var drawnBitmap: Bitmap? = null

        try {
            drawnBitmap = Bitmap.createBitmap(150, 150, Bitmap.Config.ARGB_8888)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val canvas = drawnBitmap?.let { Canvas(it) }
        drawnBitmap?.height?.div(2)?.let { canvas?.drawText(text!!, 0F, it.toFloat(), paint) }
        return drawnBitmap?.let { Bitmap.createBitmap(it) }
    }
    companion object {
        private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE = 34
        private const val FEATURE = "Feature"
        private const val MAP_TEXT_SIZE = 70F
        private const val MAP_ZOOM = 17F
    }

}