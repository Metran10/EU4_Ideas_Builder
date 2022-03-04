package com.example.eu4_ideasbuilder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BuildSummaryActivity : AppCompatActivity() {

    private lateinit var newRec: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_build_summary)
        supportActionBar?.hide()

        val title = findViewById(R.id.summary_build_title) as TextView
        title.setText(Build.currentBuild.buildName)

        newRec = findViewById(R.id.recyclerViewSummary)
        newRec.layoutManager = LinearLayoutManager(this)
        newRec.setHasFixedSize(true)
        newRec.adapter = SummaryAdapter(createArray())


        val spinnerMenu: Spinner = findViewById(R.id.build_summary_spinner)
        ArrayAdapter.createFromResource(this, R.array.menu_choices, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMenu.adapter = adapter
        }

        val spinner: Spinner = findViewById(R.id.build_summary_spinner)
        spinner.setSelection(0, false)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

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


    fun createArray(): ArrayList<BonusData>
    {
        var Ara: ArrayList<BonusData> = arrayListOf<BonusData>()

        for (i in Build.currentBuild.totalBonusesList)
        {
            val data: BonusData = BonusData(i.bonusName, i.amount, i.icon)
            Log.d("MYTAG",data.bonusName + " "+data.bonusAmount)
            Ara.add(data)
        }
        return Ara
    }


    override fun onResume() {
        super.onResume()

        val title = findViewById(R.id.summary_build_title) as TextView
        title.setText(Build.currentBuild.buildName)
    }

}