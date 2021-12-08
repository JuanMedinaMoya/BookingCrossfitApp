package com.example.bookingcrossfitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val trainingList: ArrayList<Training>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {



    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val UID : TextView = itemView.findViewById(R.id.trainingUserCardView)
        val time : TextView = itemView.findViewById(R.id.trainingTimeCardView)
        val type : TextView = itemView.findViewById(R.id.trainingTypeCardView)
        val quitButton : TextView = itemView.findViewById(R.id.quitTrainingButton)
        val joinButton : TextView = itemView.findViewById(R.id.joinTrainingButton)




        //var photo : ImageView = itemView.findViewById(R.id.trainingImageCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.training, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val training : Training = trainingList[position]
        holder.UID.text = training.UID
        holder.time.text = training.time
        holder.type.text = training.type



    }

    override fun getItemCount(): Int {
        return trainingList.size
    }
}