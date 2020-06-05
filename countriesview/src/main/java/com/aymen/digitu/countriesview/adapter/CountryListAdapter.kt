package com.aymen.digitu.countriesview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aymen.digitu.countriesview.CountriesDisplayTag
import com.aymen.digitu.countriesview.R
import com.aymen.digitu.countriesview.entity.Country
import com.aymen.digitu.countriesview.glide.GlideApp
import kotlinx.android.synthetic.main.country_row.view.*
import java.util.*

/**
 * Created by mohamedaymenmejri on 29,February,2020
 */
class CountryListAdapter(
    var countries: MutableList<Country>?,
    var countryDisplayTag: CountriesDisplayTag,
    private var itemClickEvent: ItemClickEvent
) :
    RecyclerView.Adapter<CountryListAdapter.MyHolder>() {

    companion object {
        const val DRAWABLE_PREFIX = "flag_"
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.row_title
        val imageView: ImageView = itemView.row_icon
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.country_row, parent, false)
        return MyHolder(v)
    }

    override fun getItemCount(): Int = countries?.size ?: 0

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text =
            if (countryDisplayTag == CountriesDisplayTag.WORLD_CALLING_CODE_DISPLAY ||
                countryDisplayTag == CountriesDisplayTag.WORLD_EU_FIRST_CALLING_CODE_DISPLAY
            ) holder.itemView.context.getString(
                R.string.country_calling_code_placeholder,
                countries?.get(position)?.callingCode,
                countries?.get(position)?.name
            ) else countries?.get(position)?.name

        val drawableName =
            (DRAWABLE_PREFIX + countries?.get(position)?.code?.toLowerCase(Locale.getDefault()))
        GlideApp.with(holder.imageView).load(getResId(drawableName)).into(holder.imageView)
        holder.itemView.setOnClickListener {
            itemClickEvent.onItemClick(countries?.get(position))
        }
    }

    private fun getResId(drawableName: String): Int {
        try {
            val res = R.drawable::class.java
            val field = res.getField(drawableName)
            return field.getInt(null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return -1
    }

    interface ItemClickEvent {
        fun onItemClick(country: Country?)
    }
}
