package com.myprojects.shoppinglist.ui

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.myprojects.shoppinglist.data.Item


//@Preview
@Composable
fun ShoppingListUI() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        val itemList = remember {
            mutableListOf(listOf<Item>())
        }

        val showDialog = remember {
            mutableStateOf(false)
        }

        var itemName = remember {
            mutableStateOf("")
        }

        var itemQuantity = remember {
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
            items(itemList) { item ->

            }
        }

        if(showDialog.value) {
            AddItemDialog(showDialog, itemName, itemQuantity, itemList)
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun AddItemDialog(showDialog: MutableState<Boolean>,
                  itemName: MutableState<String>,
                  itemQuantity: MutableState<String>,
                  itemList: MutableList<List<Item>>) {

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
                                  itemList.add(listOf(newItem))
                                  itemName.value = ""
                                  itemQuantity.value = ""
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



@Composable
fun ItemCard(item: Item) {

}