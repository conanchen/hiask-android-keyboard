package org.ditto.keyboard;

import android.os.Bundle;
import android.support.v13.view.inputmethod.InputContentInfoCompat;

import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.dbroom.vo.Video;
import org.ditto.keyboard.panel.more.Action;

public interface CommitListener {
    void onCommitContent(InputContentInfoCompat info, int flags, Bundle opts);

    void onCommitText(String text);

    void onCommitGif(Gif gif);

    void onCommitGift(Gift gift);

    void onCommitAction(Action action);

    void onCommitPhoto(Photo photo);

    void onCommitVideo(Video video);
}

