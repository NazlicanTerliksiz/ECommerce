package com.nazlican.ecommerce.ui.signUp

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSignUpBinding
import com.nazlican.sisterslabproject.common.viewBinding

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initializeFirebase()

        binding.apply {
            signUpButton.setOnClickListener {

                val email = binding.signUpEmail2.text.toString()
                val password = binding.signUpPassword2.text.toString()

                if (checkFields(email, password)) {
                    viewModel.signUpToFirebase(email, password, {
                        findNavController().popBackStack()
                    }, { errorMessage ->
                        Snackbar.make(requireView(), errorMessage, 2000).show()
                    })
                }
            }
        }
    }
    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.signUpEmail2.error = "mail can't be empty"
                binding.signUpEmail2.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.signUpEmail2.error = "valid email required"
                binding.signUpEmail2.requestFocus()
                false
            }
            password.isEmpty() ->  {
                binding.signUpPassword2.error = "password can't be empty"
                binding.signUpPassword2.requestFocus()
                false
            }
            password.length<6 -> {
                binding.signUpPassword2.error = "6 char password required"
                binding.signUpPassword2.requestFocus()
                false
            }
            else -> true
        }
    }
}