package org.ditto.keyboard.panel.emoji;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.R;
import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.emoji.di.EmojiViewModelFactory;
import org.ditto.keyboard.util.Constants;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class EmojiFragment extends KeyboardPanelbaseFragment implements EmojiController.AdapterCallbacks {
    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            KeyboardInjector.getKeyboardComponent().inject(EmojiFragment.this);
            viewModel = ViewModelProviders.of(EmojiFragment.this, viewModelFactory).get(EmojiViewModel.class);
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
            Timber.d("calling setupController");

            Map<String, Object> datas = new HashMap<>();
            datas.put(Constants.DATA1, new ArrayList<Emoji>());

            viewModel.getLiveEmojisAndStatus().observe(EmojiFragment.this, dataNstatus -> {
                if (dataNstatus.first != null) {
                    Log.e(TAG, "has data dataNstatus.first.size()=" + dataNstatus.first.size());
                    controller.setData(dataNstatus.first);
                }
                if (dataNstatus.second != null) {
                    Log.e(TAG, "error dataNstatus.second.name()=" + dataNstatus.second.code);
                }
            });
        }


    }

    private final static String TAG = "EmojiFragment";
    private static final int SPAN_COUNT = 7;

    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final EmojiController controller = new EmojiController(this, recycledViewPool);

    private RecyclerView recyclerView;


    @Inject
    EmojiViewModelFactory viewModelFactory;
    private EmojiViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EmojiFragment() {

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
    public void onEmojiClicked(Emoji carousel, int colorPosition) {
        super.getCommitKeyboard().upsertText(carousel.codepointu16,1);
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

        public EmojiFragment build() {
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
            EmojiFragment fragment = new EmojiFragment();
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
