package com.ddd4.synesthesia.beer.presentation.ui.mypage.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ddd4.synesthesia.beer.data.source.local.InfomationsData
import com.ddd4.synesthesia.beer.data.source.local.MyInfo
import com.ddd4.synesthesia.beer.domain.repository.BeerRepository
import com.ddd4.synesthesia.beer.domain.repository.LoginRepository
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.util.AppConfig
import com.ddd4.synesthesia.beer.util.SingleLiveEvent
import com.kakao.sdk.user.model.User
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(
    private val loginRepository : LoginRepository,
    private val beerRepository: BeerRepository,
    private val appConfig : AppConfig
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
        // 내 활동
        MyInfo(InfomationsData.ACTIVE.title, InfomationsData.ACTIVE.type),
        MyInfo(InfomationsData.REVIEW.title, InfomationsData.REVIEW.type),
        MyInfo(InfomationsData.RECENTLY_VISIT_TIME.title, InfomationsData.RECENTLY_VISIT_TIME.type,appConfig.lastVisitTime),
        // 고객센터 & 도움말
        MyInfo(InfomationsData.HELP.title, InfomationsData.HELP.type),
        MyInfo(InfomationsData.NOTICE.title, InfomationsData.NOTICE.type),
        MyInfo(InfomationsData.CONTACT.title, InfomationsData.CONTACT.type),
        // 설정
        MyInfo(InfomationsData.SETTING.title, InfomationsData.SETTING.type),
        MyInfo(InfomationsData.PUSH.title, InfomationsData.PUSH.type),
        // 서비스 정보
        MyInfo(InfomationsData.SERVICE_INFO.title, InfomationsData.SERVICE_INFO.type),
        MyInfo(InfomationsData.TERMS_OF_USE.title, InfomationsData.TERMS_OF_USE.type),
        MyInfo(InfomationsData.RELEASE_NOTE.title, InfomationsData.RELEASE_NOTE.type),
        MyInfo(InfomationsData.OPEN_SOURCE_LIB.title, InfomationsData.OPEN_SOURCE_LIB.type),
        MyInfo(InfomationsData.PLAY_STORE.title, InfomationsData.PLAY_STORE.type),
        MyInfo(InfomationsData.APP_VERSION.title, InfomationsData.APP_VERSION.type,appConfig.version),
        // 로그아웃 & 회원탈퇴
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