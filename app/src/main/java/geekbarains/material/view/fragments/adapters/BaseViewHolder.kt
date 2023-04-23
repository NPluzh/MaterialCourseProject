package geekbarains.material.view.fragments.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.model.room.Favorite

abstract class BaseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    abstract fun bind(favorite:Favorite)
}