package com.myprojects.shoppinglist.data

data class Item (
    val id: Int,
    val itemName: String,
    val quantity: Int,
    val isEditing: Boolean
)