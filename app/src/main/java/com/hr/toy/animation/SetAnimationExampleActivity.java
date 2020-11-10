package com.hr.toy.animation;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hr.toy.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 *
 */
public class SetAnimationExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha_animation_example);

        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setAnimation(alphaAnimation);
        alphaAnimation.start();
    }
}
