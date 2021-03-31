package com.imaginato.model

import tornadofx.JsonModel

data class Response(var errorCode: String = "",
                    var errorMessage: String? = null,
                    var user: User? = null)