package com.nazlican.ecommerce.ui.signIn

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSignInBinding
import com.nazlican.sisterslabproject.common.viewBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

       auth.currentUser?.let {
            findNavController().navigate(R.id.action_signInFragment2_to_main_nav_graph)
        }


        binding.apply {
            signInButton.setOnClickListener {

                val email = binding.signInEmail2.text.toString()
                val password = binding.signInPassword2.text.toString()

                if (checkFields(email, password)) {
                    viewModel.loginToFirebase(email, password, {
                        findNavController().navigate(R.id.action_signInFragment2_to_main_nav_graph)
                    }, { errorMessage ->
                        Snackbar.make(requireView(), errorMessage, 2000).show()
                    })
                }
            }
            signInText.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment2_to_signUpFragment2)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.signInEmail2.error = "mail can't be empty"
                binding.signInEmail2.requestFocus()
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.signInEmail2.error = "valid email required"
                binding.signInEmail2.requestFocus()
                false
            }
            password.isEmpty() ->  {
                binding.signInPassword2.error = "password can't be empty"
                binding.signInPassword2.requestFocus()
                false
            }
            password.length<6 -> {
                binding.signInPassword2.error = "6 char password required"
                binding.signInPassword2.requestFocus()
                false
            }
            else -> true
        }
    }
}