package org.ditto.keyboard.panel.emoji.epoxymodels;

import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;

/**
 * This model shows an example of binding to a specific view type. In this case it is a custom view
 * we made, but it could also be another single view, like an EditText or Button.
 */
@EpoxyModelClass
public abstract class ItemEmojiModel extends EpoxyModelWithHolder<ItemEmojiModel.Holder> {


    @EpoxyAttribute
    String codepointu16;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(Holder holder) {

        holder.emoji_text.setText(codepointu16);
        holder.view.setOnClickListener(clickListener);
    }

    @Override
    public void unbind(Holder holder) {
        super.unbind(holder);
    }

    public static class Holder extends EpoxyHolder {
        @BindView(R2.id.emoji_text)
        EmojiAppCompatTextView emoji_text;
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
        return 1;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.emoji_item_model;
    }
}
