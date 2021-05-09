package ua.kpi.compsys.IO8120.ui.film

import ua.kpi.compsys.IO8120.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.textclassifier.TextLanguage
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.fasterxml.jackson.databind.ObjectMapper
import java.lang.reflect.Field

class DetailActivity : AppCompatActivity() {

    private lateinit var info: DetailedFilm
    private lateinit var poster: ImageView
    private lateinit var year: TextView
    private lateinit var genre: TextView
    private lateinit var rated: TextView
    private lateinit var released: TextView
    private lateinit var runtime: TextView
    private lateinit var director: TextView
    private lateinit var actors: TextView
    private lateinit var plot: TextView
    private lateinit var country: TextView
    private lateinit var awords: TextView
    private lateinit var rating: TextView
    private lateinit var prodaction: TextView
    private lateinit var title: TextView
    private lateinit var language: TextView
    private lateinit var writer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
    }

    fun init() {
        val mapper = ObjectMapper()
        val jsonText = intent.extras!!["info"].toString()
        info = mapper.readValue(jsonText, DetailedFilm::class.java)
        poster = findViewById(R.id.poster)
        val drawable = getResId(info.poster!!.replace(".jpg", ""), R.drawable::class.java)
        if (drawable != -1)
            poster.setImageDrawable(this.resources.getDrawable(drawable))

        year = findViewById(R.id.year)
        year.text = year.text.toString() +  " "  + info.year
        genre = findViewById(R.id.genre)
        genre.text = genre.text.toString() +  " "  + info.genre
        rated = findViewById(R.id.rated)
        rated.text = rated.text.toString() +  " "  + info.rated
        released = findViewById(R.id.released)
        released.text = released.text.toString() +  " "  + info.released
        runtime = findViewById(R.id.runtime)
        runtime.text = runtime.text.toString() +  " "  + info.runtime
        director = findViewById(R.id.director)
        director.text = director.text.toString() +  " "  + info.director
        actors = findViewById(R.id.actors)
        actors.text = actors.text.toString() +  " "  + info.actors
        plot = findViewById(R.id.plot)
        plot.text = plot.text.toString() +  " "  + info.plot
        country = findViewById(R.id.country)
        country.text = country.text.toString() +  " "  + info.country
        awords = findViewById(R.id.awards)
        awords.text = awords.text.toString() +  " "  + info.awards
        rating = findViewById(R.id.rated)
        rating.text = rating.text.toString() +  " "  + info.rated
        prodaction = findViewById(R.id.production)
        prodaction.text = prodaction.text.toString() +  " "  + info.production
        title = findViewById(R.id.title)
        title.text = title.text.toString() +  " "  + info.title
        language = findViewById(R.id.language)
        language.text = language.text.toString() +  " "  + info.language
        writer = findViewById(R.id.write)
        writer.text = writer.text.toString() +  " "  + info.writer
    }

    fun getResId(resName: String, c: Class<*>): Int {
        return try {
            val idField: Field = c.getDeclaredField(resName)
            idField.getInt(idField)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            -1
        }
    }
}