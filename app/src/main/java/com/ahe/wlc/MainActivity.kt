package com.ahe.wlc

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import java.io.IOException
import java.io.InputStream
import com.google.gson.Gson
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import com.ahe.wlc.CustomViews.IndefinitePagerIndicator
import com.ahe.wlc.CustomViews.Slider.CardScaleHelper
import com.ahe.wlc.CustomViews.Slider.util.BlurBitmapUtils
import com.ahe.wlc.CustomViews.Slider.util.ViewSwitchUtils


class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mBlurView: ImageView? = null
    private var mList = java.util.ArrayList<Int>()
    private var mCardScaleHelper: CardScaleHelper? = null
    private var mBlurRunnable: Runnable? = null
    private var mLastPos = -1
    private lateinit var pagerIndicator: IndefinitePagerIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cityList=findViewById<ListView>(R.id.listview_city)

        //val myJson = inputStreamToString(this.resources.openRawResource(R.raw.camsjson))
        val myJson = inputStreamToString(this.resources.openRawResource(R.raw.usjson))
        val arr = Gson().fromJson(myJson, Array<City>::class.java)





        var ulkeler=ArrayList<String>()

        for (city in arr)
        {
            ulkeler.add(city.CityName)
        }



        val veriAdaptoru = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ulkeler)


        cityList.setAdapter(veriAdaptoru)

        cityList.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var eleman=arr[p2]
                var intent = Intent(this@MainActivity,CamsActivity::class.java)
                intent.putExtra("city",Gson().toJson(eleman, City::class.java))
                startActivity(intent)
            }

        }

        init()
        var mMainLayout =  findViewById<ConstraintLayout>(R.id.main_root)
        var mMainLayoutAnim: AnimationDrawable=mMainLayout!!.background as AnimationDrawable
        mMainLayoutAnim!!.setEnterFadeDuration(2000)
        mMainLayoutAnim!!.setExitFadeDuration(4000)
        mMainLayoutAnim!!.start()

    }

    fun inputStreamToString(inputStream: InputStream): String {
        try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            return String(bytes)
        } catch (e: IOException) {
            return ""
        }

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
                    //notifyBackgroundChange()
                }
            }
        })

        //notifyBackgroundChange()
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
        mBlurView!!.postDelayed(mBlurRunnable, /*500*/500)
    }
}
