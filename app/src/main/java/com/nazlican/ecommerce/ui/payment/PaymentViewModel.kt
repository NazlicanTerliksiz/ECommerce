package com.nazlican.ecommerce.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PaymentViewModel : ViewModel() {

    private val _paymentState: MutableLiveData<PaymentState> = MutableLiveData()
    val paymentState: LiveData<PaymentState> get() = _paymentState

    fun checkFields(
        name: String,
        carNumber: String,
        mont: String,
        year: String,
        cvv: String,
        adress: String
    ): Any {
        return when {
            name.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("name can't be empty")
                false
            }

            carNumber.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("car number can't be empty")
                false
            }

            carNumber.length < 16 -> {
                _paymentState.value = PaymentState.ShowPopUp("car number 16 char required")
                false
            }

            mont.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("mont can't be empty")
                false
            }

            year.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("year can't be empty")
                false
            }

            cvv.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("cvv can't be empty")
                false
            }

            adress.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("adress can't be empty")
                false
            }

            else -> {
                _paymentState.value = PaymentState.PaymentSuccessState
            }
        }
    }

}

sealed interface PaymentState {
    object PaymentSuccessState : PaymentState
    data class ShowPopUp(val errorMessage: String) : PaymentState
}