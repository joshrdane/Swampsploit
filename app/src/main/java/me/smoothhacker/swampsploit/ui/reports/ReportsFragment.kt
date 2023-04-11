package me.smoothhacker.swampsploit.ui.reports

import android.R
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
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.databinding.FragmentReportsBinding
import java.io.File


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
        val reportsViewModel =
            ViewModelProvider(this)[ReportsViewModel::class.java]

        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // get reports files from Downloads folder
        val downloadFolder = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        val fileList: Array<out File>? = downloadFolder?.listFiles()
        val recentReport: TextView = root.findViewById(me.smoothhacker.swampsploit.R.id.recentReport)

        if (fileList!!.isEmpty()) {
            recentReport.text = me.smoothhacker.swampsploit.R.string.reports_empty.toString()
        }
        else {
            if (fileList != null) {
                // TODO: grab date/time from most recent report and fill below text with fileList[0]
                recentReport.text = ""
                recentReport.setOnClickListener {
                    // launch new fragment to display report details
                   createChildFragment()
                }
                if (fileList.size > 1) {

                    for (i in 1 until fileList.size) {
                        val mainLayout =
                            root.findViewById<LinearLayout>(me.smoothhacker.swampsploit.R.id.reports_dashboard)
                        // add additional reports if they exist, start with fileList[1]

                        val nextReport = TextView(this.context)
                        // TODO: grab date/time from second most recent report and fill text
                        nextReport.text = ""
                        nextReport.textSize = 16f
                        nextReport.gravity = Gravity.CENTER_VERTICAL
                        nextReport.setBackgroundResource(me.smoothhacker.swampsploit.R.drawable.rounded_teal_textview)
                        nextReport.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )

                        // constrain each new textView below its parent
                        val layoutParams: ConstraintLayout.LayoutParams =
                            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                                ConstraintLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams.topToTop = recentReport.id
                        layoutParams.startToStart = recentReport.id
                        layoutParams.endToEnd = recentReport.id

                        nextReport.layoutParams = layoutParams
                        nextReport.setOnClickListener {
                            createChildFragment()
                        }

                        mainLayout.addView(nextReport)
                    }
                }
            }
        }
        return root

        // potentially how to get info from reports?
        /* File("/home/arjun/tutorials/").walk().forEach {
            println(it)
        }
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun createChildFragment() {
        // TODO: figure out how to pass report file to fragment constructor
        val nestedReportsFragment: Fragment = ReportDetailsFragment()
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.add(me.smoothhacker.swampsploit.R.id.child_fragment_container, nestedReportsFragment).commit()
    }
}

