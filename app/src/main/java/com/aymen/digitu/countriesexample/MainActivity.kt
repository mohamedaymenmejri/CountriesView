package com.aymen.digitu.countriesexample

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.aymen.digitu.countriesview.CountriesDisplayTag
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val filterArray = arrayOf(
        CountriesDisplayTag.EU_CALLING_CODE_DISPLAY,
        CountriesDisplayTag.EU_NAME_DISPLAY,
        CountriesDisplayTag.WORLD_EU_FIRST_CALLING_CODE_DISPLAY,
        CountriesDisplayTag.WORLD_EU_FIRST_NAME_DISPLAY,
        CountriesDisplayTag.WORLD_CALLING_CODE_DISPLAY,
        CountriesDisplayTag.WORLD_NAME_DISPLAY
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        search?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Log.i("Bottom Sheet", "" + s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i("Bottom Sheet", "" + s)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countryView?.search(s.toString())
            }
        })

        initSpinnerView()
    }

    private fun initSpinnerView() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            filterArray
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        choose_countries_filter.adapter = adapter
        choose_countries_filter.onItemSelectedListener = this
        choose_countries_filter.setSelection(0)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d(MainActivity::class.java.name, "onNothingSelected")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        countryView.initViewAccordingTo(
            filterArray[position]
        )
    }
}
