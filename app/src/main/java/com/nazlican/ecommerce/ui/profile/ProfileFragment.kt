package com.nazlican.ecommerce.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentProfileBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileFragmentViewModel by viewModels()

    private lateinit var db:FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchProductObserve()

        binding.logOut.setOnClickListener {
            findNavController().setGraph(R.navigation.auth_nav_graph)
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
        }
    }

    private fun searchProductObserve() = with(binding) {
        viewModel.storeState.observe(viewLifecycleOwner) { state ->
            when (state) {

                StoreState.Loading -> progressBar.visible()

                is StoreState.AddUserInfoSuccessState -> {
                    progressBar.gone()
                    val uid = FirebaseAuth.getInstance().currentUser!!.uid
                    db.collection("users").document("${uid}").get().addOnSuccessListener {
                        if(it!=null){
                            val userEmail = it.get("email") as String
                            binding.EmailInfoText.text = userEmail
                            viewModel.addUserInfo(userEmail)
                        }
                    }
                }
                is StoreState.FailState -> {
                    Snackbar.make(requireView(), state.failMessage, 2000).show()
                }

                is StoreState.ErrorState -> {
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}