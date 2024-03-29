package me.smoothhacker.swampsploit.ui.reports

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.smoothhacker.swampsploit.databinding.FragmentNetatalkReportDetailsBinding
import me.smoothhacker.swampsploit.utils.Report
import java.io.File


class NetatalkReportDetailsFragment : Fragment() {

    private var _binding: FragmentNetatalkReportDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val reportFileName: String =
        Environment.getExternalStorageDirectory().path + File.separator + "name.pdf"
    private lateinit var report: Report


    private var activityResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            var allAreGranted = true
            for (b in result.values) {
                allAreGranted = allAreGranted && b
            }

            if (allAreGranted) {
                // not sure if 2 below lines are needed -- will see when testing
                val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
            }
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get report from bundle
        val bundle = this.requireArguments()
        this.report = bundle.getSerializable("report") as Report
        _binding = FragmentNetatalkReportDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val reportContents: TextView =
            root.findViewById(me.smoothhacker.swampsploit.R.id.report_contents)

        reportContents.text = String.format(
            "Selected exploit: %s\nSelected payload: %s\nTime completed: %s\nExecution status: %s",
            this.report.getSelectedExploit().toString(),
            this.report.getSelectedPayload().toString(),
            this.report.getTimestamp().toString(),
            this.report.getWasSuccess().toString(),
        )

        val reportLog: TextView =
            root.findViewById(me.smoothhacker.swampsploit.R.id.report_log)

        reportLog.text = String.format("Execution log:\n")

        for (i in 0 until this.report.getLogSize()) {
            val currLogDisplay = reportLog.text.toString()
            val currentLog = currLogDisplay + this.report.getLog(i)
            reportLog.text = String.format("%s\n", currentLog)
        }

        // Set & ask for permissions to share report externally
        val appPerms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )

        val shareButton =
            _binding!!.root.findViewById<FloatingActionButton>(me.smoothhacker.swampsploit.R.id.fab)
        shareButton.setOnClickListener {
            Thread {
                this.activity?.runOnUiThread {
                    Toast.makeText(
                        this.context,
                        "Saving report...",
                        Toast.LENGTH_LONG
                    ).show()
                }
                activityResultLauncher.launch(appPerms)

                val reportFile = File(reportFileName)

                val intentShare = Intent(Intent.ACTION_SEND)
                intentShare.type = "application/pdf"
                intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$reportFile"))
                startActivity(Intent.createChooser(intentShare, "Sharing the report..."))
            }.start()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onBackPressed() {
        // Catch back action and pops from backstack
        if (requireActivity().supportFragmentManager.backStackEntryCount > 0){
            requireActivity().supportFragmentManager.popBackStack()
        }
        else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}

