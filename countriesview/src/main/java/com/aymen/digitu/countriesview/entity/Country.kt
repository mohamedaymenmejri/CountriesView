package com.aymen.digitu.countriesview.entity

/**
 * Created by mohamedaymenmejri on 29,February,2020
 */
class Country {

    var code: String? = null
    var callingCode: String? = null
    var name: String? = null

    override fun toString(): String {
        return "Country(code=$code, callingCode=$callingCode, name=$name)"
    }
}
