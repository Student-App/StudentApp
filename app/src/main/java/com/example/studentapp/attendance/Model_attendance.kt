package com.example.studentapp.attendance

class Model_attendance{
    var course_name: String? = null
    var Absence: String? = null
    constructor()

    constructor(course_name: String?, Absence: String?) {
        this.course_name = course_name
        this.Absence = Absence
    }


}