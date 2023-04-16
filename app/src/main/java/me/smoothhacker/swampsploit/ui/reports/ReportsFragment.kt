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
import androidx.fragment.app.FragmentManager
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
        val mainLayout = root.findViewById<ConstraintLayout>(
            me.smoothhacker.swampsploit.R.id.reports_dashboard
        )

        val recentReport: TextView =
            root.findViewById(me.smoothhacker.swampsploit.R.id.recentReport)

        if (reports.getSize() == 0) {
            recentReport.text = me.smoothhacker.swampsploit.R.string.reports_empty.toString()
        } else {
            recentReport.text = String.format(
            "%s %s", reports.getReport(0).getTimestamp().toString(), reports.getReport(0).getReportText()
            )
            recentReport.setOnClickListener {
                // launch new fragment to display report details
                createChildFragment(reports.getReport(0))
            }

            for (i in 1 until reports.getSize()) {
                val currReport: Report = reports.getReport(i)
                val currReportTextView = TextView(this.context)
                currReportTextView.text = String.format(
                    "%s %s", currReport.getTimestamp().toString(), currReport.getReportText()
                )
                currReportTextView.textSize = 16f
                currReportTextView.gravity = Gravity.CENTER_VERTICAL
                currReportTextView.setBackgroundResource(
                    me.smoothhacker.swampsploit.R.drawable.rounded_teal_textview
                )
                currReportTextView.layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
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

                currReportTextView.layoutParams = layoutParams
                currReportTextView.setOnClickListener {
                    createChildFragment(currReport)
                }

                mainLayout.addView(currReportTextView)
                currReportTextView.setOnClickListener {
                    // launch new fragment to display report details
                    createChildFragment(currReport)
                }
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun createChildFragment(report: Report) {
        val bundle = Bundle()
        bundle.putSerializable("report", report)

        val childFrag = ReportDetailsFragment()
        val childFragMan: FragmentManager = childFragmentManager
        val childFragTrans: FragmentTransaction = childFragMan.beginTransaction()

        childFragTrans.replace(me.smoothhacker.swampsploit.R.id.child_fragment_container, childFrag)
        childFragTrans.addToBackStack(null)
        childFragTrans.commit()
    }
}

