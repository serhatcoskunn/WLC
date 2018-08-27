package com.ahe.wlc

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import java.io.IOException
import java.io.InputStream
import com.google.gson.Gson
import android.widget.ArrayAdapter
import android.widget.ListView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cityList=findViewById<ListView>(R.id.listview_city)

        val myJson = inputStreamToString(this.resources.openRawResource(R.raw.camsjson))
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
}
