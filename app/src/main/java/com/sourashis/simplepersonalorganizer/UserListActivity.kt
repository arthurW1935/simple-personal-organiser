/*
    Name: Sourashis Sarkar
    Rollno: 2023EBCS656
*/

package com.sourashis.simplepersonalorganizer

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserListActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        supportActionBar?.title = "Admin Users"
        supportActionBar?.subtitle = "Users with name 'admin'"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listViewUsers)

        loadAdminUsers()
    }

    private fun loadAdminUsers() {
        val adminUsers = dbHelper.getAdminUsers()

        if (adminUsers.isEmpty()) {
            Toast.makeText(this, "No admin users found", Toast.LENGTH_SHORT).show()
            // Show empty state
            val emptyAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                listOf("No admin users found"))
            listView.adapter = emptyAdapter
        } else {
            // Format the data for display
            val userDisplayList = adminUsers.map { user ->
                "ID: ${user.id}\nName: ${user.name}\nEmail: ${user.email}"
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userDisplayList)
            listView.adapter = adapter

            Toast.makeText(this, "Found ${adminUsers.size} admin user(s)", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    data class User(val id: Int, val name: String, val email: String)
}