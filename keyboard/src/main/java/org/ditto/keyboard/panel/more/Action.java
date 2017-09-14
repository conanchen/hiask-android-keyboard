package org.ditto.keyboard.panel.more;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.base.Strings;

/**
 * Created by admin on 2017/6/2.
 */
public class Action implements Parcelable {

    public int id;
    public int iconResId;
    public String title;
    public int indexInContainer;


    public Action() {
    }


    private Action(int id, int iconResId, String title, int indexInContainer) {
        this.id = id;
        this.iconResId = iconResId;
        this.title = title;
        this.indexInContainer = indexInContainer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private int id;
        private int iconResId;
        private String title;
        private int indexInContainer;

        Builder() {
        }

        public Action build() {
            String missing = "";


            if (id ==0 ) {
                missing += " id";
            }


            if (iconResId ==0 ) {
                missing += " iconResId";
            }

            if (Strings.isNullOrEmpty(title)) {
                missing += " title";
            }

            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }

            return new Action(  id,   iconResId,   title,   indexInContainer) ;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setIconResId(int iconResId) {
            this.iconResId = iconResId;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIndexInContainer(int indexInContainer) {
            this.indexInContainer = indexInContainer;
            return this;
        }

    }

    public static final Creator<Action> CREATOR = new Creator<Action>() {
        @Override
        public Action createFromParcel(Parcel in) {
            return new Action(in);
        }

        @Override
        public Action[] newArray(int size) {
            return new Action[size];
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
    protected Action(Parcel in) {

    }


}
