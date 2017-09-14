package org.ditto.keyboard.panel.frequent;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.common.base.Strings;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.di.KeyboardInjector;
import org.ditto.keyboard.panel.frequent.di.FrequentViewModelFactory;
import org.ditto.keyboard.util.Constants;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.SampleItemAnimator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;


/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class FrequentFragment extends KeyboardPanelbaseFragment implements FrequentController.AdapterCallbacks, EasyPermissions.PermissionCallbacks {
    private final static String TAG = "AudioFragment";


    @Inject
    FrequentViewModelFactory viewModelFactory;
    private FrequentViewModel viewModel;
    @Inject
    Context mContext;

    @BindView(R2.id.itemlist)
    RecyclerView recyclerView;


    private static final int SPAN_COUNT = 3;
    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private final FrequentController controller = new FrequentController(this, recycledViewPool);
    private List<Gift> carousels = new ArrayList<Gift>() {
        {
            for (int i = 0; i < 20; i++)
                add(Gift.builder().setUuid("uuid" + i).setGroupUuid("groupId").setName("image" + i).setIcon(
                        i + "斗图棒棒糖").setPrice(
                        i + 1).build());
        }
    };


    private static final String MIME_TYPE_GIF = "image/gif";
    private static final String MIME_TYPE_PNG = "image/png";
    private static final String MIME_TYPE_WEBP = "image/webp";

    private File mPngFile;
    private File mGifFile;
    private File mWebpFile;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FrequentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
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
//        GridLayoutManager  gridLayoutManager = new GridLayoutManager(this.getContext(),SPAN_COUNT);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
//        gridLayoutManager.setSpanSizeLookup(controller.getSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new VerticalGridCardSpacingDecoration());
        recyclerView.setItemAnimator(new SampleItemAnimator());
        recyclerView.setAdapter(controller.getAdapter());
    }


    @Override
    public void onTestKeyClick(View v) {

        Log.d(TAG, String.format("v.getId()=%d R.id.button_1=%d R2.id.button_1=%d", v.getId(), R.id.button_1, R2.id.button_1));
        // Delete text or input key value
        // All communication goes through the InputConnection
        if (v.getId() == R.id.button_delete) {
            super.getCommitKeyboard().deleteText();
        } else if (v.getId() == R.id.button_gif) {
            super.getCommitKeyboard().commitContent("A waving flag", MIME_TYPE_GIF, mGifFile);
        } else if (v.getId() == R.id.button_png) {
            super.getCommitKeyboard().commitContent("A droid logo", MIME_TYPE_PNG, mPngFile);
        } else if (v.getId() == R.id.button_webp) {
            super.getCommitKeyboard().commitContent("Android N recovery animation", MIME_TYPE_WEBP, mWebpFile);
        } else if (v.getId() == R.id.button_1) {
            super.getCommitKeyboard().upsertText("1", 1);
        } else if (v.getId() == R.id.button_2) {
            super.getCommitKeyboard().upsertText("2", 1);
        } else if (v.getId() == R.id.button_3) {
            super.getCommitKeyboard().upsertText("3", 1);
        } else if (v.getId() == R.id.button_enter) {
            super.getCommitKeyboard().upsertText("\n", 1);
        } else if (v.getId() == R.id.button_rest) {
            super.getCommitKeyboard().upsertText("button_rest", 1);
        } else if (v.getId() == R.id.button_restrx) {
            super.getCommitKeyboard().upsertText("button_restrx", 1);
        } else if (v.getId() == R.id.button_grpc) {
            viewModel.updateCurrentLocation(Pair.create(409146138, -746188906));
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        KeyboardInjector.getKeyboardComponent().inject(this);
        setupController();
    }

    @Override
    public void onResume() {
        super.onResume();
//        viewModel.refresh();
        networkTask();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }


    private void setupController() {
        Timber.d("calling setupController");
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(FrequentViewModel.class);
        controller.setData(carousels);

        viewModel.getGrpcfeature().observe(this, grpcfeature -> {
//            if (grpcfeature.first != null) {
//                CommitKeyboard.getInstance().upsertText(grpcfeature.first, 1);
//            } else {
//                CommitKeyboard.getInstance().upsertText(grpcfeature.second.getMessage(), 1);
//            }
        });

//        Map<String, Object> datas = new HashMap<>();
//        datas.put(Constants.DATA1, new ArrayList<IndexBuyanswer>());
//
//
//        viewModel.getLiveBuyanswerIndices().observe(this, buyanswers -> {
//            datas.put(Constants.DATA1, buyanswers);
//            controller.setData((ArrayList<IndexBuyanswer>) datas.get(Constants.DATA1));
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "viewModel.mUsecaseFascade.userUsecase.getHello()=" + viewModel.mUsecaseFascade.userUsecase.getHello());

        // TODO: Avoid file I/O in the main thread.
        Observable
                .just(true)
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(aBoolean -> {
                    final File imagesDir = new File(mContext.getFilesDir(), "images");
                    imagesDir.mkdirs();
                    mGifFile = getFileForResource(mContext, R.raw.animated_gif, imagesDir, "image.gif");
                    mPngFile = getFileForResource(mContext, R.raw.dessert_android, imagesDir, "image.png");
                    mWebpFile = getFileForResource(mContext, R.raw.animated_webp, imagesDir, "image.webp");
                    viewModel.mUsecaseFascade
                            .giftUsecase
                            .saveSampleGifts()
                            .observeOn(Schedulers.io())
                            .subscribe(longs -> {
                                        Log.d(TAG, "sucessfully saveSampleGifts()  gifts number=" + longs.size());
                                    },
                                    throwable -> {
                                        Log.e(TAG, throwable.getMessage());
                                    }
                            );
                    viewModel.mUsecaseFascade
                            .gifUsecase
                            .saveSampleGifs()
                            .observeOn(Schedulers.io())
                            .subscribe(longs -> {
                                        Log.d(TAG, "sucessfully saveSampleGifs()  gifs number=" + longs.size());
                                    },
                                    throwable -> {
                                        Log.e(TAG, throwable.getMessage());
                                    }
                            );

                });
    }

    private static File getFileForResource(
            @NonNull Context context, @RawRes int res, @NonNull File outputDir,
            @NonNull String filename) {
        final File outputFile = new File(outputDir, filename);
        final byte[] buffer = new byte[4096];
        InputStream resourceReader = null;
        try {
            try {
                resourceReader = context.getResources().openRawResource(res);
                OutputStream dataWriter = null;
                try {
                    dataWriter = new FileOutputStream(outputFile);
                    while (true) {
                        final int numRead = resourceReader.read(buffer);
                        if (numRead <= 0) {
                            break;
                        }
                        dataWriter.write(buffer, 0, numRead);
                    }
                    return outputFile;
                } finally {
                    if (dataWriter != null) {
                        dataWriter.flush();
                        dataWriter.close();
                    }
                }
            } finally {
                if (resourceReader != null) {
                    resourceReader.close();
                }
            }
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void onGiftClicked(Gift carousel, int colorPosition) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this.getContext(), R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @AfterPermissionGranted(Constants.RC_INTERNET_PERM)
    public void networkTask() {
        if (EasyPermissions.hasPermissions(this.getContext(), Manifest.permission.INTERNET)) {
            // Have permission, do the thing!
            Toast.makeText(this.getContext(), "TODO: network things", Toast.LENGTH_SHORT).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_internet),
                    Constants.RC_INTERNET_PERM, Manifest.permission.INTERNET);
        }
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

        public FrequentFragment build() {
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
            FrequentFragment fragment = new FrequentFragment();
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
