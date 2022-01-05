package com.example.bookingcrossfitapp


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingcrossfitapp.TrainingAdapter.TrainingAdapterVH
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.training.view.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity



class TrainingAdapter(options: FirestoreRecyclerOptions<Training>) :
        FirestoreRecyclerAdapter<Training, TrainingAdapterVH>(options) {

   // val db = Firebase.firestore
    private lateinit var listener :  AdapterView.OnItemClickListener

    class TrainingAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card = itemView.cardViewTraining
        val trainer : TextView = itemView.trainingUserCardView
        val time : TextView= itemView.trainingTimeCardView
        val type : TextView = itemView.trainingTypeCardView
        val quitButton : Button = itemView.quitTrainingButton
        val joinButton: Button = itemView.joinTrainingButton

        init{
            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item #  ${position + 1}", Toast.LENGTH_SHORT).show()
                showLogin()
            }
        }

        private fun showLogin() {

            val intent = Intent(itemView.context, LoginActivity.javaClass)
            startActivity(itemView.context,intent,null)


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingAdapterVH {
        return TrainingAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.training,parent,false))
    }

    override fun onBindViewHolder(holder: TrainingAdapterVH, position: Int, model: Training) {
        holder.trainer.text = model.trainer
        holder.time.text = model.time
        holder.type.text = model.type
        var participantes = model.participants?.toMutableList()


        holder.quitButton.setOnClickListener {
            val userEmail = FirebaseAuth.getInstance().currentUser?.email

            if(position != RecyclerView.NO_POSITION) {
                snapshots.getSnapshot(position).reference.update("UID", "")
                if (userEmail != null) {
                    if (participantes != null) {
                        if(participantes.contains(userEmail)){
                            participantes?.remove(userEmail)
                            snapshots.getSnapshot(position).reference.update("participants", participantes)
                        }
                    }


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
                    if (participantes != null) {
                        if(!participantes.contains(userEmail))
                            participantes?.add(userEmail)
                        snapshots.getSnapshot(position).reference.update("participants", participantes)
                    }

                }

            }
        }


    }




}