package com.kalbe.datasource.model

import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("email")
    var email: String = "",

    @SerializedName("id")
    var id : Int = 0,
)