package com.mezzyservices.countriesapp.countrybrowser.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mezzyservices.countriesapp.R
import com.mezzyservices.countriesapp.data.Country
import com.mezzyservices.countriesapp.databinding.HolderCountryBinding

class CountryListAdapter(
    private val countryList: List<Country>,
    val onCardClicked: (String) -> Unit) :
    RecyclerView.Adapter<CountryListAdapter.CountryHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        return CountryHolder(
            HolderCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = countryList.size

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val country = countryList[position]
        with(holder.view) {
            Glide.with(root.context).load(country.flags.png).into(flagImageView)
            nameTextView.text = country.name.common
            root.setOnClickListener { onCardClicked(country.name.common) }
        }
    }

    data class CountryHolder(val view: HolderCountryBinding) : ViewHolder(view.root)
}