package me.smoothhacker.swampsploit.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.smoothhacker.swampsploit.databinding.FragmentExploitGalleryBinding
import me.smoothhacker.swampsploit.ui.exploit.ExploitActivity

class CatalogueFragment : Fragment() {

    private var _binding: FragmentExploitGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploitGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.ProFTPDButton.setOnClickListener {
            val proftpdIntent = Intent(this.context,  ExploitActivity::class.java)
            proftpdIntent.putExtra("selectedExploit", "Proftpd")
            startActivity(proftpdIntent)
        }

        binding.NetatalkButton.setOnClickListener {
            val netatalkIntent = Intent(this.context,  ExploitActivity::class.java)
            netatalkIntent.putExtra("selectedExploit", "Netatalk")
            startActivity(netatalkIntent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}