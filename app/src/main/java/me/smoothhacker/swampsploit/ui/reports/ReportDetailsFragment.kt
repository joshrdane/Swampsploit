package me.smoothhacker.swampsploit.ui.reports

import android.Manifest
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.smoothhacker.swampsploit.databinding.FragmentReportDetailsBinding
import java.io.File


class ReportDetailsFragment : Fragment() {

    private var _binding: FragmentReportDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val reportFileName: String = Environment.getExternalStorageDirectory().path + File.separator + "name.pdf"


    private var activityResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { result ->
            var allAreGranted = true
            for(b in result.values) {
                allAreGranted = allAreGranted && b
            }

            if(allAreGranted) {
                // not sure if 2 below lines are needed -- will see when testing
                val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
            }
        }

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

        // Set & ask for permissions to share report externally
        val appPerms = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
        )

        val shareButton = root.findViewById<FloatingActionButton>(me.smoothhacker.swampsploit.R.id.fab)
        shareButton.setOnClickListener {
            // TODO: do this in separate thread
            Toast.makeText(this.context, "Saving report...", Toast.LENGTH_LONG).show()
            activityResultLauncher.launch(appPerms)

            val reportFile: File = File(reportFileName)

            val intentShare: Intent = Intent(Intent.ACTION_SEND)
            intentShare.type = "application/pdf"
            intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://$reportFile"))
            startActivity(Intent.createChooser(intentShare, "Sharing the report..."))
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

