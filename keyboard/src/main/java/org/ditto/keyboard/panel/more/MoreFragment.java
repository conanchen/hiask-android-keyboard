package org.ditto.keyboard.panel.more;

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
import com.google.gson.Gson;

import org.ditto.keyboard.CommitEditText;
import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.R;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.more.di.MoreViewModelFactory;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class MoreFragment extends KeyboardPanelbaseFragment implements MoreController.AdapterCallbacks {
    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            Timber.d("ON_CREATE  calling setupController controller.getCurrentData() =" + controller.getCurrentData() );
            KeyboardInjector.getKeyboardComponent().inject(MoreFragment.this);
            viewModel = ViewModelProviders.of(MoreFragment.this, viewModelFactory).get(MoreViewModel.class);
            setupController();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            Timber.d("ON_START calling setupController controller.getCurrentData().size()=" + controller.getCurrentData().size());
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            Timber.d("ON_RESUME calling setupController controller.getCurrentData().size()=" + controller.getCurrentData().size());
            recyclerView.setAdapter(controller.getAdapter());
            viewModel.refresh();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {

            Timber.d("ON_PAUSE calling setupController controller.getCurrentData().size()=" + controller.getCurrentData().size());
            recyclerView.setAdapter(null);
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop() {
            Timber.d("ON_STOP calling setupController controller.getCurrentData().size()=" + controller.getCurrentData().size());
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        public void onDestroy() {

            Timber.d("ON_DESTROY calling setupController controller.getCurrentData().size()=" + controller.getCurrentData().size());
            getLifecycle().removeObserver(this);
        }

        private void setupController() {
            int tabXmlResource = MoreFragment.this.getCommitKeyboard().getTabXmlResource();
            Timber.d("calling setupController tabXmlResource=" + tabXmlResource);
            if (tabXmlResource != 0) {
                TabParser parser = new TabParser(getContext(), tabXmlResource);
                List<Action> actions = parser.parseTabs();
                controller.setData(actions);
            }
        }
    }


    private final static String TAG = "MoreFragment";
    private final static Gson gson = new Gson();

    @Inject
    MoreViewModelFactory viewModelFactory;
    private MoreViewModel viewModel;

    private static final int SPAN_COUNT = 3;

    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final MoreController controller = new MoreController(this, recycledViewPool);

    private RecyclerView recyclerView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoreFragment() {

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
    public void onActionClicked(Action carousel, int colorPosition) {
        checkNotNull(carousel);
        super.getCommitKeyboard().commitAction(carousel);
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

        public MoreFragment build() {
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
            MoreFragment fragment = new MoreFragment();
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
