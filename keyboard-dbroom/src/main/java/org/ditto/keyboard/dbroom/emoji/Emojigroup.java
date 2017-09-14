package org.ditto.keyboard.dbroom.emoji;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Strings;

/**
 * Created by admin on 2017/6/2.
 */
@Entity(primaryKeys={"groupId","subgroupId"})
public class Emojigroup implements Parcelable {
    public String groupId;
    public String subgroupId;
    public String name;
    public int sequence;
    public long lastUpdated;
    public boolean active;

    public Emojigroup() {
    }

    private Emojigroup(String groupId, String subgroupId, String name, int sequence, long lastUpdated,boolean active) {
        this.groupId = groupId;
        this.subgroupId = subgroupId;
        this.name = name;
        this.sequence = sequence;
        this.lastUpdated = lastUpdated;
        this.active = active;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String groupId;
        private String subgroupId;
        private String name;
        private int sequence;
        private long lastUpdated;
        private boolean active;

        Builder() {
        }

        public Emojigroup build() {
            String missing = "";

            if (Strings.isNullOrEmpty(groupId)) {
                missing += " groupId";
            }


            if (Strings.isNullOrEmpty(name)) {
                missing += " name";
            }


            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Emojigroup(groupId,subgroupId, name, sequence, lastUpdated,active);
        }

        public Builder setGroupId(String groupId) {
            this.groupId = groupId;
            return this;
        }

        public Builder setSubgroupId(String subgroupId) {
            this.subgroupId = subgroupId;
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

        public Builder setLastUpdated(long lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setActive(boolean active) {
            this.active = active;
            return this;
        }
    }

    public static final Creator<Emojigroup> CREATOR = new Creator<Emojigroup>() {
        @Override
        public Emojigroup createFromParcel(Parcel in) {
            return new Emojigroup(in);
        }

        @Override
        public Emojigroup[] newArray(int size) {
            return new Emojigroup[size];
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
    protected Emojigroup(Parcel in) {

    }


}
