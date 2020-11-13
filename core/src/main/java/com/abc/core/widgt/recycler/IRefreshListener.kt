package com.abc.core.widgt.recycler

/**
 * @author Liszt
 * @date 2020/8/28
 * Description 刷新listener
 */
interface IRefreshListener {
    //下拉刷新
    fun onRefresh()

    //上拉加载更多
    fun loadMore()
}
