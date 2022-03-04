package com.example.eu4_ideasbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class SavedBuildsAdapter() : RecyclerView.Adapter<SavedViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        Build.LoadBuilds()

        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val bonus_elem: View = layoutInflater.inflate(R.layout.saved_build_list_elem, parent, false)

        return SavedViewHolder(bonus_elem)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        holder.buildName.setText(Build.savedbuilds[position].buildName)

        holder.buildName.setOnClickListener(){
            Build.currentBuild = Build.savedbuilds[position]
            Toast.makeText(Build.getContext(), "Build Loaded", Toast.LENGTH_SHORT).show()
        }
        holder.innerLayout.setOnClickListener(){
            Build.currentBuild = Build.savedbuilds[position]
            Toast.makeText(Build.getContext(), "Build Loaded", Toast.LENGTH_SHORT).show()
        }
        holder.outerLayout.setOnClickListener(){
            Build.currentBuild = Build.savedbuilds[position]
            Toast.makeText(Build.getContext(), "Build Loaded", Toast.LENGTH_SHORT).show()
        }

        holder.del.setOnClickListener(){
            Toast.makeText(Build.getContext(), "Build ${Build.savedbuilds[position].buildName} deleted", Toast.LENGTH_SHORT).show()
            Build.savedbuilds.removeAt(position)
            Build.SaveWithoutCurrent()
        }
    }


    override fun getItemCount(): Int {
        return Build.savedbuilds.size
    }

}



class SavedViewHolder(val view: View): RecyclerView.ViewHolder(view)
{
    val buildName: TextView = itemView.findViewById(R.id.saved_build_name)
    val innerLayout: ConstraintLayout = itemView.findViewById(R.id.saved_elem_inner_layout)
    val outerLayout: ConstraintLayout = itemView.findViewById(R.id.saved_elem_outer_layout)
    val del: ImageView = itemView.findViewById(R.id.saved_build_del)
}