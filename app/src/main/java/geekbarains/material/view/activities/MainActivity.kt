package geekbarains.material.view.activities

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.google.android.material.navigation.NavigationView
import geekbarains.material.R
import geekbarains.material.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    companion object{
        const val TAG = "33333"
    }
    private var oldTheme:Int = 1
    private var oldType:Int = 1
    private var toggle:ActionBarDrawerToggle? = null

    lateinit var navController: NavController
    lateinit var  appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate " )

        //без этого что- то не работает - google требует установить
        try {
            // Google Play will install latest OpenSSL
            ProviderInstaller.installIfNeeded(applicationContext)
            val sslContext: SSLContext = SSLContext.getInstance("TLSv1.2")
            sslContext.init(null, null, null)
            sslContext.createSSLEngine()
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        //читаем сохранённный в настройках тип картинки
        oldType = PreferenceManager.getDefaultSharedPreferences(this)
               .getString("ListEarth", "1")!!.toInt()

        //читаем сохранённную в настройках тему
        oldTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        //устанавливаем сохранённную в настройках тему
        when(oldTheme){
            1->setTheme(R.style.AppTheme)
            2->setTheme(R.style.AppThemePurple)
            3->setTheme(R.style.AppThemeBlack)
        }

        setContentView(R.layout.activity_main)

        //поддержка экшенбара
        setSupportActionBar(toolbar)
        //отключаем показ заголовка тулбара, так как там свой макет с main_title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        //текстовое поле в тулбаре
        with(toolbar.findViewById<TextView>(R.id.main_title)){
            textSize = 16f
            setTextColor(Color.WHITE)
            text = context.getString(R.string.app_name)
        }

        //находим NavController
        navController =Navigation.findNavController(this, R.id.nav_host_fragment)
        //читаем из графа конфигурацию
        appBarConfiguration = AppBarConfiguration.Builder(navController.graph)
            //Отображать кнопку навигации как гамбургер , когда она не отображается как кнопка вверх
            .setOpenableLayout(drawer_layout)
            .build()
        //обработка событий экшенбара -гамбургер, кнопка вверх
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        //слушатель меню шторки -для обработки пунктов шторки
        nav_view.setNavigationItemSelectedListener(this)
    }

    // Этот метод вызывается всякий раз, когда пользователь выбирает переход вверх
    // в иерархии действий приложения из панели действий (экшенбара)
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                ||super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume " )
        //при изменении темы и возвращении из настроек проверяем - какая тема установлена
        val newTheme = PreferenceManager.getDefaultSharedPreferences(this)
            .getString("ListColor", "1")!!.toInt()
        Log.d(TAG, "MainActivity onResume newTheme = $newTheme  oldTheme = $oldTheme ")
        if (newTheme != oldTheme){
            recreate()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_favorites -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_favorites")
          navController.navigate(R.id.favoriteFragment)
            }
            R.id.nav_setting -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_setting")
                navController.navigate(R.id.settingsFragment)
            }
            R.id.nav_search_wiki ->{
                navController.navigate(R.id.searchFragment)
            }
            R.id.nav_help ->{
                navController.navigate(R.id.helpFragment)
            }

            R.id.nav_share -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_share")
                //поделиться - передаём ссылку на приложение в маркете
                //shareApp()
                toast(getString(R.string.stub))
            }
            R.id.nav_rate -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_send")
                //оценить приложение - попадаем на страницу приложения в маркете
                //rateApp()
                toast(getString(R.string.stub))
            }
        }
        // Выделяем выбранный пункт меню в шторке
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }
}
