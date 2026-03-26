package ru.sccraft.urlshortner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class LinkAdapter (private val links: ArrayList<Link>) : RecyclerView.Adapter<LinkViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_link, parent, false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        holder.bind(links[position])
    }

    override fun getItemCount(): Int {
        return links.size
    }
}
