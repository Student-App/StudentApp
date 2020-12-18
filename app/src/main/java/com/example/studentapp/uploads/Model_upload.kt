package com.example.studentapp.uploads

class Model_upload {
    var file_name: String? = null
    var file_url: String? = null

    constructor()

    constructor(file_name: String?, file_url: String?) {
        this.file_name = file_name
        this.file_url = file_url

    }
}