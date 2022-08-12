package com.example.project
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.*
import android.widget.*
import android.net.Uri
import com.example.project.BEST_APP.Companion.APP_ID
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.view.MenuInflater as MenuInflater1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var ap: Button = findViewById(R.id.button)
        var bes: TextView = findViewById(R.id.textView)
        var flag: String = "search"
        var cate = arrayOf("search", "games", "food")
        var spin: Spinner = findViewById(R.id.spinner)
        spin.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cate)
        ap.setOnClickListener {
            /* if (flag == "search") {


            } else if (flag == "games") {


            } else


        }

            */
        }
        spin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                flag = cate.get(p2)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1 ->Toast.makeText(this, "item 1 selected", Toast.LENGTH_SHORT).show()
            R.id.item2 -> Toast.makeText(this, "item 2 selected", Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(this, "Sub-item 1 selected", Toast.LENGTH_SHORT).show()
        }
        return true;
    }
}

