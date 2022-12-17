package uz.vianet.androidkeystore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var et_cipher: EditText
    lateinit var cryptoManager: CryptoManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        cryptoManager = CryptoManager()
        et_cipher = findViewById(R.id.et_cipher)

        val btn_encrypt = findViewById<Button>(R.id.btn_encrypt)
        val btn_decrypt = findViewById<Button>(R.id.btn_decrypt)

        btn_encrypt.setOnClickListener { doEncrypt() }
        btn_decrypt.setOnClickListener { doDecrypt() }
    }

    private fun doDecrypt() {
        et_cipher.text.clear()
        val file = File(filesDir,"my_keys.txt")
        val decryptedText = cryptoManager.decrypt(FileInputStream(file)).decodeToString()
        et_cipher.setText(decryptedText)
    }

    private fun doEncrypt() {
        val cipher:String = et_cipher.text.toString()
        val bytes:ByteArray = cipher.toByteArray(charset("UTF-8"))
        val file = File(filesDir,"my_keys.txt")
        if (!file.exists()){
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        val cryptedText = cryptoManager.encrypt(bytes,fos).decodeToString()
        et_cipher.setText(cryptedText)
    }
}