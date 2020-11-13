package com.abc.kim.ui.main

import com.abc.core.base.BaseVMFragment
import com.abc.kim.R
import com.abc.kim.databinding.FragmentMsgTabBinding

/**
 *@author Liszt
 *@date 2020/11/11
 *Description 主页message tab页
 */
class MessageFragment : BaseVMFragment<MessageViewModel, FragmentMsgTabBinding>() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_msg_tab
    }

    override fun init() {
    }

    override fun initListener() {
    }


}