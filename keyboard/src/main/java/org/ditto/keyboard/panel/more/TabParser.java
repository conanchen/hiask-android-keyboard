package org.ditto.keyboard.panel.more;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.support.annotation.CheckResult;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.XmlRes;
import android.support.v4.content.ContextCompat;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.ACTIVE_COLOR;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.BADGE_BACKGROUND_COLOR;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.BADGE_HIDES_WHEN_ACTIVE;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.BAR_COLOR_WHEN_SELECTED;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.ICON;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.ID;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.INACTIVE_COLOR;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.IS_TITLELESS;
import static org.ditto.keyboard.panel.more.TabParser.TabAttribute.TITLE;


class TabParser {
    private static final String TAB_TAG = "tab";
    private static final int AVG_NUMBER_OF_TABS = 5;
    private static final int COLOR_NOT_SET = -1;
    private static final int RESOURCE_NOT_FOUND = 0;

    @NonNull
    private final Context context;

    @NonNull
    private final XmlResourceParser parser;

    @Nullable
    private List<Action> tabs = null;

    TabParser(@NonNull Context context, @XmlRes int tabsXmlResId) {
        this.context = context;
        this.parser = context.getResources().getXml(tabsXmlResId);
    }

    @CheckResult
    @NonNull
    public List<Action> parseTabs() {
        if (tabs == null) {
            tabs = new ArrayList<>(AVG_NUMBER_OF_TABS);
            try {
                int eventType;
                do {
                    eventType = parser.next();
                    if (eventType == XmlResourceParser.START_TAG && TAB_TAG.equals(parser.getName())) {
                        Action bottomBarTab = parseNewTab(parser, tabs.size());
                        tabs.add(bottomBarTab);
                    }
                } while (eventType != XmlResourceParser.END_DOCUMENT);
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
                throw new TabParserException();
            }
        }

        return tabs;
    }

    @NonNull
    private Action parseNewTab(@NonNull XmlResourceParser parser,
                               @IntRange(from = 0) int containerPosition) {
        int id = 0, iconResId = 0;
        String title = "";
        final int numberOfAttributes = parser.getAttributeCount();
        for (int i = 0; i < numberOfAttributes; i++) {
            @TabAttribute
            String attrName = parser.getAttributeName(i);
            switch (attrName) {
                case ID:
                    id = parser.getIdAttributeResourceValue(i);
                    break;
                case ICON:
                    iconResId = parser.getAttributeResourceValue(i, RESOURCE_NOT_FOUND);
                    break;
                case TITLE:
                    title = getTitleValue(parser, i);
                    break;
            }
        }

        return Action.builder()
                .setId(id)
                .setIconResId(iconResId)
                .setTitle(title)
                .setIndexInContainer(containerPosition)
                .build();
    }


    @NonNull
    private String getTitleValue(@NonNull XmlResourceParser parser, @IntRange(from = 0) int attrIndex) {
        int titleResource = parser.getAttributeResourceValue(attrIndex, 0);
        return titleResource == RESOURCE_NOT_FOUND
                ? parser.getAttributeValue(attrIndex) : context.getString(titleResource);
    }

    @ColorInt
    private int getColorValue(@NonNull XmlResourceParser parser, @IntRange(from = 0) int attrIndex) {
        int colorResource = parser.getAttributeResourceValue(attrIndex, 0);

        if (colorResource == RESOURCE_NOT_FOUND) {
            try {
                String colorValue = parser.getAttributeValue(attrIndex);
                return Color.parseColor(colorValue);
            } catch (Exception ignored) {
                return COLOR_NOT_SET;
            }
        }

        return ContextCompat.getColor(context, colorResource);
    }

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            ID,
            ICON,
            TITLE,
            INACTIVE_COLOR,
            ACTIVE_COLOR,
            BAR_COLOR_WHEN_SELECTED,
            BADGE_BACKGROUND_COLOR,
            BADGE_HIDES_WHEN_ACTIVE,
            IS_TITLELESS
    })
    @interface TabAttribute {
        String ID = "id";
        String ICON = "icon";
        String TITLE = "title";
        String INACTIVE_COLOR = "inActiveColor";
        String ACTIVE_COLOR = "activeColor";
        String BAR_COLOR_WHEN_SELECTED = "barColorWhenSelected";
        String BADGE_BACKGROUND_COLOR = "badgeBackgroundColor";
        String BADGE_HIDES_WHEN_ACTIVE = "badgeHidesWhenActive";
        String IS_TITLELESS = "iconOnly";
    }

    @SuppressWarnings("WeakerAccess")
    public static class TabParserException extends RuntimeException {
        // This class is just to be able to have a type of Runtime Exception that will make it clear where the error originated.
    }
}
