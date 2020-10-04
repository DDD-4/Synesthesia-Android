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

    private val _nickName = MutableLiveData<String>()
    val nickName : LiveData<String> get() = _nickName

    init {
        me()
        userInfo()
    }

    fun generateInfoList() : List<MyInfo> = arrayListOf(
        MyInfo(InfomationsData.ACTIVE.title, InfomationsData.ACTIVE.type),
        MyInfo(InfomationsData.STAR_REVIEW.title, InfomationsData.STAR_REVIEW.type),
        MyInfo(InfomationsData.HELP.title, InfomationsData.HELP.type),
        MyInfo(InfomationsData.NOTICE.title, InfomationsData.NOTICE.type),
        MyInfo(InfomationsData.CONTACT.title, InfomationsData.CONTACT.type),
        MyInfo(InfomationsData.SERVICE_INFO.title, InfomationsData.SERVICE_INFO.type),
        MyInfo(InfomationsData.TERMS_OF_USE.title, InfomationsData.TERMS_OF_USE.type),
        MyInfo(InfomationsData.SETTING.title, InfomationsData.SETTING.type),
        MyInfo(InfomationsData.PUSH.title, InfomationsData.PUSH.type),
        MyInfo(InfomationsData.LOGOUT.title, InfomationsData.LOGOUT.type),
        MyInfo(InfomationsData.UNLINK.title, InfomationsData.UNLINK.type)
    )

    private fun me() = loginRepository.me {
        _userInfo.value = it
    }

    private fun userInfo() {
        viewModelScope.launch {
            _nickName.value = beerRepository.getUserInfo()?.nickname
        }
    }

    fun updateUserInfo(nickName : String?) {
        viewModelScope.launch {
            beerRepository.postUserInfo(nickName)
        }
    }

//    private fun me() {
//        viewModelScope.launch {
//            beerRepository.getUserInfo()
//        }
//    }

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