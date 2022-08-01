package digikraft.project.digikraftbikestation.ui.fragment.bikeStations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import digikraft.project.digikraftbikestation.R
import digikraft.project.digikraftbikestation.data.Feature
import digikraft.project.digikraftbikestation.utils.Status
import kotlinx.android.synthetic.main.fragment_bike_station.*


@AndroidEntryPoint
class BikeStationFragment : Fragment() {


    private val viewModel: BikeStationViewModel by viewModels()

    private var adapter = BikeStationsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bike_station, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // calling get bike station api
        viewModel.getBikeStations("pub_transport", "stacje_rowerowe")

        adapterInitialization()
        searchResultObserver()
    }


    private fun adapterInitialization() {
        // init recycler view
        recyclerView_bike_stations.adapter = adapter

        //recycler view on click listener
        adapter.setOnItemClickListener(object : BikeStationsAdapter.ClickListener {
            override fun onClick(result: Feature, aView: View) {
                if (result != null) {
                    directStationDetailsPage(result)
                } else {
                    Snackbar.make(
                        main_layout,
                        getString(R.string.please_try_again),
                        Snackbar.LENGTH_LONG
                    ).show()
                }

            }
        })
    }

    private fun searchResultObserver() {
        // result observer from view model
        viewModel.searchResult.observe(viewLifecycleOwner, Observer { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    animationView.isVisible = false
                    _result._data?.let {
                        adapter.submitList(it.features)
                    }
                }
                Status.LOADING -> {
                    animationView.isVisible = true
                }
                Status.ERROR -> {
                    animationView.isVisible = false
                    _result.message?.let {
                        Snackbar.make(main_layout, it, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        })

    }

    fun directStationDetailsPage(result: Feature) {
        val bundle = bundleOf("Feature" to result)
        NavHostFragment.findNavController(this).navigate(R.id.fragment_bike_station_details, bundle)
    }


}