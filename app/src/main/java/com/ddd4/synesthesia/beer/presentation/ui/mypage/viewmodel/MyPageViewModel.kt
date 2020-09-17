package com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository,
    private val beerRepository: BeerRepository
) : BaseViewModel() {

    val isUnConnected = SingleLiveEvent<Boolean>()

    private val _userInfo = MutableLiveData<User?>()
    val userInfo : LiveData<User?> get() = _userInfo

    init {
        me()
    }

    fun generateInfoList() : List<MyInfo> = arrayListOf(
        MyInfo(InfomationsData.ACTIVE.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.STAR.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.REVIEW.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.HELP.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.NOTICE.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.CONTACT.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.SERVICE_INFO.title, InfomationsType.HEADER),
        MyInfo(InfomationsData.TERMS_OF_USE.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.SETTING.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.PUSH.title, InfomationsType.ITEM),
        MyInfo(InfomationsData.LOGOUT.title, InfomationsType.LOGOUT),
        MyInfo(InfomationsData.UNLINK.title, InfomationsType.UNLINK)
    )

//    private fun me() = loginRepository.me {
//        _userInfo.value = it
//    }

    private fun me() {
        viewModelScope.launch {
            beerRepository.getUserInfo()
        }
    }

    fun logout() = loginRepository.logout {
        isUnConnected.call(it)
    }

    fun unlink() = loginRepository.unlink {
        isUnConnected.call(it)
    }

    override fun onCleared() {
        super.onCleared()
    }
}