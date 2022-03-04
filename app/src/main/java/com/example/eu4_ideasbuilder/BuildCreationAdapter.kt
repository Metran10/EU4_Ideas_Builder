package com.example.eu4_ideasbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class BuildCreationAdapter(): RecyclerView.Adapter<BuildCreationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildCreationViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val buildElem: View = layoutInflater.inflate(R.layout.build_list_elem, parent, false)

        return BuildCreationViewHolder(buildElem)
    }

    override fun onBindViewHolder(holder: BuildCreationViewHolder, position: Int) {

        holder.groupIcon.setImageResource(Build.currentBuild.IdeaGroupsList[position].icon)
        holder.groupName.setText(Build.currentBuild.IdeaGroupsList[position].name)

        val type = Build.currentBuild.IdeaGroupsList[position].type
        when (type)
        {
            GroupType.ADMINISTRATIVE -> holder.groupType.setImageResource(R.drawable.type_icon_adm)
            GroupType.DYPLOMATIC -> holder.groupType.setImageResource(R.drawable.type_icon_dip)
            GroupType.MILITARY -> holder.groupType.setImageResource(R.drawable.type_icon_mil)
        }


        holder.groupDel.setOnClickListener(){
            Toast.makeText(Build.getContext(), "Group ${Build.currentBuild.IdeaGroupsList[position].name} deleted.", Toast.LENGTH_SHORT).show()
            Build.currentBuild.DeleteGroup(position)
        }

        holder.groupBonus.setImageResource(R.drawable.bonus_icon_bonus)

        holder.i1.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[0].icon)
        holder.i2.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[1].icon)
        holder.i3.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[2].icon)
        holder.i4.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[3].icon)
        holder.i5.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[4].icon)
        holder.i6.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[5].icon)
        holder.i7.setImageResource(Build.currentBuild.IdeaGroupsList[position].ideas[6].icon)


    }

    override fun getItemCount(): Int {
        return Build.currentBuild.IdeaGroupsList.size
    }


}


class BuildCreationViewHolder(val view: View): RecyclerView.ViewHolder(view){

    val groupIcon: ImageView = itemView.findViewById(R.id.idea_group_addition_gIcon)
    var groupName: TextView = itemView.findViewById(R.id.idea_group_add_gName)
    val groupType: ImageView = itemView.findViewById(R.id.idea_group_add_type)
    val groupDel: ImageView = itemView.findViewById(R.id.idea_group_add_del)
    val groupBonus: ImageView = itemView.findViewById(R.id.group_bonus_add)

    val i1: ImageView = itemView.findViewById(R.id.idea_1_add)
    val i2: ImageView = itemView.findViewById(R.id.idea_2_add)
    val i3: ImageView = itemView.findViewById(R.id.idea_3_add)
    val i4: ImageView = itemView.findViewById(R.id.idea_4_add)
    val i5: ImageView = itemView.findViewById(R.id.idea_5_add)
    val i6: ImageView = itemView.findViewById(R.id.idea_6_add)
    val i7: ImageView = itemView.findViewById(R.id.idea_7_add)

}