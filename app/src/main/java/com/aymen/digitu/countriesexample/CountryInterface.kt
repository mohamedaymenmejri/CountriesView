package com.aymen.digitu.countriesexample

import androidx.fragment.app.Fragment


/**
 * Created by mohamedaymenmejri on 19,March,2020
 */
interface CountryInterface {
    fun hideSoftKeyboardWhenNeeded()
    fun removeFragmentFromLayoutWhenNeeded()
    fun addFragmentToLayoutWhenNeeded(fragment: Fragment)
}
