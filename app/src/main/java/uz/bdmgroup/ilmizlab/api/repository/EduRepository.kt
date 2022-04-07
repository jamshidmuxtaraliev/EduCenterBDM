package uz.bdmgroup.ilmizlab.api.repository

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import uz.bdmgroup.ilmizlab.api.ApiServices
import uz.bdmgroup.ilmizlab.model.*
import uz.bdmgroup.ilmizlab.model.request.*
import uz.bdmgroup.ilmizlab.model.response.CheckPhoneResponse
import uz.bdmgroup.ilmizlab.model.response.LoginResponse

class EduRepository() {
    val compositeDisposable = CompositeDisposable()

    fun getOffers(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<OfferModel>>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getOffers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<OfferModel>>>() {
                    override fun onNext(t: BaseResponse<List<OfferModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }


    fun getRegions(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<RegionSearchModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().getRegions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :
                    DisposableObserver<BaseResponse<List<RegionSearchModel>>>() {
                    override fun onNext(t: BaseResponse<List<RegionSearchModel>>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value = false
                    }

                    override fun onComplete() {

                    }
                })
        )

    }


    fun getNews(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<NewsModel>>
    ) {
        progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().getAllNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<NewsModel>>>() {
                    override fun onNext(t: BaseResponse<List<NewsModel>>) {
                        progress.value=false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value=false
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getNewsById(
        center_id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<NewsModel>>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getNewsById(center_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<NewsModel>>>() {
                    override fun onNext(t: BaseResponse<List<NewsModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getGradeById(
        center_id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<GradeModel>>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getGradeById(center_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<GradeModel>>>() {
                    override fun onNext(t: BaseResponse<List<GradeModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }


    fun getCategory(
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<CategoryModel>>>() {
                    override fun onNext(t: BaseResponse<List<CategoryModel>>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getEduCenter(
        filter: LCFilterModel,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<EduCenterModel>>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().getEduCenter(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<EduCenterModel>>>() {
                    override fun onNext(t: BaseResponse<List<EduCenterModel>>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value = false
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun checkPhone(
        phone: CheckPhoneRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckPhoneResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().checkPhone(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<CheckPhoneResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<CheckPhoneResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun login(
        loginRequest: LoginRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<LoginResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().login(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<LoginResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }

    fun registration(
        registrationRequest: RegistrationRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<LoginResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().register(registrationRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<LoginResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        progress.value = false
                        error.value = e.localizedMessage
                    }
                })
        )
    }


    fun makeRating(
        makeRatingRequest: RatingRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<Any>
    ) {
        progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().makeRating(makeRatingRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<Any>>() {
                    override fun onNext(t: BaseResponse<Any>) {
                        progress.value=false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value=false
                    }

                    override fun onComplete() {

                    }

                })
        )
    }

    fun setSubscriber(
        center_id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<Any>
    ) {
        progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().setSubscriber(SubscriberRequest(center_id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<Any>>() {
                    override fun onNext(t: BaseResponse<Any>) {
                        progress.value=false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value=false
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

    fun getCheckSubscriber(
        center_id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<Boolean>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getCheckSubscriber(center_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<Boolean>>() {
                    override fun onNext(t: BaseResponse<Boolean>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }

    fun getUser(
        error: MutableLiveData<String>,
        success: MutableLiveData<LoginResponse>
    ) {
        compositeDisposable.add(
            ApiServices.ApiCreator().getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<LoginResponse>>() {
                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                    }

                    override fun onComplete() {

                    }
                })
        )

    }


    fun updateProfile(
        fullname: MultipartBody.Part,
        file: MultipartBody.Part?,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<LoginResponse>
    ) {
        progress.value = true
        compositeDisposable.add(
            ApiServices.ApiCreator().updateProfile(fullname, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<LoginResponse>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: BaseResponse<LoginResponse>) {
                        progress.value = false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value = false

                    }
                })
        )
    }


    fun getTeacherByCourse(
        course_id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<TeacherModel>>
    ) {
        progress.value=true
        compositeDisposable.add(
            ApiServices.ApiCreator().getTeacherByCourse(course_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<BaseResponse<List<TeacherModel>>>() {
                    override fun onNext(t: BaseResponse<List<TeacherModel>>) {
                        progress.value=false
                        if (t.success) {
                            success.value = t.data
                        } else {
                            error.value = t.message
                        }
                    }

                    override fun onError(e: Throwable) {
                        error.value = e.localizedMessage
                        progress.value=false
                    }

                    override fun onComplete() {

                    }
                })
        )
    }

}