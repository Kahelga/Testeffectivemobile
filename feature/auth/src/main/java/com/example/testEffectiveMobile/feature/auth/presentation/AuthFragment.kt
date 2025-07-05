package com.example.testEffectiveMobile.feature.auth.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.testEffectiveMobile.feature.auth.R
import com.example.testEffectiveMobile.feature.auth.databinding.FragmentAuthBinding
import com.example.testEffectiveMobile.util.validation.InputValidator
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val authViewModel: AuthViewModel by viewModel()

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private var navigation: AuthNavigation? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAuthBinding.bind(view)

        setupInputValidation()

        lifecycleScope.launchWhenStarted {
            authViewModel.state.collect { state ->
                when(state) {
                    is AuthState.Initial -> {
                        binding.emailTextField.text?.clear()
                        binding.passTextField.text?.clear()

                        binding.emailTextField.error = null
                        binding.passTextField.error = null
                    }
                    is AuthState.Success -> {
                        Toast.makeText(requireContext(), "Статус: ${state.response.success}", Toast.LENGTH_SHORT).show()
                        onAuthSuccess()
                    }
                    is AuthState.Failure -> {
                        Toast.makeText(requireContext(), "Ошибка: ${state.message}", Toast.LENGTH_SHORT).show()
                    }
                    is AuthState.LoggedOut -> {
                        // состояние выхода
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener {
            val login = binding.emailTextField.text.toString()
            val password = binding.passTextField.text.toString()
            authViewModel.authorize(login, password)
        }

        binding.vkButton.setOnClickListener {
            openUrl("https://vk.com/")
        }
        binding.okButton.setOnClickListener {
            openUrl("https://ok.ru/")
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
         try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Нет приложения для открытия ссылки", Toast.LENGTH_SHORT).show()
        }

    }
    private fun setupInputValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.emailTextField.addTextChangedListener(textWatcher)
        binding.passTextField.addTextChangedListener(textWatcher)

        binding.loginButton.isEnabled = false
    }
    private fun validateInputs() {
        val email = binding.emailTextField.text.toString()
        val password = binding.passTextField.text.toString()

        binding.loginButton.isEnabled = InputValidator.areInputsValid(email, password)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AuthNavigation) {
            navigation = context
        } else {
            throw IllegalStateException("Activity must implement AuthNavigation")
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    fun onAuthSuccess() {
        navigation?.openHomeScreen() ?: Log.e("AuthFragment", "Navigation is null!")
    }
}