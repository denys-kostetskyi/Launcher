package com.denyskostetskyi.launcher.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.presentation.view.ClockView
import com.denyskostetskyi.launcher.presentation.view.SystemInfoView
import com.denyskostetskyi.launcher.presentation.view.WeatherForecastView
import com.denyskostetskyi.launcher.presentation.viewmodel.HomeViewModel
import com.denyskostetskyi.launcher.presentation.viewmodel.SharedViewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        initApplicationsButton()
    }

    private fun observeViewModel() {
        val clockView = requireView().findViewById<ClockView>(R.id.clockView)
        val weatherView = requireView().findViewById<WeatherForecastView>(R.id.weatherView)
        val systemInfoView = requireView().findViewById<SystemInfoView>(R.id.systemInfoView)
        viewModel.clock.observe(viewLifecycleOwner) {
            clockView.updateState(it)
        }
        viewModel.weatherForecast.observe(viewLifecycleOwner) {
            weatherView.updateState(it)
        }
        viewModel.systemInfo.observe(viewLifecycleOwner) {
            systemInfoView.updateState(it)
        }
    }

    private fun initApplicationsButton() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_applications)
        val buttonApplications = requireView().findViewById<View>(R.id.buttonApplications)
        val textViewAppName = buttonApplications.findViewById<TextView>(R.id.textViewAppName)
        textViewAppName.text = getString(R.string.applications)
        val imageViewAppIcon = buttonApplications.findViewById<ImageView>(R.id.imageViewAppIcon)
        with(imageViewAppIcon) {
            setImageDrawable(drawable)
            setOnClickListener { launchAppListFragment() }
        }
    }

    private fun launchAppListFragment() {
        val appListFragment = AppListFragment.newInstance()
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.bottom_in,
                R.anim.top_out,
                R.anim.top_in,
                R.anim.bottom_out,
            )
            .replace(R.id.container, appListFragment)
            .addToBackStack(null)
            .commit()
    }
}
