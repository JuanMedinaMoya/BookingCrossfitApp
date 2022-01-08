package com.example.bookingcrossfitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.training.*
import kotlinx.android.synthetic.main.training.view.*

class HomeActivity : AppCompatActivity() {

    private val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore
    private val trainingReference : CollectionReference = db.collection("training")



    var trainingAdapter : TrainingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        setUpRecyclerView()


    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val currentUser : String? = FirebaseAuth.getInstance().currentUser?.uid
        FirebaseFirestore.getInstance().collection("admin")
            .whereEqualTo("id", currentUser).get().addOnSuccessListener {
                if(it != null && !it.isEmpty) {
                    menuInflater.inflate(R.menu.menu,menu)

                } else {
                    menuInflater.inflate(R.menu.menu_user,menu)
                }
            }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.logOutIcon -> logOut()
            R.id.addTrainingIcon -> showCreateTraining()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCreateTraining() {
        val createTrainingIntent = Intent(this, CreateTraining::class.java).apply {
        }
        startActivity(createTrainingIntent)
    }


    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val loginIntent = Intent(this, LoginActivity::class.java).apply {
        }
        startActivity(loginIntent)
    }

    private fun setUpRecyclerView() {


        val query : Query = trainingReference.orderBy("time", Query.Direction.ASCENDING)
        val options : FirestoreRecyclerOptions<Training>  = FirestoreRecyclerOptions.Builder<Training>()
            .setQuery(query,Training::class.java)
            .build()

        trainingAdapter = TrainingAdapter(options)

        trainingRecyclerView.layoutManager = LinearLayoutManager(this)
        trainingRecyclerView.adapter = trainingAdapter

    }

    override fun onStart() {
        super.onStart()
        trainingAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        trainingAdapter!!.stopListening()
    }





}