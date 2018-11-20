package com.ahe.wlc

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_cam.view.*

class AdapterCams(var context: Context,var camList:ArrayList<Cam>,var gyroscopeObserver: GyroscopeObserver?) : RecyclerView.Adapter<AdapterCams.ViewHolder>() {

    private var onItemClickListener:OnItemClickListener?=null

    interface OnItemClickListener{
        fun OnItemClick(cam:Cam)
    }

    fun setOnItemClickListener(onItemClickListener:OnItemClickListener)
    {
        this.onItemClickListener=onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterCams.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate((R.layout.list_item_cam),parent,false))
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val camItem=view
        val camName=view.tV_cam
        val camImg=view.iV_cam as PanoramaImageView
    }

    override fun getItemCount(): Int {
        return camList.size
    }

    override fun onBindViewHolder(holder: AdapterCams.ViewHolder, position: Int) {
        holder?.camName?.text=camList[position].CamLink
        var img=camList[position].CamLink
        if(camList[position].CamLink.contains("mjpg/video.mjpg"))
        {
            img=camList[position].CamLink.replace("mjpg/video.mjpg","jpg/image.jpg")
        }
        img=img.replace("&amp;","&")



        holder?.camImg.setGyroscopeObserver(gyroscopeObserver)
        Picasso.get().load(img).into(holder?.camImg)

        holder?.camItem.setOnClickListener {

            onItemClickListener?.OnItemClick(camList[position])
        }



    }
}