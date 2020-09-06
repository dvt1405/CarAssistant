package ai.kitt.vnest.feature.screensettings.selectmaps

import ai.kitt.vnest.R
import ai.kitt.vnest.base.BaseFragment
import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference
import ai.kitt.vnest.databinding.FragmentMapsBinding
import ai.kitt.vnest.feature.screensettings.FragmentSettings
import ai.kitt.vnest.feature.screensettings.SettingsViewModel
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_maps.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class FragmentSelectMaps : BaseFragment(R.layout.fragment_maps) {
    val adapter by lazy {
        Adapter(VnestSharePreference.getInstance(requireContext()).mapAppId) {
            viewModel.settingLiveData.postValue(true)
            VnestSharePreference.getInstance(requireContext()).saveMapAppId(it.mapId)
            requireActivity().onBackPressed()
        }
    }
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentMapsBinding>(inflater,layoutRes,container,false)
        return binding.root
    }
    override fun initView(view: View) {
        view.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        view.recyclerView.adapter = adapter
        view.recyclerView.edgeEffectFactory = adapter.edgeEffectFactory
        view.recyclerView.addOnScrollListener(adapter.onScrollListener)
    }

    override fun initAction(view: View) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(view.appbar.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}