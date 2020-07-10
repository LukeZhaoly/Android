package com.zly.collectapp.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

import com.zly.collectapp.R;
import com.zly.collectapp.utils.AnimationUtils;
import com.zly.collectapp.viewmodel.TestViewModel;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivAnim,btnQuit;
    private Button btnStarAnim,btnStopAnim,btnFrame;
    private  AnimationDrawable drawable;
    private  AnimationUtils.FrameAnimation frame;
    private Handler handler=new Handler();

    private  Animation animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ivAnim=findViewById(R.id.iv_anim);
        btnQuit=findViewById(R.id.btn_quit);
        btnStarAnim=findViewById(R.id.btn_star_anim);
        btnStopAnim=findViewById(R.id.btn_stop_anim);
        btnFrame=findViewById(R.id.btn_frame);
        final TestViewModel viewModel = new ViewModelProvider(this).get(TestViewModel.class);
        Button btnTest=findViewById(R.id.btn_test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.add(5);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                 frame = AnimationUtils.getInstance(R.array.sleep_moon, 120, TestActivity.this).createFrame(ivAnim);
            }
        }).start();
        btnStopAnim.setOnClickListener(this);
        btnStarAnim.setOnClickListener(this);
        btnFrame.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        //ivAnim.setBackgroundResource(R.drawable.moon_gif);
        //drawable= (AnimationDrawable) ivAnim.getBackground();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_star_anim:
                animation = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.curation_anim);
                //animation.setRepeatMode(Animation.REVERSE);
                ivAnim.setBackgroundResource(R.drawable.smbh_00001);
                btnQuit.setVisibility(View.VISIBLE);
                ivAnim.setAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        frame.star();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                frame.stop();
//                            }
//                        },3000);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
//                drawable.start();
                break;
            case R.id.btn_quit:
                btnQuit.setVisibility(View.INVISIBLE);
                animation.reset();
                ivAnim.setBackground(null);
                ivAnim.setVisibility(View.INVISIBLE);
            case R.id.btn_stop_anim:
                frame.stop();
                break;
            case R.id.btn_frame:
                frame.star();
                break;
        }
    }
}