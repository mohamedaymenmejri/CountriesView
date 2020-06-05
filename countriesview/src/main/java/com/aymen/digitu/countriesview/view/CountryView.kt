package com.aymen.digitu.countriesview.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aymen.digitu.countriesview.CountriesDisplayTag
import com.aymen.digitu.countriesview.R
import com.aymen.digitu.countriesview.adapter.CountryListAdapter
import com.aymen.digitu.countriesview.entity.Country
import com.aymen.digitu.countriesview.extension.readFileAsString
import kotlinx.android.synthetic.main.all_countries_view.view.*
import org.json.JSONObject
import java.util.*

/**
 * Created by mohamedaymenmejri on 29,February,2020
 */
class CountryView : ConstraintLayout {

    ///////////////////////////////////////////////////////////////////////////
    // GENERAL PROPERTY SECTION
    ///////////////////////////////////////////////////////////////////////////
    private var adapter: CountryListAdapter? = null
    private var allCountriesList: MutableList<Country>? = null
    private var selectedCountriesList: MutableList<Country>? = null
    private var allCountriesParams: Pair<CountriesDisplayTag, String>? = null

    ///////////////////////////////////////////////////////////////////////////
    // VIEW ATTRIBUTES SECTION
    ///////////////////////////////////////////////////////////////////////////
    private var rv: RecyclerView? = null

    ///////////////////////////////////////////////////////////////////////////
    // INTERACTION PROPERTY SECTION
    ///////////////////////////////////////////////////////////////////////////
    var countryViewInteraction: CountryViewInteraction? = null

    ///////////////////////////////////////////////////////////////////////////
    // COUNTRIES GENERATING PROPERTY
    ///////////////////////////////////////////////////////////////////////////
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun generateCountriesList() {
        if (allCountriesList == null) {
            try {
                allCountriesList = mutableListOf()
                val allCountriesString = allCountriesParams?.second
                val jsonObject = JSONObject(allCountriesString)
                val keys: Iterator<*> = jsonObject.keys()

                val c = Country()

                c.code = "TN"
                c.callingCode = "+216"
                c.name = "Tunisie"

                allCountriesList?.add(c)
                while (keys.hasNext()) {
                    val key = keys.next() as String
                    val country = Country()

                    country.code = key
                    country.callingCode = jsonObject.getJSONArray(key)[1].toString()
                    country.name = jsonObject.getJSONArray(key)[0].toString()

                    allCountriesList?.add(country)
                }
                // Initialize selected countries with all countries
                selectedCountriesList = mutableListOf()
                selectedCountriesList?.addAll(allCountriesList ?: arrayListOf())

                // Return
                allCountriesList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    constructor(@NonNull context: Context) : super(context)

    constructor(@NonNull context: Context, @NonNull attr: AttributeSet) : super(context, attr) {
        val view = LayoutInflater.from(context).inflate(R.layout.all_countries_view, this)
        rv = view.rv_country
    }

    fun initViewAccordingTo(countryTag: CountriesDisplayTag) {
        defineCountryListType(countryTag)
        generateCountriesList()
        initCountryList(countryTag)
    }

    private fun defineCountryListType(countryTag: CountriesDisplayTag) {
        allCountriesParams = when (countryTag) {
            CountriesDisplayTag.WORLD_NAME_DISPLAY -> Pair(
                CountriesDisplayTag.WORLD_NAME_DISPLAY,
                readFileAsString(resources.getString(R.string.countries_all))
            )
            CountriesDisplayTag.WORLD_CALLING_CODE_DISPLAY -> Pair(
                CountriesDisplayTag.WORLD_CALLING_CODE_DISPLAY,
                readFileAsString(resources.getString(R.string.countries_all))
            )
            CountriesDisplayTag.WORLD_EU_FIRST_CALLING_CODE_DISPLAY -> Pair(
                CountriesDisplayTag.WORLD_EU_FIRST_CALLING_CODE_DISPLAY,
                readFileAsString(resources.getString(R.string.country_eu_first))
            )
            CountriesDisplayTag.WORLD_EU_FIRST_NAME_DISPLAY -> Pair(
                CountriesDisplayTag.WORLD_EU_FIRST_NAME_DISPLAY,
                readFileAsString(resources.getString(R.string.country_eu_first))
            )
            CountriesDisplayTag.EU_CALLING_CODE_DISPLAY -> Pair(
                CountriesDisplayTag.EU_CALLING_CODE_DISPLAY,
                readFileAsString(resources.getString(R.string.country_eu_only))
            )
            CountriesDisplayTag.EU_NAME_DISPLAY -> Pair(
                CountriesDisplayTag.EU_NAME_DISPLAY,
                readFileAsString(resources.getString(R.string.country_eu_only))
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // INTERACTION VIEW HANDLING SECTION
    ///////////////////////////////////////////////////////////////////////////
    private fun initCountryList(countryDisplayTag: CountriesDisplayTag) {
        context?.let { ctx ->
            adapter = CountryListAdapter(
                selectedCountriesList,
                countryDisplayTag,
                object : CountryListAdapter.ItemClickEvent {
                    override fun onItemClick(country: Country?) {
                        countryViewInteraction?.chooseCountry(country)
                    }
                }
            )
            val llm = LinearLayoutManager(ctx)
            llm.orientation = LinearLayoutManager.VERTICAL
            rv?.layoutManager = llm
            rv?.adapter = adapter
        }
    }

    @SuppressLint("DefaultLocale")
    fun search(text: String) {
        selectedCountriesList?.clear()
        allCountriesList?.let { list ->
            for (country in list) {
                buildCountryListFromCountryDataAndSearchedWord(text, country)
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun buildCountryListFromCountryDataAndSearchedWord(text: String, country: Country) {
        country.name?.toLowerCase(Locale.getDefault())
            ?.contains(text.toLowerCase(Locale.getDefault()))?.let { doesContain ->
                if (doesContain) selectedCountriesList?.add(country)
            }
    }

    interface CountryViewInteraction {
        fun chooseCountry(country: Country?)
    }
}
