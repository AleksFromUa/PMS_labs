package ua.kpi.comsys.IO8120.main.graph_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ua.kpi.comsys.IO8120.main.R
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.intrusoft.scatter.ChartData
import com.intrusoft.scatter.PieChart
import kotlin.math.E
import kotlin.math.log


class GraphScreenFragment : Fragment() {

    private lateinit var graphScreenViewModel: GraphScreenViewModel
    private lateinit var graph: GraphView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        graphScreenViewModel =
            ViewModelProvider(this).get(GraphScreenViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_chart, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()
        drawGraph()
        drawPieChart()
    }


    private fun init() {
        graph = requireView().findViewById(R.id.graph)
    }

    private fun drawPieChart() {
        val pieChart = requireView().findViewById(R.id.pie_chart) as PieChart
        val data = ArrayList<ChartData>()
        data.add(ChartData("Жовтий 10%", 10f, Color.BLACK, Color.YELLOW))
        data.add(ChartData("Зелений 20%", 20f, Color.BLACK, Color.GREEN))
        data.add(ChartData("Синій 25%", 25f, Color.BLACK, Color.BLUE))
        data.add(ChartData("Червоний 5%", 5f, Color.BLACK, Color.RED))
        data.add(ChartData("Блакитний 40%", 40f, Color.BLACK, Color.parseColor("#2ad1f3")))
        pieChart.setChartData(data)
    }

    private fun drawGraph() {
        val start = -4.0
        val end = 4.0
        val maxPoints = 1000
        val arrOfX: DoubleArray = funcX(start, end, maxPoints)
        val arrOfY = DoubleArray(maxPoints) { logFun(arrOfX[it]) }
        val series = LineGraphSeries<DataPoint>()

        for (i in 0 until maxPoints)
            series.appendData(DataPoint(arrOfX[i], arrOfY[i]), false, arrOfX.size)

        graph.viewport.isXAxisBoundsManual = true
        graph.viewport.setMaxX(end)
        graph.viewport.setMinX(start)
        graph.viewport.isYAxisBoundsManual = true
        graph.viewport.setMaxY(2.0)
        graph.viewport.setMinY(-7.0)
        graph.addSeries(series)
    }

    private fun logFun(x: Double) = log(x, E)

    private fun funcX(start: Double, stop: Double, num: Int) =
        DoubleArray(num) { start + it * ((stop - start) / (num - 1)) }
}
