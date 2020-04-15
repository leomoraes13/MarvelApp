package com.leomoraes.marvelapp.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leomoraes.marvelapp.R
import com.leomoraes.marvelapp.data.model.Hero
import com.leomoraes.marvelapp.extensions.loadSmallImage
import kotlinx.android.synthetic.main.item_main.view.*
import java.util.*

class HeroAdapter(private val listener: HeroAdapterListener) :
    RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

    private var heroList: List<Hero> = ArrayList()

    interface HeroAdapterListener {
        fun onClick(id: String)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hero = heroList[position]
        holder.let {
            it.bindView(hero)
            it.itemView.setOnClickListener {
                listener.onClick(hero.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = heroList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: Hero) {
            itemView.heroNameTextView.text = item.name
            itemView.heroImageView.loadSmallImage(item.image)
        }
    }

    fun setData(list: List<Hero>) {
        heroList = list
    }
}