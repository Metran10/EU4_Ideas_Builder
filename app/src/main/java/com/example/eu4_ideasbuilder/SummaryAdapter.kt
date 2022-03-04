package com.example.eu4_ideasbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class SummaryAdapter(private val bonList : ArrayList<BonusData>): RecyclerView.Adapter<SummaryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummaryViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val bonus_elem: View = layoutInflater.inflate(R.layout.bonus_list_elem, parent, false)

        return SummaryViewHolder(bonus_elem)
    }

    override fun onBindViewHolder(holder: SummaryViewHolder, position: Int) {

        holder.icon.setImageResource(bonList[position].bonusImage)

        if(Build.currentBuild.totalBonusesList[position].bonusType == BonusType.PERCENT)
        {
            holder.text.setText("-" + Build.currentBuild.totalBonusesList[position].bonusName +" " +Build.currentBuild.totalBonusesList[position].amount + "%")
        }
        else if(Build.currentBuild.totalBonusesList[position].bonusType == BonusType.FLAT)
        {
            holder.text.setText("-" + Build.currentBuild.totalBonusesList[position].bonusName +" " +Build.currentBuild.totalBonusesList[position].amount)
        }
        else{
            holder.text.setText("-" + Build.currentBuild.totalBonusesList[position].bonusName)
        }

    }

    override fun getItemCount(): Int {

        return Build.currentBuild.totalBonusesList.size
    }


}


class SummaryViewHolder(val view: View): RecyclerView.ViewHolder(view)
{
    val icon: ImageView = itemView.findViewById(R.id.summary_bonus_image)
    val text: TextView = itemView.findViewById(R.id.summary_bonus_text)
}