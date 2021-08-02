package com.qrolic.sampleproject.database

import android.content.Context
import android.content.SharedPreferences
import com.qrolic.sampleproject.utility.AppConstants

class MySharePreference(context: Context) {
    var sharePreference:SharedPreferences=context.getSharedPreferences(AppConstants.SHARE_PREF_NAME,
        Context.MODE_PRIVATE)
    lateinit var editor: SharedPreferences.Editor;
    init {
        editor=sharePreference.edit();
    }

    public fun saveLoginUserDetail(name:String?,email:String?,mobile:String?,password:String?)
    {
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("mobile",mobile)
        editor.putString("password",password)
        editor.putBoolean("is_login",true)
        editor.commit()
    }

    public fun getUserName():String?{
        return sharePreference.getString("name","")
    }

    public fun getEmail():String?{
        return sharePreference.getString("email","")
    }


    public fun getMobile():String?{
        return sharePreference.getString("mobile","")
    }

    public fun getPassword():String?{
        return sharePreference.getString("password","")
    }


    public fun getUserLogin():Boolean?{
        return sharePreference.getBoolean("is_login",false)
    }

}