package ru.sccraft.urlshortner

import android.content.Intent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LinkViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    private val text1 = itemView.findViewById<TextView>(R.id.text1)

    fun bind(model: Link) {
        text1.setText(model.longU)
        itemView.setOnClickListener {
            info(model)
        }
    }

    private fun info(link: Link) {
        if (!MainActivity.разрешить_использование_интендификатора)
            Toast.makeText(itemView.context.applicationContext,
                "Please, restart this app!", Toast.LENGTH_LONG).show()
        val intent = Intent(itemView.context, LinkInfoActivity::class.java)
        intent.putExtra("link", link)
        itemView.context.startActivity(intent)
    }
}