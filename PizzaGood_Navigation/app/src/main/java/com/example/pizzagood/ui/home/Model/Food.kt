package com.example.pizzagood.ui.home.Model

class Food {
    var name : String? = null
    //var price : Double? = null
    var des : String? = null
    var image : Int? = null

    constructor(name: String?, des: String?, image: Int) {
        this.name = name
        // this.price = price
        this.des = des
        this.image = image
    }
}