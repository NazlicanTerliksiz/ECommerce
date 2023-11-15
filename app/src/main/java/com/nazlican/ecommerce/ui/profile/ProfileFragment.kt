package com.nazlican.ecommerce.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentProfileBinding
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)

    private val viewModel: ProfileFragmentViewModel by viewModels()

    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = FirebaseFirestore.getInstance()

        binding.logOut.setOnClickListener {
            viewModel.logOut()
            findNavController().navigate(R.id.auth_nav_graph)
        }
        observeData()
    }


    private fun observeData() = with(binding) {
        viewModel.profileState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileState.SuccessState -> {
                    binding.emailInfoText.text =  state.user.email
                    binding.nameInfoText.text =  state.user.name
                    binding.surnameInfoText.text =  state.user.surname
                }
            }
        }
    }
}