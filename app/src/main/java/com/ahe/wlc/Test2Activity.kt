package com.ahe.wlc

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.ahe.wlc.CustomViews.IndefinitePagerIndicator
import com.ahe.wlc.CustomViews.Slider.CardScaleHelper
import com.ahe.wlc.CustomViews.Slider.SpeedRecyclerView
import com.ahe.wlc.CustomViews.Slider.util.BlurBitmapUtils
import com.ahe.wlc.CustomViews.Slider.util.ViewSwitchUtils
import java.util.ArrayList

class Test2Activity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mBlurView: ImageView? = null
    private var mList = ArrayList<Int>()
    private var mCardScaleHelper: CardScaleHelper? = null
    private var mBlurRunnable: Runnable? = null
    private var mLastPos = -1
    private lateinit var pagerIndicator: IndefinitePagerIndicator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        init()

    }

    private fun init() {
        for (i in 0..9) {
            mList.add(R.drawable.pic4)
            mList.add(R.drawable.pic5)
            mList.add(R.drawable.pic6)
        }

        mRecyclerView = findViewById(R.id.recyclerView_speed)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView!!.layoutManager = linearLayoutManager
        mRecyclerView!!.adapter = CardAdapter(mList)
        // mRecyclerView绑定scale效果

        pagerIndicator = findViewById(R.id.recyclerview_pager_indicator)
        pagerIndicator.attachToRecyclerView(mRecyclerView!!)
        pagerIndicator.visibility = View.VISIBLE

        mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper!!.currentItemPos = 0
        mCardScaleHelper!!.attachToRecyclerView(mRecyclerView)

        initBlurBackground()
    }

    private fun initBlurBackground() {
        mBlurView = findViewById<ImageView>(R.id.blurView)
        mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    notifyBackgroundChange()
                }
            }
        })

        notifyBackgroundChange()
    }

    private fun notifyBackgroundChange() {
        if (mLastPos == mCardScaleHelper!!.getCurrentItemPos()) return
        mLastPos = mCardScaleHelper!!.getCurrentItemPos()
        val resId = mList[mCardScaleHelper!!.getCurrentItemPos()]
        mBlurView!!.removeCallbacks(mBlurRunnable)
        mBlurRunnable = Runnable {
            val bitmap = BitmapFactory.decodeResource(resources, resId)
            ViewSwitchUtils.startSwitchBackgroundAnim(mBlurView, BlurBitmapUtils.getBlurBitmap(mBlurView!!.context, bitmap, 15))
        }
        mBlurView!!.postDelayed(mBlurRunnable, /*500*/0)
    }
}
