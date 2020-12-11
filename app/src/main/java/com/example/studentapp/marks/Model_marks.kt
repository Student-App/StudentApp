package com.example.studentapp.marks

class Model_marks {
    var course_name: String? = null
    var Total_marks: String? = null
    var Marks_obtained: String? = null
    constructor(){

    }

    constructor(course_name: String?, Total_marks: String?, Marks_obtained: String?) {
        this.course_name = course_name
        this.Total_marks = Total_marks
        this.Marks_obtained = Marks_obtained
    }
}