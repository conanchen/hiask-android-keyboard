package org.ditto.keyboard.panel.gift;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.R;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.gif.GifFragment;
import org.ditto.keyboard.panel.gift.di.GiftViewModelFactory;
import org.ditto.keyboard.util.Constants;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class GiftFragment extends KeyboardPanelbaseFragment implements GiftController.AdapterCallbacks {
    private final static String TAG = "GiftFragment";
    private final static Gson gson = new Gson();

    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            KeyboardInjector.getKeyboardComponent().inject(GiftFragment.this);
            viewModel = ViewModelProviders.of(GiftFragment.this, viewModelFactory).get(GiftViewModel.class);
            setupController();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            recyclerView.setAdapter(controller.getAdapter());
            viewModel.refresh();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            recyclerView.setAdapter(null);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {

        }

        private void setupController() {
            Timber.d("calling setupController");

            viewModel.getLiveGifts().observe(GiftFragment.this, gifts -> {
                controller.setData(gifts);
            });
        }

    }

    @Inject
    GiftViewModelFactory viewModelFactory;
    private GiftViewModel viewModel;

    private static final int SPAN_COUNT = 4;

    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final GiftController controller = new GiftController(this, recycledViewPool);

    private RecyclerView recyclerView;

    private ThisLifecycleObserver mThisLifecycleObserver = new ThisLifecycleObserver();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GiftFragment() {
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);


        // Many carousels and color models are shown on screen at once. The default recycled view
        // pool size is only 5, so we manually set the pool size to avoid constantly creating new views
        // We also use a shared view pool so that carousels can recycle items between themselves.
//        recycledViewPool.setMaxRecycledViews(R.layout.model_color, Integer.MAX_VALUE);
//        recycledViewPool.setMaxRecycledViews(R.layout.model_carousel_group, Integer.MAX_VALUE);
        recyclerView.setRecycledViewPool(recycledViewPool);

        // We are using a multi span grid to allow two columns of buttons. To set this up we need
        // to set our span count on the controller and then get the span size lookup object from
        // the controller. This look up object will delegate span size lookups to each model.
        controller.setSpanCount(SPAN_COUNT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), SPAN_COUNT);
        gridLayoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new SampleItemAnimator());

        return recyclerView;
    }

    @Override
    public void onGiftClicked(Gift gift, int colorPosition) {
        checkNotNull(gift);

        super.getCommitKeyboard().commitGift(gift);
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

        public GiftFragment build() {
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
            GiftFragment fragment = new GiftFragment();
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
