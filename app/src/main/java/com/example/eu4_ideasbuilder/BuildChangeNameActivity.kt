package com.example.eu4_ideasbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class BuildChangeNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_change_name)
        supportActionBar?.hide()

        val title = findViewById(R.id.build_addition_name_title) as TextView
        title.setText(Build.currentBuild.buildName)

        val text = findViewById(R.id.change_name_text_field) as EditText


        val confirmText = findViewById(R.id.change_name_confirm_text) as TextView
        val confirmInner = findViewById(R.id.change_name_confirm_innerLayout) as ConstraintLayout
        val confirmOuter = findViewById(R.id.change_name_confirm_outerLayout) as ConstraintLayout


        confirmText.setOnClickListener(){
            var new_name = text.text.toString()
            Build.currentBuild.buildName = new_name
            title.setText(Build.currentBuild.buildName)
        }

        confirmInner.setOnClickListener(){
            var new_name = text.text.toString()
            Build.currentBuild.buildName = new_name
            title.setText(Build.currentBuild.buildName)
        }

        confirmOuter.setOnClickListener(){
            var new_name = text.text.toString()
            Build.currentBuild.buildName = new_name
            title.setText(Build.currentBuild.buildName)
        }


        val spinnerMenu: Spinner = findViewById(R.id.build_addition_spinner)
        ArrayAdapter.createFromResource(this, R.array.menu_choices, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMenu.adapter = adapter
        }

        spinnerMenu.setSelection(0, false)

        spinnerMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long)
            {
                var intent: Intent
                if(position == 1)
                {
                    Build.currentBuild = Build()
                    intent = Intent(applicationContext, BuildCreation::class.java)
                    startActivity(intent)
                }
                if(position == 2)
                {
                    intent = Intent(applicationContext, BuildCreation::class.java)
                    startActivity(intent)
                }
                if(position == 3)
                {
                    intent = Intent(applicationContext, BuildChangeNameActivity::class.java)
                    startActivity(intent)
                }
                if(position == 4)
                {
                    intent = Intent(applicationContext, BuildAdditionActivity::class.java)
                    startActivity(intent)
                }
                if(position == 5)
                {
                    Build.LoadBuilds()
                    intent = Intent(applicationContext, SavedBuilds::class.java)
                    startActivity(intent)
                    //wczytywanie
                }
                if(position == 6){
                    //zapisywanie aktualnego
                    Build.SaveBuild()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val title = findViewById(R.id.build_addition_name_title) as TextView
        title.setText(Build.currentBuild.buildName)
    }
}