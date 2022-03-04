package com.example.eu4_ideasbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuildCreation : AppCompatActivity() {

    private lateinit var newRec: RecyclerView
    private var isSpinnerTouched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_creation)
        supportActionBar?.hide()


        val title = findViewById(R.id.build_addition_name_title) as TextView
        title.setText(Build.currentBuild.buildName)

        val check_BT = findViewById(R.id.build_creation_check_bonuses) as TextView;
        val check_la = findViewById(R.id.build_addition_check_bonuses_layout) as ConstraintLayout
        val check_outLa = findViewById(R.id.build_addition_outer_check) as ConstraintLayout

        val SummaryActivity = Intent(applicationContext, BuildSummaryActivity::class.java);
        check_BT.setOnClickListener(){
            startActivity(SummaryActivity);
        }

        check_la.setOnClickListener()
        {
            startActivity(SummaryActivity);
        }
        check_outLa.setOnClickListener()
        {
            startActivity(SummaryActivity);
        }

        newRec = findViewById(R.id.recyclerView_Creation)
        newRec.layoutManager = LinearLayoutManager(this)
        newRec.setHasFixedSize(true)
        newRec.adapter = BuildCreationAdapter()


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



    override fun onResume()
    {
        super.onResume()
        newRec = findViewById(R.id.recyclerView_Creation)
        newRec.layoutManager = LinearLayoutManager(this)
        newRec.setHasFixedSize(true)
        newRec.adapter = BuildCreationAdapter()

        val title = findViewById(R.id.build_addition_name_title) as TextView
        title.setText(Build.currentBuild.buildName)
    }





}