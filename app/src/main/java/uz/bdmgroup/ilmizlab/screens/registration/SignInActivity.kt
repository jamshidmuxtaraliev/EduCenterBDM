package uz.bdmgroup.ilmizlab.screens.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_check_phone.*
import kotlinx.android.synthetic.main.activity_check_phone.flProgress
import kotlinx.android.synthetic.main.activity_check_phone.fullNameRegister
import kotlinx.android.synthetic.main.activity_check_phone.passwordRegister
import kotlinx.android.synthetic.main.activity_check_phone.passwordTryRegister
import kotlinx.android.synthetic.main.activity_register.*
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.screens.MainActivity
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.model.request.CheckPhoneRequest
import uz.bdmgroup.ilmizlab.model.request.LoginRequest
import uz.bdmgroup.ilmizlab.model.request.RegistrationRequest
import uz.bdmgroup.ilmizlab.utils.PrefUtils

enum class  LoginState{
    CHECK_PHONE,
    LOGIN,
    REGISTRATION
}

class SignInActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    var state = LoginState.CHECK_PHONE
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_phone)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        viewModel.checkPhoneData.observe(this, Observer {
            if (it.isReg){
                state = LoginState.LOGIN
            }else{
                state = LoginState.REGISTRATION
            }
            initViews()
        })

        viewModel.registrationData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            PrefUtils.setUser(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.loginData.observe(this, Observer {
            PrefUtils.setToken(it.token)
            PrefUtils.setUser(it)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        viewModel.progress.observe(this, Observer {
            flProgress.visibility = if (it) View.VISIBLE else View.GONE
        })

        cardViewBack.setOnClickListener {
            finish()
        }

        btnLogin.setOnClickListener {
            when(state){
                LoginState.CHECK_PHONE ->{
                    phone = edPhone.text.toString().replace(" ", "")
                    val checkPhoneRequest = CheckPhoneRequest(phone)
                    viewModel.checkPhone(checkPhoneRequest)
                }
                LoginState.LOGIN->{
                    val loginRequest = LoginRequest(phone,passwordRegister.text.toString())
                    viewModel.login(loginRequest)
                }
                LoginState.REGISTRATION->{
                    val fullname = fullNameRegister.text.toString()
                    val password = passwordRegister.text.toString()
                    val repassword = passwordTryRegister.text.toString()
                    if (fullname.isNullOrEmpty()){
                        Toast.makeText(this, "Please input full name!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password.isNullOrEmpty()){
                        Toast.makeText(this, "Please input password!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    if (password != repassword){
                        Toast.makeText(this, "Please input correct password!", Toast.LENGTH_LONG).show()
                        return@setOnClickListener
                    }
                    val registrationRequest = RegistrationRequest(phone,password,"1111",fullname)
                    viewModel.registration(registrationRequest)
                }
            }
        }

        initViews()
}

    private fun initViews() {
        namePoligon.visibility = View.GONE
        smsPoligon.visibility = View.GONE
        passwordPoligon.visibility = View.GONE
        resPasswordPoligon.visibility = View.GONE


        when(state){
            LoginState.CHECK_PHONE ->{
                tvTitle.text = "LOGIN"
                edPhone.isEnabled = true
            }
            LoginState.LOGIN ->{
                tvTitle.text = "LOGIN"
                passwordPoligon.visibility = View.VISIBLE
               edPhone.isEnabled = false
            }
            LoginState.REGISTRATION ->{
                tvTitle.text = "REGISTRATION"
                namePoligon.visibility = View.VISIBLE
                passwordPoligon.visibility = View.VISIBLE
                resPasswordPoligon.visibility = View.VISIBLE
                edPhone.isEnabled = false
            }
        }
    }
}