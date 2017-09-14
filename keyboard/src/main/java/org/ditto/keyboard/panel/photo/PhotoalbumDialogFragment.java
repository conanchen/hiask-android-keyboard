package org.ditto.keyboard.panel.photo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;

import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

import static com.google.common.base.Preconditions.checkNotNull;
// ...

public class PhotoalbumDialogFragment extends DialogFragment implements PhotoalbumController.AdapterCallbacks {
    private static final Gson gson = new Gson();
    private static final String TAG = "PhotoviewDialogFragment";
    private static final int SPAN_COUNT = 3;

    @BindView(R2.id.button_send)
    Button button_send;

    @BindView(R2.id.itemlist)
    RecyclerView recyclerView;

    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final PhotoalbumController controller = new PhotoalbumController(this, recycledViewPool);

    PhotoFragment parentPhotoFragment = null;

    public PhotoalbumDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PhotoalbumDialogFragment newInstance(String title) {
        checkNotNull(title);
        PhotoalbumDialogFragment frag = new PhotoalbumDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_view, container);
        ButterKnife.bind(this, view);
        // Many carousels and color models are shown on screen at once. The default recycled view
        // pool size is only 5, so we manually set the pool size to avoid constantly creating new views
        // We also use a shared view pool so that carousels can recycle items between themselves.
        // recycledViewPool.setMaxRecycledViews(R.layout.model_color, Integer.MAX_VALUE);
        recycledViewPool.setMaxRecycledViews(R.layout.photo_item_model, Integer.MAX_VALUE);
        recyclerView.setRecycledViewPool(recycledViewPool);

        // We are using a multi span grid to allow two columns of buttons. To set this up we need
        // to set our span count on the controller and then get the span size lookup object from
        // the controller. This look up object will delegate span size lookups to each model.
        controller.setSpanCount(SPAN_COUNT);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), SPAN_COUNT);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        gridLayoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new SampleItemAnimator());


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title + " Added title");

        String path = getArguments().getString("imagePath", "");

        setupController();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
        recyclerView.setAdapter(controller.getAdapter());
        recyclerView.scrollToPosition(parentPhotoFragment.currentVisitPhotoPosition);

    }

    private void setupController() {
        Log.e(TAG, "setupController");
        parentPhotoFragment = (PhotoFragment) getTargetFragment();
        parentPhotoFragment.viewModel.getLiveContentQueryPhotos().observe(parentPhotoFragment, photos -> {
            if (photos != null && photos.size() > 0) {
                parentPhotoFragment.mContentQueryPhotos = photos;

                parentPhotoFragment.viewModel.getLiveDatabasePhotos().observe(parentPhotoFragment, dbphotos -> {
                    if (dbphotos != null && dbphotos.size() > 0) {
                        parentPhotoFragment.mDatabasePhotos = dbphotos;
                    }else{
                        parentPhotoFragment.mDatabasePhotos = new ArrayList<>(0);
                    }
                    controller.setData(parentPhotoFragment.mContentQueryPhotos, parentPhotoFragment.mDatabasePhotos);
                });

            }
        });

        parentPhotoFragment.viewModel.getLiveCountPhotoSelected().observe(parentPhotoFragment, count -> {
            button_send.setText(String.format("发送(%d)图片", count));
            button_send.setEnabled(count > 0);
        });

    }


    @Override
    public void onStop() {
        super.onStop();
        recyclerView.setAdapter(null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (subscriber != null && !subscriber.isDisposed()) {
            subscriber.dispose();
        }
    }

    class MySubscriber extends DisposableSubscriber<List<Photo>> {
        @Override
        public void onStart() {
            request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(List<Photo> photos) {
            for (Photo photo:photos) {
                Log.e(TAG, "commitPhoto=" + gson.toJson(photo));
                parentPhotoFragment.getCommitKeyboard().commitPhoto(photo);
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            this.dispose();
        }
    }

    MySubscriber subscriber = new MySubscriber();


    @OnClick(R2.id.button_send)
    public void onButtonSendClicked(Button button) {
        subscriber = new MySubscriber();
        parentPhotoFragment.viewModel.listFlowableSelectedPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        this.dismiss();
    }

    @Override
    public void onPhotoSelected(Photo carousel, int position) {
        if (subscriber != null && !subscriber.isDisposed()) {
            subscriber.dispose();
            subscriber = null;
        }
        parentPhotoFragment.viewModel.toggleSelect(carousel).observe(parentPhotoFragment, aLong -> {
            if (carousel.selectIdx > 0) {
                parentPhotoFragment.mContentQueryPhotos.get(position).selectIdx = 0;
            } else {
                parentPhotoFragment.mContentQueryPhotos.get(position).selectIdx = 1;
            }
            controller.setData(parentPhotoFragment.mContentQueryPhotos, parentPhotoFragment.mDatabasePhotos);
        });
    }


    @Override
    public void onPhotoClicked(Photo carousel, int position) {
       this.onPhotoSelected(carousel,position);
    }
}