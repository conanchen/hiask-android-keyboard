package org.ditto.keyboard.panel.frequent.epoxymodels;

import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;
import org.ditto.keyboard.panel.frequent.FrequentController;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * This model shows an example of binding to a specific view type. In this case it is a custom view
 * we made, but it could also be another single view, like an EditText or Button.
 */
@EpoxyModelClass
public abstract class FrequentItemTestModel extends EpoxyModelWithHolder<FrequentItemTestModel.Holder> {


    @EpoxyAttribute(DoNotHash)
    FrequentController.AdapterCallbacks callbacks;

 

    @Override
    public void bind(Holder holder) {

        // set button click listeners
        holder.mButton1.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButton2.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButton3.setOnClickListener((view) -> callbacks.onTestKeyClick(view));

        holder.mButtonDelete.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonEnter.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonGif.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonPng.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonWebp.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonRest.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonGrpc.setOnClickListener((view) -> callbacks.onTestKeyClick(view));
        holder.mButtonRestrx.setOnClickListener((view) -> callbacks.onTestKeyClick(view));

    }

    @Override
    public void unbind(Holder holder) {
        super.unbind(holder);
    }


    public static class Holder extends EpoxyHolder {
        // keyboard keys (buttons)
        @BindView(R2.id.button_1)
        Button mButton1;
        @BindView(R2.id.button_2)
        Button mButton2;
        @BindView(R2.id.button_3)
        Button mButton3;
        @BindView(R2.id.button_delete)
        Button mButtonDelete;
        @BindView(R2.id.button_enter)
        Button mButtonEnter;
        @BindView(R2.id.button_gif)
        Button mButtonGif;
        @BindView(R2.id.button_png)
        Button mButtonPng;
        @BindView(R2.id.button_webp)
        Button mButtonWebp;

        @BindView(R2.id.button_rest)
        Button mButtonRest;

        @BindView(R2.id.button_restrx)
        Button mButtonRestrx;

        @BindView(R2.id.button_grpc)
        Button mButtonGrpc;

        View view;

        @Override
        protected void bindView(View itemView) {
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        // We want the header to take up all spans so it fills the screen width
        return 3;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.frequent_item_test_model;
    }
}
