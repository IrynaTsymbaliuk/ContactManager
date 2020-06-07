package com.tsymbaliuk.contactmanager

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.contact_list_fragment.*

class ContactListFragment : Fragment(R.layout.contact_list_fragment) {

    private val contactViewModel: ContactViewModel by activityViewModels()
    private lateinit var contactAdapter: ContactAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setContactList()

        contactViewModel.allContacts.observe(viewLifecycleOwner, Observer {
            contactAdapter.updateData(it)
        })

        setToolBar()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.reset) {
            contactViewModel.reset()
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    private fun setToolBar() {
        val supportActionBar = (activity as AppCompatActivity).supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24)
        setHasOptionsMenu(true)
    }

    private fun setContactList() {
        contactAdapter = ContactAdapter(requireContext())

        contactAdapter.setOpenItemClickListener { position ->
            findNavController().navigate(
                ContactListFragmentDirections.actionContactListFragmentToContactDetailFragment(
                    position = position
                )
            )

        }

        contact_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactAdapter
        }

        contactAdapter.setDeleteItemClickListener { position ->
            contactViewModel.deleteContact(position)
        }

    }

}