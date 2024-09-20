package com.denyskostetskyi.launcher.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.FragmentAppListBinding
import com.denyskostetskyi.launcher.domain.model.AppItem
import com.denyskostetskyi.launcher.presentation.adapter.AppListAdapter

class AppListFragment : Fragment() {
    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentAppListBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initRecyclerView()
        binding.buttonCloseAppList.setOnClickListener {
            closeAppListFragment()
        }
    }

    private fun initRecyclerView() {
        val adapter = AppListAdapter()
        adapter.onAppClicked = ::launchApp
        adapter.submitList(mockAppList)
        binding.recyclerViewAppList.adapter = adapter
    }

    private val mockAppList: List<AppItem> get() {
        val appList = mutableListOf<AppItem>()
        val appIcon = ContextCompat.getDrawable(requireContext(), R.mipmap.ic_launcher_round)
        for (i in 0..105) {
            val appItem = AppItem(
                appIcon = appIcon!!,
                appName = "Application $i",
                packageName = "com.denyskostetskyi.application$i"
            )
            appList.add(appItem)
        }
        return appList
    }

    private fun launchApp(appItem: AppItem) {
        Log.d(TAG, "Launching ${appItem.packageName}")
    }

    private fun closeAppListFragment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "AppListFragment"

        @JvmStatic
        fun newInstance() = AppListFragment()
    }
}
