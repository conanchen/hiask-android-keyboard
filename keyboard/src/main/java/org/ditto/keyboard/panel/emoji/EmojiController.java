package org.ditto.keyboard.panel.emoji;

import android.support.v7.widget.RecyclerView.RecycledViewPool;

import com.airbnb.epoxy.TypedEpoxyController;

import org.ditto.keyboard.dbroom.emoji.Emoji;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.panel.emoji.epoxymodels.ItemEmojiModel_;

import java.util.List;

public class EmojiController extends TypedEpoxyController<List<Emoji>> {
    public interface AdapterCallbacks {

        void onEmojiClicked(Emoji carousel, int colorPosition);
    }


    private final AdapterCallbacks callbacks;
    private final RecycledViewPool recycledViewPool;

    public EmojiController(AdapterCallbacks callbacks, RecycledViewPool recycledViewPool) {
        this.callbacks = callbacks;
        this.recycledViewPool = recycledViewPool;
        setDebugLoggingEnabled(true);
    }

    @Override
    protected void buildModels(List<Emoji> carousels) {
        if (carousels != null) {
            for (Emoji emoji:carousels) {
                add(new ItemEmojiModel_()
                        .id(emoji.codepoint)
                        .codepointu16(emoji.codepointu16)
                        .clickListener((model, parentView, clickedView, position) -> {
                            // A model click listener is used instead of a normal click listener so that we can get
                            // the current position of the view. Since the view may have been moved when the colors
                            // were shuffled we can't rely on the position of the model when it was added here to
                            // be correct, since the model won't have been rebound when shuffled.
                            callbacks.onEmojiClicked(emoji, position);
                        }));

            }
        }
    }

    @Override
    protected void onExceptionSwallowed(RuntimeException exception) {
        // Best practice is to throw in debug so you are aware of any issues that Epoxy notices.
        // Otherwise Epoxy does its best to swallow these exceptions and continue gracefully
        throw exception;
    }
}
