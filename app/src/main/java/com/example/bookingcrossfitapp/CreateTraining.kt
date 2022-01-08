package com.example.bookingcrossfitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_create_training.*

class CreateTraining : AppCompatActivity(){

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_training)

        setup()
    }

    private fun setup() {
        buttonCreateTraining.setOnClickListener {
            if(editTextTrainingType.text.isNotEmpty() && editTextTrainingTime.text.isNotEmpty() && editTextTrainer.text.isNotEmpty() && editTextPassword.text.contentEquals("admin")){
                var type = editTextTrainingType.text.toString()
                var time = editTextTrainingTime.text.toString()
                var trainer = editTextTrainer.text.toString()
                var description = editTextDescription.text.toString()
                var participantsnumber = editTextNumberParticipants.text.toString().toInt()
                var participantes = mutableListOf<String>()


                db.collection("training").document().set(
                    hashMapOf(
                        "trainer" to trainer,
                        "time" to time,
                        "type" to type,
                        "participants" to participantes,
                        "nofparticipants" to participantsnumber,
                        "description" to description,
                        "UID" to null
                    )
                )
                Toast.makeText(this, "Training added successfully", Toast.LENGTH_LONG).show()
                showHome()
            }else{
                showAlert()
            }
        }
    }
    private fun showLogin() {
        val loginIntent = Intent(this, LoginActivity.javaClass)
        startActivity(loginIntent)
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
