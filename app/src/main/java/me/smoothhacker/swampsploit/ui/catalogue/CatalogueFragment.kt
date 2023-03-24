package me.smoothhacker.swampsploit.ui.catalogue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.databinding.FragmentExploitGalleryBinding
import me.smoothhacker.swampsploit.ui.exploit.ExploitActivity
import me.smoothhacker.swampsploit.ui.exploit.NetatalkActivity

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
        val catalogueViewModel =
            ViewModelProvider(this)[CatalogueViewModel::class.java]

        _binding = FragmentExploitGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.ProFTPDButton.setOnClickListener {
            startActivity(Intent(this.context, ExploitActivity::class.java))
        }

        binding.NetatalkButton.setOnClickListener {
            startActivity(Intent(this.context, NetatalkActivity::class.java))
        }

        /*val textView: TextView = binding.exploitText
        catalogueViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}