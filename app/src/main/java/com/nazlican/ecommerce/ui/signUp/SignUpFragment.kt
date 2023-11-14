package com.nazlican.ecommerce.ui.signUp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.nazlican.ecommerce.R
import com.nazlican.ecommerce.databinding.FragmentSignUpBinding
import com.nazlican.ecommerce.util.extensions.gone
import com.nazlican.ecommerce.util.extensions.snackbar
import com.nazlican.ecommerce.util.extensions.visible
import com.nazlican.sisterslabproject.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel: SignUpViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUp()
        signUpObserve()

        binding.signUpToSignInTv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun signUp() {
        binding.signUpButton.setOnClickListener {
            with(binding) {
                viewModel.registerToFirebase(
                    signUpEmail2.text.toString(),
                    signUpPassword2.text.toString()
                )
            }
        }
    }

    private fun signUpObserve() = with(binding) {
        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            when (state) {
                RegisterState.Loading -> signUpProgressBar.visible()

                RegisterState.RegisterSuccessState -> {
                    signUpProgressBar.gone()
                    findNavController().popBackStack()
                }

                RegisterState.AddUserInfoSuccessState -> {
                    signUpProgressBar.gone()

                }

                is RegisterState.ShowPopUp -> {
                    view?.snackbar(state.errorMessage)
                }
            }
        }
    }
}