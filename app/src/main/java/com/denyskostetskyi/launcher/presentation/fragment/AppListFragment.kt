package com.denyskostetskyi.launcher.presentation.fragment

import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
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
import com.denyskostetskyi.launcher.presentation.adapter.AdaptiveGridLayoutManager
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
        initCloseButton()
    }

    private fun initRecyclerView() {
        val adapter = AppListAdapter(getAppIcon = ::getAppIcon, onAppClicked = ::launchApp)
        adapter.submitList(mockAppList)
        binding.recyclerViewAppList.adapter = adapter
        val columnWidth = resources.getDimension(R.dimen.app_item_width)
        binding.recyclerViewAppList.layoutManager =
            AdaptiveGridLayoutManager(requireContext(), columnWidth)
    }

    private fun getAppIcon(appItem: AppItem): Drawable {
        val icon = try {
            requireContext().packageManager.getApplicationIcon(appItem.packageName)
        } catch (e: NameNotFoundException) {
            ContextCompat.getDrawable(requireContext(), R.mipmap.ic_launcher_round)
        }
        return icon!!
    }

    private val mockAppList: List<AppItem>
        get() {
            val appList = mutableListOf<AppItem>()
            for (i in 0..105) {
                val appItem = AppItem(
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

    private fun initCloseButton() {
        val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_close)
        with(binding.buttonCloseAppList) {
            textViewAppName.text = getString(R.string.close)
            imageViewAppIcon.setImageDrawable(drawable)
            imageViewAppIcon.setOnClickListener { closeAppListFragment() }
        }
    }

    private fun closeAppListFragment() {
        parentFragmentManager.popBackStack()
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
