package com.darkmat13r.samplechatdemo.data

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseRepository {
    private val mCompositeDisposable = CompositeDisposable()
    fun <T>  execute(observable: Observable<ApiResponse<T>>) :  MutableLiveData<Resource<T>>{
        val  result  = MutableLiveData<Resource<T>>()
        result.postValue(Resource.loading(null))
        execute(observable, result)
        return result
    }

    fun <T>  execute(observable: Observable<ApiResponse<T>>, result  : MutableLiveData<Resource<T>>){
        result.postValue(Resource.loading(null))
        val disposable =observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.error){
                    result.postValue(Resource.error(it.message.toString(), null))
                }else{
                    result.postValue(Resource.success(it.result))
                }
            }, {
                it.printStackTrace()
                result.postValue(Resource.error(it.message.toString(), null))
            })
        mCompositeDisposable.add(disposable)
    }
}