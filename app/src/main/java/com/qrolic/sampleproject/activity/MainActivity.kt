package com.qrolic.sampleproject.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.qrolic.sampleproject.R
import com.qrolic.sampleproject.database.AppDatabase
import com.qrolic.sampleproject.database.MySharePreference
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var tvLoginRegisterNow: TextView
    lateinit var btnLoginNow: Button
    lateinit var etLoginPassword: EditText
    lateinit var etLoginEmail: EditText
    lateinit var mySharePreference: MySharePreference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAllControls()
    }

    private fun initAllControls() {
        mySharePreference= MySharePreference(applicationContext)
        if(mySharePreference.getUserLogin()!!)
        {
            val intent: Intent = Intent(this@MainActivity, HomeActivity ::class.java)
            startActivity(intent)
            finish()
        }
        tvLoginRegisterNow = findViewById(R.id.tvRegisterLoginNow)
        btnLoginNow = findViewById(R.id.btnLoginNow)
        etLoginEmail = findViewById(R.id.etLoginEmail)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        tvLoginRegisterNow.setOnClickListener {

        }

        btnLoginNow.setOnClickListener {
            onLoginButtonClicked()
        }

    }

    private fun onLoginButtonClicked() {
        val email = etLoginEmail.text.toString();
        val password = etLoginPassword.text.toString();
        if (email.isEmpty()) {
            Snackbar.make(etLoginEmail, "Please Enter Email Address", Snackbar.LENGTH_LONG).show()
        } else if (password.isEmpty()) {
            Snackbar.make(etLoginEmail, "Please Enter Password", Snackbar.LENGTH_LONG).show()
        } else {
            verifyLoginDetail(email, password)
        }
    }

    private fun verifyLoginDetail(email: String, password: String) {
        lifecycleScope.launch {
            val appDatabase: AppDatabase = AppDatabase.getDatabase(applicationContext)
            val userDao = appDatabase.userDao()
            val user = userDao.loginUser(email, password)
            if (user != null) {
                mySharePreference.saveLoginUserDetail(
                    user.name,
                    user.email,
                    user.mobile,
                    user.password
                )
                var intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Snackbar.make(etLoginEmail, "User Name Password Not Match", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

}