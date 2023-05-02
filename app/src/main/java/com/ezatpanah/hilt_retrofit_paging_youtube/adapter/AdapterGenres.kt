package com.ezatpanah.hilt_retrofit_paging_youtube.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ezatpanah.hilt_retrofit_paging_youtube.R
import com.ezatpanah.hilt_retrofit_paging_youtube.response.GenreDto

class AdapterGenres(): RecyclerView.Adapter<AdapterGenres.MyViewHolder>() {
    private val array= mutableListOf<GenreDto>()
    private var actionEdit:((GenreDto)->Unit)? = null

    class MyViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val name = item.findViewById<TextView>(R.id.name)
        val genres = item.findViewById<LinearLayout>(R.id.genres)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.genre_list,parent, false))
    }

    override fun getItemCount(): Int {
        return array.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val arrayList = array[position]

        holder.name.text =  arrayList.name
        holder.genres.setOnClickListener{
            actionEdit?.invoke(arrayList)

        }

    }


    fun setOnClick(callback : (GenreDto) -> Unit){
        this.actionEdit = callback
    }
    fun setData(data: List<GenreDto>){
        array.apply {
            clear()
            add(GenreDto(1, "Popular Now"))
            add(GenreDto(2, "Now Playing"))
            add(GenreDto(3, "Top Rated"))
            add(GenreDto(4, "Upcoming"))
            addAll(data)
            notifyDataSetChanged()
        }
    }


}

