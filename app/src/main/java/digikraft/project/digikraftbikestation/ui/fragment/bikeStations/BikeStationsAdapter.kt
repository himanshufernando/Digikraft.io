package digikraft.project.digikraftbikestation.ui.fragment.bikeStations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import digikraft.project.digikraftbikestation.data.Feature
import digikraft.project.digikraftbikestation.databinding.ListBikeStationBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BikeStationsAdapter @Inject constructor() :
    ListAdapter<Feature, RecyclerView.ViewHolder>(BIKE_STATIONS_COMPARATOR) {

    companion object {
        private val BIKE_STATIONS_COMPARATOR = object : DiffUtil.ItemCallback<Feature>() {
            override fun areItemsTheSame(oldItem: Feature, newItem: Feature): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Feature, newItem: Feature): Boolean =
                oldItem == newItem
        }
    }

    lateinit var mClickListener: ClickListener


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var item = getItem(position)
        (holder as ItemViewHolder).bind(item!!)
    }

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(selectedPlanet: Feature, aView: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListBikeStationBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            mClickListener
        )

    }

    class ItemViewHolder(
        private val binding: ListBikeStationBinding,
        var mClickListener: ClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.feature?.let { feature ->
                    mClickListener.onClick(feature, it)
                }
            }
        }

        fun bind(rec: Feature) {
            binding.apply {
                feature = rec
                executePendingBindings()
            }
        }
    }

}