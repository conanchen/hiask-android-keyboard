package org.ditto.keyboard.panel.gif;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.dbroom.gif.Gifgroup;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.gif.di.GifViewModelFactory;
import org.ditto.keyboard.util.KeyboardPagerAdapter;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class GifgroupFragment extends KeyboardPanelbaseFragment {
    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            KeyboardInjector.getKeyboardComponent().inject(GifgroupFragment.this);
            viewModel = ViewModelProviders.of(GifgroupFragment.this, viewModelFactory).get(GifViewModel.class);
            setupController();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            viewModel.refresh();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {

        }


        private void setupController() {
            Timber.d("calling setupController");

            viewModel.getLiveGifgroups().observe(GifgroupFragment.this, groups -> {
                if (groups != null) {
                    List<KeyboardPanelbaseFragment> fragments = new ArrayList<>();
                    for (Gifgroup group : groups) {
                        fragments.add(GifFragment
                                .builder()
                                .setTitle(group.name)
                                .setTagName(group.uuid)
                                .build());
                    }
                    KeyboardPagerAdapter fmAapter = new KeyboardPagerAdapter(GifgroupFragment.this.getCommitKeyboard(),GifgroupFragment.this.getChildFragmentManager(), fragments);
                    mViewPager.setAdapter(fmAapter);

                    //-----------setup TabLayout------------
                    mTabLayout.setupWithViewPager(mViewPager);
                    mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            tabSelected(tab);
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            tabSelected(tab);
                        }


                        private void tabSelected(TabLayout.Tab tab) {
                        }
                    });
                }
            });
        }
    }


    @Inject
    GifViewModelFactory viewModelFactory;
    private GifViewModel viewModel;

    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GifgroupFragment() {


    }


    private ThisLifecycleObserver mThisLifecycleObserver = new ThisLifecycleObserver();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.getLifecycle().addObserver(mThisLifecycleObserver);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.getLifecycle().removeObserver(mThisLifecycleObserver);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        if (childFragment instanceof KeyboardPanelbaseFragment) {
            KeyboardPanelbaseFragment fragment = (KeyboardPanelbaseFragment) childFragment;
            fragment.setCommitKeyboard(getCommitKeyboard());
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_panel_gif, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private int icon;
        private String tagName;

        Builder() {
        }

        public GifgroupFragment build() {
            String missing = "";
            if (Strings.isNullOrEmpty(title)) {
                missing += " title";
            }
            if (Strings.isNullOrEmpty(tagName)) {
                missing += " tagName";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            GifgroupFragment fragment = new GifgroupFragment();
            fragment.setTitle(title).setIcon(icon).setTagName(tagName);
            return fragment;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(int icon) {
            this.icon = icon;
            return this;
        }

        public Builder setTagName(String tagName) {
            this.tagName = tagName;
            return this;
        }

    }

}
