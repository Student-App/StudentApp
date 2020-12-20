package com.example.studentapp.showTotal

class Model_showTotal {
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