package uz.bdmgroup.ilmizlab.model


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import uz.bdmgroup.ilmizlab.api.repository.EduRepository
import uz.bdmgroup.ilmizlab.model.request.*
import uz.bdmgroup.ilmizlab.model.response.CheckPhoneResponse
import uz.bdmgroup.ilmizlab.model.response.LoginResponse
import uz.bdmgroup.ilmizlab.model.response.RegistrationModel
import uz.bdmgroup.ilmizlab.utils.PrefUtils

class MainViewModel:ViewModel() {

    val repository=EduRepository()

    val error =MutableLiveData<String>()
    val progress=MutableLiveData<Boolean>()
    val categoriesData=MutableLiveData<List<CategoryModel>>()
    val topEduCenterData=MutableLiveData<List<EduCenterModel>>()
    val nearEduCenterData=MutableLiveData<List<EduCenterModel>>()
    val offerData=MutableLiveData<List<OfferModel>>()
    val newsData=MutableLiveData<List<NewsModel>>()
    val newsByIdData=MutableLiveData<List<NewsModel>>()
    val gradeById=MutableLiveData<List<GradeModel>>()
    val regionData=MutableLiveData<List<RegionSearchModel>>()


    val checkPhoneData = MutableLiveData<CheckPhoneResponse>()
    val registrationData = MutableLiveData<LoginResponse>()
//    val confirmData = MutableLiveData<LoginResponse>()
    val loginData = MutableLiveData<LoginResponse>()

    val makeRatingData = MutableLiveData<Any>()
    val subscriberData=MutableLiveData<Any>()
    val checkSubscriberData=MutableLiveData<Boolean>()
    val userData=MutableLiveData<LoginResponse>()
    val updateProfileData=MutableLiveData<LoginResponse>()
    val teacherData=MutableLiveData<List<TeacherModel>>()

    fun getTeacherByCourse(course_id:Int){
        repository.getTeacherByCourse(course_id, error, progress, teacherData)
    }

    fun updateProfile(fullname:MultipartBody.Part, file:MultipartBody.Part?){
        repository.updateProfile(fullname, file, error, progress, updateProfileData)
    }

    fun getUser(){
        repository.getUser(error, userData)
    }

    fun getOffers(){
        repository.getOffers(error, offerData)
    }

    fun getNews(){
        repository.getNews(error, progress, newsData)
    }

    fun getRegions(){
        repository.getRegions(error, progress, regionData)
    }

    fun getNewsById(center_id:Int){
        repository.getNewsById(center_id, error, newsByIdData)
    }

    fun getGradeById(center_id: Int){
        repository.getGradeById(center_id, error, gradeById)
    }

    fun getCategories(){
        repository.getCategory(error, categoriesData)
    }

    fun getEduCenter(filter:LCFilterModel){
        repository.getEduCenter(filter , error, progress, topEduCenterData)
    }

    fun getNearEduCenter(filter:LCFilterModel =  LCFilterModel(0,0,0, 0, "", "distance",
        50, PrefUtils.getLocation()?.lat ?: 0.0, PrefUtils.getLocation()?.long ?: 0.0, false )){
        repository.getEduCenter(filter , error, progress, nearEduCenterData)
    }

    fun checkPhone(phone: CheckPhoneRequest) {
        repository.checkPhone(phone, error, progress, checkPhoneData)
    }

    fun login(loginRequest: LoginRequest) {
        repository.login(loginRequest, error, progress, loginData)
    }

    fun registration(registrationRequest: RegistrationRequest) {
        repository.registration(registrationRequest, error, progress, registrationData)
    }

    fun makeRating(makeRatingRequest: RatingRequest) {
        repository.makeRating(
            makeRatingRequest, error, progress, makeRatingData
        )
    }

    fun setSubscriber(center_id: Int){
        repository.setSubscriber(center_id, error, progress, subscriberData)
    }

    fun getCheckSubscriber(center_id: Int){
        repository.getCheckSubscriber(center_id, error, checkSubscriberData)
    }
}