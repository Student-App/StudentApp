package com.example.studentapp.ans

class Model_answers {
    var author_id: String? = null
    var answer: String? = null
    constructor()

    constructor( author_id: String?, answer: String?) {

        this.author_id = author_id
        this.answer = answer
    }
}