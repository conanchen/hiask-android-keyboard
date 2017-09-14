package org.ditto.keyboard.panel.photo;

import android.support.v7.widget.RecyclerView.RecycledViewPool;

import com.airbnb.epoxy.Typed2EpoxyController;

import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.panel.photo.epoxymodels.ItemAlbumPhotoModel_;
import org.ditto.keyboard.panel.photo.epoxymodels.ItemPhotoModel_;

import java.util.List;

public class PhotoalbumController extends Typed2EpoxyController<List<Photo>, List<Photo>> {
    private final static String TAG = PhotoalbumController.class.getSimpleName();

    public interface AdapterCallbacks {

        void onPhotoSelected(Photo carousel, int position);

        void onPhotoClicked(Photo carousel, int position);
    }

    private final AdapterCallbacks callbacks;
    private final RecycledViewPool recycledViewPool;

    public PhotoalbumController(AdapterCallbacks callbacks, RecycledViewPool recycledViewPool) {
        this.callbacks = callbacks;
        this.recycledViewPool = recycledViewPool;
        setDebugLoggingEnabled(true);
    }

    @Override
    public void setData(List<Photo> contentQueryPhotos, List<Photo> databaseSelectedPhotos) {
        super.setData(contentQueryPhotos, databaseSelectedPhotos);
    }

    @Override
    protected void buildModels(List<Photo> photos, List<Photo> dbphotos) {
        if (photos != null) {
            for (Photo photo : photos) {
                Photo dbphoto = findInDatabasePhotos(dbphotos, photo.id);
                int selectIdx = dbphoto != null ? dbphoto.selectIdx : 0;
                add(new ItemAlbumPhotoModel_()
                        .id(photo.id)
                        .path(photo.path)
                        .name(photo.name)
                        .width(photo.width)
                        .height(photo.height)
                        .selectIdx(selectIdx)
                        .selectListener((model, parentView, clickedView, position) -> {
                            callbacks.onPhotoSelected(photo, position);
                        })
                        .clickListener((model, parentView, clickedView, position) -> {
                            // A model click listener is used instead of a normal click listener so that we can get
                            // the current position of the view. Since the view may have been moved when the colors
                            // were shuffled we can't rely on the position of the model when it was added here to
                            // be correct, since the model won't have been rebound when shuffled.
                            callbacks.onPhotoClicked(photo, position);
                        }));
            }
        }

    }

    private Photo findInDatabasePhotos(List<Photo> dbphotos, long id) {
        for (Photo p : dbphotos) {
            if (p.id == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    protected void onExceptionSwallowed(RuntimeException exception) {
        // Best practice is to throw in debug so you are aware of any issues that Epoxy notices.
        // Otherwise Epoxy does its best to swallow these exceptions and continue gracefully
        throw exception;
    }
}
