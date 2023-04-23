package geekbarains.material.model.help

import android.app.Application
import geekbarains.material.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class HelpRepo(val app: Application):
    IHelpRepo {

    override fun getHelpText(): String? {
      return app.applicationContext.getString(R.string.helpString222)
    }

}