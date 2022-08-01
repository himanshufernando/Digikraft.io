package digikraft.project.digikraftbikestation.data

import digikraft.project.digikraftbikestation.utils.Status

sealed class ResultWrapper <out R>  (val status : Status, val _data : R?, val message: String?) {
    data class Success<out R>(val data : R) : ResultWrapper<R>(
        status = Status.SUCCESS,
        _data = data,
        message = null
    )
    data class Loading(val isLoading : Boolean) : ResultWrapper<Nothing>(
        status = Status.LOADING,
        _data = null,
        message = null
    )
    data class Error(val exception: String) : ResultWrapper<Nothing>(
        status = Status.ERROR,
        _data = null,
        message = exception
    )

}