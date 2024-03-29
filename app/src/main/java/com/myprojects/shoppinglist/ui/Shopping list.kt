package com.myprojects.shoppinglist.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myprojects.shoppinglist.data.Item


@Composable
fun ShoppingListUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        var itemList by remember {
            mutableStateOf(listOf<Item>())
        }

        val showDialog = remember {
            mutableStateOf(false)
        }

        val itemName = remember {
            mutableStateOf("")
        }

        val itemQuantity = remember {
            mutableStateOf("")
        }


        Button(
            onClick = { showDialog.value = true },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        ) {
            Text(text = "Add item")
        }


        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(itemList) {
                item ->
                if(item.isEditing){
                    ItemEditor(item = item, onEditComplete = {
                        editedName, editedQuantity ->
                        itemList = itemList.map { it.copy(isEditing = false) }
                        val editedItem = itemList.find { it.id == item.id}
                        editedItem?.let {
                            it.itemName = editedName
                            it.quantity = editedQuantity
                        }
                    })
                }else{
                    ItemCard(item = item, onEditClick = {
                        itemList.map { it.copy(isEditing = it.id == item.id) }
                    }, onDeleteClick = {
                        itemList -= item
                    })
                }
            }
        }

        if(showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = {
                                if(itemName.value.isNotBlank()) {
                                    val newItem = Item(
                                        1,
                                        itemName.value,
                                        itemQuantity.value.toInt(),
                                        false
                                    )
                                    itemList += newItem
                                    itemName.value = ""
                                    itemQuantity.value = ""
                                    showDialog.value = false
                                }
                            },
                        ) {
                            Text(text = "Add")
                        }

                        Button(
                            onClick = { showDialog.value = false },
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                },
                title = { Text(text = "Add Shopping item")},
                text = {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        OutlinedTextField(value = itemName.value,
                            onValueChange = { itemName.value = it },
                            label = { Text(text = "Enter item name") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(value = itemQuantity.value,
                            onValueChange = { itemQuantity.value = it },
                            label = { Text(text = "Enter quantity") }
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun ItemCard(
    item: Item,
    onEditClick: () -> Unit, 
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                BorderStroke(2.dp, Color.Blue),
                shape = RoundedCornerShape(20)
            )
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "${item.itemName}          Qty: ${item.quantity}", Modifier.padding(8.dp))
        Row {
            IconButton(onClick = { onEditClick }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit item")
            }
            IconButton(onClick = { onDeleteClick }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item")
            }
        }
    }
}


@Composable
fun ItemEditor(item: Item, onEditComplete: (String, Int) -> Unit) {

    var itemNameEdit by remember {
        mutableStateOf(item.itemName)
    }

    var itemQuantityEdit by remember {
        mutableStateOf(item.quantity.toString())
    }

    Row {
        Column {
            OutlinedTextField(
                value = itemNameEdit,
                onValueChange = {itemNameEdit = it},
                Modifier.border(0.dp, Color.Green),
                )
            OutlinedTextField(
                value = itemQuantityEdit,
                onValueChange = {itemQuantityEdit = it},
                Modifier.border(0.dp, Color.Green),
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
        }
    }
}