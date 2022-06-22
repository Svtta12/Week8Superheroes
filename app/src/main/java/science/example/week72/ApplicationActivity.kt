package science.example.week72


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import science.example.week72.databinding.ActivityApplicationBinding
import science.example.week72.objects.Screens

class ApplicationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApplicationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)

        App.INSTANCE.router.backTo(Screens.mainScreen())

        setContentView(binding.root)
    }
}