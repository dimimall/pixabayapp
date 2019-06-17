package com.example.pixabayapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pixabayapp.Fragments.Favorities;
import com.example.pixabayapp.Fragments.SaveFragment;
import com.example.pixabayapp.Fragments.Wallpaper;
import com.example.pixabayapp.Models.Images;
import com.example.pixabayapp.R;
import com.squareup.picasso.Picasso;

public class HomeScreenLockActivity extends AppCompatActivity implements SaveFragment.OnFragmentInteractionListener, Favorities.OnFragmentInteractionListener, Wallpaper.OnFragmentInteractionListener{
    private ImageView imageView;

    private ActionBar toolbar;

    private Images images;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Set Wallpaper");
                    fragment =  Wallpaper.newInstance(images.getLarge_url());
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setTitle("Save");
                    fragment = SaveFragment.newInstance(images.getLarge_url(),images.getId());
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Favorities");
                    fragment = Favorities.newInstance(images.getLarge_url(),images.getId());
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_lock);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        imageView = findViewById(R.id.message);

        toolbar = getSupportActionBar();

        Intent intent = getIntent();

        images = (Images) intent.getSerializableExtra("images");

        Picasso.with(HomeScreenLockActivity.this).load(images.getLarge_url()).placeholder(R.drawable.ic_home_black_24dp).resize(500,800).into(imageView);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
