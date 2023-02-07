package me.smoothhacker.swampsploit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BootScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boot_screen)

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}