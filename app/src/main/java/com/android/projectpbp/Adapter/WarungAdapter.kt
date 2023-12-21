package com.android.projectpbp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.Model.Warung
import com.android.projectpbp.R

class WarungAdapter (private var warung : List<Warung>,
                     private val context: Context
) : RecyclerView.Adapter<WarungAdapter.WarungViewHolder>(){

    private val db : DatabaseHelper = DatabaseHelper(context)
    class WarungViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namaWarung : TextView = itemView.findViewById(R.id.namaWarung)
        val nomorMeja : TextView = itemView.findViewById(R.id.nomorMeja)
        val gambarWarung : ImageView = itemView.findViewById(R.id.warungImage)
        val logoWarung : ImageView = itemView.findViewById(R.id.warungLogo)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WarungViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_warung, parent, false)
        return WarungViewHolder(view)
    }

    override fun getItemCount(): Int {
        return warung.size
    }

    override fun onBindViewHolder(holder: WarungViewHolder, position: Int) {
        val Warung = warung[position]
        holder.namaWarung.text = Warung.namawarung
        holder.nomorMeja.text = Warung.nomormeja
        holder.gambarWarung.setImageBitmap(Warung.gambarwarung)
        holder.logoWarung.setImageBitmap(Warung.logowarung)
    }

    fun refreshData(newWarung: List<Warung>){
        warung = newWarung
        notifyDataSetChanged()
    }
}