package utec.pi3.siembrahora

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val langEs = findViewById<Button>(R.id.lang_es)
        val langEn = findViewById<Button>(R.id.lang_en)

        langEs.setOnClickListener {
            LocaleManager.setLocale(this, "es")
            recreate()
        }

        langEn.setOnClickListener {
            LocaleManager.setLocale(this, "en")
            recreate()
        }
    }
}
