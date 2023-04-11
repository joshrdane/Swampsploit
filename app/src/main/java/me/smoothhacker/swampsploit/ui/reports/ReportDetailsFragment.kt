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
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.databinding.FragmentReportDetailsBinding
import java.io.File


class ReportDetailsFragment : Fragment() {

    private var _binding: FragmentReportDetailsBinding? = null

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

        _binding = FragmentReportDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // TODO: display report details
        // TODO: implement "share" functionality to send report

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

