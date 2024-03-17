package com.myprojects.shoppinglist.data

data class Item (
    val id: Int,
    var itemName: String,
    var quantity: Int,
    val isEditing: Boolean
)