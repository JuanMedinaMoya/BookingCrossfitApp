package com.example.bookingcrossfitapp


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingcrossfitapp.TrainingAdapter.TrainingAdapterVH
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.training.view.*
import android.content.Intent
import androidx.core.view.isVisible
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.training.*


class TrainingAdapter(options: FirestoreRecyclerOptions<Training>) :
        FirestoreRecyclerAdapter<Training, TrainingAdapterVH>(options) {

    private val db : FirebaseFirestore = SingletonMap.getInstance()[LoginActivity.databaseReference] as FirebaseFirestore
    private val adminReference : CollectionReference = db.collection("admin")


    class TrainingAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card = itemView.cardViewTraining
        val trainer : TextView = itemView.trainingUserCardView
        val time : TextView= itemView.trainingTimeCardView
        val type : TextView = itemView.trainingTypeCardView
        val quitButton : Button = itemView.quitTrainingButton
        val joinButton: Button = itemView.joinTrainingButton
        val removeButton : Button = itemView.removeTrainingButton
        val editButton : Button = itemView.editTrainingButton
        val actualnofparticipats : TextView = itemView.textViewActualNofParticipants
        val ofparticipants : TextView = itemView.textViewmaxNumberOfParticipants

        init{


                val currentUser : String? = FirebaseAuth.getInstance().currentUser?.uid
                FirebaseFirestore.getInstance().collection("admin")
                    .whereEqualTo("id", currentUser).get().addOnSuccessListener {
                        if(it != null && !it.isEmpty) {
                            itemView.adminButtons.isVisible = true
                            itemView.spaceAdminButtons.isVisible = true
                            itemView.editTrainingButton.isVisible = true
                            itemView.removeTrainingButton.isVisible = true

                        } else {
                            itemView.userButtons.isVisible = true
                            itemView.spaceUserButtons.isVisible = true
                            itemView.joinTrainingButton.isVisible = true
                            itemView.quitTrainingButton.isVisible = true
                        }
                    }

            itemView.setOnClickListener { v: View ->
                val position: Int = adapterPosition
                Toast.makeText(itemView.context,"You clicked on item #  ${position + 1}", Toast.LENGTH_SHORT).show()

            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingAdapterVH {
        return TrainingAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.training,parent,false))
    }

    private fun checkAdmin(adminID: String): Boolean {

        return adminReference.whereEqualTo("id", adminID).get().isSuccessful
    }

    override fun onBindViewHolder(holder: TrainingAdapterVH, position: Int, model: Training) {
        holder.trainer.text = model.trainer
        holder.time.text = model.time
        holder.type.text = model.type
        holder.ofparticipants.text = model.nofparticipants.toString()


        var participantes = model.participants?.toMutableList()


        holder.editButton.setOnClickListener {

            val itemSelectedDocumentId = snapshots.getSnapshot(position).id
            val intent = Intent(holder.itemView.context, EditTrainingActivity::class.java).apply {
                if (participantes != null) {
                    putExtra("type", holder.type.text)
                    putExtra("time",holder.time.text)
                    putExtra("trainer",holder.trainer.text)
                    putExtra("description" , model.description)
                    putExtra("maxParticipants", holder.ofparticipants.text)
                    putExtra("documentId", itemSelectedDocumentId)
                }
            }
            startActivity(holder.itemView.context,intent,null)
        }



        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, TrainingActivity::class.java).apply {
                if (participantes != null) {
                    putExtra("participants", (participantes).toTypedArray())
                    putExtra("type", holder.type.text)
                    putExtra("time",holder.time.text)
                    putExtra("trainer",holder.trainer.text)
                    putExtra("description" , model.description)

                }
            }
            startActivity(holder.itemView.context,intent,null)
        }

        if (participantes != null) {
            holder.actualnofparticipats.text =  participantes.size.toString() + "/"
        }else{
            holder.actualnofparticipats.text = "0"
        }
        holder.ofparticipants.text = model.nofparticipants.toString()

/*
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        if(userEmail.equals("javigallego99@gmail.com")) {
            holder.quitButton.isVisible = false
        }


*/


        val currentUser : String? = FirebaseAuth.getInstance().currentUser?.uid
        val checkAdmin = FirebaseFirestore.getInstance().collection("admin")
            .whereEqualTo("id", currentUser).get().addOnSuccessListener {
                if(it != null && !it.isEmpty) {
                    holder.quitButton.isVisible = false
                }
            }


        holder.removeButton.setOnClickListener {
            snapshots.getSnapshot(position).reference.delete()
        }

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
                        if(!participantes.contains(userEmail)){
                            if(participantes.size < model.nofparticipants!!){
                                participantes?.add(userEmail)
                                snapshots.getSnapshot(position).reference.update("participants", participantes)
                            }else{
                                Toast.makeText(holder.itemView.context,"Training is full", Toast.LENGTH_SHORT).show()

                            }
                        }else{
                            Toast.makeText(holder.itemView.context,"You are already in the training", Toast.LENGTH_SHORT).show()
                        }


                    }

                }

            }
        }



    }




}