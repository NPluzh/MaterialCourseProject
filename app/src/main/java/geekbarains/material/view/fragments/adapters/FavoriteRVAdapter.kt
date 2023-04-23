package geekbarains.material.view.fragments.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.model.room.Favorite
import kotlinx.android.synthetic.main.favorite_foto.view.*
import kotlinx.android.synthetic.main.favorite_header.view.*
import kotlinx.android.synthetic.main.favorite_video.view.*

class FavoriteRVAdapter (private val onitemClickListener: OnitemClickListener,
                         private val onRemoveListener: OnRemoveListener,
                         private val onAddDescriptionListener: OnAddDescriptionListener
)
    : RecyclerView.Adapter<BaseViewHolder> (){

    companion object {
        const val TAG = "33333"
        private const val TYPE_IMAGE = 0
        private const val TYPE_VIDEO = 1
        private const val TYPE_HEADER = 2

    }
    lateinit var context: Context
    var isOpenDescription = false

    var isEdit:Boolean = false

    interface OnRemoveListener{
        fun onRemove(favorite: Favorite)
    }

    interface OnitemClickListener{
        fun onItemclick(favorite: Favorite)
    }

    interface OnAddDescriptionListener{
        fun  onAddDescription(favorite: Favorite)
    }

    fun setEditType(isEdit:Boolean){
        this.isEdit = isEdit
    }

    //так сделано чтобы передавать список в адаптер без конструктора
    // - через присвоение полю значения
    var listFavorites: MutableList<Favorite> = mutableListOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)

        return  when(viewType){
            TYPE_IMAGE ->ImageViewHolder(inflater.inflate(R.layout.favorite_foto, parent, false))
            TYPE_VIDEO ->VideoViewHolder(inflater.inflate(R.layout.favorite_video, parent, false))
            else ->HeaderViewHolder(inflater.inflate(R.layout.favorite_header, parent, false))

        }
    }

    //здесь описываем как различаем типы
    override fun getItemViewType(position: Int): Int {
        return when{
           // position == 0 -> TYPE_HEADER
            listFavorites[position].type == "image" -> TYPE_IMAGE
            else -> TYPE_VIDEO
        }
    }

    override fun getItemCount(): Int = listFavorites.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    inner class ImageViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(favorite: Favorite){
            if(isEdit){
                itemView.profileFotoEdit.visibility = View.VISIBLE
                itemView.showTextFoto.visibility = View.GONE
                itemView.fotoDescriptionTextViewEdit.visibility = View.GONE
            }else{
                itemView.profileFotoEdit.visibility = View.GONE
                itemView.showTextFoto.visibility = View.VISIBLE
            }

            itemView.fotoTitleEdit.text = favorite.title
            itemView.fotoDateEdit.text = favorite.date
            itemView.fotoDescriptionTextViewEdit.text = favorite.description

            //грузим картинку
            Picasso.with(context)
                .load(favorite.url)
                .placeholder(R.drawable.ic_no_photo_vector)
                .error(R.drawable.ic_load_error_vector)
                .into(itemView.fotoImageViewEdit)

            itemView.showTextFoto.setOnClickListener {
                Log.d(TAG, "FavoriteRVAdapter setOnClickListener ImageViewHolder")
                 isOpenDescription  = !isOpenDescription
                if (isOpenDescription){
                    itemView.fotoDescriptionTextViewEdit.visibility = View.VISIBLE
                }else{
                    itemView.fotoDescriptionTextViewEdit.visibility = View.GONE
                }
            }
            //переход на экран просмотра
            itemView.fotoImageViewEdit.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }

            itemView.removeItemImageViewFotoEdit.setOnClickListener {
                removeItem()
                onRemoveListener.onRemove(favorite)
            }

            itemView.moveItemUpFotoEdit.setOnClickListener {
                moveUp()
            }

            itemView.moveItemDownFotoEdit.setOnClickListener {
                moveDown()
            }

            itemView.addDescrFotoEdit.setOnClickListener {
                addDescription(favorite)
            }
        }

        private fun addDescription(favorite:Favorite){
            onAddDescriptionListener.onAddDescription(favorite)
          notifyItemChanged(layoutPosition)
        }

        private fun removeItem(){
            listFavorites.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp(){
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                listFavorites.removeAt(currentPosition).apply {
                    listFavorites.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown(){
            layoutPosition.takeIf { it < listFavorites.size - 1 }?.also { currentPosition ->
                listFavorites.removeAt(currentPosition).apply {
                    listFavorites.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class VideoViewHolder(itemView: View) : BaseViewHolder(itemView) {

        @SuppressLint("SetJavaScriptEnabled")
         override  fun bind(favorite: Favorite){

            if(isEdit){
                itemView.profileVideoEdit.visibility = View.VISIBLE
                itemView. showTextVideo.visibility = View.GONE
                itemView. fotoDescriptionVideo.visibility = View.GONE
            }else{
                itemView.profileVideoEdit.visibility = View.GONE
                itemView. showTextVideo.visibility = View.VISIBLE
            }

            itemView.fotoTitleVideo.text = favorite.title
            itemView.fotoDateVideo.text = favorite.date
            itemView.fotoDescriptionVideo.text = favorite.description

            itemView. web_view_video_anim.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
            favorite.url?. let{itemView. web_view_video_anim.loadUrl(it)}

            itemView.showTextVideo.setOnClickListener {
                Log.d(TAG, "FavoriteRVAdapter setOnClickListener VideoViewHolder")
                isOpenDescription  = !isOpenDescription
                if (isOpenDescription){
                    itemView.fotoDescriptionVideo.visibility = View.VISIBLE
                }else{
                    itemView.fotoDescriptionVideo.visibility = View.GONE
                }
            }
            //переход на экран просмотра
            //web_view накрыто прозрачной шторкой videoScreen - поэтому не запускается а идёт переход
            itemView. videoScreen.setOnClickListener {
                onitemClickListener.onItemclick(favorite)
            }

            itemView.removeItemImageViewVideoEdit.setOnClickListener {
                removeItem()
                onRemoveListener.onRemove(favorite)
            }

            itemView.moveItemUpVideoEdit.setOnClickListener {
                moveUp()
            }

            itemView.moveItemDownVideoEdit.setOnClickListener {
                moveDown()
            }

            itemView.addDescrVideoEdit.setOnClickListener {
                addDescription(favorite)
            }
        }

        private fun addDescription(favorite:Favorite){
            onAddDescriptionListener.onAddDescription(favorite)
            notifyItemChanged(layoutPosition)
        }

        private fun removeItem(){
            listFavorites.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun moveUp(){
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                listFavorites.removeAt(currentPosition).apply {
                    listFavorites.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown(){
            layoutPosition.takeIf { it < listFavorites.size - 1 }?.also { currentPosition ->
                listFavorites.removeAt(currentPosition).apply {
                    listFavorites.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView){
       override fun bind(favorite: Favorite){
            itemView.header.text = context.getString(R.string.headerFavorite)
        }
    }
}

//fun showDeleteDialog(favorite: Favorite) {
//    val deleteDialog = AlertDialog.Builder(context)
//    deleteDialog.setTitle("Удалить: Вы уверены?")
//    deleteDialog.setPositiveButton(
//        "Нет"
//    ) { dialog, which ->
//        //ничего не делаем
//    }
//    deleteDialog.setNegativeButton("Да") { _, _ ->
//        removeItem()
//        onRemoveListener.onRemove(favorite)
//    }
//    deleteDialog.show()
//}