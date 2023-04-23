package geekbarains.material.view.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import geekbarains.material.R
import geekbarains.material.model.map.states.entity.CapitalOfState
import kotlinx.android.synthetic.main.item_capital.view.*

class MapRecyclerAdapter(private val onitemClickListener: OnitemClickListener)
    : RecyclerView.Adapter<MapRecyclerAdapter.ViewHolder>() {

    interface OnitemClickListener{
        fun onItemclick(capitalOfState: CapitalOfState)
    }

    //так сделано чтобы передавать список в адаптер без конструктора
    // - через присвоение полю значения
    var listCapitals: List<CapitalOfState> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_capital, parent, false))

    override fun getItemCount(): Int {
        return listCapitals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind( listCapitals[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

        fun bind(capitalOfState: CapitalOfState){
            itemView.tv_state.text = capitalOfState.capital
            itemView.tv_capital.text = capitalOfState.name

            itemView.setOnClickListener {
                onitemClickListener.onItemclick(capitalOfState)
            }
        }
    }
}

