package com.example.wishlist

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class WishlistItemAdapter(private val wishlistItems: MutableList<WishlistItem>) : RecyclerView.Adapter<WishlistItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView
        val priceTextView: TextView
        val urlTextView: TextView
        init {
            nameTextView = itemView.findViewById(R.id.nameTextView)
            priceTextView = itemView.findViewById(R.id.priceTextView)
            urlTextView = itemView.findViewById(R.id.urlTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.wishlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wishlistItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wishlistItem = wishlistItems.get(position)
        holder.nameTextView.text = wishlistItem.name
        holder.priceTextView.text = wishlistItem.price
        holder.urlTextView.text = wishlistItem.url

        //long click to delete
        holder.itemView.setOnLongClickListener {
            wishlistItems.removeAt(position)
            notifyDataSetChanged()
            true
        }

        //normal click to open URL (if exists)
        holder.itemView.setOnClickListener {
            try {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wishlistItem.url))
                ContextCompat.startActivity(it.context, browserIntent, null)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    it.context,
                    "Invalid URL for ${wishlistItem.name}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}