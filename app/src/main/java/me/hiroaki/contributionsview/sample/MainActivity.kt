package me.hiroaki.contributionsview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

//    private val sampleContributions by lazy {
//        HashMap<LocalDate, Int>().apply {
//            put(LocalDate.now().minusDays(8.toLong()), 1)
//            put(LocalDate.now().minusDays(6.toLong()), 3)
//            put(LocalDate.now().minusMonths(9.toLong()), 5)
//        }
//    }

//    private val sampleContributions by lazy {
//        HashMap<Date, Int>().apply {
//            put(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -8) }.time, 1)
//            put(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }.time, 3)
//            put(Calendar.getInstance().apply { add(Calendar.MONTH, -9) }.time, 5)
//        }
//    }

    private val sampleContributions by lazy {
        HashMap<Calendar, Int>().apply {
            put(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -8) }, 1)
            put(Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -6) }, 3)
            put(Calendar.getInstance().apply { add(Calendar.MONTH, -9) }, 5)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        contributionsView.setContributionsMap(sampleContributions)
//        contributionsView.setContributionsDateMap(sampleContributions)
        contributionsView.setContributionsCalendarMap(sampleContributions)

        setTodayCommitButton.setOnClickListener {
//            contributionsView.setCommit(LocalDate.now(), 7)
//            contributionsView.setCommit(Date(), 7)
            contributionsView.setCommit(Calendar.getInstance(), 7)
        }

        addYesterdayDayCommitButton.setOnClickListener {
//            val tmp = LocalDate.now().minusDays(1)
//            val tmp = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }.time
            val tmp = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -1) }
            contributionsView.addCommit(tmp, 1)
        }

        changeDayOfWeekStart.setOnClickListener {
            contributionsView.isMondayStart = !contributionsView.isMondayStart
        }

        showPreviousPage.setOnClickListener {
            contributionsView.endLocalDate = contributionsView.startLocalDate.minusDays(1)
        }
    }
}
