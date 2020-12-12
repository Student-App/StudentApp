package com.example.studentapp.marks

class Model_marks {
    var course_name: String? = null
    constructor()

    constructor(course_name: String?, Total_marks: String?, Marks_obtained: String?) {
        this.course_name = course_name
    }
}