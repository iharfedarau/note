package project.note.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import project.note.data.Note

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesView(
    note: Note,
    paddingValues: PaddingValues,
    back: () -> Unit,
    save: (Note) -> Unit,
    delete: (Long) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        back()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                title = {

                },
                actions = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            bottomSheetState.show()
                        }

                    }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        content = { it ->
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                var title by rememberSaveable { mutableStateOf(note.title) }
                var content by rememberSaveable { mutableStateOf(note.content) }

                TextField(title, singleLine = true, modifier = Modifier.fillMaxWidth(),  onValueChange = {
                    if (it.length <= 30) {
                        title = it
                        save(Note(title, content, note.id))
                    }
                })

                TextField(content, modifier = Modifier.fillMaxWidth().fillMaxHeight(1.0f).verticalScroll(rememberScrollState()), onValueChange = {
                    content = it
                    save(Note(title, content, note.id))
                })
            }

            ModalBottomSheetLayout(
                sheetState = bottomSheetState,
                sheetContent = {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(1, itemContent = {
                            ClickableText(modifier = Modifier.fillMaxWidth().height(48.dp),
                                text = AnnotatedString("Delete"),
                                onClick = {
                                    delete(note.id)
                                })
                            Divider(color = Color.Black, thickness = 1.dp)
                        })
                    }
                },
                content = {
                    // Main content of the screen goes here
                },
                scrimColor = Color.Black.copy(alpha = 0.5f),
                sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
        }
    )
}