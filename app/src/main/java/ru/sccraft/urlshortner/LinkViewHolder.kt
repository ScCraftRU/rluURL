package ru.sccraft.urlshortner

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LinkViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

    private val text1 = itemView.findViewById<TextView>(R.id.text1)

    fun bind(model: Link) {
        text1.setText(model.longU)
    }
}