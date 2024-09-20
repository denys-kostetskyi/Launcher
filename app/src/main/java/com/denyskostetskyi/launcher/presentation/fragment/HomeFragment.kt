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
import com.denyskostetskyi.launcher.domain.model.Weather
import com.denyskostetskyi.launcher.domain.model.WeatherForecast
import com.denyskostetskyi.launcher.presentation.state.SystemInfoMapper
import com.denyskostetskyi.launcher.presentation.state.WeatherForecastMapper

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
        val systemInfoState = SystemInfoMapper().mapToState(SystemInfo(
            batteryLevel = 90,
            availableMemory = 6.5,
            totalMemory = 16.0,
            availableStorage = 100.5,
            totalStorage = 236.5
        ))
        binding.systemInfoView.updateState(systemInfoState)
        val weatherForecastState = WeatherForecastMapper().mapToState(WeatherForecast(
            temperature = 15,
            weather = Weather.CLEAR,
            location = "Lviv"
        ))
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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
