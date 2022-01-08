package com.example.bookingcrossfitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_edit_training.*

class EditTrainingActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_training)

        val time = intent.getStringExtra("time")
        val type = intent.getStringExtra("type")
        val trainer = intent.getStringExtra("trainer")
        val description = intent.getStringExtra("description")
        val maxParticipants = intent.getStringExtra("maxParticipants")
        val documentId = intent.getStringExtra("documentId")

        val timeView: TextView = findViewById(R.id.timeEditTrainingEditText)
        timeView.text= time
        val typeView: TextView = findViewById(R.id.typeEditTrainingEditText)
        typeView.text=type
        val trainerView: TextView = findViewById(R.id.trainerEditTrainingEditText)
        trainerView.text = trainer
        val descriptionView: TextView = findViewById(R.id.descriptionEditTrainingEditText)
        descriptionView.text = description
        val maxParticipantsView : TextView = findViewById(R.id.participantsEditTrainingEditText)
        maxParticipantsView.text = maxParticipants


        setup(documentId)

    }

    private fun setup(documentId: String?) {
        updateTrainingButton.setOnClickListener {
            if(typeEditTrainingEditText.text.isNotEmpty() && timeEditTrainingEditText.text.isNotEmpty() && trainerEditTrainingEditText.text.isNotEmpty() && passwordEditTrainingEditText.text.contentEquals("admin")){
                var type = typeEditTrainingEditText.text.toString()
                var time = timeEditTrainingEditText.text.toString()
                var trainer = trainerEditTrainingEditText.text.toString()
                var description = descriptionEditTrainingEditText.text.toString()
                var participantsnumber = participantsEditTrainingEditText.text.toString().toInt()
                var participantes = mutableListOf<String>()




                if (documentId != null) {
                    db.collection("training").document(documentId).update(
                        hashMapOf(
                            "trainer" to trainer,
                            "time" to time,
                            "type" to type,
                            "nofparticipants" to participantsnumber,
                            "description" to description,
                            "UID" to null
                        ) as Map<String, Any>
                    )
                }
                Toast.makeText(this, "Training added successfully", Toast.LENGTH_LONG).show()
                showHome()

            }else{
                showAlert()
            }
        }
    }

    private fun showHome() {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
        }
        startActivity(homeIntent)
    }


    private fun showAlert() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle(("Error"))
        builder.setMessage("Error creating training")
        builder.setPositiveButton("Accept", null);
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}