package me.smoothhacker.swampsploit.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.smoothhacker.swampsploit.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textView4
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Set Dashboard text
        val librarySizeTextView: Button = binding.LibrarySizeTextView
        homeViewModel.librarySize.observe(viewLifecycleOwner) {
           librarySizeTextView.text = it
        }

        val payloadSizeTextview: Button = binding.PayloadSizeTextview
        homeViewModel.payloadSize.observe(viewLifecycleOwner) {
            payloadSizeTextview.text = it
        }

        val registeredTargetsTextView: Button = binding.RegisteredTargetsTextView
        homeViewModel.registeredTargets.observe(viewLifecycleOwner) {
            registeredTargetsTextView.text = it
        }

        val pastAttemptsTextView: Button = binding.PastAttemptsTextView
        homeViewModel.pastAttempts.observe(viewLifecycleOwner) {
            pastAttemptsTextView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}