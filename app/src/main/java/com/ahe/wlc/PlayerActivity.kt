package com.ahe.wlc

import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import com.google.gson.Gson
import android.graphics.Bitmap
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import android.widget.Toast


class PlayerActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)

        var cam=intent.getStringExtra("htmlContent")
        var camObj= Gson().fromJson(cam, Cam::class.java)
        var link=camObj.CamLink

        var web = findViewById<WebView>(R.id.webview) //WebView(this@PlayerActivity)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var str=""


        if(camObj.CamType==0)
        {
            str="<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<style>\n" +
                    ".content {\n" +
                    "\twidth: 85%;\n" +
                    "    height: 85%;\n" +
                    "\t\n" +
                    "\tposition:absolute;\n" +
                    "    left:0; right:0;\n" +
                    "    top:0; bottom:0;\n" +
                    "\tmargin:auto;\n" +
                    "\t\n" +
                    "\tmax-width:100%;\n" +
                    "\tmax-height:100%;\n" +
                    "\toverflow:auto;\n" +
                    "}\n" +
                    "\n" +
                    "-->\n" +
                    "</style>\n" +
                    "<head>\n" +
                    "<script>\n" +
                    "var flag=false;\n" +
                    "function myFunction() {\n" +
                    "\t\n" +
                    "\tsetInterval(myMethod, 1000);\n" +
                    "}\n" +
                    "function myMethod( )\n" +
                    "{\n" +
                    "  if(flag)\n" +
                    "  {\n" +
                    "\tflag=false;\n" +
                    "\tvar fl=\"http://37.142.116.227:80/cgi-bin/camera?resolution=640&amp;amp;amp;quality=1&amp;amp;amp;Language=0&amp;amp;amp;\";\n" +
                    "\tvar ts=Math.floor(new Date().getTime()/1000);\n" +
                    "\tdocument.getElementById(\"myImg\").src = fl+ts;\n" +
                    "  }\n" +
                    "\n" +
                    "}\n" +
                    "function setLoad()\n" +
                    "{\n" +
                    "\tflag=true;\n" +
                    "}\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "\n" +
                    "<body onload=\"myFunction()\">\n" +
                    "\n" +
                    "<img id=\"myImg\" onload=\"setLoad()\" class=\"content\"\n" +
                    "src=\""+link.substring(0, link.length - 1)+"\">\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>"
        }
        else if(camObj.CamType==1)
        {
            str="<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<style>\n" +
                    ".content {\n" +
                    "\twidth: 85%;\n" +
                    "    height: 85%;\n" +
                    "\t\n" +
                    "\tposition:absolute;\n" +
                    "    left:0; right:0;\n" +
                    "    top:0; bottom:0;\n" +
                    "\tmargin:auto;\n" +
                    "\t\n" +
                    "\tmax-width:100%;\n" +
                    "\tmax-height:100%;\n" +
                    "\toverflow:auto;\n" +
                    "}\n" +
                    "\n" +
                    "-->\n" +
                    "</style>\n" +
                    "<head>\n" +
                    "    \n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "\n" +
                    "<img id=\"myImg\"  class=\"content\"\n" +
                    "src=\""+link+"\">\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>"
        }


        val webSettings = web.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.mixedContentMode=WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.setSupportZoom(true)
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.loadsImagesAutomatically=true

        web.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility=View.VISIBLE
            }
            override fun onPageFinished(view: WebView, url: String) {
                progressBar.visibility=View.INVISIBLE
                //Toast.makeText(this@PlayerActivity,"Yüklendi",Toast.LENGTH_SHORT).show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Toast.makeText(this@PlayerActivity, error.description.toString(),Toast.LENGTH_SHORT).show()
            }

        }




        web.loadDataWithBaseURL("", str, "text/html", "UTF-8","")



    }
}