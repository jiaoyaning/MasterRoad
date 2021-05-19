package com.jyn.common.Base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by jiaoyaning on 2021/5/19.
 */
class BaseFragment<dataBinding : ViewDataBinding>(var layoutId: Int = 0) : Fragment() {
    lateinit var binding: dataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, null, false)
        return binding.root
    }
}