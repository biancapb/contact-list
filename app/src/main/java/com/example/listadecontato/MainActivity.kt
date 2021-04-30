package com.example.listadecontato

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import com.example.listadecontato.DetailActivity.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {
    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list)
    }
    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_menu)

        initDrawer()
        fetchListContact()
        bindViews()
    }

    //simula o retorno de uma API
    private fun fetchListContact() {
        val list = arrayListOf(
            Contact(
                "Bianca", "11 1234-1234", "img.png"
            ),
            Contact(
                "Paulo", "11 6588-1234", "img.png"
            ),
            Contact(
                "Daniela", "21 9966-1234", "img.png"
            ),
            Contact(
                "Thainá", "11 5656-1234", "img.png"
            ),
            Contact(
                "Reinaldo", "21 4563-1234", "img.png"
            ),
            Contact(
                "Vivi", "21 4994-1634", "img.png"
            ),
            Contact(
                "Clarice", "21 8633-1234", "img.png"
            ),
            Contact(
                "Bernardo", "21 8799-1234", "img.png"
            )

        )

        getInstaceSharedPreferences().edit{
            val json = Gson().toJson(list)
            putString("contacts", json)

            //salva mudanças de forma síncrona
            commit()
        }

    }

    private fun getInstaceSharedPreferences() : SharedPreferences {
        return getSharedPreferences("com.example.listadecontato.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer() {
        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }


    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)

        updateList()
    }

    //converte uma string para um objeto de classe
    private fun getListContacts() : List<Contact> {
        val list = getInstaceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList() {
        val list = getListContacts()
        adapter.updateList(list)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_menu_1 -> {
                showToast("Teste 1")
                return true
            }
            R.id.item_menu_2 -> {
                showToast("Teste 2")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)

    }

}