package com.eap.bankin_challenge_jerome_eap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.resource_layout.view.*


class ResourcesAdapter(private val resources: List<ResourceModel>) :
    RecyclerView.Adapter<ResourcesAdapter.ResourcesViewHolder>() {

    class ResourcesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ResourcesViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.resource_layout, viewGroup, false)

        return ResourcesViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ResourcesViewHolder, position: Int) {
        viewHolder.view.resource_id.text = resources[position].name
    }

    override fun getItemCount() = resources.size

}