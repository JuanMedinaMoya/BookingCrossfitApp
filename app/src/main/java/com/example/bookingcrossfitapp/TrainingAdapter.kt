package com.example.bookingcrossfitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookingcrossfitapp.TrainingAdapter.TrainingAdapterVH
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.training.view.*

class TrainingAdapter(options: FirestoreRecyclerOptions<Training>) :
        FirestoreRecyclerAdapter<Training, TrainingAdapterVH>(options) {

    class TrainingAdapterVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val UID = itemView.trainingUserCardView
        val time = itemView.trainingTimeCardView
        val type = itemView.trainingTypeCardView
        val quitButton = itemView.quitTrainingButton
        val joinButton = itemView.joinTrainingButton

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingAdapterVH {
        return TrainingAdapterVH(LayoutInflater.from(parent.context).inflate(R.layout.training,parent,false))
    }

    override fun onBindViewHolder(holder: TrainingAdapterVH, position: Int, model: Training) {
        holder.UID.text = model.UID
        holder.time.text = model.time
        holder.type.text = model.type
    }


}