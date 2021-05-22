package ua.kpi.comsys.IO8120.main

import android.os.Bundle
import android.view.View
import com.intrusoft.scatter.PieChart
import com.jjoe64.graphview.GraphView
import ua.kpi.comsys.IO8120.core_ui.MainActivity
import ua.kpi.comsys.IO8120.main.navigation_fragment.NavigationFragment

class AppActivity : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFragment(NavigationFragment(), animEnter = R.anim.no_anim)
    }

    fun onChangeChartClick(view: View) {
        val graph = findViewById<GraphView>(R.id.graph)
        val pieChart = findViewById<PieChart>(R.id.pie_chart)

        if (graph.visibility == View.GONE) {
            graph.visibility = View.VISIBLE
            pieChart.visibility = View.GONE
        } else {
            graph.visibility = View.GONE
            pieChart.visibility = View.VISIBLE
        }
    }
}
