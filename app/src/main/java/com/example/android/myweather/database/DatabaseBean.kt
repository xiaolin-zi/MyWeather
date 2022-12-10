package com.example.android.myweather.database


class DatabaseBean {
    private var _id = 0
    var city: String? = null
    var content: String? = null

    constructor() {}
    constructor(_id: Int, city: String?, content: String?) {
        this._id = _id
        this.city = city
        this.content = content
    }

    fun get_id(): Int {
        return _id
    }

    fun set_id(_id: Int) {
        this._id = _id
    }
}

