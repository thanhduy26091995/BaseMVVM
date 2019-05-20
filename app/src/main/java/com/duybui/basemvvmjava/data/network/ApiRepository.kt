package com.duybui.basemvvmjava.data.network

import com.duybui.basemvvmjava.MyApplication
import com.duybui.basemvvmjava.data.models.User
import com.duybui.basemvvmjava.data.response.BaseResponseObject

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ApiRepository(private val apiInterface: ApiInterface) {

    private var apiListener: ApiListener? = null

    fun setApiListener(apiListener: ApiListener) {
        this.apiListener = apiListener
    }

    fun getRandomUser(number: Int) {
        val observable = apiInterface.getRandomUser(number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        handleResponseObject(observable, ApiFunction.GET_RANDOM_USER)
    }

    private fun <T> handleResponseObject(observable: Observable<T>, apiFunction: ApiFunction) {
        observable.subscribe(object : Observer<T> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(value: T?) {
                if (value != null) {
                    val responseObject = value as BaseResponseObject<T>?
                    if (responseObject!!.code == 0) {
                        apiListener!!.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_SUCCESS, (value as BaseResponseObject<T>).data)
                    } else {
                        apiListener!!.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, responseObject.message)
                    }
                }
            }

            override fun onError(e: Throwable) {
                apiListener!!.onResponseListener(apiFunction, ApiStatus.CALL_API_RESULT_FAILURE, e.toString())
            }

            override fun onComplete() {

            }
        })
    }
}
