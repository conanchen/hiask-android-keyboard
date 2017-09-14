package org.ditto.inappkeyboard;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;


public class MainFragmentActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        setupViewPager();
    }

    private void setupViewPager() {

        TextView tipsView = (TextView) findViewById(R.id.tips);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        List<Fragment> fmList = new ArrayList<>();
        fmList.add(new BlankFragment());
        fmList.add(new BlankFragment());
        fmList.add(new BlankFragment());
        fmList.add(new BlankFragment1());
        fmList.add(new BlankFragment2());

        FragmentsPagerAdapter fmAapter = new FragmentsPagerAdapter(this.getSupportFragmentManager(), fmList);

        viewPager.setAdapter(fmAapter);
        viewPager.setCurrentItem(0, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tipsView.setText("以下是viewpager中Fragment " + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}