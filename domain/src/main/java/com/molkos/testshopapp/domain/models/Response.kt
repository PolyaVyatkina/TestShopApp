package com.molkos.testshopapp.domain.models

sealed class Response {
    data class Success(val body: ResponseBody) : Response()
    data class Error(val error: String) : Response()
}
