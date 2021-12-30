package com.example.validation_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.example.validation_form.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

  // lateinit - для случаев, когда переменная не сразу инициализируется
  // binding - привязка представления
  // ActivityMainBinding - связывает activity_main.xml
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // inflate - создает экземпляр класса привязки
    // layoutInflater - автоматически присоединяет созданную иерархию компонентов к корневосу элементу
    binding = ActivityMainBinding.inflate(layoutInflater)
    // binding.root - получает ссылку на корневое представление
    setContentView(binding.root)
    emailFocusListener()
    passwordFocusListener()
    phoneFocusListener()

    binding.submitButton.setOnClickListener { submitForm() }
  }

  // todo: submit form
  private fun submitForm() {
    // helperText - возвращает вспомогательное сообщение
    binding.emailContainer.helperText = validEmail()
    binding.passwordContainer.helperText = validPassword()
    binding.phoneContainer.helperText = validPhone()

    val validEmail = binding.emailContainer.helperText == null
    val validPassword = binding.passwordContainer.helperText == null
    val validPhone = binding.phoneContainer.helperText == null

    if (validEmail && validPassword && validPhone) {
      resetForm()
    } else {
      invalidForm()
    }
  }

  // todo: invalid form
  private fun invalidForm() {
    var message = ""

    if (binding.emailContainer.helperText != null) {
      message += "\n\nEmail: ${binding.emailContainer.helperText}"
    }

    if (binding.passwordContainer.helperText != null) {
      message += "\n\nPassword: ${binding.passwordContainer.helperText}"
    }

    if (binding.phoneContainer.helperText != null) {
      message += "\n\nPhone: ${binding.phoneContainer.helperText}"
    }

    // alertDialog - диалоговое окно
    AlertDialog.Builder(this).setTitle("Invalid Form").setMessage(message)
      .setPositiveButton("Okay") { _, _ ->
        // do nothing
      }.show()
  }

  // todo: reset form
  private fun resetForm() {
    var message = "Email: ${binding.emailEditText.text}"
    message += "\nPassword: ${binding.passwordEditText.text}"
    message += "\nPhone: ${binding.phoneEditText.text}"

    AlertDialog.Builder(this).setTitle("Form Submitted").setMessage(message)
      .setPositiveButton("Okay") { _, _ ->
        binding.emailEditText.text = null
        binding.passwordEditText.text = null
        binding.phoneEditText.text = null

        binding.emailContainer.helperText = getString(R.string.required)
        binding.passwordContainer.helperText = getString(R.string.required)
        binding.phoneContainer.helperText = getString(R.string.required)
      }.show()
  }

  // todo: email listener
  private fun emailFocusListener() {
    binding.emailEditText.setOnFocusChangeListener { _, focused ->
      if (!focused) {
        binding.emailContainer.helperText = validEmail()
      }
    }
  }

  // todo: validation email
  private fun validEmail(): String? {

    val emailText = binding.emailEditText.text.toString()

    if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
      return "Invalid Email Address"
    }

    return null
  }

  // todo: password listener
  private fun passwordFocusListener() {
    binding.passwordEditText.setOnFocusChangeListener { _, focused ->
      if (!focused) {
        binding.passwordContainer.helperText = validPassword()
      }
    }
  }

  // todo: password validation
  private fun validPassword(): String? {

    val passwordText = binding.passwordEditText.text.toString()

    if (passwordText.length < 8) {
      return "Minimum 8 Characters Password"
    }

    if (!passwordText.matches(".*[A-Z].*".toRegex())) {
      return "Must Contain 1 Upper-case Character"
    }

    if (!passwordText.matches(".*[a-z].*".toRegex())) {
      return "Must Contain 1 Lower-case Character"
    }

    if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
      return "Must Contain 1 Special Character: @#\$%^&+="
    }

    return null
  }

  // todo: phone number listener
  private fun phoneFocusListener() {
    binding.phoneEditText.setOnFocusChangeListener { _, focused ->
      if (!focused) {
        binding.phoneContainer.helperText = validPhone()
      }
    }
  }

  // todo: phone number validation
  private fun validPhone(): String? {

    val phoneText = binding.phoneEditText.text.toString()

    if (phoneText.length != 11) {
      return "Must be 11 Digits"
    }

    if (!phoneText.matches(".*[0-9].*".toRegex())) {
      return "Must be all Digits"
    }

    return null
  }
}