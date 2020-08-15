package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.ext.dateFormat
import com.ddd4.synesthesia.beer.ext.showSnackBar
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding>(private val layoutId : Int) : AppCompatActivity() {

    @Inject lateinit var preference : SharedPreferenceProvider
    lateinit var binding : B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId, null, false)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        initBind()
        initObserving()
    }

    fun showRecentlyVisitTime() {
        preference.getPreferenceString(getString(R.string.key_recently_visit))?.let {
            when(it.isEmpty()) {
                false -> {
                    dateFormat().run {
                        binding.root.showSnackBar("최근 접속일 : ${it}")
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