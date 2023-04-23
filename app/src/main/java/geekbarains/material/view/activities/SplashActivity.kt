package geekbarains.material.view.activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import geekbarains.material.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //читаем сохранённный в настройках тип картинки
       val  isShowScreen = PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean("cbScreen", true)

        if (isShowScreen){
            setContentView(R.layout.activity_splash)

            image_view_splash.animate()
                .scaleY(2.5f)
                .scaleX(2.5f)
                .setInterpolator(LinearInterpolator()).setDuration(1500)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationEnd(animation: Animator?) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                    override fun onAnimationRepeat(animation: Animator?) {}
                    override fun onAnimationCancel(animation: Animator?) {}
                    override fun onAnimationStart(animation: Animator?) {}
                })
        }else{
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
}