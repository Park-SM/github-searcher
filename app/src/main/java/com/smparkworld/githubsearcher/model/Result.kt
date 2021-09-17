package com.smparkworld.githubsearcher.model

sealed class Result<out R> {

    data class Success<T>(val data: T): Result<T>()
    data class Error(val e: Exception): Result<Nothing>() {
        fun printStackTrace() = e.printStackTrace()
    }

    override fun toString(): String {
        return when(this) {
            is Success -> "Success[data:${this.data}]"
            is Error ->   "Error[exception:${e}]"
        }
    }
}