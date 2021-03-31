package com.imaginato.viewmodel

import com.google.gson.Gson
import com.imaginato.Constant
import com.imaginato.MainApp
import com.imaginato.extension.toObject
import com.imaginato.model.Response
import com.imaginato.model.User
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.util.prefs.Preferences

class LoginViewModel: ViewModel() {

    private val api: Rest by inject()

    var isDark = SimpleBooleanProperty()
    var userName = SimpleStringProperty()
    var password = SimpleStringProperty()
    var errorMessage = SimpleStringProperty()
    var isLoading = SimpleBooleanProperty()
    var loginSuccess = SimpleBooleanProperty()

    private var prefs = Preferences.userRoot()

    init {
        isDark.value = prefs.getBoolean(Constant.keyIsDark, false)
        isLoading.value = false
        api.baseURI = "http://private-222d3-homework5.apiary-mock.com/api"
        api.engine.requestInterceptor = {
            it.addHeader("IMSI","357175048449937")
            it.addHeader("IMSI","37175048449937")
        }
        userName.onChange { errorMessage.value = null }
        password.onChange { errorMessage.value = null }
    }

    fun login() {
        if(userName.value.isNullOrBlank()) {
            errorMessage.value = "Username is not empty"
            return
        }
        if(!userName.value.matches("[A-Za-z0-9]*".toRegex())
            || userName.value.length > 30) {
            errorMessage.value = "Username is not valid (Characters and Numbers (max 30))"
            return
        }
        if(password.value.isNullOrBlank()) {
            errorMessage.value = "Password is not empty"
            return
        }
        if(!password.value.matches("[A-Za-z0-9]*".toRegex())
            || userName.value.length > 30) {
            errorMessage.value = "Password is not valid (Characters and Numbers (max 16))"
            return
        }
        isLoading.value = true
        loginSuccess.value = false
        val data = JsonBuilder()
        data.apply {
            add("username", userName)
            add("password", password)
        }
        runAsync {
            val response = api.post("login", data.build())
            try {
                val responseData = if (response.ok()) {
                    response.one().toString().toObject(Response::class.java)
                } else {
                    Response(response.statusCode.toString(), response.reason)
                }
                runLater  {
                    if ("00" == responseData.errorCode) {
                        errorMessage.value = "Login success"
                        prefs.put(Constant.keyXAcc, response.header(Constant.keyXAcc))
                        prefs.put(Constant.keyUser, Gson().toJson(responseData.user))
                        loginSuccess.value = true
                    } else {
                        errorMessage.value = responseData.errorMessage
                    }
                }
            } finally {
                runLater  {
                    isLoading.value = false
                }
                response.consume()
            }
        }
    }

    fun changeTheme(toDark: Boolean = true) {
        isDark.value = toDark
        prefs.putBoolean(Constant.keyIsDark, toDark)
    }
}