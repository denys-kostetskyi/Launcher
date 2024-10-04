package com.denyskostetskyi.launcher.presentation.fragment

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.denyskostetskyi.launcher.R
import com.denyskostetskyi.launcher.databinding.FragmentAppListBinding
import com.denyskostetskyi.launcher.domain.model.AppItem
import com.denyskostetskyi.launcher.presentation.adapter.AdaptiveGridLayoutManager
import com.denyskostetskyi.launcher.presentation.adapter.AppListAdapter
import com.denyskostetskyi.launcher.presentation.viewmodel.AppListViewModel
import com.denyskostetskyi.launcher.presentation.viewmodel.SharedViewModel

class AppListFragment : Fragment() {
    private var _binding: FragmentAppListBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentAppListBinding is null")
    private val viewModel: AppListViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }
    private val appListAdapter = AppListAdapter(::getAppIcon, ::startAppActivity)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAppListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeViewModel()
    }

    private fun initViews() {
        initRecyclerView()
        initCloseButton()
    }

    private fun observeViewModel() {
        viewModel.appList.observe(viewLifecycleOwner) {
            appListAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        val columnWidth = resources.getDimension(R.dimen.app_item_width)
        with(binding) {
            recyclerViewAppList.adapter = appListAdapter
            recyclerViewAppList.layoutManager =
                AdaptiveGridLayoutManager(requireContext(), columnWidth)
        }
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

    private fun getAppIcon(appItem: AppItem): Drawable {
        val icon = try {
            requireContext().packageManager.getApplicationIcon(appItem.packageName)
        } catch (e: NameNotFoundException) {
            ContextCompat.getDrawable(requireContext(), R.mipmap.ic_launcher_round)
        }
        return icon!!
    }

    private fun startAppActivity(appItem: AppItem) {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            component = ComponentName(appItem.packageName, appItem.activityName)
        }
        startActivity(intent)
    }

    companion object {
        private const val TAG = "AppListFragment"

        @JvmStatic
        fun newInstance() = AppListFragment()
    }
}
