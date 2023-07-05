package com.kaisebhi.githubproject.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kaisebhi.githubproject.R
import com.kaisebhi.githubproject.data.room.AllRepoEntity
import com.kaisebhi.githubproject.ui.home.MainViewModel

class GithubRepoAdapter(private val context: Context, private val viewModel: MainViewModel) :
    androidx.recyclerview.widget.ListAdapter<AllRepoEntity, GithubRepoAdapter.ViewHolder>(DiffUtils()) {
    val TAG = "ItemsAdapter.kt"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDataModel = getItem(position)
        holder.itemNameTv.text = "Name: " + itemDataModel.name
        holder.itemDescTv.text = "Description: " + itemDataModel.description
        holder.root.setOnClickListener(View.OnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(itemDataModel.html_url))
            context.startActivity(intent)
        })

        holder.shareRL.setOnClickListener(View.OnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, "Repository name: ${itemDataModel.name} Click here to view Repository: ${itemDataModel.html_url}")
            intent.type = "text/plain"
            context.startActivity(intent)
        })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemNameTv = view.findViewById<TextView>(R.id.itemName)
        val itemDescTv = view.findViewById<TextView>(R.id.desc)
        val root = view.findViewById<CardView>(R.id.root)
        val shareRL = view.findViewById<RelativeLayout>(R.id.shareRL)
    }

    class DiffUtils : DiffUtil.ItemCallback<AllRepoEntity>() {
        override fun areItemsTheSame(oldItem: AllRepoEntity, newItem: AllRepoEntity): Boolean {
            return oldItem.html_url == newItem.html_url
        }

        override fun areContentsTheSame(oldItem: AllRepoEntity, newItem: AllRepoEntity): Boolean {
            return oldItem == newItem
        }
    }

}