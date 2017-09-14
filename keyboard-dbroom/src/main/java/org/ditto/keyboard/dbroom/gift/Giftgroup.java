package org.ditto.keyboard.dbroom.gift;

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
public class Giftgroup implements Parcelable {
    @PrimaryKey
    public String uuid;
    public String name;
    public int sequence;
    public long created;

    public Giftgroup() {
    }

    private Giftgroup(String uuid, String name, int sequence, long created) {
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

        public Giftgroup build() {
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

            return new Giftgroup(uuid, name, sequence, created);
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

    public static final Creator<Giftgroup> CREATOR = new Creator<Giftgroup>() {
        @Override
        public Giftgroup createFromParcel(Parcel in) {
            return new Giftgroup(in);
        }

        @Override
        public Giftgroup[] newArray(int size) {
            return new Giftgroup[size];
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
    protected Giftgroup(Parcel in) {

    }


}
