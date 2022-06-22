package science.example.week72

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import science.example.week72.adapter.Listener
import science.example.week72.adapter.SuperheroesAdapter
import science.example.week72.data.ApiInterface
import science.example.week72.data.Repository
import science.example.week72.databinding.ActivityMainBinding
import science.example.week72.model.ModelSuperheroes
import science.example.week72.objects.Screens

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var api: ApiInterface
    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: LinearLayoutManager
    private lateinit var adapter: SuperheroesAdapter

    private lateinit var preferences: SharedPreferences
    private lateinit var forjson: List<ModelSuperheroes>

    private val navigator =  AppNavigator(this, R.id.activity)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            App.INSTANCE.router.newRootScreen(Screens.mainScreen())
        }

        api = Repository.retrofitService

        binding.recyclerview.setHasFixedSize(true)

        layout = LinearLayoutManager(this)
        binding.recyclerview.layoutManager = layout
        initViews()
        if (open()) {
            init()
        }
        else getAllSuperheroes()
    }

    override fun onClickItem(superheroes: MutableList<ModelSuperheroes>, position: Int) {

        App.INSTANCE.router.replaceScreen(Screens.secondScreen())

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("name", position)
        startActivity(intent)
    }



    override fun onPause() {
        App.INSTANCE.navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun getAllSuperheroes() {
        api.getSuperheroes().enqueue(object : Callback<MutableList<ModelSuperheroes>> {
            override fun onFailure(call: Call<MutableList<ModelSuperheroes>>, t: Throwable) {
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MutableList<ModelSuperheroes>>,
                response: Response<MutableList<ModelSuperheroes>>
            ) {
                adapter = SuperheroesAdapter(response.body() as MutableList<ModelSuperheroes>, this@MainActivity)
                adapter.notifyDataSetChanged()
                binding.recyclerview.adapter = adapter
                Information = response.body()!!
                save()
            }
        })
    }


    private fun save(){
        val gson = Gson()
        val editor = preferences.edit()
        editor.putString("Log", gson.toJson(Information)).apply()
    }

    private fun open() :Boolean{
        val dataFromSharedPrefs = preferences.getString("Log", null)
        val gson = Gson()
        if (dataFromSharedPrefs != null) {
            forjson = gson.fromJson(
                dataFromSharedPrefs,
                Array<ModelSuperheroes>::class.java
            ).asList()
            return true
        }
        return false
    }


    private fun init(){
        adapter = SuperheroesAdapter(forjson as MutableList<ModelSuperheroes>, this@MainActivity)
        binding.recyclerview.adapter = adapter
    }
    private fun initViews() {
        binding.apply {
            buttonApp.setOnClickListener {
                showPopupMenu(view = buttonApp)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val context = view.context

        popupMenu.menu.add(0,ID_APP, Menu.NONE, context.getString(R.string.app))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ID_APP -> {
                    App.INSTANCE.router.newChain(Screens.applicationScreen())

//                    val intent = Intent(this@MainActivity, ApplicationActivity::class.java)
//                    startActivity(intent)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ID_APP = 1
        var Information = listOf<ModelSuperheroes>()
    }
}




