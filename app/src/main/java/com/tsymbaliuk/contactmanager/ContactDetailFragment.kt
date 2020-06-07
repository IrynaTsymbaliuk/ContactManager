package com.tsymbaliuk.contactmanager

import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.contact_detail_fragment.*

class ContactDetailFragment : Fragment(R.layout.contact_detail_fragment) {

    private val contactViewModel: ContactViewModel by activityViewModels()

    private lateinit var currentContact: ContactItem
    private var editedContact = ContactItem(0, "", "", "")

    private var actionMode: ActionMode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentContactIndex = 0

        if (arguments != null) {
            currentContactIndex = requireArguments().getInt("position")
        }

        currentContact = contactViewModel.allContacts.value!![currentContactIndex]
        editedContact.photoId = currentContact.photoId
        editedContact.firstName = currentContact.firstName
        editedContact.secondName = currentContact.secondName
        editedContact.mail = currentContact.mail

        setContactInfo()

        addFocusChangeListeners()

        addTextChangeListeners()

    }

    private fun setContactInfo() {
        photo.setImageResource(editedContact.photoId)
        first_name.setText(editedContact.firstName)
        second_name.setText(editedContact.secondName)
        mail_tiet.setText(editedContact.mail)
    }

    private fun addFocusChangeListeners() {
        first_name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (actionMode == null) createContextualActionMode()
            }
        }

        second_name.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (actionMode == null) createContextualActionMode()
            }
        }

        mail_tiet.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (actionMode == null) createContextualActionMode()
            }
        }
    }

    private fun addTextChangeListeners() {
        first_name.addTextChangedListener {
            editedContact.firstName = it.toString()
        }

        second_name.addTextChangedListener {
            editedContact.secondName = it.toString()
        }

        mail_tiet.addTextChangedListener {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches()) {
                mail_tiet.error = getString(R.string.invalid_email)
            } else {
                mail_tiet.error = null
                editedContact.mail = it.toString()
            }
        }
    }

    inner class ActionModeCallback : ActionMode.Callback {

        override fun onActionItemClicked(
            mode: ActionMode?,
            item: MenuItem?
        ): Boolean {
            return if (item?.itemId == R.id.save) {
                val contactId = currentContact.contactId

                if (currentContact.firstName != editedContact.firstName) {
                    currentContact.firstName = editedContact.firstName
                    contactViewModel.updateFirstName(
                        currentContact.firstName,
                        contactId
                    )
                }

                if (currentContact.secondName != editedContact.secondName) {
                    currentContact.secondName = editedContact.secondName
                    contactViewModel.updateSecondName(
                        currentContact.secondName,
                        contactId
                    )
                }

                if (currentContact.mail != editedContact.mail) {
                    currentContact.mail = editedContact.mail
                    contactViewModel.updateMail(
                        currentContact.mail,
                        contactId
                    )
                }
                actionMode?.finish()
                true
            } else false
        }

        override fun onCreateActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            val inflater = mode?.menuInflater
            inflater?.inflate(R.menu.detail_menu, menu)
            return true
        }

        override fun onPrepareActionMode(
            mode: ActionMode?,
            menu: Menu?
        ): Boolean {
            mode?.title = getString(R.string.contact_editing)
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            root.focusedChild?.closeKeyboard()
            findNavController().popBackStack()
        }
    }

    private fun createContextualActionMode() {
        if (actionMode == null) {
            actionMode =
                (activity)!!.startActionMode(ActionModeCallback())
        }
    }

}