package org.ditto.keyboard.repository;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import org.ditto.keyboard.dbroom.photo.Photo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by admin on 2017/7/28.
 */

public class PhotoContentQuery {
    private final Context mContext;

    public PhotoContentQuery(Context context) {
        this.mContext = context;
    }

    public LiveData<List<Photo>> listPhotosBy(int pageSize, int pageIndex) {
        final String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC limit " + pageSize + " offset " + pageIndex * pageIndex;

        return new LiveData<List<Photo>>() {
            {
                final Cursor _cursor = mContext.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{
                                MediaStore.Images.Media._ID,
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                                MediaStore.Images.Media.SIZE,
                                MediaStore.Images.Media.WIDTH,
                                MediaStore.Images.Media.HEIGHT,
                                MediaStore.Images.Media.DATE_TAKEN,
                                MediaStore.Images.Media.MINI_THUMB_MAGIC
                        },
                        null,
                        null,
                        sortOrder);

                try {
                    final int _cursorIndexOfID = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
                    final int _cursorIndexOfDATA = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
                    final int _cursorIndexOfWIDTH = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH);
                    final int _cursorIndexOfHEIGHT = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT);
                    final int _cursorIndexOfCreated = _cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
                    final List<Photo> _result = new ArrayList<Photo>(_cursor.getCount());
                    while (_cursor.moveToNext()) {
                        _result.add(Photo.builder()
                                .setId(_cursor.getLong(_cursorIndexOfID))
                                .setPath(_cursor.getString(_cursorIndexOfDATA))
                                .setName(_cursor.getString(_cursorIndexOfName))
                                .setWidth(_cursor.getInt(_cursorIndexOfWIDTH))
                                .setHeight(_cursor.getInt(_cursorIndexOfHEIGHT))
                                .setCreated(_cursor.getLong(_cursorIndexOfCreated))
                                .setSelectIdx(0)
                                .build());
                    }
                    postValue(_result);
                } finally {
                    _cursor.close();
                }
            }

        };
    }

    public static Query imagesQuery(int pageSize, int pageIndex) {
        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " DESC limit " + pageSize + " offset " + pageIndex * pageIndex;

        return new Query.Builder()
                .setContentUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                .setProjection(new String[]{
                        MediaStore.Images.Media._ID,
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                        MediaStore.Images.Media.SIZE,
                        MediaStore.Images.Media.WIDTH,
                        MediaStore.Images.Media.HEIGHT,
                        MediaStore.Images.Media.MINI_THUMB_MAGIC
                })
//            .setSelection(MediaStore.Images.Media.MIME_TYPE)
//            .setSelectionArgs(new String[]{"image/jpeg", "image/png"})
                .setSortOrder(sortOrder)
                .create();


    }



    public static final class Query implements Parcelable {

        Uri contentUri;
        String[] projection;
        String selection;
        String[] selectionArgs;
        String sortOrder;

        Query() {

        }

        Query(@NonNull final Parcel p) {
            contentUri = p.readParcelable(Uri.class.getClassLoader());
            projection = p.createStringArray();
            selection = p.readString();
            selectionArgs = p.createStringArray();
            sortOrder = p.readString();
        }

        @Override
        public void writeToParcel(final Parcel p, final int i) {
            p.writeParcelable(contentUri, 0);
            p.writeStringArray(projection);
            p.writeString(selection);
            p.writeStringArray(selectionArgs);
            p.writeString(sortOrder);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        // Generated by Android Studio
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final Query query = (Query) o;

            if (contentUri != null ? !contentUri.equals(query.contentUri)
                    : query.contentUri != null) {
                return false;
            }
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(projection, query.projection)) {
                return false;
            }
            if (selection != null ? !selection.equals(query.selection) : query.selection != null) {
                return false;
            }
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            //noinspection SimplifiableIfStatement
            if (!Arrays.equals(selectionArgs, query.selectionArgs)) {
                return false;
            }
            return sortOrder != null ? sortOrder.equals(query.sortOrder) : query.sortOrder == null;

        }

        // Generated by Android Studio
        @Override
        public int hashCode() {
            int result = contentUri != null ? contentUri.hashCode() : 0;
            result = 31 * result + Arrays.hashCode(projection);
            result = 31 * result + (selection != null ? selection.hashCode() : 0);
            result = 31 * result + Arrays.hashCode(selectionArgs);
            result = 31 * result + (sortOrder != null ? sortOrder.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "mContentUri=" + contentUri +
                    ", mProjection=" + Arrays.toString(projection) +
                    ", mSelection='" + selection + '\'' +
                    ", mSelectionArgs=" + Arrays.toString(selectionArgs) +
                    ", mSortOrder='" + sortOrder + '\'' +
                    '}';
        }

        public static final Creator<Query> CREATOR = new Creator<Query>() {

            @Override
            public Query createFromParcel(final Parcel parcel) {
                return new Query(parcel);
            }

            @Override
            public Query[] newArray(final int size) {
                return new Query[size];
            }
        };

        /**
         * {@link Query} builder.
         * <p>
         * The only required parameter is a content URI.
         */
        public static final class Builder {

            private Uri mContentUri;
            private String[] mProjection;
            private String mSelection;
            private String[] mSelectionArgs;
            private String mSortOrder;

            public Builder() {

            }

            @NonNull
            public Builder setContentUri(@NonNull final Uri contentUri) {
                mContentUri = contentUri;
                return this;
            }

            @NonNull
            public Builder setProjection(final String[] projection) {
                mProjection = projection;
                return this;
            }

            @NonNull
            public Builder setSelection(final String selection) {
                mSelection = selection;
                return this;
            }

            @NonNull
            public Builder setSelectionArgs(final String[] selectionArgs) {
                mSelectionArgs = selectionArgs;
                return this;
            }

            @NonNull
            public Builder setSortOrder(final String sortOrder) {
                mSortOrder = sortOrder;
                return this;
            }

            /**
             * Creates the {@link Query}
             *
             * @return the {@link Query}
             * @throws IllegalStateException if content uri is null
             */
            @NonNull
            public Query create() {
                if (mContentUri == null) {
                    throw new IllegalStateException("Content URI not set");
                }
                final Query query = new Query();
                query.contentUri = mContentUri;
                query.projection = mProjection;
                query.selection = mSelection;
                query.selectionArgs = mSelectionArgs;
                query.sortOrder = mSortOrder;
                return query;
            }
        }
    }

}
