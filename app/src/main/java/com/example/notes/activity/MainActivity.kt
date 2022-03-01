package com.example.notes.activity

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.example.a14_recyclerviewdragandswipekotlin.helper.RecyclerItemTouchHelper
import com.example.notes.R
import com.example.notes.adapter.NotesAdapter
import com.example.notes.model.Notes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    lateinit var bt_add :Button
    lateinit var recyclerView: RecyclerView
    private var adapter: NotesAdapter? = null
    lateinit var et_notes: EditText
    private var courseModalArrayList: ArrayList<Notes> = ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        getArrayList()
        buildRecyclerView()
        bt_add = findViewById(R.id.bt_add)
        bt_add.setOnClickListener {
            showAlertDialogButtonClicked()
        }
    }


    private fun showAlertDialogButtonClicked() {
        // Create an alert builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Note")
        // set the custom layout
        val customLayout: View = layoutInflater.inflate(R.layout.item_dialog, null)
        builder.setView(customLayout)
        // add a button
        builder.setPositiveButton("Save") { dialog, which -> // send data from the
                // AlertDialog to the Activity
            et_notes= customLayout.findViewById(R.id.et_notes)
            courseModalArrayList.add(Notes(et_notes.text.toString()))
            adapter!!.notifyItemInserted(courseModalArrayList.size);
            saveArrayList()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> // send data from the
                // AlertDialog to the Activity
        }
        // create and show
        // the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    private fun buildRecyclerView() {
        adapter = NotesAdapter(this, courseModalArrayList)
        val manager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback = RecyclerItemTouchHelper(
            0,
            ItemTouchHelper.LEFT,
            object : RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, adapterPosition: Int) {

                }
            })
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    private fun getArrayList(){
        val prefs: SharedPreferences = getSharedPreferences("My pref", MODE_PRIVATE)
        val emptyList = Gson().toJson(ArrayList<Notes>())
        courseModalArrayList = Gson().fromJson(prefs.getString("key", emptyList ), object : TypeToken<ArrayList<Notes>>() {}.type)

    }

    private fun saveArrayList() {
        val prefs: SharedPreferences = getSharedPreferences("My pref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(courseModalArrayList)
        editor.putString("key", json)
        editor.apply()
        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

}

