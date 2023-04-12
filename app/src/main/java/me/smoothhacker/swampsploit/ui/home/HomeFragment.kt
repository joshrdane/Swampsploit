package me.smoothhacker.swampsploit.ui.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import me.smoothhacker.swampsploit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var pieChart: PieChart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView4
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set Dashboard text
        val librarySizeTextView: Button = binding.LibrarySizeTextView
        homeViewModel.librarySize.observe(viewLifecycleOwner) {
           librarySizeTextView.text = it
        }

        val payloadSizeTextview: Button = binding.PayloadSizeTextview
        homeViewModel.payloadSize.observe(viewLifecycleOwner) {
            payloadSizeTextview.text = it
        }

        val registeredTargetsTextView: Button = binding.RegisteredTargetsTextView
        homeViewModel.registeredTargets.observe(viewLifecycleOwner) {
            registeredTargetsTextView.text = it
        }

        val pastAttemptsTextView: Button = binding.PastAttemptsTextView
        homeViewModel.pastAttempts.observe(viewLifecycleOwner) {
            pastAttemptsTextView.text = it
        }

        // configure pieChart
        pieChart = root.findViewById(me.smoothhacker.swampsploit.R.id.pieChart)
        setPieChartData(pieChart)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // logic implemented from: https://www.geeksforgeeks.org/android-create-a-pie-chart-with-kotlin/
    fun setPieChartData(pieChart: PieChart) {
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        pieChart.dragDecelerationFrictionCoef = 0.95f

        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f

        pieChart.setDrawCenterText(true)

        pieChart.rotationAngle = 0f

        // enable rotation of the pieChart by touch
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true

        // setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        // on below line we are creating array list and
        // adding data to it to display in pie chart
        val entries: ArrayList<PieEntry> = ArrayList()
        // TODO: extract exploit history to add below
        entries.add(PieEntry(70f))
        entries.add(PieEntry(20f))
        entries.add(PieEntry(10f))

        // set pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors to list
        val colors: ArrayList<Int> = ArrayList()
        colors.add(me.smoothhacker.swampsploit.R.color.green)
        colors.add(me.smoothhacker.swampsploit.R.color.yellow)
        colors.add(me.smoothhacker.swampsploit.R.color.dustyRed)

        dataSet.colors = colors

        // set pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.data = data

        // undo highlights
        pieChart.highlightValues(null)

        // load chart
        pieChart.invalidate()
    }
}