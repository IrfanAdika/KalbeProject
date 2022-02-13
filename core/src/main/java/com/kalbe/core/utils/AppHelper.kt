package com.kalbe.core.utils

import java.util.regex.Pattern

object AppHelper {

    /**
     * Validation email
     */
    fun isValidEmail(email: String): Boolean {
        val pattern =
            Pattern.compile("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
        return pattern.matcher(email).matches()
    }

    /**
     * Validation phone number
     */
    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val pattern =
            Pattern.compile("^(0|62)8(1[1-9]|2[1238]|3[18]|5[12356789]|7[78]|8[1-9]|9[5-9])[0-9]{6,9}\$")
        return pattern.matcher(phoneNumber).matches()
    }

    /**
     * Validation name
     */
    fun isValidName(name: String): Boolean {
        val pattern =
            Pattern.compile("^[a-zA-Z]+(([', -][a-zA-Z ])?[a-zA-Z]*)*\$")
        return pattern.matcher(name).matches()
    }

    /**
     * Change text to capitalize
     */
    private fun capitalize(s: String?): String {
        if (s == null || s.isEmpty()) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }

    /**
     * Global variable
     */
    var isPopupConnectionHasShow = false //Tagging pop up alert (Connection / Time out), so that double showing
}