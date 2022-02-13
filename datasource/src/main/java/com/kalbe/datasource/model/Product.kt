package com.kalbe.datasource.model

import com.google.gson.annotations.SerializedName

data class Product (

    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("sku")
    var sku: String = "",

    @SerializedName("product_name")
    var productName: String = "",

    @SerializedName("qty")
    var qty: Int = 0,

    @SerializedName("price")
    var price: Int = 0,

    @SerializedName("unit")
    var unit: String = "",

    @SerializedName("status")
    var status: Int = 0,

)