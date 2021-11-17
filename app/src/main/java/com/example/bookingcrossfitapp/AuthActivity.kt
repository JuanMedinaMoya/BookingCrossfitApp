package com.example.bookingcrossfitapp



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setup()

    }

    private fun setup() {
        title = "Authentication"

        signUpButton.setOnClickListener {
            if(emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(
                        (emailEditText.text.toString())
                        ,passwordEditText.text.toString())
                    .addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Hi there! This is a Toast.", Toast.LENGTH_LONG).show()
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
        builder.setMessage("Error while authentication")
        builder.setPositiveButton("Accept", null);
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

}