package com.github.zhira.githubgraphqlapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.zhira.githubgraphqlapp.R
import android.content.Intent
import android.view.animation.DecelerateInterpolator
import android.animation.ObjectAnimator
import android.os.Handler
import android.widget.ProgressBar
import android.view.animation.AnimationUtils
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.zhira.githubgraphqlapp.MainActivity


class SplashScreen : AppCompatActivity() {

    @BindView(R.id.splashLoadingBar) lateinit var progressBar: ProgressBar
    @BindView(R.id.splashImage) lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        ButterKnife.bind(this)
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation)
        imageView.setAnimation(animation)

        val animobj = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
        animation.duration = 4000
        animation.interpolator = DecelerateInterpolator()
        animobj.start()

        val handler = Handler()
        handler.postDelayed(Runnable {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
            return@Runnable
        }, 3000)
    }
}
