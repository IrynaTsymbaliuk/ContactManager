package com.tsymbaliuk.contactmanager

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ContactItem (
    var photoId: Int,
    var firstName: String?,
    var secondName: String?,
    var mail: String?
) {
    @PrimaryKey(autoGenerate = true)
    var contactId: Long = 0
}