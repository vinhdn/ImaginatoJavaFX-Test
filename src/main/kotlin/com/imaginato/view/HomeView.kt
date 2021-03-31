package com.imaginato.view

import com.imaginato.Constant
import com.imaginato.style.Styles
import javafx.geometry.Pos
import tornadofx.*
import java.util.prefs.Preferences

class HomeView: Fragment("Home") {

    private var prefs = Preferences.userRoot()

    override val root = vbox(alignment = Pos.CENTER, spacing = 5) {
        if(prefs.getBoolean(Constant.keyIsDark, false)) {
            addClass(Styles.dark)
        }
        button("Logout") {
            addClass(Styles.loginButton)
            action {
                prefs.clear()
                prefs.sync()
                replaceWith(LoginView())
            }
        }
    }
}