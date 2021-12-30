package com.example.bookingcrossfitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setup()
    }

    private fun setup() {
        signUpButton.setOnClickListener {
            if(signUpEmailEditText.text.isNotEmpty() && signUpPasswordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        (signUpEmailEditText.text.toString())
                        ,signUpPasswordEditText.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            db.collection("User").document(signUpEmailEditText.text.toString()).set(
                                hashMapOf(
                                    "email" to signUpEmailEditText.text.toString(),
                                    "photo" to null,
                                    "name" to null,
                                    "telephone" to null,
                                    "admin" to false
                                )
                            )
                            Toast.makeText(this, "Sign Up successfully", Toast.LENGTH_LONG).show()

                            showLogin();
                        } else {
                            showAlert()
                        }
                    }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this);
        builder.setTitle(("Error"))
        builder.setMessage("Error while register")
        builder.setPositiveButton("Accept", null);
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showLogin() {
        val loginIntent = Intent(this, LoginActivity.javaClass)
        startActivity(loginIntent)


    }



}