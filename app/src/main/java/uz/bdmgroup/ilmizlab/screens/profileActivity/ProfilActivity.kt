package uz.bdmgroup.ilmizlab.screens.profileActivity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_check_phone.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.fragment_see_more.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.bdmgroup.ilmizlab.R
import uz.bdmgroup.ilmizlab.model.MainViewModel
import uz.bdmgroup.ilmizlab.screens.MainActivity
import uz.bdmgroup.ilmizlab.utils.Constants
import uz.bdmgroup.ilmizlab.utils.PrefUtils
import java.io.File


class ProfilActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var part:MultipartBody.Part
    var part2:MultipartBody.Part? = null

    companion object{
        val IMAGE_REQUEST_CODE=100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)

        editNAmeUSer.setText(PrefUtils.getUser()!!.fullname)
        Glide.with(imageUser).load(Constants.HOSTING_IMAGE+PrefUtils.getUser()?.avatar?:"").into(imageUser)

        viewModel.error.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

        btnSaveProfile.setOnClickListener {
            part = MultipartBody.Part.createFormData("fullname", "${editNAmeUSer.text}")
            viewModel.updateProfile(part, part2)
        }

        viewModel.updateProfileData.observe(this, Observer {
            val i = Intent(this, MainActivity::class.java)
            startActivityForResult(i, 2002)
            finish()
        })

        viewModel.progress.observe(this, Observer {
            flProgressProfil.visibility = if (it) View.VISIBLE else View.GONE
        })

        closeLevel.setOnClickListener {
            finish()
        }

        pickerImage.setOnClickListener {
            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            imageUser.setImageURI(uri)

            val file:File=uri.toFile()
            val fileBody: RequestBody =
                RequestBody.create(MediaType.parse("image/*"), file)
            part2 = MultipartBody.Part.createFormData("avatar",file.name,fileBody)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}

