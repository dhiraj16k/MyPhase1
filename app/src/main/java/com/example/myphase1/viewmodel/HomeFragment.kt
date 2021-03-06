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

            var isLogged = true
            for (i in 0 until arrayusers.size)
            {
                var jsonObj = arrayusers.get(index = i)
                if (jsonObj.getString("UserId") == etName.text.toString() &&
                    jsonObj.getString("Password") == etPassword.text.toString()) {

                        isLogged = false
                    val contact = jsonObj.getString("Contact").toString()
                    val mail = jsonObj.getString("Mail").toString()
                    val name = jsonObj.getString("Name").toString()

                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                            "Welcome ${etName.text}",
                            "$mail", "$contact", "$name"
                        )
                    )
                    break
                }
            }
            if (isLogged){
                Toast.makeText(requireContext(),"Please Enter Valid Credentials!!", Toast.LENGTH_SHORT).show()
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