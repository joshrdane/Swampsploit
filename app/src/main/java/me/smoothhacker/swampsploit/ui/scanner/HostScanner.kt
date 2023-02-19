package me.smoothhacker.swampsploit.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.aux.tcpScanner
import me.smoothhacker.swampsploit.databinding.FragmentHostScannerBinding
import java.io.File

class HostScanner : Fragment() {

    private var _binding: FragmentHostScannerBinding? = null
    private val binding get() = _binding!!
    private val tcpScanner: tcpScanner = tcpScanner()
    private var hostsListFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scannerViewModel = ViewModelProvider(this)[HostScannerViewModel::class.java]

        _binding = FragmentHostScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val scanButtonView = binding.button
        scanButtonView.setOnClickListener { tcpScanner.scanForHosts() }

        hostsListFile = File(context?.dataDir, "hosts_list.dat")
        if (hostsListFile!!.exists())
            hostsListFile!!.forEachLine { tcpScanner.addHost(it) }

        binding.addTargetSubmit.setOnClickListener {
            val ipAddrText = binding.editTextIpAddrTarget.text
            tcpScanner.addHost(ipAddrText.toString())
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        hostsListFile = null
    }

}