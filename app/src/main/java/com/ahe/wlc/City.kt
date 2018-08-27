package com.ahe.wlc

import com.google.gson.annotations.SerializedName

class City(
        @SerializedName("CityId") var CityId:Int,
        @SerializedName("CityName") var CityName:String,
        @SerializedName("CamCount") var CamCount:Int,
        @SerializedName("Cams")var Cams:ArrayList<Cam>) {
}