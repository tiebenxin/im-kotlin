package com.abc.core.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

/**
 *@author Liszt
 *@date 2020/11/11
 *Description
 */
abstract class BaseVMFragment<T : ViewModel, M : ViewDataBinding> : BaseFragment() {
    lateinit var mViewModel: T
    lateinit var mViewBinding: M
    lateinit var inflater: LayoutInflater

    abstract fun getLayoutResId(): Int

    abstract fun init()

    abstract fun initListener()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        initViewModel()
        return mViewBinding.root
    }

    @SuppressLint("NewApi")
    private fun initViewModel() {
        val clazz =
            this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<T>
        mViewModel = getViewModel<T>(clazz) //koin 注入

    }
}