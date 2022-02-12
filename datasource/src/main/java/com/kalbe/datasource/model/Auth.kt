package com.kalbe.datasource.model

import com.google.gson.annotations.SerializedName

data class Auth(

    @SerializedName("token")
    var token: String = ""
)