package com.example.pmdm_04;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pmdm_04.databinding.ActivityMain1Binding;

public class Main1 extends AppCompatActivity
{
    private ActivityMain1Binding binding;

    ViewPager viewPager;
    AdaptadorViewPager adaptadorViewPager;
    Animation fadeInOut;

    private int gallery[] = {R.drawable.malagueta, R.drawable.paseorey, R.drawable.chimenea, R.drawable.rioverde, R.drawable.ronda};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        binding = ActivityMain1Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        startAnimation();
    }

    private void startAnimation()
    {
        binding.buttonStart.setOnClickListener(v ->
        {
            binding.buttonStart.setEnabled(false);
            fadeInOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_inout);
            binding.textViewAnimated.setVisibility(View.VISIBLE);
            binding.textViewAnimated.startAnimation(fadeInOut);

            //Terminar la animación tras 3 segundos.
            new Handler().postDelayed(() ->
            {
                binding.textViewAnimated.clearAnimation();
                showViewPager();
            },
                    4000);
        });
    }
        private void showViewPager()
        {
            binding.textViewAnimated.setVisibility(View.INVISIBLE);
            binding.buttonStart.setVisibility(View.INVISIBLE);

            viewPager = binding.viewPagger;

            adaptadorViewPager = new AdaptadorViewPager(Main1.this, gallery);
            viewPager.setAdapter(adaptadorViewPager);

            binding.editTextValoracion.setVisibility(View.VISIBLE);
            binding.textViewValoracion.setVisibility(View.VISIBLE);

            ViewPager.OnPageChangeListener player = new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageSelected(int arg1)
                {
                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.swipe);
                    mp.start();
                    binding.editTextValoracion.setText("");
                }
                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2)
                {
                }
                @Override
                public void onPageScrollStateChanged(int arg0)
                {
                }
            };
            viewPager.addOnPageChangeListener(player);
        }
    }