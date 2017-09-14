package org.ditto.keyboard.dbroom.photo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Strings;

/**
 * Created by admin on 2017/6/2.
 */
@Entity
public class Photo implements Parcelable {
    @PrimaryKey
    public long id;
    public String path;
    public String name;
    public int width;
    public int height;
    public long created;
    public String bucketName;
    public String miniThumbMagic;
    public int selectIdx;

//    long id = mCursor.getLong(mCursor.getColumnIndex(MediaStore.Images.Media._ID));
//    // 获取图片的路径
//    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
//    String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
//    String bucketName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
//    String miniThumbMagic = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.MINI_THUMB_MAGIC));
//    int width = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH));
//    int height = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT));
    //MediaStore.Images.Media.DATE_TAKEN



    public Photo() {
    }

    private Photo(long id, String path, String name, int width, int height, long created, String bucketName, String miniThumbMagic,int selectIdx) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.width = width;
        this.height = height;
        this.created = created;
        this.bucketName = bucketName;
        this.miniThumbMagic = miniThumbMagic;
        this.selectIdx = selectIdx;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private long id;
        private String path;
        private String name;
        private int width;
        private int height;
        private long created;
        private String bucketName;
        private String miniThumbMagic;
        private int selectIdx;

        Builder() {
        }

        public Photo build() {
            String missing = "";

            if (Strings.isNullOrEmpty(path)) {
                missing += " path";
            }

            if (Strings.isNullOrEmpty(name)) {
                missing += " name";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Photo(  id,   path,   name,   width,   height,   created,   bucketName,   miniThumbMagic,selectIdx) ;
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setCreated(long created) {
            this.created = created;
            return this;
        }

        public Builder setBucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public Builder setMiniThumbMagic(String miniThumbMagic) {
            this.miniThumbMagic = miniThumbMagic;
            return this;
        }

        public Builder setSelectIdx(int selectIdx) {
            this.selectIdx = selectIdx;
            return this;
        }
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Ignore
    protected Photo(Parcel in) {

    }

}
