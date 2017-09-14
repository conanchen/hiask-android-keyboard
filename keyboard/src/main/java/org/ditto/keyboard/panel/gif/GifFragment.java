package org.ditto.keyboard.panel.gif;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;

import org.ditto.keyboard.R;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.gif.di.GifViewModelFactory;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class GifFragment extends KeyboardPanelbaseFragment implements GifController.AdapterCallbacks {
    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            KeyboardInjector.getKeyboardComponent().inject(GifFragment.this);
            viewModel = ViewModelProviders.of(GifFragment.this, viewModelFactory).get(GifViewModel.class);
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
            getLifecycle().removeObserver(this);
        }

        private void setupController() {
            controller.setData(carousels);
        }

    }

    @Inject
    GifViewModelFactory viewModelFactory;
    private GifViewModel viewModel;


    private static final int SPAN_COUNT = 4;

    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final GifController controller = new GifController(this, recycledViewPool);
    private List<Gif> carousels = new ArrayList<Gif>() {
        {
            for (int i = 0; i < 20; i++)
                add(Gif.builder().setUuid("uuid" + i).setGroupUuid("groupId").setName("image" + i).setIcon(
                        i + "斗图棒棒糖")
                        .build());
        }
    };

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GifFragment() {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getLifecycle().addObserver(new ThisLifecycleObserver());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        Context context = recyclerView.getContext();

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
    public void onGifClicked(Gif carousel, int colorPosition) {
        getCommitKeyboard().commitGif(carousel);
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

        public GifFragment build() {
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
            GifFragment fragment = new GifFragment();
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
