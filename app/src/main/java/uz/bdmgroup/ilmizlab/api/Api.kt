package uz.bdmgroup.ilmizlab.api

import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*
import uz.bdmgroup.ilmizlab.model.*
import uz.bdmgroup.ilmizlab.model.request.*
import uz.bdmgroup.ilmizlab.model.response.CheckPhoneResponse
import uz.bdmgroup.ilmizlab.model.response.LoginResponse
import uz.bdmgroup.ilmizlab.model.response.RegistrationModel

interface Api {
    @GET("offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_news")
    fun getAllNews(): Observable<BaseResponse<List<NewsModel>>>

    @GET("get_news/{center_id}")
    fun getNewsById(@Path("center_id") center_id: Int): Observable<BaseResponse<List<NewsModel>>>

    @GET("get_ratings/{center_id}")       //////////////////
    fun getGradeById(@Path("center_id") center_id: Int): Observable<BaseResponse<List<GradeModel>>>

    @GET("regions")
    fun getRegions(): Observable<BaseResponse<List<RegionSearchModel>>>


    @GET("categories")
    fun getCategories(): Observable<BaseResponse<List<CategoryModel>>>

    @POST("training_centers")
    fun getEduCenter(
        @Body LFCenter: LCFilterModel
    ): Observable<BaseResponse<List<EduCenterModel>>>

    @POST("check_phone")
    fun checkPhone(@Body checkPhone:CheckPhoneRequest): Observable<BaseResponse<CheckPhoneResponse>>

    @POST("login")
    fun login(@Body loginRequest: LoginRequest): Observable<BaseResponse<LoginResponse>>

    @POST("registration")
    fun register(@Body registrationRequest: RegistrationRequest): Observable<BaseResponse<LoginResponse>>

    @POST("make_rating")
    fun makeRating(@Body makeRatingRequest: RatingRequest): Observable<BaseResponse<Any>>

    @POST("set_subscriber")
    fun setSubscriber(
        @Body request: SubscriberRequest
    ):Observable<BaseResponse<Any>>

    @GET("check_subscriber/{center_id}")
    fun getCheckSubscriber(@Path("center_id") center_id: Int): Observable<BaseResponse<Boolean>>

    @GET("user")
    fun getUser():Observable<BaseResponse<LoginResponse>>

    @Multipart
    @POST("update_profile")
    fun updateProfile(
        @Part fullname:MultipartBody.Part,
        @Part file:MultipartBody.Part?
    ):Observable<BaseResponse<LoginResponse>>

    @GET("course_teachers/{course_id}")
    fun getTeacherByCourse(@Path("course_id") courseId:Int):Observable<BaseResponse<List<TeacherModel>>>
}