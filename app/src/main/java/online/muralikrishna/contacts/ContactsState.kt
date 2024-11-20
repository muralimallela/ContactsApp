package online.muralikrishna.contacts

data class ContactsState(
    val contacts: List<Contact> = emptyList(),
    val firstName : String = "",
    val lastName : String = "",
    val phoneNumber : String = "",
    val isAddingContact : Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
