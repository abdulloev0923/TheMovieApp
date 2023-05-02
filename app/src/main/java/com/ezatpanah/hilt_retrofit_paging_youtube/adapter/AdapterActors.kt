package com.ezatpanah.hilt_retrofit_paging_youtube.adapter

import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.response.Actors
import com.ezatpanah.hilt_retrofit_paging_youtube.utils.POSTER_BASE_URL

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class AdapterActors(): RecyclerView.Adapter<AdapterActors.MyViewHolder>() {

    private val array = mutableListOf<Actors>()
    private var action:((Actors)->Unit)? = null

    class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val name = item.findViewById<TextView>(R.id.actor_name)
        val profile_actor = item.findViewById<ImageView>(R.id.actors_image)
        val action = item.findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.action)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.actors_list,parent, false))
    }

    override fun getItemCount(): Int {
        return array.size
    }




    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val arrayList = array[position]
        holder.name.text =  arrayList.original_name

        Glide.with(holder.profile_actor.context).load(POSTER_BASE_URL + arrayList.profile_path).into(holder.profile_actor)

        holder.action.setOnClickListener { action?.invoke(arrayList) }

    }

    fun setData(data: List<Actors>){
        array.apply {
            clear()
            addAll(data)
            notifyDataSetChanged()
        }
    }


}
