  package geekbarains.material.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import geekbarains.material.model.help.HelpRepo
import geekbarains.material.model.help.IHelpRepo

  class HelpViewModel( application: Application) : AndroidViewModel(application) {

    val helpRepo: IHelpRepo = HelpRepo(application)

    fun getHelpText():String?{
      return  helpRepo.getHelpText()
    }
}