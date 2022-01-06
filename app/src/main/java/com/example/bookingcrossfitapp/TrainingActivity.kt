package com.example.bookingcrossfitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class TrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training)

        val listView = findViewById<ListView>(R.id.listViewTraining)

        val participants = intent.getStringArrayExtra("participants")
        val time = intent.getStringExtra("time")
        val type = intent.getStringExtra("type")
        val trainer = intent.getStringExtra("trainer")
        val description = intent.getStringExtra("description")

        val timeView: TextView = findViewById(R.id.textViewTimeTraining)
        timeView.text= time
        val typeView: TextView = findViewById(R.id.textViewTrainingName)
        typeView.text=type
        val trainerView: TextView = findViewById(R.id.textViewTrainerTraining)
        trainerView.text = trainer
        val descriptionView: TextView = findViewById(R.id.textViewDescription)
        descriptionView.text = description




        var parti = mutableListOf<String>()
        if (participants != null) {
            for(p in participants){
                parti.add(p)
            }
        }

        val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, parti
        )

        listView.adapter = arrayAdapter



    }
    private fun setup() {

    }
}