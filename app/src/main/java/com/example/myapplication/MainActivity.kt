package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.di.ApiService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var btnPickImage: Button
    private lateinit var imageViewOriginal: ImageView
    private lateinit var imageViewResponse: ImageView

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedImageUri ->
                val bitmap = decodeUri(selectedImageUri)
                imageViewOriginal.setImageBitmap(bitmap)

                // Convert Bitmap to File
                val file = createTempImageFile()
                val outputStream = file.outputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                outputStream.close()

                // Upload image
                uploadImage(file)
            }
        }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPickImage = findViewById(R.id.pick)
        imageViewOriginal = findViewById(R.id.sourceimage)
        imageViewResponse = findViewById(R.id.responseimage)

        btnPickImage.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast.makeText(
                this,
                "Permission to access gallery is required.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun decodeUri(uri: Uri): Bitmap? {
        val inputStream = contentResolver.openInputStream(uri)
        return inputStream?.use {
            BitmapFactory.decodeStream(it)
        }
    }

    private fun createTempImageFile(): File {
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile("temp_image", ".jpg", storageDir)
    }

    private fun uploadImage(file: File) {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

        lifecycleScope.launch {
            try {
                val response = apiService.uploadImage(body)

                //  val responseBodyString: String = response.string()

                // Log.i("ExceptionOnRes", "uploadImage: $responseBodyString")
                Toast.makeText(this@MainActivity, "response", Toast.LENGTH_SHORT).show()

                if (response.isSuccessful) {

                    val apiResponse = response.body()
                    Log.i("Response", "uploadImage: ${apiResponse?.msg}")

                    apiResponse?.data?.imageBase64?.let { imageBase64 ->
                        Log.i("Response", "uploadImage64: $imageBase64")
                        Toast.makeText(this@MainActivity, "Base 64", Toast.LENGTH_SHORT)
                            .show()
                        val imageData =
                            android.util.Base64.decode(imageBase64, android.util.Base64.DEFAULT)
                        val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
                        imageViewResponse.setImageBitmap(bitmap)

                    }

                    if (apiResponse?.data?.imageBase == null) {
                        Toast.makeText(this@MainActivity, apiResponse?.msg, Toast.LENGTH_SHORT)
                            .show()


                    }


                } else {
                    Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()

                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle failure
                Log.e("ExceptionOnRes", "uploadImage: ${e.message}")
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

