package com.example.bookingcrossfitapp



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.model.Document
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_auth.*

class LoginActivity : AppCompatActivity() {

    // public final static databaseReference
    companion object {
        const val databaseReference = "databaseReference"
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        //Reference to the database "FirebaseFirestore@18113"
        val db : FirebaseFirestore = FirebaseFirestore.getInstance();

        //Setting key=databaseReference and value="FirebaseFirestore@18113" to the map
        SingletonMap.getInstance()[databaseReference] = db;


        val prueba : FirebaseFirestore = SingletonMap.getInstance().get("databaseReference") as FirebaseFirestore;
        val collection = prueba.collection("training").document();

        setup()

    }

    private fun setup() {
        title = "Login"

        loginButtonNotRegistered.setOnClickListener{
            showSignUp();
        }

        loginButton.setOnClickListener {
            if(loginEmailEditText.text.isNotEmpty() && loginPasswordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(
                        (loginEmailEditText.text.toString())
                        ,loginPasswordEditText.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Hello " + it.result?.user?.email ?: "", Toast.LENGTH_LONG).show()
                            showHome(it.result?.user?.email ?: "")
                        } else {
                            showAlert()
                        }
                    }
            }

        }

        trainingButton.setOnClickListener{
            showCreateTraining()
        }


    }

    private fun showCreateTraining() {
        val createTrainingIntent = Intent(this, CreateTraining::class.java).apply {
        }
        startActivity(createTrainingIntent)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle(("Error"))
        builder.setMessage("Error while authentication")
        builder.setPositiveButton("Accept", null);
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String) {
        val homeIntent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(homeIntent)
    }

    private fun showSignUp() {
        val signUpIntent = Intent(this, SignUpActivity::class.java).apply {

        }
        startActivity(signUpIntent)
    }

}