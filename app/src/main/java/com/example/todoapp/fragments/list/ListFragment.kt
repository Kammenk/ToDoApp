package com.example.todoapp.fragments.list

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.Adapter
import com.example.todoapp.R
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragments.SharedViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val mViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: Adapter by lazy { Adapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.listRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        mViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.isDBEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDB.observe(viewLifecycleOwner, Observer {
            setFieldsVisibility(it)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun setFieldsVisibility(isEmptyDb: Boolean) {
        if(isEmptyDb){
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_date_textView?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageView?.visibility = View.INVISIBLE
            view?.no_date_textView?.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_delete_all -> deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        AlertDialog.Builder(context)
            .setTitle("Delete all items?")
            .setMessage("Are you sure you want to delete all items?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int ->
                mViewModel.deleteAll()
                Toast.makeText(requireContext(),"Successfully deleted everything!", Toast.LENGTH_LONG).show()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface: DialogInterface, i: Int -> })
            .create()
            .show()

    }

}