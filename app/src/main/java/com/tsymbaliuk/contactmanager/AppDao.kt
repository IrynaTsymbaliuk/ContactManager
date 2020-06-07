package com.tsymbaliuk.contactmanager

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDao {

    @Query("SELECT * FROM ContactItem")
    fun getAllContacts(): LiveData<List<ContactItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllContacts(contactList: List<ContactItem>)

    @Query("UPDATE ContactItem SET firstName = :newFirstName WHERE contactId = :contactId")
    fun updateFirstNameOfContactWithID(newFirstName: String?, contactId: Long?): Int

    @Query("UPDATE ContactItem SET secondName = :newSecondName WHERE contactId = :contactId")
    fun updateSecondNameOfContactWithID(newSecondName: String?, contactId: Long?): Int

    @Query("UPDATE ContactItem SET mail = :newMail WHERE contactId = :contactId")
    fun updateMailOfContactWithID(newMail: String?, contactId: Long?): Int

    @Delete
    fun deleteContact(contact: ContactItem)

    @Query("DELETE FROM ContactItem")
    fun deleteAll()

}