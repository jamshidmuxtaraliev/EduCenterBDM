package uz.bdmgroup.ilmizlab.screens.maps


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.databinding.FragmentMapsBinding
import uz.bdmgroup.ilmizlab.model.*
import uz.bdmgroup.ilmizlab.screens.eduCenterDetail.EduCenterDetailActivity
import uz.bdmgroup.ilmizlab.screens.search_category.regions.FanSearchActivity
import uz.bdmgroup.ilmizlab.screens.search_category.regions.RegionActivity
import uz.bdmgroup.ilmizlab.utils.Constants
import java.io.Serializable


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    lateinit var viewModel: MainViewModel
    var selectedItem: EduCenterModel?=null

    var filter= LCFilterModel(
        0, 0, 0, 0, "", "",
        0, 0.0, 0.0, false,
    )

    var myMarkers: Marker? = null
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    var locationClient: FusedLocationProviderClient? = null
    var selectDistrict:DistrictSearchModel?=null
    var selectSciences:SciencesModel?=null

    var districtResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.data!=null && it.resultCode==RESULT_OK && it.data!!.hasExtra(Constants.EXTRA_DATA)){
            selectDistrict=it.data!!.getSerializableExtra(Constants.EXTRA_DATA) as DistrictSearchModel
            titleViloyatMap.text=selectDistrict!!.name_uz
            filter.district_id=selectDistrict!!.id
            loadData()
        }
    }
     var sciencesResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
         if (it.data!=null && it.resultCode== RESULT_OK && it.data!!.hasExtra(Constants.EXTRA_DATA_FAN)){
             selectSciences=it.data!!.getSerializableExtra(Constants.EXTRA_DATA_FAN) as SciencesModel
             titleFan.text=selectSciences!!.title
             filter.science_id=selectSciences!!.id
             loadData()
         }
     }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fallasMap) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        locationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        cardViewRegionMap.setOnClickListener {
            val intent=Intent(requireActivity(), RegionActivity::class.java)
            if (selectDistrict!=null){
                intent.putExtra(Constants.EXTRA_DATA, selectDistrict as Serializable)
            }
            districtResultLauncher.launch(intent)
        }

        cardviewFan.setOnClickListener {
            val intent=Intent(requireActivity(), FanSearchActivity::class.java)
            if (selectSciences!=null){
                intent.putExtra(Constants.EXTRA_DATA_FAN, selectSciences as Serializable)
            }
            sciencesResultLauncher.launch(intent)
        }

        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.progress.observe(viewLifecycleOwner, Observer {
            flProgressMap.visibility=if(it) View.VISIBLE else View.GONE
        })

        viewModel.topEduCenterData.observe(requireActivity(), Observer {
            googleMap.clear()
            it.forEach {
                myMarkers = googleMap.addMarker(
                    MarkerOptions().position(LatLng(it.latitude, it.longitude))
                        .title(it.name+"id=${it.id}")
                        .snippet(it.comment)
                )
            }
            googleMap.setOnMarkerClickListener { markerItems->
                selectedItem=it.filter { markerItems.title==(it.name+"id=${it.id}") }.firstOrNull()
                showInfo()
                false
            }
        })

        loadData()
    }

    private fun showInfo() {
        if (selectedItem!=null){
            infoContainer.visibility=View.VISIBLE
            infoName.text= selectedItem!!.name
            infoComment.text=selectedItem!!.comment
            infoRating.text=selectedItem!!.rating.toString().subSequence(0, 3)
            Glide.with(this).load(Constants.HOSTING_IMAGE+selectedItem!!.main_image).into(infoImage)

            infoGoogleMap.setOnClickListener {
                val uri: Uri = Uri.parse("http://maps.google.com/maps?q=${selectedItem!!.latitude}, ${selectedItem!!.longitude}")
                val mapIntent= Intent(Intent.ACTION_VIEW, uri)
                startActivity(mapIntent)
            }
            infoDetail.setOnClickListener {
                val intent=Intent(requireActivity(), EduCenterDetailActivity::class.java)
                intent.putExtra("go_extra", selectedItem)
                startActivity(intent)
            }
        }else{
            infoContainer.visibility=View.GONE
        }
    }

    override fun onMapReady(map: GoogleMap) {

        map.let {
            googleMap = it
            googleMap.resetMinMaxZoomPreference()
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1002
                )
                return
            }
            googleMap.isMyLocationEnabled = true
            locationClient?.lastLocation?.addOnCompleteListener {
                if(it.result == null){
                    return@addOnCompleteListener
                }
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(it.result.latitude, it.result.longitude),
                        14.0F
                    )
                )
            }
        }
    }

    fun loadData() {
        viewModel.getEduCenter(filter)
        viewModel.getCategories()
        viewModel.getRegions()
    }
}