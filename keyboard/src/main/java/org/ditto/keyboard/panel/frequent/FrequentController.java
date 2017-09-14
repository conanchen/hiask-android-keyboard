package org.ditto.keyboard.panel.frequent;

import android.support.v7.widget.RecyclerView.RecycledViewPool;
import android.view.View;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.panel.frequent.epoxymodels.FrequentItemGifModel_;
import org.ditto.keyboard.panel.frequent.epoxymodels.FrequentItemTestModel_;
import org.ditto.keyboard.panel.frequent.epoxymodels.FrequentItemTextModel_;

import java.util.List;

public class FrequentController extends TypedEpoxyController<List<Gift>> {
    public interface AdapterCallbacks {

        void onTestKeyClick(View view);

        void onGiftClicked(Gift carousel, int colorPosition);
    }


    private final AdapterCallbacks callbacks;
    private final RecycledViewPool recycledViewPool;

    public FrequentController(AdapterCallbacks callbacks, RecycledViewPool recycledViewPool) {
        this.callbacks = callbacks;
        this.recycledViewPool = recycledViewPool;
        setDebugLoggingEnabled(true);
    }

    @AutoModel
    FrequentItemTestModel_ frequentItemTestModel_;

    @Override
    protected void buildModels(List<Gift> carousels) {

        frequentItemTestModel_.callbacks(callbacks).addTo(this);

        long now = System.currentTimeMillis();
        for (int i = 0; i < carousels.size(); i++) {
            Gift carousel = carousels.get(i);
            if (i % 2 == 0) {
                add(new FrequentItemGifModel_()
                        .id(now + i)
                        .name(carousel.name)
                        .price(carousel.price)
                        .clickListener((model, parentView, clickedView, position) -> {
                            // A model click listener is used instead of a normal click listener so that we can get
                            // the current position of the view. Since the view may have been moved when the colors
                            // were shuffled we can't rely on the position of the model when it was added here to
                            // be correct, since the model won't have been rebound when shuffled.
                            callbacks.onGiftClicked(carousel, position);
                        }));
            } else {
                add(new FrequentItemTextModel_()
                        .id(now + i)
                        .name(carousel.name)
                        .clickListener((model, parentView, clickedView, position) -> {
                            // A model click listener is used instead of a normal click listener so that we can get
                            // the current position of the view. Since the view may have been moved when the colors
                            // were shuffled we can't rely on the position of the model when it was added here to
                            // be correct, since the model won't have been rebound when shuffled.
                            callbacks.onGiftClicked(carousel, position);
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
