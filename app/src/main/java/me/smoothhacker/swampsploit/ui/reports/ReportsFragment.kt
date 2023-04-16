package me.smoothhacker.swampsploit.ui.reports

import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import me.smoothhacker.swampsploit.databinding.FragmentReportsBinding
import me.smoothhacker.swampsploit.ui.exploit.SelectedExploit
import me.smoothhacker.swampsploit.utils.Report
import me.smoothhacker.swampsploit.utils.Reports
import java.util.*


class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val reports = Reports(requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!)
        reports.addReport(Report(SelectedExploit.PROFTPD, true, "Run #1: executed", Date(), false))
        reports.addReport(Report(SelectedExploit.NETATALK, false, "Run #3: Failed", Date(), false))
        reports.addReport(Report(SelectedExploit.PROFTPD, false, "Run #30: success", Date(), false))

        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recentReport: TextView =
            root.findViewById(me.smoothhacker.swampsploit.R.id.recentReport)

        if (reports.getSize() == 0) {
            recentReport.text = me.smoothhacker.swampsploit.R.string.reports_empty.toString()
        } else {
            /* TODO: grab date/time from most recent report and fill below text with fileList[0]
            recentReport.text = "testing"
            recentReport.setOnClickListener {
                // launch new fragment to display report details
                createChildFragment()
            }*/
            for (i in 0 until reports.getSize()) {
                val mainLayout = root.findViewById<LinearLayout>(me.smoothhacker.swampsploit.R.id.reports_dashboard)
                val currReport: Report = reports.getReport(i)
                val nextReport = TextView(this.context)
                // TODO: grab date/time from second most recent report and fill text
                nextReport.text = currReport.getTimestamp().toString() + currReport.getReportText()
                nextReport.textSize = 16f
                nextReport.gravity = Gravity.CENTER_VERTICAL
                nextReport.setBackgroundResource(me.smoothhacker.swampsploit.R.drawable.rounded_teal_textview)
                nextReport.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )

                // constrain each new textView below its parent
                val layoutParams: ConstraintLayout.LayoutParams =
                    ConstraintLayout.LayoutParams(
                        ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                layoutParams.topToTop = recentReport.id
                layoutParams.startToStart = recentReport.id
                layoutParams.endToEnd = recentReport.id

                nextReport.layoutParams = layoutParams
                nextReport.setOnClickListener {
                    createChildFragment(currReport)
                }

                mainLayout.addView(nextReport)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createChildFragment(report: Report) {
        // TODO: figure out how to pass report file to fragment constructor
        val nestedReportsFragment: Fragment = ReportDetailsFragment()
        val bundle = Bundle()
        bundle.putSerializable("report", report)
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(
            me.smoothhacker.swampsploit.R.id.child_fragment_container,
            nestedReportsFragment
        ).commit()
    }
}

