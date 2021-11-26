package com.example.myphase1.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.myphase1.R
import com.example.myphase1.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding : FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val userdata: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val userid: String = userdata.userid
        val mail: String = userdata.mail
        val contact: String = userdata.contact
        val name: String = userdata.name

        var nameText = binding.nameText
        var mailText = binding.mailText
        var contactText = binding.contactText
        var userId = binding.userId

        userId.text = userid
        mailText.text = mail
        contactText.text = contact
        nameText.text = name
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}