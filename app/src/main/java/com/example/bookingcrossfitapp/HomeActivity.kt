package com.example.bookingcrossfitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore
    private val trainingReference : CollectionReference = db.collection("training")

    var trainingAdapter : TrainingAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setUpRecyclerView()


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