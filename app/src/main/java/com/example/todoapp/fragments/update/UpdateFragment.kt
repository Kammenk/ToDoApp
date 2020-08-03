package com.example.todoapp.fragments.update

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setHasOptionsMenu(true)

        view.update_title.setText(args.currentItem.title)
        view.update_spinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        view.update_description.setText(args.currentItem.description)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> deleteItem()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem() {
        AlertDialog.Builder(context)
            .setTitle("Delete ${args.currentItem.title}?")
            .setMessage("Are you sure you want to delete this item?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                mToDoViewModel.deleteData(args.currentItem)
                Toast.makeText(requireContext(),"Successfully deleted!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int -> })
            .create()
            .show()

    }

    private fun updateItem() {
        val title = update_title.text.toString()
        val priority = update_spinner.selectedItem.toString()
        val description = update_description.text.toString()

        val validation = mSharedViewModel.verifyData(title,description)
        if(validation){
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mToDoViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(),"Successfully updated!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(),"Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }


}