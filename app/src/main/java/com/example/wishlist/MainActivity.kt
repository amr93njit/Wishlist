package com.example.wishlist


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    lateinit var wishlistItems: MutableList<WishlistItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Title, Price, URL edittexts and related
        var nameEditText = findViewById<EditText>(R.id.nameEditText)
        var priceEditText = findViewById<EditText>(R.id.priceEditText)
        var urlEditText = findViewById<EditText>(R.id.urlEditText)

        //adapter stuff
        val wishlistRv = findViewById<RecyclerView>(R.id.wishlistRv)
        wishlistItems = mutableListOf()
        val adapter = WishlistItemAdapter(wishlistItems)
        wishlistRv.adapter = adapter
        wishlistRv.layoutManager = LinearLayoutManager(this)


        findViewById<Button>(R.id.submitButton).setOnClickListener {
            hideKeyboard()

            //submit a new wishlist item
            val nameText = nameEditText.text.toString()
            val priceText = priceEditText.text.toString()
            val urlText = urlEditText.text.toString()
            val newWishlistItem = WishlistItem(nameText, priceText, urlText)
            wishlistItems.add(newWishlistItem)
            Log.d("MyApp", "New item added: $newWishlistItem")
            adapter.notifyItemInserted(wishlistItems.size-1)
            // Debugging: Print the contents of wishlistItems
            wishlistItems.forEach {
                println("Item: ${it.name}, ${it.price}, ${it.url}")
            }
            nameEditText.setText(""); priceEditText.setText(""); urlEditText.setText("")
            wishlistRv.scrollToPosition(adapter.itemCount - 1)
        }
    }


    //https://stackoverflow.com/a/45857155
    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}