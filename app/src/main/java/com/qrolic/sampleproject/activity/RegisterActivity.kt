package com.qrolic.sampleproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.qrolic.sampleproject.R
import com.qrolic.sampleproject.database.AppDatabase
import com.qrolic.sampleproject.model.User
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    lateinit var tvRegisterLoginNow: TextView
    lateinit var btnRegisterNow: Button
    lateinit var etRegisterConfirmPassword: EditText
    lateinit var etRegisterPassword: EditText
    lateinit var etRegisterMobile: EditText
    lateinit var etRegisterEmail: EditText
    lateinit var etRegisterName: EditText
    val TAG = "RegisterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initAllControls()
    }

    private fun initAllControls() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Register")
        tvRegisterLoginNow = findViewById(R.id.tvRegisterLoginNow)
        btnRegisterNow = findViewById(R.id.btnRegisterNow)
        etRegisterConfirmPassword = findViewById(R.id.etRegisterConfirmPassword)
        etRegisterPassword = findViewById(R.id.etRegisterPassword)
        etRegisterMobile = findViewById(R.id.etRegisterMobile)
        etRegisterEmail = findViewById(R.id.etRegisterEmail)
        etRegisterName = findViewById(R.id.etRegisterName)
        tvRegisterLoginNow.setOnClickListener {
            finish()
        }
        btnRegisterNow.setOnClickListener(View.OnClickListener {
            onRegisterNowButtonClicked()
        })

    }

    private fun onRegisterNowButtonClicked() {
        val name = etRegisterName.text.toString();
        val email = etRegisterEmail.text.toString();
        val mobile = etRegisterMobile.text.toString();
        val password = etRegisterPassword.text.toString();
        val confirmPassword = etRegisterConfirmPassword.text.toString();
        if (name.isEmpty()) {
            Snackbar.make(etRegisterName, "Please Enter Name", Snackbar.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            Snackbar.make(etRegisterName, "Please Enter Email", Snackbar.LENGTH_SHORT).show()
        } else if (mobile.isEmpty()) {
            Snackbar.make(etRegisterName, "Please Enter Mobile No", Snackbar.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Snackbar.make(etRegisterName, "Please Enter Password", Snackbar.LENGTH_SHORT).show()
        } else if (!confirmPassword.equals(password)) {
            Snackbar.make(etRegisterName, "Enter Confirm Password Not Match", Snackbar.LENGTH_SHORT)
                .show()
        } else {
            checkAndSaveDataToLocalDataBase(name, email, mobile, password)
        }
    }

    private fun checkAndSaveDataToLocalDataBase(
        name: String,
        email: String,
        mobile: String,
        password: String
    ) {

        lifecycleScope.launch {


            val appDatabase: AppDatabase = AppDatabase.getDatabase(applicationContext)
            val userDao = appDatabase.userDao()
            if (userDao.findByEmail(email) != null) {
                Snackbar.make(etRegisterName, "Email Address Already Exiest", Snackbar.LENGTH_SHORT)
                    .show()
            } else {
                val user = User(null, name, email, mobile, password)
                val inserted = userDao.insertUser(user)
                Log.d(TAG, "checkAndSaveDataToLocalDataBase: $inserted")
                if (inserted > 0) {
                    Snackbar.make(
                        etRegisterName,
                        "Sucessfully Register Login Now",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        etRegisterName,
                        "Something wrong Please try again",
                        Snackbar.LENGTH_LONG
                    )
                }
            }

        }
    }
}