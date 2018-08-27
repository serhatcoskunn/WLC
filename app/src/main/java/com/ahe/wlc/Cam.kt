package com.ahe.wlc

import com.google.gson.annotations.SerializedName

//class Cam(Id:Int, CamLink:String, Latitude:Float, Longitude:Float, CamType:Int) {
class Cam(
    @SerializedName("Id") var Id:Int,
    @SerializedName("CamLink") var CamLink:String,
    @SerializedName("Latitude") var Latitude:Float,
    @SerializedName("Longitude") var Longitude:Float,
    @SerializedName("CamType")  var CamType:Int)

