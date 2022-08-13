package com.example.project


import android.content.ContentValues
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.MenuInflater as MenuInflater1
import android.widget.*




import com.google.android.material.navigation.NavigationBarView

class insartdia : DialogFragment(R.layout.ins) {

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val cancelbt : Button = view.findViewById(R.id.cancle);
            val submitbt : Button = view.findViewById(R.id.add1);

            cancelbt.setOnClickListener{
                dismiss()
            }
            submitbt.setOnClickListener{
                val values = ContentValues()

                values.put(
                    BEST_APP.NAME,
                    (view.findViewById<View>(R.id.editText1) as EditText).text.toString()
                )
                values.put(
                    BEST_APP.TOP,
                    (view.findViewById<View>(R.id.editText2) as EditText).text.toString()
                )
               values.put(
                    BEST_APP.CATEGORY,
                    (view.findViewById<View>(R.id.editText3) as EditText).text.toString()
                )

              insarter(values)


            }
        }
        fun insarter(va : ContentValues ?) {
            val m1:MainActivity = activity as MainActivity;
            m1.inserts(va)
        }



    }






