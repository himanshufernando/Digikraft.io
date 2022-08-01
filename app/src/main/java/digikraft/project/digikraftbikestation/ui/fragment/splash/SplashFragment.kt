package digikraft.project.digikraftbikestation.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import digikraft.project.digikraftbikestation.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {


    lateinit var job: Job


    companion object{
        const val SPLASH_TIME=3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onResume() {
        super.onResume()

        //3000 second delay to direct bike station fragment
        job = lifecycleScope.launch(context = Dispatchers.Main) {
            delay(SPLASH_TIME)
            goToBikeStation()
        }
    }

    override fun onStop() {
        super.onStop()
        job.cancel()
    }

    private fun goToBikeStation() {
        NavHostFragment.findNavController(this).navigate(R.id.fragment_splash_to_bike_station)
    }
}