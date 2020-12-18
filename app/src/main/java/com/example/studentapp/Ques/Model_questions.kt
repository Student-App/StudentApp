package com.example.studentapp.Ques

class Model_questions {
    var ques_title: String? = null
    var author_id: String? = null
    constructor()

    constructor(ques_title: String?, author_id: String?) {
        this.ques_title = ques_title
        this.author_id= author_id
    }
}