package com.example.bookingcrossfitapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingcrossfitapp.TrainingAdapter.TrainingAdapterVH
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.training.view.*


class TrainingAdapter(options: FirestoreRecyclerOptions<Training>) :
        FirestoreRecyclerAdapter<Training, TrainingAdapterVH>(options) {

   // val db = Firebase.firestore
    private lateinit var listener :  AdapterView.OnItemClickListener

    class TrainingAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val UID : TextView = itemView.trainingUserCardView
        val time : TextView= itemView.trainingTimeCardView
        val type : TextView = itemView.trainingTypeCardView
        val quitButton : Button = itemView.quitTrainingButton
        val joinButton: Button = itemView.joinTrainingButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingAdapterVH {
        return TrainingAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.training,parent,false))
    }

    override fun onBindViewHolder(holder: TrainingAdapterVH, position: Int, model: Training) {
        holder.UID.text = model.UID
        holder.time.text = model.time
        holder.type.text = model.type
        var participantes = model.participants?.toMutableList()


        holder.quitButton.setOnClickListener {
            val userEmail = FirebaseAuth.getInstance().currentUser?.email

            if(position != RecyclerView.NO_POSITION) {
                snapshots.getSnapshot(position).reference.update("UID", "")
                if (userEmail != null) {
                    
                    participantes?.remove(userEmail)

                }
            }

        }

        holder.joinButton.setOnClickListener {

            if(position != RecyclerView.NO_POSITION) {
                val userEmail = FirebaseAuth.getInstance().currentUser?.email

               /* db.collection("training").document(holder.UID.toString()).get().addOnSuccessListener {
                    if(it.exists()){

                         var participantes = it.get("participants") as List<*>
                            participantes.plusElement(userEmail.toString())

                    }else{

                    }
                    db.collection("training").document(holder.UID.toString()).update("participants",participantes)
                }*/
                if (userEmail != null) {

                    participantes?.add(userEmail)

                }
                snapshots.getSnapshot(position).reference.update("participants", participantes)
            }
        }
    }


}