package com.watools.wacleaner.module.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.watools.wacleaner.module.R
import com.watools.wacleaner.module.model.WADirectoryItem
import java.lang.StringBuilder

class RVWADirectoriesAdapter(val waDirectoryDetailList: ArrayList<WADirectoryItem>, val checkChangeListener: CBCheckChangeListener) :
        RecyclerView.Adapter<RVWADirectoriesAdapter.DirectoryItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DirectoryItemViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.cm_wa_directory_item, parent, false)
        return DirectoryItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return waDirectoryDetailList.size
    }

    override fun onBindViewHolder(holder: DirectoryItemViewHolder, position: Int) {
        holder.tvDirectoryName.text = waDirectoryDetailList[position]?.dirName
        var text: StringBuilder
        if (waDirectoryDetailList[position]?.totalFiles != 0 && !waDirectoryDetailList[position]?.dirSize.contentEquals("0 +  B")) {
            text = StringBuilder().append(waDirectoryDetailList[position]?.totalFiles).append(" Files - ")
                    .append(waDirectoryDetailList[position]?.dirSize)
        } else {
            text = StringBuilder("Empty")
        }
        holder.tvDirDetails.text = text

        holder.ivDirectoryIconBg.setColorFilter(ContextCompat.getColor(holder.ivDirectoryIconBg.context, waDirectoryDetailList[position]?.bgColorCode), android.graphics.PorterDuff.Mode.SRC_IN)
        holder.ivDirectoryIcon.setBackgroundResource(waDirectoryDetailList[position]?.icon)
        if (position == itemCount - 1) {
            holder.vDivider.visibility = View.INVISIBLE
        } else {
            holder.vDivider.visibility = View.VISIBLE
        }

        holder.cbClearData.setOnCheckedChangeListener { buttonView, isChecked ->
            checkChangeListener.onCheckChanged(isChecked)
        }
    }

    class DirectoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDirectoryName = itemView.findViewById<TextView>(R.id.tvDirectoryName)
        val tvDirDetails = itemView.findViewById<TextView>(R.id.tvDirDetails)
        val vDivider = itemView.findViewById<View>(R.id.vDivider)
        val ivDirectoryIconBg = itemView.findViewById<ImageView>(R.id.ivDirectoryIconBg)
        val ivDirectoryIcon = itemView.findViewById<ImageView>(R.id.ivDirectoryIcon)
        val cbClearData = itemView.findViewById<CheckBox>(R.id.cbClearData)
    }

    interface CBCheckChangeListener {
        fun onCheckChanged(isChecked: Boolean)
    }
}