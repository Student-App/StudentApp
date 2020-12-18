package com.example.studentapp.Resources

class Model_course {
    var course_name: String? = null
    constructor()

    constructor(course_name: String?, Total_marks: String?, Marks_obtained: String?) {
        this.course_name = course_name
    }
}