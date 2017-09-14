package org.ditto.keyboard.dbroom.vo;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Strings;

public class Video implements Parcelable {
    public String path;
    public String name;
    public int width;
    public int height;
    public long created;

    public Video() {
    }

    private Video(String path, String name, int width, int height, long created) {
        this.path = path;
        this.name = name;
        this.width = width;
        this.height = height;
        this.created = created;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String path;
        private String name;
        private int width;
        private int height;
        private long created;

        Builder() {
        }

        public Video build() {
            String missing = "";

            if (Strings.isNullOrEmpty(path)) {
                missing += " [path]";
            }

            if (Strings.isNullOrEmpty(name)) {
                missing += " [name]";
            }

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Video(  path,   name,   width,   height,   created) ;
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
    }



    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
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
    protected Video(Parcel in) {
    }

}
