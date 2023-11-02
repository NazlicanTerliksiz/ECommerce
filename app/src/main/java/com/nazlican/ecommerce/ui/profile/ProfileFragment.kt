package com.nazlican.ecommerce.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentProfileBinding
import com.nazlican.sisterslabproject.common.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private lateinit var db:FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()
        listenUsers()

        binding.logOut.setOnClickListener {
            findNavController().setGraph(R.navigation.auth_nav_graph)
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
        }
    }

    private fun listenUsers(){
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("users").document("${uid}").get().addOnSuccessListener {
            if(it!=null){
                val userEmail = it.get("email") as String
                binding.EmailInfoText.text = userEmail
            }
        }
    }
}