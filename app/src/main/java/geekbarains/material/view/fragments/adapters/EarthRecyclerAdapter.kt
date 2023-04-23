package geekbarains.material.view.fragments.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.model.earth.entity.PictureOfEarth
import kotlinx.android.synthetic.main.item_earth.view.*


class EarthRecyclerAdapter(val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<EarthRecyclerAdapter.ViewHolder>() {

    companion object{
        const val TAG = "33333"
    }
   lateinit var context:Context
   var  isExpanded = false

interface OnItemClickListener{
    fun onItemClick(url:String)
}

    var listOfPictures : List<PictureOfEarth>  = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_earth, parent, false )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int  =  listOfPictures.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfPictures[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(pictureOfEarth: PictureOfEarth){
            val date = pictureOfEarth.date?.take(10)
            val dateWithDiv = date?.replace("-", "/")
            val url ="https://epic.gsfc.nasa.gov/archive/natural/$dateWithDiv/" +
                    "jpg/${pictureOfEarth.image}.jpg "
            Log.d(TAG, "EarthRecyclerAdapter bind $url " )
            //грузим картинку
            Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(itemView.iv_earth)
            //стапим дату и время
            itemView.textViewEarth.text = pictureOfEarth.date
            //слушатель для передачи url картинки
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(url)
            }
        }

    }
}