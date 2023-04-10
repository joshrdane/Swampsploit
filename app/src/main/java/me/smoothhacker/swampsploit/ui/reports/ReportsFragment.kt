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
import androidx.fragment.app.Fragment
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
        // if no files yet: display nothing w message
        if (fileList != null) {
            if (fileList.isEmpty()) {
                // TODO:  Display "no results yet"
            }
        } else {
            if (fileList != null) {
                // edit text on existing textView
                val report1: TextView = root.findViewById(me.smoothhacker.swampsploit.R.id.recentReport)
                // TODO: grab date/time from most recent report and fill below text with fileList[0]
                report1.setText("")
                if (fileList.size > 1) {
                    val mainLayout =
                        root.findViewById<LinearLayout>(me.smoothhacker.swampsploit.R.id.reports_dashboard)
                    // add additional reports if they exist, start with fileList[1]
                    for (i in 1 until fileList.size) {
                        val text = TextView(this.context)
                        // TODO: grab date/time from second most recent report and fill text
                        text.text = ""
                        text.textSize = 18f
                        text.gravity = Gravity.CENTER_VERTICAL
                        text.setBackgroundResource(me.smoothhacker.swampsploit.R.drawable.rounded_teal_textview)
                        text.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        // TODO: constrain each new textView below its parent
                        // TODO: set onclick for textview strings to switch to a fragment that displays the details
                        mainLayout.addView(text)
                    }
                }
            }
        }
        return root
        
        /* File("/home/arjun/tutorials/").walk().forEach {
            println(it)
        } // potentially how to get info from reports?
        */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

