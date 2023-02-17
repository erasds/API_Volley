package com.esardo.p6_api_volley.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.esardo.p6_api_volley.R
import com.esardo.p6_api_volley.databinding.ItemDigimonBinding
import com.esardo.p6_api_volley.model.Digimon
import com.squareup.picasso.Picasso

class DigimonAdapter(private val digimonList: ArrayList<Digimon>) : RecyclerView.Adapter<DigimonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_digimon, parent, false))
    }

    override fun getItemCount(): Int = digimonList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = digimonList[position]
        holder.bind(item)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemDigimonBinding.bind(view)

        // Binds the MainActivity elements to it's value
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val tvLevel = view.findViewById<TextView>(R.id.tvLevel)

        //With Picasso library this will load the Digimon image, an image to show while data is loading,
        // and an image to show if there's an error loading the Digimon image
        fun bind (digimon: Digimon ) {
            Picasso.get().load(digimon.img).placeholder(R.drawable.loading).error(R.drawable.error).into(binding.ivImage)
            tvName.text = digimon.name
            tvLevel.text = digimon.level
        }
    }
}