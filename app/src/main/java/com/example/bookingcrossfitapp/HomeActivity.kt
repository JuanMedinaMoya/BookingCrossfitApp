package com.example.bookingcrossfitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.ChangeEventListener
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var trainingList : ArrayList<Training>
    private lateinit var myAdapter: MyAdapter


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

    override fun onStart() {
        super.onStart()
        myAdapter.notifyDataSetChanged()
    }

    private fun setUpReclyclerView() {
        val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore;
        val trainingReference : CollectionReference = db.collection("training");

        val query : Query = trainingReference.orderBy("time", Query.Direction.ASCENDING)

        val options : FirestoreRecyclerOptions<Training>  = FirestoreRecyclerOptions.Builder<Training>().setQuery(query,Training::class.java).build()


    }


    private fun EventChangeListener() {

        val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore;
        val collection = db.collection("training").orderBy("time", Query.Direction.ASCENDING);

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

                            if(dc.type == DocumentChange.Type.REMOVED) {
                                trainingList.remove(dc.document.toObject(Training::class.java))
                            }
                            if(dc.type == DocumentChange.Type.ADDED) {

                                trainingList.add(dc.document.toObject(Training::class.java))
                            }

                        }

                        myAdapter!!.notifyDataSetChanged()
                    }
                })

    }






}