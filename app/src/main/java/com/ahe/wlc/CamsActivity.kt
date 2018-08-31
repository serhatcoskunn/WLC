package com.ahe.wlc

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.gson.Gson



class CamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cams)

        var camList=findViewById<ListView>(R.id.listview_cams)

        var city=intent.getStringExtra("city")

        var cityObj= Gson().fromJson(city, City::class.java)

        var ulkeler=ArrayList<String>()

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

        }
    }
}
