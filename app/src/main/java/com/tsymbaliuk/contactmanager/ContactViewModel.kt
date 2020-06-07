package com.tsymbaliuk.contactmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val database: AppDatabase = AppDatabase.getDatabase(application, MainScope())
    private val repository = AppRepository(database.appDao())
    val allContacts = repository.allGameResults

    fun deleteContact(position: Int) {
        if (allContacts.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.deleteContact(allContacts.value!![position])
            }
        }
    }

    fun updateFirstName(newFirstName: String?, contactId: Long) {
        if (allContacts.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateFirstName(newFirstName, contactId)
            }
        }
    }

    fun updateSecondName(newSecondName: String?, contactId: Long) {
        if (allContacts.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateSecondName(newSecondName, contactId)
            }
        }
    }

    fun updateMail(newMail: String?, contactId: Long) {
        if (allContacts.value != null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateMail(newMail, contactId)
            }
        }
    }

    fun reset() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.reset()
        }
    }

}