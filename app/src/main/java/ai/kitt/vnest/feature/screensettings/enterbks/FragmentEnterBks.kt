package ai.kitt.vnest.feature.screensettings.enterbks

import ai.kitt.vnest.R
import ai.kitt.vnest.base.BaseFragment
import ai.kitt.vnest.basedata.database.sharepreference.VnestSharePreference
import ai.kitt.vnest.databinding.FragmentEnterBksBinding
import ai.kitt.vnest.feature.activitymain.ViewModel
import ai.kitt.vnest.feature.screensettings.SettingsViewModel
import ai.kitt.vnest.util.NavigationUtil
import ai.kitt.vnest.util.ConfirmDialog
import ai.kitt.vnest.util.KeyboardUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_enter_bks.view.*
import kotlinx.android.synthetic.main.fragment_enter_bks.view.appbar
import kotlinx.android.synthetic.main.toolbar.view.*

class FragmentEnterBks : BaseFragment(R.layout.fragment_enter_bks) {
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(SettingsViewModel::class.java) }
    private val parentViewModel by lazy { ViewModelProvider(requireActivity()).get(ViewModel::class.java) }
    lateinit var binding: FragmentEnterBksBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun initView(view: View) {
        val bks = VnestSharePreference.getInstance(requireContext()).bks
        if (!bks.isNullOrEmpty()) {
            view.mEditText.setText(bks)
        }

    }

    override fun initAction(view: View) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(view.appbar.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        view.btnSave.setOnClickListener {
            if (view.mEditText.text.isNullOrEmpty()) {
                ConfirmDialog.Builder(requireContext(), true)
                        .title(getString(R.string.alert_enter_bks_title))
                        .message(getString(R.string.alert_enter_bks_message))
                        .setOnDismissListener {
                            view.mEditText.requestFocus()
                        }
                        .build()
                        .show()
                return@setOnClickListener
            }
            VnestSharePreference.getInstance(requireContext()).saveBks(view.mEditText.text.toString())
            viewModel.settingLiveData.postValue(true)
            parentViewModel.sendCarBks(NavigationUtil.getDeviceId(requireActivity()), view.mEditText.text.toString())
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        KeyboardUtil.hideSoftKeyboard(requireActivity(),requireActivity().currentFocus)
        super.onDestroyView()
    }
}