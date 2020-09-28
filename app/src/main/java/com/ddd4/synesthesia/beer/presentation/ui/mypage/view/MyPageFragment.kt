package com.ddd4.synesthesia.beer.presentation.ui.mypage.view

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.databinding.FragmentMyPageBinding
import com.ddd4.synesthesia.beer.ext.showSimpleDialog
import com.ddd4.synesthesia.beer.ext.showToast
import com.ddd4.synesthesia.beer.presentation.base.BaseFragment
import com.ddd4.synesthesia.beer.presentation.base.BaseItemsApdater
import com.ddd4.synesthesia.beer.presentation.ui.login.view.LoginActivity
import com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel.MyPageViewModel
import com.ddd4.synesthesia.beer.util.ItemClickListener
import com.ddd4.synesthesia.beer.util.SimpleCallback
import com.hyden.ext.start
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_global_toolbar.view.*

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {


    private val myPageViewModel by viewModels<MyPageViewModel>()
    private val nickNameCallback = object : SimpleCallback {
        override fun call(text: String) {
            binding.tvName.text = text
            myPageViewModel.updateUserInfo(text)
        }
    }
    private val itemClickListener by lazy {
        object : ItemClickListener {
            override fun <T> onItemClick(item: T?) {
                when ((item as? MyInfo)?.type) {
                    InfomationsType.ITEM -> { infomationsEvent(item.title) }
                    InfomationsType.LOGOUT -> { unConnected(getString(R.string.logout_message)) }
                    InfomationsType.UNLINK -> { unConnected(getString(R.string.unlink_message)) }
                    else -> {
                        val bundle = bundleOf(
                            getString(R.string.key_review) to binding.tvName.text
                        )
                        start<WriteNickNameActivity>(false,bundle)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        SimpleCallback.callback = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SimpleCallback.callback = nickNameCallback
        binding.apply {
            myPageVm = myPageViewModel
            itemClickListener = this@MyPageFragment.itemClickListener
            userAdapter = BaseItemsApdater(R.layout.layout_my_page, BR.my, itemClickListener).apply { updateItems(myPageViewModel.generateInfoList()) }
            includeToolbar.toolbar.setNavigationOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        myPageViewModel.isUnConnected.observe(viewLifecycleOwner, Observer {
            if (it) {
                preference.clear()
                start<LoginActivity>(true, bundleOf(Pair(getString(R.string.is_show_snackbar), getString(R.string.success_logout))))
            }
        })
    }

    private fun infomationsEvent(section: String) {
        context?.showToast(section)
        when (section) {
            InfomationsData.ACTIVE.title -> {

            }
            InfomationsData.STAR.title -> {

            }
            InfomationsData.REVIEW.title -> {

            }
            InfomationsData.HELP.title -> {

            }
            InfomationsData.NOTICE.title -> {

            }
            InfomationsData.CONTACT.title -> {

            }
            InfomationsData.SERVICE_INFO.title -> {

            }
            InfomationsData.TERMS_OF_USE.title -> {

            }
            InfomationsData.SETTING.title -> {

            }
            InfomationsData.PUSH.title -> {

            }
            InfomationsData.LOGOUT.title -> {

            }
        }
    }

    private fun unConnected(message : String) {
        context?.showSimpleDialog(message = message) {
            when(message) {
                getString(R.string.logout_message) -> {
                    myPageViewModel.logout()
                }
                getString(R.string.unlink_message) -> {
                    myPageViewModel.unlink()
                }
            }
        }
    }
}