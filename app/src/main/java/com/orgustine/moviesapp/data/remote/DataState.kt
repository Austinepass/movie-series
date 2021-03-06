package com.orgustine.moviesapp.data.remote

//data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

//    enum class Status {
//        SUCCESS,
//        ERROR,
//        LOADING
//    }
//
//    companion object {
//        fun <T> success(data: T): Resource<T> {
//            return Resource(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(message: String, data: T? = null): Resource<T> {
//            return Resource(Status.ERROR, data, message)
//        }
//
//        fun <T> loading(data: T? = null): Resource<T> {
//            return Resource(Status.LOADING, data, null)
//        }
//    }
//}
sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Waiting : DataState<Nothing>()
}