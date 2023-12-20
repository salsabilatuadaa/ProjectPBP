package com.android.projectpbp.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.projectpbp.Activity.UpdateMenu
import com.android.projectpbp.DatabaseHelper
import com.android.projectpbp.Model.Menu
import com.android.projectpbp.R

class MenuAdapter(private var menu : List<Menu>, private val context: Context) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>(){

    private val db : DatabaseHelper = DatabaseHelper(context)
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val namaMenu : TextView = itemView.findViewById(R.id.namaMenu)
        val hargaMenu : TextView = itemView.findViewById(R.id.hargaMenu)
        val kategori : TextView = itemView.findViewById(R.id.kategoriMenu)

        val updateButton : ImageView = itemView.findViewById(R.id.edit)
        val deleteButton : ImageView = itemView.findViewById(R.id.hapus)
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
        holder.kategori.text = Menu.kategori
        holder.hargaMenu.text = "Rp " + Menu.harga.toString()

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateMenu::class.java).apply {
                putExtra("menu_id", Menu.idmenu)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            showDeleteConfirmationDialog(Menu.idmenu)
        }
    }

    private fun showDeleteConfirmationDialog(menuId: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konfirmasi Hapus")
        builder.setMessage("Apakah Anda yakin ingin menghapus menu ini?")

        builder.setPositiveButton("Ya") { _, _ ->
            db.deleteMenu(menuId)
            refreshData(db.getAllMenu())
            Toast.makeText(context, "Menu Deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Tidak") { _, _ ->
            // Tidak melakukan apa-apa jika pengguna memilih "Tidak"
        }

        val dialog = builder.create()
        dialog.show()
    }


    fun refreshData(newMenu: List<Menu>){
        menu = newMenu
        notifyDataSetChanged()
    }

}