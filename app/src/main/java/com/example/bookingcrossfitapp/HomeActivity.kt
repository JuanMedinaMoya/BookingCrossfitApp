package com.example.bookingcrossfitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.ChangeEventListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trainingList : ArrayList<Training>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.trainingRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        trainingList = arrayListOf()

        myAdapter = MyAdapter(trainingList)

        recyclerView.adapter = myAdapter

        EventChangeListener()
    }

    private fun EventChangeListener() {

        val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore;
        val collection = db.collection("training");

        //db = FirebaseFirestore.getInstance()
        //db.collection("training").
        collection.addSnapshotListener(object : EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if(error != null) {
                            Log.e("Firestore Error", error.message.toString())
                            return
                        }

                        for(dc : DocumentChange in value?.documentChanges!!) {

                            if(dc.type == DocumentChange.Type.ADDED) {

                                trainingList.add(dc.document.toObject(Training::class.java))
                            }
                        }

                        myAdapter.notifyDataSetChanged()
                    }
                })

    }

    private fun setup(email:String) {

        title = "Home"
//        emailTextView.text = email
//        logOutButton.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//            onBackPressed()
//        }

    }





}