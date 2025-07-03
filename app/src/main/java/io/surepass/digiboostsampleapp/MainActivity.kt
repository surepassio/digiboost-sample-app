package io.surepass.digiboostsampleapp

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import io.surepass.digiboostsampleapp.databinding.ActivityMainBinding
import io.surepass.digiboost.ui.activity.InitSdk

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var digiboostActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var response: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        registerActivityForResult()

        binding.btnGetStarted.setOnClickListener {
            val token = binding.etApiToken.text.toString()
            val env = "PROD"
            openActivity(env, token)
        }

        // to copy the entire response received from sdk
        binding.btnCopyButton.setOnClickListener {
            if (response.isNotEmpty()) {
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Response", response)
                clipboard.setPrimaryClip(clip)
                showToast("Response Copied...")
            }
        }
    }


    private fun openActivity(env: String, token: String) {
        val intent = Intent(this, InitSdk::class.java)
        intent.putExtra("token", token)
        intent.putExtra("env", env)
        digiboostActivityResultLauncher.launch(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun registerActivityForResult() {
        digiboostActivityResultLauncher =
            registerForActivityResult(
                ActivityResultContracts.StartActivityForResult(),
                ActivityResultCallback { result ->
                    val resultCode = result.resultCode
                    val data = result.data
                    if (resultCode == RESULT_OK && data != null) {
                        val digibootResponse = data.getStringExtra("signedResponse")
                        Log.e("MainActivity", "digiboost Response $digibootResponse")
                        showResponse(digibootResponse)
                    }
                })
    }

    private fun showResponse(digibootResponse: String?) {
        binding.etResponse.visibility = View.VISIBLE
        binding.btnCopyButton.visibility = View.VISIBLE
        binding.etResponse.setText(digibootResponse.toString())
        response = digibootResponse.toString()
    }
}