package com.example.myphase1.viewmodel

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myphase1.R
import com.example.myphase1.databinding.FragmentHomeBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    var arrayusers = arrayListOf<JSONObject>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etName = binding.etName
        val etPassword = binding.etPassword
        readUsersList(this.requireContext())

        binding.loginButton.setOnClickListener{

            for (i in 0 until arrayusers.size)
            {
                var jsonObj = arrayusers.get(index = i)
                if (jsonObj.getString("userName") == etName.text.toString() &&
                    jsonObj.getString("password") == etPassword.text.toString()) {
                    val contact = jsonObj.getString("contact").toString()
                    val mail = jsonObj.getString("mail").toString()
                    val name = jsonObj.getString("name").toString()
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                            "Welcome ${etName.text}",
                            "$mail", "$contact", "$name"
                        )
                    )
                    Toast.makeText(requireContext(), "User Logged In!!", Toast.LENGTH_SHORT)
                        .show()
                    break

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun readUsersList(context: Context) {

        var json: String?

        try {

            val inputStream: InputStream = context.assets.open("users.json")
            json = inputStream.bufferedReader().use { it.readText() }

            var jsonarr = JSONArray(json)

            for (i in 0 until jsonarr.length())
            {
                var jsonObj = jsonarr.getJSONObject(i)
                arrayusers.add(jsonObj)
            }
        }catch (e: IOException){

        }
    }
}