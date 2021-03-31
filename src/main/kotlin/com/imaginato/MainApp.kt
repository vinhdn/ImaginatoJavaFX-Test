package com.imaginato

import com.imaginato.style.Styles
import com.imaginato.view.LoginView
import tornadofx.App

class MainApp: App(LoginView::class, Styles::class)