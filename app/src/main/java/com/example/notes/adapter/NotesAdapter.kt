package com.example.notes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.model.Notes
import com.example.notes.R
import com.example.notes.activity.MainActivity
import java.util.ArrayList

class NotesAdapter(var context: MainActivity, var items: ArrayList<Notes>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes_list, parent, false)
        return AddedViewHolderYes(view)

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = items[position]
        if (holder is AddedViewHolderYes){
            val tv_note = holder.tv_note
            val iv_remove = holder.iv_remove
            tv_note.text = chat.note
            iv_remove.setOnClickListener {
                items.remove(chat)
            }
        }
    }

    class AddedViewHolderYes(view: View) : RecyclerView.ViewHolder(view) {
        var tv_note : TextView = view.findViewById(R.id.tv_note)
        var iv_remove : ImageView = view.findViewById(R.id.iv_remove)
        var view_background: RelativeLayout = view.findViewById(R.id.view_background)
        var view_foreground: RelativeLayout = view.findViewById(R.id.view_foreground)

    }

}