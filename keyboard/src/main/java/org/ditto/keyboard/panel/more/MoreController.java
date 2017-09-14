package org.ditto.keyboard.panel.more;

import android.support.v7.widget.RecyclerView.RecycledViewPool;

import com.airbnb.epoxy.TypedEpoxyController;

import org.ditto.keyboard.panel.more.epoxymodels.MoreActionItemModel_;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MoreController extends TypedEpoxyController<List<Action>> {
    public interface AdapterCallbacks {

        void onActionClicked(Action carousel, int colorPosition);

    }


    private final AdapterCallbacks callbacks;
    private final RecycledViewPool recycledViewPool;

    public MoreController(AdapterCallbacks callbacks, RecycledViewPool recycledViewPool) {
        this.callbacks = callbacks;
        this.recycledViewPool = recycledViewPool;
        setDebugLoggingEnabled(true);
    }

    @Override
    protected void buildModels(List<Action> actions) {
        if (actions != null) {
            for (Action action : actions) {
                add(new MoreActionItemModel_()
                        .id(action.id)
                        .iconResId(action.iconResId)
                        .title(action.title)
                        .clickListener((model, parentView, clickedView, position) -> {
                            // A model click listener is used instead of a normal click listener so that we can get
                            // the current position of the view. Since the view may have been moved when the colors
                            // were shuffled we can't rely on the position of the model when it was added here to
                            // be correct, since the model won't have been rebound when shuffled.
                            checkNotNull(callbacks);
                            callbacks.onActionClicked(action, position);
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
