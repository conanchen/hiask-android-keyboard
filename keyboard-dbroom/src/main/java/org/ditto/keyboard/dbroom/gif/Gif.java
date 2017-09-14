package org.ditto.keyboard.dbroom.gif;

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
public class Gif implements Parcelable {
    @PrimaryKey
    public String uuid;
    public String groupUuid;
    public String icon;
    public String name;
    public int sequence;
    public long created;

    public Gif() {
    }

    private Gif(String uuid, String groupUuid, String icon, String name, int sequence, long created) {
        this.uuid = uuid;
        this.groupUuid = groupUuid;
        this.icon = icon;
        this.name = name;
        this.sequence = sequence;
        this.created = created;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String uuid;
        private String groupUuid;
        private String icon;
        private String name;
        private int sequence;
        private long created;

        Builder() {
        }

        public Gif build() {
            String missing = "";

            if (Strings.isNullOrEmpty(uuid)) {
                missing += " codepoint";
            }

            if (Strings.isNullOrEmpty(groupUuid)) {
                missing += " groupId";
            }

            if (Strings.isNullOrEmpty(name)) {
                missing += " name";
            }

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Gif(uuid, groupUuid, icon, name, sequence, created);
        }

        public Builder setUuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setGroupUuid(String groupUuid) {
            this.groupUuid = groupUuid;
            return this;
        }

        public Builder setIcon(String icon) {
            this.icon = icon;
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

    public static final Creator<Gif> CREATOR = new Creator<Gif>() {
        @Override
        public Gif createFromParcel(Parcel in) {
            return new Gif(in);
        }

        @Override
        public Gif[] newArray(int size) {
            return new Gif[size];
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
    protected Gif(Parcel in) {

    }

}
