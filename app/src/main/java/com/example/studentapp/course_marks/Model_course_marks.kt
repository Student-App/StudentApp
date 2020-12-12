package com.example.studentapp.course_marks

class Model_course_marks {
    var test_name: String? = null
    var test_marks: String? = null
    var test_marks_obtained: String? = null

    constructor()

    constructor(course_name: String?, test_marks: String?, test_marks_obtained: String?) {
        this.test_name = course_name
        this.test_marks = test_marks
        this.test_marks_obtained = test_marks_obtained
    }
}