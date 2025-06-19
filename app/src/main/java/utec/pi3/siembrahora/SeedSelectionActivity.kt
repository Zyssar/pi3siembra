package utec.pi3.siembrahora

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import utec.pi3.siembrahora.data.AppDatabase
import utec.pi3.siembrahora.data.Seed
import java.util.concurrent.TimeUnit
import androidx.core.net.toUri

class SeedSelectionActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private var seeds: List<Seed> = emptyList()
    private var currentIndex = 0

    // Views
    private lateinit var bubbleLeft: FrameLayout
    private lateinit var bubbleCenter: FrameLayout
    private lateinit var bubbleRight: FrameLayout
    private lateinit var imgLeft: ImageView
    private lateinit var imgCenter: ImageView
    private lateinit var imgRight: ImageView
    private lateinit var nameView: TextView
    private lateinit var infoView: TextView
    private lateinit var timeView: TextView
    private lateinit var configButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seed_selection)

        db = AppDatabase.getInstance(this)

        // find views
        bubbleLeft = findViewById(R.id.bubble_left)
        bubbleCenter = findViewById(R.id.bubble_center)
        bubbleRight = findViewById(R.id.bubble_right)
        imgLeft = findViewById(R.id.seed_image_left)
        imgCenter = findViewById(R.id.seed_image_center)
        imgRight = findViewById(R.id.seed_image_right)
        nameView = findViewById(R.id.seed_name)
        infoView = findViewById(R.id.seed_info)
        timeView = findViewById(R.id.seed_time)
        configButton = findViewById(R.id.btn_config)

        // Config/settings button
        configButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Bubble click listeners
        bubbleLeft.setOnClickListener {
            if (seeds.isNotEmpty()) {
                currentIndex = (currentIndex - 1 + seeds.size) % seeds.size
                showSeed(currentIndex)
            }
        }
        bubbleRight.setOnClickListener {
            if (seeds.isNotEmpty()) {
                currentIndex = (currentIndex + 1) % seeds.size
                showSeed(currentIndex)
            }
        }
        bubbleCenter.setOnClickListener {
            if (seeds.isNotEmpty()) {
                val selected = seeds[currentIndex]
                // Return result or start next activity
                val data = Intent().apply {
                    putExtra("selectedSeedId", selected.id)
                }
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }

        // Observe seeds from DB
        lifecycleScope.launch {
            db.seedDao().getAllSeeds().collectLatest { list ->
                if (list.isEmpty()) {
                    insertDefaultSeeds()
                    // After insertion, next emission will have data
                } else {
                    // update list and UI
                    seeds = list
                    // ensure currentIndex valid
                    if (currentIndex >= seeds.size) currentIndex = 0
                    showSeed(currentIndex)
                }
            }
        }
    }

    private suspend fun insertDefaultSeeds() {
        val defaultSeeds = listOf(
            Seed(
                name = "Rabanito",
                imageUri = "rabanito", // drawable name
                info = "¡El rabanito es pequeño, valiente y sorprendente!\n" +
                        "Solo necesita agua, sombra parcial y tierra suelta para crecer fuerte. En muy poco tiempo, ¡verás cómo asoma bajo la tierra un tesoro rojo brillante! Es como tener un secreto escondido bajo el suelo.",
                estimatedDurationSeconds = TimeUnit.HOURS.toSeconds(24 * 25L).toInt() // e.g., example
            ),
            Seed(
                name = "Lechuga",
                imageUri = "lechuga",
                info = "¡La lechuga es una amiga verde muy interesante! Tan solo necesita un poco de sol, tierra húmeda y mucho cariño. La lechuga crece rápido y en pocas semanas podrás ver cómo sus hojas se hacen grandes y crujientes, ¡listas para una ensalada fresca!",
                estimatedDurationSeconds = TimeUnit.HOURS.toSeconds(24 * 45L).toInt()
            )
        )
        defaultSeeds.forEach { db.seedDao().insert(it) }
    }

    private fun showSeed(index: Int) {
        if (seeds.isEmpty()) return

        val size = seeds.size
        // compute indices
        val prevIndex = (index - 1 + size) % size
        val nextIndex = (index + 1) % size

        val seedPrev = seeds[prevIndex]
        val seedCurr = seeds[index]
        val seedNext = seeds[nextIndex]

        // load images into ImageViews
        loadSeedImageInto(seedPrev, imgLeft)
        loadSeedImageInto(seedCurr, imgCenter)
        loadSeedImageInto(seedNext, imgRight)

        // update info for center
        nameView.text = seedCurr.name
        infoView.text = seedCurr.info
        timeView.text = formatDuration(seedCurr.estimatedDurationSeconds)
    }

    @SuppressLint("DiscouragedApi")
    private fun loadSeedImageInto(seed: Seed, imageView: ImageView) {
        val uriStr = seed.imageUri
        if (uriStr.startsWith("content://") || uriStr.startsWith("file://")) {
            try {
                imageView.setImageURI(uriStr.toUri())
            } catch (e: Exception) {
                imageView.setImageResource(android.R.color.darker_gray)
            }
        } else {
            // assume drawable name
            val resId = resources.getIdentifier(uriStr, "drawable", packageName)
            if (resId != 0) {
                imageView.setImageResource(resId)
            } else {
                // placeholder if not found
                imageView.setImageResource(android.R.color.darker_gray)
            }
        }
    }

    private fun formatDuration(totalSeconds: Int): String {
        if (totalSeconds <= 0) return ""
        var seconds = totalSeconds.toLong()
        val days = seconds / (24 * 3600)
        seconds %= (24 * 3600)
        val hours = seconds / 3600
        seconds %= 3600
        val minutes = seconds / 60
        seconds %= 60

        val parts = mutableListOf<String>()
        if (days > 0) parts.add("$days día${if (days>1) "s" else ""}")
        if (hours > 0) parts.add("$hours hora${if (hours>1) "s" else ""}")
        if (minutes > 0) parts.add("$minutes min")
        if (seconds > 0 && parts.isEmpty()) {
            // only show seconds if very short and no larger unit
            parts.add("$seconds s")
        }
        return if (parts.isEmpty()) "0" else parts.joinToString(" ")
    }
}
