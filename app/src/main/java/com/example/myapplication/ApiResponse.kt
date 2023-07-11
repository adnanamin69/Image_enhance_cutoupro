package com.example.myapplication

data class ApiResponse(
    val code: Int,
    val data: Data?,
    val msg: String?,
    val time: Long
) {
    data class Data(
        val imageBase64: String,
        val imageBase: String?
    )
}
