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
import com.android.projectpbp.Activity.UpdateWarung
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

        val updateButton : ImageView = itemView.findViewById(R.id.editWarung)
        val deleteButton : ImageView = itemView.findViewById(R.id.hapusWarung)
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

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateWarung::class.java).apply {
                putExtra("warung_id", Warung.idwarung)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener{
            showDeleteConfirmationDialog(Warung.idwarung)
        }
    }

    private fun showDeleteConfirmationDialog(menuId: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Konfirmasi Hapus")
        builder.setMessage("Apakah Anda yakin ingin menghapus data warung ini?")

        builder.setPositiveButton("Ya") { _, _ ->
            db.deleteWarung(menuId)
            refreshData(db.getAllWarung())
            Toast.makeText(context, "Warung Deleted", Toast.LENGTH_SHORT).show()
        }

        builder.setNegativeButton("Tidak") { _, _ ->
            // Tidak melakukan apa-apa jika pengguna memilih "Tidak"
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun refreshData(newWarung: List<Warung>){
        warung = newWarung
        notifyDataSetChanged()
    }
}