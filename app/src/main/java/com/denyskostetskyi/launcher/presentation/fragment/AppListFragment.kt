package com.denyskostetskyi.launcher.presentation.fragment

import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import android.os.Bundle
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

    private var appManager: AppManager? = null
    private val appListAdapter = AppListAdapter(::getAppIcon) { app -> appManager?.launchApp(app) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppManager) {
            appManager = context
        } else {
            throw RuntimeException(
                "Activity ${context::class.java.canonicalName} should implement AppListProvider"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onResume() {
        super.onResume()
        appListAdapter.submitList(appManager?.appList)
    }

    private fun initViews() {
        initRecyclerView()
        initCloseButton()
    }

    private fun initRecyclerView() {
        binding.recyclerViewAppList.adapter = appListAdapter
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

    interface AppManager {
        val appList: List<AppItem>

        fun launchApp(app: AppItem)
    }

    companion object {
        private const val TAG = "AppListFragment"

        @JvmStatic
        fun newInstance() = AppListFragment()
    }
}
