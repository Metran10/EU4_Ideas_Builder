package com.example.eu4_ideasbuilder

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView



class BuildAdditionAdapter(): RecyclerView.Adapter<BuildAdditionViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildAdditionViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val buildElem: View = layoutInflater.inflate(R.layout.build_addition_list_elem, parent, false)

        return BuildAdditionViewHolder(buildElem)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: BuildAdditionViewHolder, position: Int) {

        holder.groupIcon.setImageResource(Build.everyGroup[position].icon)
        holder.groupName.setText(Build.everyGroup[position].name)

        val type = Build.everyGroup[position].type
        when (type)
        {
            GroupType.ADMINISTRATIVE -> holder.groupType.setImageResource(R.drawable.type_icon_adm)
            GroupType.DYPLOMATIC -> holder.groupType.setImageResource(R.drawable.type_icon_dip)
            GroupType.MILITARY -> holder.groupType.setImageResource(R.drawable.type_icon_mil)
        }


        if(Build.currentBuild.IsGroupPresent(Build.everyGroup[position]))
        {
            holder.outerAdd.setBackgroundColor(R.color.gray)
            holder.textAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                Toast.makeText(Build.getContext(), "Group is already present", Toast.LENGTH_SHORT).show()
            }

            holder.innerAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                Toast.makeText(Build.getContext(), "Group is already present", Toast.LENGTH_SHORT).show()
            }

            holder.outerAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                Toast.makeText(Build.getContext(), "Group is already present", Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            holder.textAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                if(Build.currentBuild.currentSize >= 7){
                    Toast.makeText(Build.getContext(), "Every group slot is filled", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(Build.getContext(), "Group added", Toast.LENGTH_SHORT).show()
                    holder.outerAdd.setBackgroundColor(R.color.gray)
                }
            }

            holder.innerAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                if(Build.currentBuild.currentSize >= 7){
                    Toast.makeText(Build.getContext(), "Every group slot is filled", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(Build.getContext(), "Group added", Toast.LENGTH_SHORT).show()
                    holder.outerAdd.setBackgroundColor(R.color.gray)
                }
            }

            holder.outerAdd.setOnClickListener(){
                Build.currentBuild.AddGroup(Build.everyGroup[position])
                if(Build.currentBuild.currentSize >= 7){
                    Toast.makeText(Build.getContext(), "Every group slot is filled", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(Build.getContext(), "Group added", Toast.LENGTH_SHORT).show()
                    holder.outerAdd.setBackgroundColor(R.color.gray)
                }
            }
        }


        holder.i1.setImageResource(Build.everyGroup[position].ideas[0].icon)
        holder.i2.setImageResource(Build.everyGroup[position].ideas[1].icon)
        holder.i3.setImageResource(Build.everyGroup[position].ideas[2].icon)
        holder.i4.setImageResource(Build.everyGroup[position].ideas[3].icon)
        holder.i5.setImageResource(Build.everyGroup[position].ideas[4].icon)
        holder.i6.setImageResource(Build.everyGroup[position].ideas[5].icon)
        holder.i7.setImageResource(Build.everyGroup[position].ideas[6].icon)
        holder.groupBonus.setImageResource(Build.everyGroup[position].ideas[7].icon)
    }


    override fun getItemCount(): Int {
        var groupNumb: Int = Build.admGroups.size + Build.dipGroups.size + Build.milGroups.size

        return groupNumb
    }


}




class BuildAdditionViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    val groupIcon: ImageView = itemView.findViewById(R.id.idea_group_addition_gIcon)
    var groupName: TextView = itemView.findViewById(R.id.idea_group_add_gName)
    val groupType: ImageView = itemView.findViewById(R.id.idea_group_add_type)
    val groupBonus: ImageView = itemView.findViewById(R.id.group_bonus_add)

    val i1: ImageView = itemView.findViewById(R.id.idea_1_add)
    val i2: ImageView = itemView.findViewById(R.id.idea_2_add)
    val i3: ImageView = itemView.findViewById(R.id.idea_3_add)
    val i4: ImageView = itemView.findViewById(R.id.idea_4_add)
    val i5: ImageView = itemView.findViewById(R.id.idea_5_add)
    val i6: ImageView = itemView.findViewById(R.id.idea_6_add)
    val i7: ImageView = itemView.findViewById(R.id.idea_7_add)

    val textAdd: TextView = itemView.findViewById(R.id.Add_group_text)
    val innerAdd: ConstraintLayout = itemView.findViewById(R.id.Add_group_inner_lkayout)
    val outerAdd: ConstraintLayout = itemView.findViewById(R.id.Add_group_outer_layout)

    val context = itemView.context


}