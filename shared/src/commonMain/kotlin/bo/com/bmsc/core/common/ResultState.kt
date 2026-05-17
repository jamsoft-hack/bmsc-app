package bo.com.bmsc.core.common

import bo.com.bmsc.core.domain.CustomException

sealed class ResultState<out T: Any> {
    data class Success<out T : Any>(val data: T) : ResultState<T>()
    data class Error(val exception: CustomException) : ResultState<Nothing>()
    object Loading : ResultState<Nothing>()
    object Idle : ResultState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            else -> "Loading[]"
        }
    }
}