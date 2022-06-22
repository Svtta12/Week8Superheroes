package science.example.week72

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import science.example.week72.MainActivity.Companion.Information
import science.example.week72.databinding.ActivitySecondBinding
import science.example.week72.objects.Screens

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.INSTANCE.router.backTo(Screens.mainScreen())

        binding = ActivitySecondBinding.inflate(layoutInflater)
        with(binding) {

            val number = intent.getIntExtra("name", 0)
            val baseURl = Information[number].imageurl

            textName.text = Information[number].name
            textRealname.text = Information[number].realname.toString()
            textTeamSuperheroes.text = Information[number].team.toString()
            textCreatedBy.text = Information[number].createdby.toString()
            textPublisher.text = Information[number].publisher.toString()
            textBio.text = Information[number].bio.toString()

            Picasso.get()
                .load(baseURl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imageSuperheroesDetail)
        }
        setContentView(binding.root)

    }

}