package org.ditto.keyboard.panel.more.epoxymodels;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public abstract class MoreActionItemModel extends EpoxyModelWithHolder<MoreActionItemModel.Holder> {

    @EpoxyAttribute
    int iconResId;

    @EpoxyAttribute
    String title;


    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @Override
    public void bind(Holder holder) {
        holder.name.setText(title);
        holder.view.setOnClickListener(clickListener);
    }

    @Override
    public void unbind(Holder holder) {
        super.unbind(holder);
    }

    public static class Holder extends EpoxyHolder {
        @BindView(R2.id.icon)
        ImageView icon;
        @BindView(R2.id.name)
        TextView name;


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
        return R.layout.moreaction_item_model;
    }
}
