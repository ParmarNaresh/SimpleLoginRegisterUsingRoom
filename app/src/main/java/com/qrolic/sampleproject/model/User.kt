package com.qrolic.sampleproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) val uid: Int?,
                @ColumnInfo(name = "name") val name: String?,
                @ColumnInfo(name = "email") val email: String?,
                @ColumnInfo(name = "mobile") val mobile: String?,
                @ColumnInfo(name = "password") val password: String?)
{

}
