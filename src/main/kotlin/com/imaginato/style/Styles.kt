package com.imaginato.style

import javafx.scene.paint.Color
import tornadofx.*

class Styles : Stylesheet() {
    companion object {
        val light by cssclass()
        val dark by cssclass()
        val loginButton by cssclass()
        val themeButton by cssclass()
        val errorLabel by cssclass()
    }

    init {
        root {
            prefHeight = 400.px
            prefWidth = 300.px
        }
        root and light {
            baseColor = Color.WHITE
        }
        root and dark {
            baseColor = Color.BLACK
            accentColor = Color.BLACK
        }

        button and loginButton {
            prefWidth = 200.px
            backgroundRadius = multi(box(10.px))
        }

        button and Companion.themeButton {
            prefWidth = 100.px
            backgroundRadius = multi(box(0.px))
        }

        label and errorLabel {
            startMargin = 10.px
            textFill = Color.RED
        }
    }
}