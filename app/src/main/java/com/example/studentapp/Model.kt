package com.example.studentapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class Model{
    var course_name: String? = null
    var time: String? = null
    constructor()

    constructor(course_name: String?, time: String?) {
        this.course_name = course_name
        this.time = time
    }


    @Parcelize
    class User(var id: String, var name:  String, var image: String, var Email: String): Parcelable{
        constructor(): this("","","","")
    }

    class ChatMessage(val id: String ,val text: String, val fromID: String, val toID: String, val timeStamp: Long )
    {
        constructor(): this("","", "", "", -1)
    }

}
