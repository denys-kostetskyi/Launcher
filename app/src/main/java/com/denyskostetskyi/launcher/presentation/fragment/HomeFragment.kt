package com.denyskostetskyi.launcher.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.FragmentHomeBinding
import com.denyskostetskyi.launcher.presentation.viewmodel.HomeViewModel
import com.denyskostetskyi.launcher.presentation.viewmodel.SharedViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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
        viewModel.clock.observe(viewLifecycleOwner) {
            binding.clockView.updateState(it)
        }
        viewModel.weatherForecast.observe(viewLifecycleOwner) {
            binding.weatherView.updateState(it)
        }
        viewModel.systemInfo.observe(viewLifecycleOwner) {
            binding.systemInfoView.updateState(it)
        }
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
