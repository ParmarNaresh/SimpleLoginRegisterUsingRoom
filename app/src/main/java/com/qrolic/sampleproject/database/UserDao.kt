package com.qrolic.sampleproject.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.qrolic.sampleproject.model.User
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE email= :email LIMIT 1")
    suspend fun findByEmail(email: String): User

    @Query("SELECT * FROM user WHERE email= :email and password=:password LIMIT 1")
    suspend fun loginUser(email: String,password:String): User?

    @Insert
    suspend fun insertAll(vararg users: User)

    @Insert
    suspend fun insertUser(users: User):Long

    @Delete
    suspend fun delete(user: User)
}