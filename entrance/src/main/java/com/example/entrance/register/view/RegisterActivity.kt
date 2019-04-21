package com.example.entrance.register.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import com.example.entrance.R
import com.example.entrance.register.presenter.RegisterPresenterCompl
import com.example.tools.picture.getOrientation
import com.example.tools.picture.imagePath
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.activity_register.*
import java.io.File
import java.io.FileNotFoundException

class RegisterActivity : AppCompatActivity() {

    private val presenter by lazy { RegisterPresenterCompl(this) }
    private var type = ""
    private var sex = ""
    private var identity_button: RadioButton? = null
    private var sex_button: RadioButton? = null
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        identity_register.setOnCheckedChangeListener { _, checkedId ->
            identity_button = findViewById(checkedId)
            type = identity_button?.text.toString()
        }

        sex_register.setOnCheckedChangeListener { _, checkedId ->
            sex_button = findViewById(checkedId)
            sex = sex_button?.text.toString()
        }

        register_head.setOnClickListener {
            presenter.doGetHeadPicture()
        }

        main_register.setOnClickListener {
            presenter.doRegister(
                type,
                name_register.text.toString(),
                age_register.text.toString(),
                sex,
                account_register.text.toString(),
                password_register.text.toString(),
                repeat_register.text.toString()
            )
        }

        register_back.setOnClickListener {
            presenter.doBack()
        }
    }

    @Suppress(
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (123 == requestCode && data != null) {
            try {
//                    val file = File(data.data.path)
                bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(data.data))
                register_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(this, data.data)))
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }

}
