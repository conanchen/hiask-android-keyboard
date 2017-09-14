package org.ditto.keyboard.dbroom.photo;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Strings;

/**
 * Created by admin on 2017/6/2.
 */
public class Photogroup implements Parcelable {
    public String uuid;
    public String name;
    public int sequence;
    public long created;

    public Photogroup() {
    }

    private Photogroup(String uuid, String name, int sequence, long created) {
        this.uuid = uuid;
        this.name = name;
        this.sequence = sequence;
        this.created = created;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String uuid;
        private String name;
        private int sequence;
        private long created;

        Builder() {
        }

        public Photogroup build() {
            String missing = "";

            if (Strings.isNullOrEmpty(uuid)) {
                missing += " uuid";
            }


            if (Strings.isNullOrEmpty(name)) {
                missing += " name";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Photogroup(uuid, name, sequence, created);
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSequence(int sequence) {
            this.sequence = sequence;
            return this;
        }

        public Builder setCreated(long created) {
            this.created = created;
            return this;
        }
    }

    public static final Creator<Photogroup> CREATOR = new Creator<Photogroup>() {
        @Override
        public Photogroup createFromParcel(Parcel in) {
            return new Photogroup(in);
        }

        @Override
        public Photogroup[] newArray(int size) {
            return new Photogroup[size];
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
    protected Photogroup(Parcel in) {

    }


}
