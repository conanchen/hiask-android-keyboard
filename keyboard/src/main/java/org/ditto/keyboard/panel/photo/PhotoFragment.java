package org.ditto.keyboard.panel.photo;

import android.Manifest;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.photo.di.PhotoViewModelFactory;
import org.ditto.keyboard.util.Constants;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class PhotoFragment extends KeyboardPanelbaseFragment implements PhotoController.AdapterCallbacks, EasyPermissions.PermissionCallbacks {

      int currentVisitPhotoPosition=0;

    class ThisLifecycleObserver implements LifecycleObserver {
        private final String TAG = ThisLifecycleObserver.class.getSimpleName();

        // 该注解表明我们要观测 onCreate() 事件
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate() {
            Log.e(TAG, "onCreate");
            KeyboardInjector.getKeyboardComponent().inject(PhotoFragment.this);
            viewModel = ViewModelProviders.of(PhotoFragment.this, viewModelFactory).get(PhotoViewModel.class);
            photoPermissionRequestTask();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            // LifecycleOwner 的 onStart() 被调用
            Log.e(TAG, "onStart");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
            Log.e(TAG, "onResume");
            recyclerView.setAdapter(controller.getAdapter());
            viewModel.refresh();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
            Log.e(TAG, "onPause");
            recyclerView.setAdapter(null);
        }


        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            // LifecycleOwner 的 onStop() 被调用
            Log.e(TAG, "onStop");
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        void onDestroy() {
            // LifecycleOwner 的 onDestroy() 被调用
            Log.e(TAG, "onDestroy");
            getLifecycle().removeObserver(this);
        }
    }

    private void setupController() {
        Log.e(TAG, "setupController");
        viewModel = ViewModelProviders.of(PhotoFragment.this, viewModelFactory).get(PhotoViewModel.class);
        viewModel.getLiveContentQueryPhotos().observe(PhotoFragment.this, photos -> {
            if (photos != null && photos.size() > 0) {
                mContentQueryPhotos = photos;

                viewModel.getLiveDatabasePhotos().observe(PhotoFragment.this, dbphotos -> {
                    if (dbphotos != null && dbphotos.size() > 0) {
                        mDatabasePhotos = dbphotos;
                    }else{
                        mDatabasePhotos = new ArrayList<>(0);
                    }
                    controller.setData(mContentQueryPhotos, mDatabasePhotos);
                });

            }
        });

        viewModel.getLiveCountPhotoSelected().observe(PhotoFragment.this, count -> {
            button_send.setText(String.format("发送(%d)图片", count));
            button_send.setEnabled(count > 0);
            button_clean.setEnabled(count > 0);
        });

    }


    private static final Gson gson = new Gson();
    private static final String TAG = "PhotoFragment";
    private static final int SPAN_COUNT = 1;
    private static final String IMAGE_TYPE = "*/*";
    private static final int IMAGE_REQUEST_CODE = 102;


    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final PhotoController controller = new PhotoController(this, recycledViewPool);


    List<Photo> mContentQueryPhotos = new ArrayList<>();
    List<Photo> mDatabasePhotos = new ArrayList<>();

    @Inject
    PhotoViewModelFactory viewModelFactory;
    PhotoViewModel viewModel;

    @BindView(R2.id.action_layout)
    LinearLayout actionLayout;

    @BindView(R2.id.button_send)
    Button button_send;

    @BindView(R2.id.button_clean)
    Button button_clean;

    @BindView(R2.id.itemlist)
    RecyclerView recyclerView;

    @Inject
    Context mContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PhotoFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getLifecycle().addObserver(new ThisLifecycleObserver());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_panel_photo, container, false);
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
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gridLayoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(false);
        recyclerView.setItemAnimator(new SampleItemAnimator());

        return view;
    }


    @OnClick(R2.id.button_album)
    public void onButtonAlbumClicked(Button button) {
        //show photoview
        FragmentManager fm = this.getFragmentManager();
        PhotoalbumDialogFragment photoalbumDialogFragment = PhotoalbumDialogFragment.newInstance("Some Title");

        photoalbumDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenActionBarStyle);
        // SETS the target fragment for use later when sending results
        photoalbumDialogFragment.setTargetFragment(this, 300);

        photoalbumDialogFragment.show(fm, "photo-album-dialog");
    }


    class MySubscriber extends DisposableSubscriber<List<Photo>> {
        @Override
        public void onStart() {
            request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(List<Photo> photos) {
            for (Photo photo:photos) {
                Log.e(TAG,"commitPhoto="+gson.toJson(photo));
                PhotoFragment.this.getCommitKeyboard().commitPhoto(photo);
            }
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            Log.e(TAG, "MySubscriber.onComplete()");
            this.dispose();
        }
    }

    MySubscriber subscriber = new MySubscriber();


    @OnClick(R2.id.button_send)
    public void onButtonSendClicked(Button button) {
        subscriber = new MySubscriber();
        viewModel.listFlowableSelectedPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @OnClick(R2.id.button_clean)
    public void onButtonCleanClicked(Button button){
        viewModel.cleanSelectedPhotos();
    }

    @Override
    public void onPhotoSelected(Photo carousel, int position) {
        if (subscriber != null && !subscriber.isDisposed()) {
            subscriber.dispose();
            subscriber = null;
        }
        viewModel.toggleSelect(carousel).observe(this, aLong -> {
            if (carousel.selectIdx > 0) {
                mContentQueryPhotos.get(position).selectIdx = 0;
            } else {
                mContentQueryPhotos.get(position).selectIdx = 1;
            }
            controller.setData(mContentQueryPhotos, mDatabasePhotos);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST_CODE) {
        }
    }


    @Override
    public void onPhotoClicked(org.ditto.keyboard.dbroom.photo.Photo carousel, int position) {
        this.currentVisitPhotoPosition = position;
        //show photoview
        FragmentManager fm = this.getFragmentManager();
        PhotoviewDialogFragment photoviewDialogFragment = PhotoviewDialogFragment.newInstance("Some Title");

        photoviewDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenActionBarStyle);
        // SETS the target fragment for use later when sending results
        photoviewDialogFragment.setTargetFragment(this, 300);

        photoviewDialogFragment.show(fm, "photo-view-dialog");

    }

    @AfterPermissionGranted(Constants.RC_READWRITE_EXTERNAL_STORAGE_PERM)
    public void photoPermissionRequestTask() {
        if (EasyPermissions.hasPermissions(this.getContext(), externalStoragePermission())) {
            setupController();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_external_storage),
                    Constants.RC_READWRITE_EXTERNAL_STORAGE_PERM, externalStoragePermission());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        setupController();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    private static String externalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            return Manifest.permission.READ_EXTERNAL_STORAGE;
        }
        return Manifest.permission.WRITE_EXTERNAL_STORAGE;
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

        public PhotoFragment build() {
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
            PhotoFragment fragment = new PhotoFragment();
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
