package com.tsymbaliuk.contactmanager

import androidx.lifecycle.LiveData

class AppRepository(private val appDao: AppDao) {

    val allGameResults: LiveData<List<ContactItem>> = appDao.getAllContacts()

    fun deleteContact(contact: ContactItem) {
        appDao.deleteContact(contact)
    }

    fun reset() {
        appDao.deleteAll()
        appDao.insertAllContacts(TestData.contacts)
    }

    fun updateFirstName(newFirstName: String?, contactId: Long) {
        appDao.updateFirstNameOfContactWithID(newFirstName, contactId)
    }

    fun updateSecondName(newSecondName: String?, contactId: Long) {
        appDao.updateSecondNameOfContactWithID(newSecondName, contactId)
    }

    fun updateMail(newMail: String?, contactId: Long) {
        appDao.updateMailOfContactWithID(newMail, contactId)
    }

}