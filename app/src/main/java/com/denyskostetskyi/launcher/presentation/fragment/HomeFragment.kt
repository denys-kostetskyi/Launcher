package com.denyskostetskyi.launcher.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.FragmentHomeBinding
import com.denyskostetskyi.launcher.domain.model.SystemInfo
import com.denyskostetskyi.launcher.presentation.state.SystemInfoMapper
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastMapper
import com.denyskostetskyi.weatherforecast.library.domain.model.HourlyWeatherForecast
import com.denyskostetskyi.weatherforecast.library.domain.model.Location
import com.denyskostetskyi.weatherforecast.library.domain.model.Weather

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val systemInfoState = SystemInfoMapper().mapToState(
            SystemInfo(
                batteryLevel = 90,
                availableMemory = 102410241024,
                totalMemory = 204820482048,
                availableStorage = 102410241024,
                totalStorage = 204820482048,
            )
        )
        binding.systemInfoView.updateState(systemInfoState)
        val weatherForecastState = WeatherForecastMapper().mapToState(
            HourlyWeatherForecast(
                temperature = 0.0,
                weather = Weather.UNKNOWN,
                location = Location(
                    name = "Unknown",
                    latitude = 49.8397,
                    longitude = 24.0297
                ),
                dateTime = ""
            )
        )
        binding.weatherView.updateState(weatherForecastState)
        initViews()
    }

    private fun initViews() {
        initApplicationsButton()
    }

    private fun initApplicationsButton() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_applications)
        with(binding.buttonApplications) {
            textViewAppName.text = getString(R.string.applications)
            imageViewAppIcon.setImageDrawable(drawable)
            imageViewAppIcon.setOnClickListener { launchAppListFragment() }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
