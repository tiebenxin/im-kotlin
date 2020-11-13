package com.abc.core.widgt.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.abc.core.R


/***
 * 加载视图
 *
 * @author 姜永健
 * @date 2016年3月1日
 */
class LoadView : RelativeLayout {

    private lateinit var pb: View
    private lateinit var imgNoData: ImageView
    private lateinit var imgNoNet: ImageView

    @SuppressLint("InflateParams")
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rootView = inflater.inflate(R.layout.view_load_data, null)
        pb = rootView.findViewById(R.id.prog)
        imgNoData = rootView.findViewById(R.id.img_no_data)
        imgNoNet = rootView.findViewById(R.id.img_no_net)

        addView(rootView)
        rootView.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }

    constructor(context: Context) : super(context)

    /***
     * 状态:无数据
     *
     * @param resId 背景图
     */
    fun setStateNoData(resId: Int) {
        Log.e("noDataRid", "===resId>$resId")
        imgNoData.setImageResource(resId)

        imgNoData.visibility = View.VISIBLE
        imgNoNet.visibility = View.GONE
        pb.visibility = View.GONE
        this.visibility = View.VISIBLE
    }


    /***
     * 状态:无网络
     */
    fun setStateNoNet(oc: View.OnClickListener) {
        imgNoNet.visibility = View.VISIBLE
        imgNoData.visibility = View.GONE
        this.setOnClickListener(oc)
        pb.visibility = View.GONE
        this.visibility = View.VISIBLE
    }

    /***
     * 状态:加载中
     */
    fun setStateLoading() {
        imgNoNet.visibility = View.GONE
        imgNoData.visibility = View.GONE
        pb.visibility = View.VISIBLE
        this.visibility = View.VISIBLE
    }

    /***
     * 状态:正常
     */
    fun setStateNormal() {
        imgNoNet.visibility = View.GONE
        imgNoData.visibility = View.GONE
        pb.visibility = View.GONE
        this.visibility = View.GONE
    }
}
