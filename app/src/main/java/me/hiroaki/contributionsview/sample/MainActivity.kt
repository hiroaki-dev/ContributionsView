package me.hiroaki.contributionsview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    private val sampleContributions by lazy {
        HashMap<LocalDate, Int>().apply {
            put(LocalDate.now(), 7)
            put(LocalDate.now().minusDays(8.toLong()), 1)
            put(LocalDate.now().minusDays(6.toLong()), 3)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contributionsView.contributions = sampleContributions
    }
}
