package org.ditto.keyboard.dbroom.emoji;

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
public class Emoji implements Parcelable {
    @PrimaryKey
    public String codepoint;
    public String codepointu16;
    public String groupId;
    public String subgroupId;
    public String name;
    public int sequence;
    public long lastUpdated;
    public boolean active;

    public Emoji() {
    }

    private Emoji(String codepoint,String codepointu16, String groupId, String subgroupId,
                  String name, int sequence, long lastUpdated,boolean active) {
        this.codepoint = codepoint;
        this.codepointu16 = codepointu16;
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
        private String codepoint;
        private String codepointu16;
        private String groupId;
        private String subgroupId;
        private String name;
        private int sequence;
        private long lastUpdated;
        private boolean active;

        Builder() {
        }

        public Emoji build() {
            String missing = "";

            if (Strings.isNullOrEmpty(codepoint)) {
                missing += " codepoint";
            }

            if (Strings.isNullOrEmpty(codepointu16)) {
                missing += " codepointu16";
            }

            if (Strings.isNullOrEmpty(groupId)) {
                missing += " groupId";
            }


            if (Strings.isNullOrEmpty(subgroupId)) {
                missing += " subgroupId";
            }

            if (Strings.isNullOrEmpty(name)) {
                missing += " name";
            }

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Emoji(codepoint, codepointu16,groupId, subgroupId,name, sequence, lastUpdated,active);
        }

        public Builder setCodepoint(String codepoint) {
            this.codepoint = codepoint;
            return this;
        }

        public Builder setCodepointu16(String codepointu16) {
            this.codepointu16 = codepointu16;
            return this;
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

    public static final Creator<Emoji> CREATOR = new Creator<Emoji>() {
        @Override
        public Emoji createFromParcel(Parcel in) {
            return new Emoji(in);
        }

        @Override
        public Emoji[] newArray(int size) {
            return new Emoji[size];
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
    protected Emoji(Parcel in) {

    }

}
