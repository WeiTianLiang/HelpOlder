package com.example.entrance.register.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.RadioButton
import com.example.entrance.R
import com.example.entrance.register.presenter.RegisterPresenterCompl
import com.example.tools.fragment.BaseFragment
import com.example.tools.picture.getOrientation
import com.example.tools.picture.rotateImage
import kotlinx.android.synthetic.main.escort_fragment.*
import java.io.FileNotFoundException

/**
 *
 * @author WeiTianLiang
 */
class EscortRegisterFragment : BaseFragment() {

    private val presenter by lazy { context?.let { RegisterPresenterCompl(it) } }
    private var sex = ""
    private var sex_button: RadioButton? = null
    private var workType_button: RadioButton? = null
    private var workState = 0
    private var bitmap: Bitmap? = null
    private var picture: String = "http://pic29.nipic.com/20130601/12122227_123051482000_2.jpg"

    override fun onViewCreate(savedInstanceState: Bundle?) {

    }

    override fun onInflated(savedInstanceState: Bundle?) {
        sex_register.setOnCheckedChangeListener { _, checkedId ->
            sex_button = findViewById(checkedId)
            sex = sex_button?.text.toString()
        }

        type_work.setOnCheckedChangeListener { _, checkedId ->
            workType_button = findViewById(checkedId)
            workState = if(workType_button?.text == "全职") {
                0
            } else {
                1
            }
        }

        escort_head.setOnClickListener {
            presenter?.doGetHeadPicture(this)
        }

        main_register.setOnClickListener {
            presenter?.doRegister(
                type = "陪护",
                name = name_register.text.toString(),
                age = age_register.text.toString(),
                sex = sex,
                account = account_register.text.toString(),
                password = password_register.text.toString(),
                repeat = repeat_register.text.toString(),
                imageUrl = picture,
                type_work = workState,
                workExperience = workExperience.text.toString(),
                workTime = workTime.text.toString()
            )
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.escort_fragment
    }

    @Suppress(
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
        "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (123 == requestCode && data != null) {
            try {
//                    val file = File(data.data.path)
                try {
                    bitmap =
                        BitmapFactory.decodeStream((context as RegisterActivity).contentResolver.openInputStream(data.data))
                    escort_head.setImageBitmap(rotateImage(bitmap!!, getOrientation(context as RegisterActivity, data.data)))
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}