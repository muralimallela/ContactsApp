package online.muralikrishna.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactsScreen(
    state: ContactsState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold (
        floatingActionButton= {
            FloatingActionButton(onClick = {
                    onEvent(ContactEvent.ShowDialog)
                }
            ){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact"
                )
            }
        },


    ){ innerPadding ->
        if(state.isAddingContact){
            AddContactDialog(
                state = state,
                onEvent = onEvent
            )
        }
        LazyColumn (
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            item{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(ContactEvent.SortContacts(sortType))
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            RadioButton(selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(ContactEvent.SortContacts(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.contacts.size){ contactIndex ->
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "${state.contacts[contactIndex].firstName} ${state.contacts[contactIndex].lastName}",
                            fontSize = 20.sp
                        )
                        Text(
                            text = state.contacts[contactIndex].phoneNumber,
                            fontSize = 16.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(ContactEvent.DeleteContact(state.contacts[contactIndex]))
                    }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Contact")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ContactsScreenPreview() {
    ContactsScreen(state = ContactsState(), onEvent = {})
}