package com.ddd4.synesthesia.beer.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.util.SharedPreferenceProvider
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding>(private val layoutId : Int) : Fragment() {

    lateinit var binding : B
    @Inject
    lateinit var preference : SharedPreferenceProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserving()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,layoutId,container,false)
        binding.lifecycleOwner = this
        return binding.root
    }

    open fun initBind() { }
    open fun initObserving() { }
}