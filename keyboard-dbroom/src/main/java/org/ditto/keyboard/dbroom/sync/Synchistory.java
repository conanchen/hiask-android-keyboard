package org.ditto.keyboard.dbroom.sync;

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
public class Synchistory implements Parcelable {
    @PrimaryKey
    public String entityClassName;

    public long lastUpdated;

    public Synchistory() {
    }


    private Synchistory(String entityClassName, long lastUpdated) {
        this.entityClassName = entityClassName;
        this.lastUpdated = lastUpdated;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String entityClassName;

        private long lastUpdated;

        Builder() {
        }

        public Synchistory build() {
            String missing = "";

            if (Strings.isNullOrEmpty(entityClassName)) {
                missing += " entityClassName";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Synchistory(entityClassName, lastUpdated);
        }

        public Builder setEntityClassName(String entityClassName) {
            this.entityClassName = entityClassName;
            return this;
        }

        public Builder setLastUpdated(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }
    }

    public static final Creator<Synchistory> CREATOR = new Creator<Synchistory>() {
        @Override
        public Synchistory createFromParcel(Parcel in) {
            return new Synchistory(in);
        }

        @Override
        public Synchistory[] newArray(int size) {
            return new Synchistory[size];
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
    protected Synchistory(Parcel in) {

    }

}
