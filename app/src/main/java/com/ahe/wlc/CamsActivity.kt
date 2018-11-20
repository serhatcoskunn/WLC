package com.ahe.wlc

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson



class CamsActivity : AppCompatActivity() {

    private var gyroscopeObserver: GyroscopeObserver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cams)
        gyroscopeObserver = GyroscopeObserver()

        //var camList=findViewById<ListView>(R.id.listview_cams)

        var city=intent.getStringExtra("city")

        var cityObj= Gson().fromJson(city, City::class.java)

       /* var ulkeler=ArrayList<String>()

        for (city in cityObj.Cams)
        {
            ulkeler.add(city.CamLink)
        }



        val veriAdaptoru = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, ulkeler)


        camList.setAdapter(veriAdaptoru)


        camList.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var camObject=cityObj.Cams[p2]
                var intent = Intent(this@CamsActivity,PlayerActivity::class.java)
                intent.putExtra("htmlContent", Gson().toJson(camObject, Cam::class.java))
                startActivity(intent)
            }

        }*/

        var adapterCams=AdapterCams(this,cityObj.Cams,gyroscopeObserver)
        adapterCams.setOnItemClickListener(object :AdapterCams.OnItemClickListener{
            override fun OnItemClick(cam: Cam) {
                var intent = Intent(this@CamsActivity,PlayerActivity::class.java)
                intent.putExtra("htmlContent", Gson().toJson(cam, Cam::class.java))
                startActivity(intent)
            }
        })


        var layoutManager:LinearLayoutManager= LinearLayoutManager(this@CamsActivity, LinearLayoutManager.VERTICAL,false)
        var recyclerCams=findViewById<RecyclerView>(R.id.recycler_cams)
        recyclerCams.layoutManager=layoutManager
        recyclerCams.adapter=adapterCams

    }

    override fun onResume() {
        super.onResume()
        gyroscopeObserver!!.register(this)
    }

    override fun onPause() {
        super.onPause()
        gyroscopeObserver!!.unregister()
    }
}
