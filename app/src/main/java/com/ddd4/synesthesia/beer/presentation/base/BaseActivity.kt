package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.ext.dateFormat
import com.ddd4.synesthesia.beer.ext.showSnackBar
import com.ddd4.synesthesia.beer.util.AppConfig
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding>(private val layoutId : Int) : AppCompatActivity() {

    @Inject lateinit var preference : SharedPreferenceProvider
    @Inject lateinit var appConfig : AppConfig
    private var backKeyPressedTime = 0L
    lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initBind()
        initObserving()
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate()) {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis()
                binding.root.showSnackBar(getString(R.string.back_press))
                return
            }
        }

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish()
        }
    }

    fun showRecentlyVisitTime() {
        preference.getPreferenceString(getString(R.string.key_recently_visit))?.let {
            when(it.isEmpty()) {
                false -> {
                    dateFormat().run {
                        binding.root.showSnackBar("최근 접속일 : ${it}")
                        appConfig.lastVisitTime = it
                        preference.setPreference(getString(R.string.key_recently_visit),format(System.currentTimeMillis()))
                    }
                }
                true -> {
                    dateFormat().run {
                        binding.root.showSnackBar("환영합니다")
                        preference.setPreference(getString(R.string.key_recently_visit),format(System.currentTimeMillis()))
                    }
                }
            }
        }
    }

    open fun initBind() { }
    open fun initObserving() { }
}