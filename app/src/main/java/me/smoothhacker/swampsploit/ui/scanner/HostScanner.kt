package me.smoothhacker.swampsploit.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import me.smoothhacker.swampsploit.auxilary.TcpScanner
import me.smoothhacker.swampsploit.databinding.FragmentHostScannerBinding
import me.smoothhacker.swampsploit.utils.ExploitContext
import java.io.File

class HostScanner : Fragment() {
    private var _binding: FragmentHostScannerBinding? = null
    private val binding get() = _binding!!
    private lateinit var tcpScanner: TcpScanner
    private var hostsListFile: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHostScannerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        this.tcpScanner = TcpScanner(context?.dataDir!!.path.toString())

        val scanButtonView = binding.button
        scanButtonView.setOnClickListener {
            if (tcpScanner.scanForHosts()) {
                this.activity?.runOnUiThread {
                    Toast.makeText(
                        this.context,
                        "Scanner Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        hostsListFile = File(context?.dataDir, "hosts_list.dat")
        val ctx = ExploitContext(context?.dataDir!!.path.toString())
        ctx.create()
        if (hostsListFile!!.exists())
            hostsListFile!!.forEachLine { tcpScanner.addHost(it); ctx.addHost(it); }
        ctx.save()

        binding.buttonSetPort.setOnClickListener {
            tcpScanner.setTargetPort(binding.editTextPort.text.toString().toInt())
            binding.editTextPort.text.clear()
        }

        binding.buttonSetIP.setOnClickListener {
            tcpScanner.addHost(binding.editTextIpAddr.text.toString())
            binding.editTextIpAddr.text.clear()
            tcpScanner.save()
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        hostsListFile = null
    }

}