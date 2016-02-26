package com.mobintum.pueblosmagicos.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.fragments.ImagesTownFragment;
import com.mobintum.pueblosmagicos.fragments.ListMagicTownsFragment;
import com.mobintum.pueblosmagicos.fragments.ListNewsFragment;
import com.mobintum.pueblosmagicos.fragments.LocationFragment;
import com.mobintum.pueblosmagicos.fragments.NavigationFragment;
import com.mobintum.pueblosmagicos.models.MagicTown;


public class MainActivity extends AppCompatActivity implements ListMagicTownsFragment.Callbacks {

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if(savedInstanceState==null) {
            setContentView(R.layout.activity_main);
            fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, new ImagesTownFragment());
            ft.commit();
        //}
        /*}else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content, fm.findFragmentById(R.id.content));
            ft.commit();

        }*/

    }

    @Override
    public void onTownSelected(MagicTown magicTown) {
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content, LocationFragment.newInstance(magicTown.getName()+" "+magicTown.getState()));
        ft.addToBackStack(LocationFragment.TAG);
        ft.commit();
    }
}
