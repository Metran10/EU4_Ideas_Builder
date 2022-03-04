package com.example.eu4_ideasbuilder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val New_Build = findViewById(R.id.MNB_BUTTON) as Button;
        val Load_Build = findViewById(R.id.main_load_build) as Button;

        Build.currentBuild = Build()
        Build.setContext(applicationContext)

        New_Build.setOnClickListener()
        {
            Build.currentBuild = Build()
            Build.LoadBuilds()
            Build.setContext(applicationContext)
            val BuildActivity = Intent(applicationContext, BuildCreation::class.java)
            startActivity(BuildActivity);
        }

        Load_Build.setOnClickListener()
        {
            Build.currentBuild = Build()
            Build.LoadBuilds()
            val LoadActivity = Intent(applicationContext, SavedBuilds::class.java)
            startActivity(LoadActivity);
        }
    }
}