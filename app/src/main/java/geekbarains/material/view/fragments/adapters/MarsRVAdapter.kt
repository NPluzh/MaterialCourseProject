package geekbarains.material.view.fragments.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.model.mars.entity.FotosOfMars
import kotlinx.android.synthetic.main.favorite_foto.view.*
import kotlinx.android.synthetic.main.item_mars.view.*

class MarsRVAdapter(val listener:OnItemClickListener)
    : RecyclerView.Adapter<MarsRVAdapter.ViewHolder>() {

    companion object{
        const val TAG = "33333"
    }

    lateinit var context: Context

    var data: List<FotosOfMars> = listOf()
        set(value){
        field = value
        notifyDataSetChanged()
        }

    interface OnItemClickListener{
        fun onItemClick(url:String)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_mars, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        fun bind(foto:FotosOfMars){

            var url: String? = null
            foto.img_src ?. let{
                 url = it.replace("http", "https")
            }
            url?. let{
                //грузим картинку
                Picasso.with(context)
                    .load(it)
                    .placeholder(R.drawable.ic_no_photo_vector)
                    .error(R.drawable.ic_load_error_vector)
                    .into(itemView.iv_mars)
            }

            itemView.tv_mars_date.text = foto.earth_date
            itemView.tv_mars_rover.text = foto.rover?.name ?: context.getString(R.string.unknown)

            Log.d(EarthRecyclerAdapter.TAG, "MarsRVAdapter bind tv_mars.text ${foto.earth_date} " +
                    "foto.img_src = ${foto.img_src}" )

            itemView.iv_mars.setOnClickListener {
                foto.img_src?.let {listener.onItemClick(it) }
            }
        }
    }
}