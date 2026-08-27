package io.surepass.digiboostsampleapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import io.surepass.digiboostsampleapp.databinding.ActivityMainBinding
import io.surepass.digilocker.ui.activity.Environment
import io.surepass.digilocker.ui.activity.InitSdk

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sdkLauncher: ActivityResultLauncher<Intent>
    private var response: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registerActivityForResult()

        binding.btnGetStarted.setOnClickListener {
            val token = binding.etApiToken.text.toString().trim()
            openSdk(Environment.PROD.value, token)
        }

        // to copy the entire response received from the SDK
        binding.btnCopyButton.setOnClickListener {
            if (response.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("Response", response))
                showToast("Response Copied...")
            }
        }
    }

    private fun openSdk(env: String, token: String) {
        val intent = Intent(this, InitSdk::class.java)
        intent.putExtra(InitSdk.EXTRA_TOKEN, token)
        intent.putExtra(InitSdk.EXTRA_ENV, env)
        sdkLauncher.launch(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun registerActivityForResult() {
        sdkLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null) {
                val signedResponse = data.getStringExtra(InitSdk.EXTRA_SIGNED_RESPONSE)
                Log.d("MainActivity", "Digilocker response: $signedResponse")
                showResponse(signedResponse)
            }
        }
    }

    private fun showResponse(signedResponse: String?) {
        binding.etResponse.visibility = View.VISIBLE
        binding.btnCopyButton.visibility = View.VISIBLE
        binding.etResponse.setText(signedResponse.orEmpty())
        response = signedResponse.orEmpty()
    }
}
