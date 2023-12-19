package com.android.projectpbp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.R

class MenuAdapter(private var menu : List<Menu>, context: Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namaMenu : TextView = itemView.findViewById(R.id.namaMenu)
        val hargaMenu : TextView = itemView.findViewById(R.id.hargaMenu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menu.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val Menu = menu[position]
        holder.namaMenu.text = Menu.namamenu
        holder.hargaMenu.text = "Rp " + Menu.harga.toString()
    }

    fun refreshData(newMenu: List<Menu>){
        menu = newMenu
        notifyDataSetChanged()
    }

}