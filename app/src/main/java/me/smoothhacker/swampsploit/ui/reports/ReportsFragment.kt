package me.smoothhacker.swampsploit.ui.reports

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.databinding.FragmentReportsBinding

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

        val downloadFolder = requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)


        /*
        val textView: TextView = binding.textSlideshow
        reportsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
         */
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}