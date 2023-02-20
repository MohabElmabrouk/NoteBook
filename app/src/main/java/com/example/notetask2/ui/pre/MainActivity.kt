package com.example.notetask2.ui.pre

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notetask2.R
import com.example.notetask2.model.Note
import com.example.notetask2.ui.pre.destinations.AddNotesDestination
import com.example.notetask2.ui.pre.destinations.EditTextDestination
import com.example.notetask2.ui.pre.destinations.HomescreenDestination
import com.example.notetask2.ui.pre.destinations.SearchingNoteDestination
import com.example.notetask2.ui.theme.NOTETASK2Theme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NOTETASK2Theme {
                DestinationsNavHost(navGraph = NavGraphs.root)

            }
        }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun Homescreen(nav: DestinationsNavigator, viewModel: NoteListViewModel = hiltViewModel()) {
    val ListOfColor = listOf<Color>(
        Color(0xffFD99FF),
        Color(0xffFF9E9E),
        Color(0xff91F48F),
        Color(0xffFFF599),
        Color(0xff9EFFFF),
        Color(0xffB69CFF),
    )
    var showdilog = remember { mutableStateOf(false) }
    //viewModel.deleteAllNotes() //لمسح كل المفكرة
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            Modifier
                .width(412.dp)
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Notes", fontSize = 43.sp)
            Spacer(modifier = Modifier.width(120.dp))
            Button(
                onClick = { nav.navigate(SearchingNoteDestination) },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF3B3B3B)
                )
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.vector__3_),
                    tint = Color.White,
                    contentDescription = "",

                    )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { showdilog.value = true },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.info_outline),
                    tint = Color.White,
                    contentDescription = "",
                )
            }
            if (showdilog.value) {
                Alartdilog(showdilog)
            }
        }
        Box(
            Modifier
                .background(Color.Black)
                .fillMaxSize(), contentAlignment = Alignment.TopCenter
        ) {
            //  viewModel.getNotes() بعد الجدف كامل لازم نستدعي هادي باش تعرض محتويات القائمة
            if (viewModel.Noteslist.isEmpty()) {
                Column() {
                    Spacer(modifier = Modifier.padding(0.dp, 80.dp))
                    Image(
                        painter = painterResource(id = R.drawable.rafiki),
                        contentDescription = ""
                    )
                    Text(
                        text = "Creat your first Note!",
                        modifier = Modifier.padding(120.dp, 0.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.Black)

                ) {

                    items(viewModel.Noteslist) { note ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .background(color = ListOfColor.random())
                                .clickable {
                                    nav.navigate(EditTextDestination(note))
                                }
                        ) {
                            Spacer(modifier = Modifier.padding(10.dp, 10.dp))
                            Text(text = note.title, color = Color.Black)
                            Spacer(modifier = Modifier.padding(10.dp, 0.dp))
                            Text(text = note.content, color = Color.Black)


                        }
                    }
                }

            }
            FloatingActionButton(

                onClick = { nav.navigate(AddNotesDestination) },
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomEnd)
                    .padding(25.dp),

                backgroundColor = Color.Green,
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.vector__4_),
                    tint = Color.White,
                    contentDescription = "",

                    )
            }


        }
    }
}

@Destination
@Composable
fun SearchingNote(nav: DestinationsNavigator) {
    Column(modifier = Modifier.fillMaxSize()) {
        var text by remember { mutableStateOf("") }
        TextField(modifier = Modifier
            .background(color = Color.White)
            .width(350.dp)
            .height(50.dp)
            .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = text, onValueChange = { newtext -> text = newtext },
            placeholder = { Text(text = "Search by the keyword...", fontSize = 12.sp, color = Color.LightGray) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),

            shape = RoundedCornerShape(30.dp),
            trailingIcon = {
                IconButton(onClick = {nav.navigateUp()
                }) {
                    Image(
                        imageVector = Icons.Filled.Close,
                        contentDescription = ""
                    )

                }
            }
        )
    }

}

@Destination
@Composable
fun AddNotes(nav: DestinationsNavigator) {
    var showSavedilog = remember { mutableStateOf(false) }
    var showDeletedilog = remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier
                .width(412.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { nav.navigateUp() },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.vector__5_),
                    tint = Color.LightGray,
                    contentDescription = "",

                    )
            }

            Spacer(modifier = Modifier.width(200.dp))
            Button(
                onClick = { showDeletedilog.value = true },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.visibility),
                    tint = Color.LightGray,
                    contentDescription = "",

                    )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { showSavedilog.value = true },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.save),
                    tint = Color.White,
                    contentDescription = "",

                    )
            }

        }
        TextField(
            modifier = Modifier
                .width(412.dp)
                .height(120.dp)
                .background(color = Color.LightGray),
            textStyle = TextStyle(fontSize = 30.sp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = title, onValueChange = { newtitle -> title = newtitle },
            placeholder = { Text(text = "Title", fontSize = 50.sp) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
            ),
            shape = RoundedCornerShape(30.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        TextField(
            modifier = Modifier
                .background(color = Color.LightGray)
                .width(412.dp)
                .height(600.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            ),
            value = content, onValueChange = { newcontent -> content = newcontent },
            placeholder = { Text(text = "Type something...", fontSize = 20.sp) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
    }


    if (showSavedilog.value) {
        AlartdilogSave(showSavedilog, title, content, nav = nav)
    }
}

@Composable
fun Alartdilog(showdilog: MutableState<Boolean>) {
    var show by remember {
        mutableStateOf(true)
    }

    if (show) {
        AlertDialog(modifier = Modifier,
            onDismissRequest = {
                show = false
                showdilog.value = false
            },
            title = { Text(text = " Informations") },
            text = {
                Column() {
                    Text(text = "___")
                    Text(text = "___")
                    Text(text = "___")
                    Text(text = "___")
                }
            },
            confirmButton = {})
    }
}


@Composable
fun Alartdilogdelete(
    showDeletedilog: MutableState<Boolean>,
    note: Note,
    viewModel: NoteListViewModel = hiltViewModel(),
    nav: DestinationsNavigator? = null
) {
    var show by remember { mutableStateOf(true) }
    if (show) {
        AlertDialog(
            modifier = Modifier,
            onDismissRequest = {
                show = false
                showDeletedilog.value = false
            },
            title = {
                Row() {
                    Spacer(modifier = Modifier.padding(60.dp, 0.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.info_outline),
                        contentDescription = ""
                    )

                }
            },
            text = {
                Column() {
                    Text(text = "Are your sure you want delete your Note ?")

                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xff30BE71)),
                    onClick = {
                        show = false
                        showDeletedilog.value = false
                        viewModel.deleteNote(note)
                        nav?.navigateUp()


                    }
                ) {
                    Text(text = "sure")
                }
            }, dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xffFF0000)),
                    onClick = {
                        show = false
                        showDeletedilog.value = false
                    }) {
                    Text(text = "Discard")

                }
            }
        )

    }
}

@Composable
fun AlartdilogSave(
    showSavedilog: MutableState<Boolean>,
    ti: String,
    con: String,
    viewModel: AddNoteViewModel = hiltViewModel(),
    nav: DestinationsNavigator? = null

) {
    val context = LocalContext.current
    var title: String = ti
    var content: String = con
    var show by remember {
        mutableStateOf(true)
    }

    if (show) {
        AlertDialog(modifier = Modifier,
            onDismissRequest = {
                show = false
                showSavedilog.value = false
            },
            title = {
                Row() {
                    Spacer(modifier = Modifier.padding(60.dp, 0.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.info_outline),
                        contentDescription = ""
                    )

                }
            },
            text = {
                Column() {

                    Text(text = "Save changes ?")
                }
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xff30BE71)),
                    onClick = {
                        showSavedilog.value = false
                        viewModel.addNote(title, content)
                        Toast.makeText(context, "SAVED", Toast.LENGTH_LONG).show()
                        nav?.navigateUp()
                    }

                ) {
                    Text(text = "Save")
                }
            }, dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color(0xffFF0000)),
                    onClick = {
                        showSavedilog.value = false
                    }) {
                    Text(text = "Discard")

                }
            }

        )
    }
}

@Destination
@Composable
fun EditText(nav: DestinationsNavigator, note: Note, viewModel: NoteListViewModel = hiltViewModel()) {
    var showDeletedilog = remember { mutableStateOf(false) }
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            Modifier
                .width(412.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = { nav.navigateUp() },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.vector__5_),
                    tint = Color.LightGray,
                    contentDescription = "",

                    )
            }

            Spacer(modifier = Modifier.width(200.dp))
            Button(
                onClick = { showDeletedilog.value = true },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp),
                    tint = Color.LightGray,
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    note.title=title
                    note.content=content
                    viewModel.update(note)
                    nav.navigateUp()
                },
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF3B3B3B)),
                modifier = Modifier
                    .padding(0.dp, 12.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.baseline_edit_24),
                    tint = Color.White,
                    contentDescription = "",

                    )
            }

        }
        TextField(
            modifier = Modifier
                .width(412.dp)
                .height(120.dp)
                .background(color = Color.LightGray),
            textStyle = TextStyle(fontSize = 30.sp),
            value = title, onValueChange = { newtitle -> title = newtitle },
            placeholder = { Text(text = note.title, fontSize = 50.sp) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,

                ),
            shape = RoundedCornerShape(30.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        TextField(
            modifier = Modifier
                .background(color = Color.LightGray)
                .width(412.dp)
                .height(600.dp),
            value = content, onValueChange = { newcontent -> content = newcontent },
            placeholder = { Text(text = note.content, fontSize = 20.sp) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                cursorColor = Color.Black
            )
        )
    }

    if (showDeletedilog.value) {
        Alartdilogdelete(showDeletedilog, note, nav = nav)
    }
}
