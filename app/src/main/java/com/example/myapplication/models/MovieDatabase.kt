//package com.example.myapplication.models
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import kotlinx.coroutines.InternalCoroutinesApi
//import kotlinx.coroutines.internal.synchronized
//
//
//@Database(entities = [Result::class], version = 1)
//abstract class MovieDatabase:RoomDatabase() {
//
//    abstract fun movieDao():MovieDao
//
//
//
//    companion object{
//        @Volatile
//        private var INSTANCE: MovieDatabase? =null
//
//        @OptIn(InternalCoroutinesApi::class)
//        fun getInstance(context: Context):MovieDatabase{
//            if(INSTANCE==null){
//                synchronized(this){
//                    INSTANCE= Room.databaseBuilder(context.applicationContext,MovieDatabase::class.java,"movieDB").build()
//
//                }
//                 }
//            return INSTANCE!!
//        }
//    }
//
//}