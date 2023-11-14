package com.nazlican.ecommerce.ui.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSignInBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.snackbar
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeAuth()
        signIn()
        signInObserve()

        binding.signInToSignUpTv.setOnClickListener {
            findNavController().navigate(R.id.signInToSignUp)
        }
    }

    private fun initializeAuth() {
        auth = FirebaseAuth.getInstance()
        auth.currentUser?.let {
            findNavController().navigate(R.id.signInToMainGraph)
        }
    }

    private fun signIn() {
        with(binding) {
            binding.signInButton.setOnClickListener {
                viewModel.loginToFirebase(
                    signInEmail2.text.toString(),
                    signInPassword2.text.toString()
                )
            }
        }
    }

    private fun signInObserve() = with(binding) {
        viewModel.logInState.observe(viewLifecycleOwner) { state ->
            when (state) {
                LogInState.Loading -> signInProgressBar.visible()

                LogInState.LoginSuccessState -> {
                    signInProgressBar.gone()
                    findNavController().navigate(R.id.signInToMainGraph)
                }

                is LogInState.ShowPopUp -> {
                    view?.snackbar(state.errorMessage)
                }
            }
        }
    }
}