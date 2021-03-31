package com.imaginato.view

import com.imaginato.Constant
import com.imaginato.style.Styles
import com.imaginato.extension.centerCrop
import com.imaginato.viewmodel.LoginViewModel
import javafx.geometry.Pos
import javafx.scene.image.Image
import tornadofx.*
import java.util.prefs.Preferences

class LoginView : Fragment("Imaginato Login") {

    private val viewModel: LoginViewModel by inject()

    private var prefs = Preferences.userRoot()

    init {
        viewModel.loginSuccess.onChange {
            if(it) {
                replaceWith(HomeView())
                onRefresh()
            }
        }
    }

    override val root = vbox(alignment = Pos.CENTER, spacing = 5) {

        val xAcc = prefs.get(Constant.keyXAcc, null)
        if(!xAcc.isNullOrEmpty()) {
            return@vbox
        }
        viewModel.isDark.onChange {
            if(it) {
                addClass(Styles.dark)
            } else {
                removeClass(Styles.dark)
            }
        }

        hbox(alignment = Pos.CENTER) {
            paddingHorizontal = 10
            button("Dark") {
                addClass(Styles.themeButton)
                action {
                    viewModel.changeTheme(true)
                }
            }

            spacer {  }

            button("Light") {
                addClass(Styles.themeButton)
                action {
                    viewModel.changeTheme(false)
                }
            }
        }

        val logoImage = Image("images/imaginato.jpg")

        hbox(alignment = Pos.CENTER) {
            imageview {
                fitWidth = 100.0
                fitHeight = 100.0
                centerCrop(logoImage)
                isSmooth = true
            }
        }

        hbox (alignment = Pos.BASELINE_LEFT) {
            paddingLeft = 10
            hiddenWhen { !viewModel.errorMessage.isNotEmpty }
            label(viewModel.errorMessage) {
                addClass(Styles.errorLabel)
            }
        }

        progressindicator {
            hiddenWhen { !viewModel.isLoading }
        }
        form {
            fieldset {
                field("Username") {
                    textfield(viewModel.userName) {

                    }
                }
            }

            fieldset {
                field("Password") {
                    passwordfield(viewModel.password)
                }
            }
        }

        button("Login") {
            disableWhen { viewModel.isLoading }
            addClass(Styles.loginButton)
            action {
                viewModel.login()
            }
        }
    }

    override fun onBeforeShow() {
        replaceWith(HomeView())
    }
}
