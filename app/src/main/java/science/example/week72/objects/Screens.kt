package science.example.week72.objects

import android.content.Intent
import com.github.terrakok.cicerone.androidx.ActivityScreen
import science.example.week72.ApplicationActivity
import science.example.week72.MainActivity
import science.example.week72.SecondActivity
import science.example.week72.model.ModelSuperheroes

object Screens {

    fun mainScreen() = ActivityScreen { Intent(it, MainActivity::class.java) }

    fun secondScreen() = ActivityScreen { Intent(it, SecondActivity::class.java) }

    fun applicationScreen() = ActivityScreen { Intent(it, ApplicationActivity::class.java) }
}