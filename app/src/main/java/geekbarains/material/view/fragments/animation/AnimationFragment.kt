package geekbarains.material.view.fragments.animation

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.squareup.picasso.Picasso
import geekbarains.material.R
import geekbarains.material.view.constants.Constants
import kotlinx.android.synthetic.main.activity_animations.*
import kotlinx.android.synthetic.main.activity_animations.container_animate
import kotlinx.android.synthetic.main.activity_animations.image_view_animate
import kotlinx.android.synthetic.main.activity_animations.web_view_animate
import kotlinx.android.synthetic.main.fragment_anime.*

class AnimationFragment: Fragment() {
    companion object{
        const val TAG = "33333"
    }

    private var isExpanded = false
    lateinit var navController: NavController
    var  url: String? = null
    var mediaType: String? = null
    var isZoom = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_anime, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        //разрешаем показ меню во фрагменте
        setHasOptionsMenu(true)

        url = arguments?.getString(Constants.URL_ANIMATION)
        mediaType =  arguments?.getString(Constants.MEDIA_TYPE_ANIMATION)
        Log.d(TAG, "*** AnimationFragment onViewCreated  mediaType = $mediaType url = $url")

        if(mediaType == Constants.MEDIA_IMAGE||mediaType == Constants.MEDIA_NONE){
            Log.d(TAG, "*** AnimationFragment onViewCreated  mediaType = image")
            image_view_animate.visibility = View.VISIBLE
            web_view_zoom.visibility = View.GONE
            web_view_animate.visibility = View.GONE
            //грузим несуществующий файл
            //web_view.loadUrl("file:///android_asset/nonexistent.html")
            web_view_animate.loadUrl(  "about:blank") //или так

            url?. let{
                Picasso.with(requireActivity())
                    .load(url)
                    .placeholder(R.drawable.ic_no_photo_vector)
                    .error(R.drawable.ic_load_error_vector)
                    .into(image_view_animate)
            }

            image_view_animate.setOnClickListener {
                isExpanded = !isExpanded
                TransitionManager.beginDelayedTransition(
                    container_animate, TransitionSet()
                        .addTransition(ChangeBounds())
                        .addTransition(ChangeImageTransform())
                )
                val params: ViewGroup.LayoutParams = image_view_animate.layoutParams
                params.height =
                    if (isExpanded){ ViewGroup.LayoutParams.MATCH_PARENT }
                    else{ ViewGroup.LayoutParams.WRAP_CONTENT}
                image_view_animate.layoutParams = params

                image_view_animate.scaleType =
                    if (isExpanded) ImageView.ScaleType.CENTER_CROP
                    else ImageView.ScaleType.FIT_CENTER

                //ставим чёрный по всем краям
                container_animate.setBackgroundColor(Color.BLACK)
                image_view_animate.setBackgroundColor(Color.BLACK)
            }

        }else {
            Log.d(TAG, "*** AnimationFragment onViewCreated  mediaType = video")
            web_view_animate.clearCache(true)
            web_view_animate.clearHistory()
            web_view_animate.settings.javaScriptEnabled = true  // небезопасно тащить скрипты
            web_view_animate.settings.javaScriptCanOpenWindowsAutomatically = true

            image_view_animate.visibility = View.GONE
            web_view_zoom.visibility = View.GONE
            web_view_animate.visibility = View.VISIBLE
            url?. let{web_view_animate.loadUrl(it)}
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_app_bar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.app_bar_search_wiki).isVisible = false
        menu.findItem(R.id.app_bar_edit).isVisible = false
        menu.findItem(R.id.app_bar_favorites).isVisible = false
        menu.findItem(R.id.app_bar_zoom).isVisible =
            mediaType == Constants.MEDIA_IMAGE||!(mediaType == Constants.MEDIA_NONE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_settings -> navController.navigate(R.id.settingsFragment)
            R.id.app_bar_help ->navController.navigate(R.id.helpFragment)
            R.id.app_bar_zoom ->{
                isZoom = !isZoom
                //показываем крупно в web_view
                if (isZoom){
                    image_view_animate.visibility = View.GONE
                    web_view_animate.visibility = View.GONE
                    web_view_zoom.visibility = View.VISIBLE
                    url?. let{web_view_zoom.loadUrl(it)}
                }else{
                    //показываем  в image_view
                    image_view_animate.visibility = View.VISIBLE
                    web_view_animate.visibility = View.GONE
                    web_view_zoom.visibility = View.GONE

                    Picasso.with(requireActivity())
                        .load(url)
                        .placeholder(R.drawable.ic_no_photo_vector)
                        .error(R.drawable.ic_load_error_vector)
                        .into(image_view_animate)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}