package com.example.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todolist.databinding.ActivitySplashBinding
import com.example.todolist.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar!!.hide()

        setupSplash()
    }

    private fun setupSplash() = with(binding) {
        tvAuthor.alpha = 0f
        tvAuthor.animate().setDuration(1200).alpha(1f)

        ivLogoDio.alpha = 0f
        ivLogoDio.animate().setDuration(1000).alpha(1f)

        ivLogoNtt.alpha = 0f
        ivLogoNtt.animate().setDuration(1000).alpha(1f)

        tvSplash.alpha = 0f
        tvSplash.animate().setDuration(1200).alpha(1f).withEndAction{
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

}
