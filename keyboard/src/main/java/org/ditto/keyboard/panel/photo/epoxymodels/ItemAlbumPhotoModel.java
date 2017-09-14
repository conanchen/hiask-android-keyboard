package org.ditto.keyboard.panel.photo.epoxymodels;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelClass;
import com.airbnb.epoxy.EpoxyModelWithHolder;

import org.ditto.keyboard.R;
import org.ditto.keyboard.R2;
import org.ditto.keyboard.glide.GlideApp;
import org.ditto.keyboard.panel.photo.AspectRatioImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * This model shows an example of binding to a specific view type. In this case it is a custom view
 * we made, but it could also be another single view, like an EditText or Button.
 */
@EpoxyModelClass
public abstract class ItemAlbumPhotoModel extends EpoxyModelWithHolder<ItemAlbumPhotoModel.Holder> {
    @EpoxyAttribute
    String name;

    @EpoxyAttribute
    String path;

    @EpoxyAttribute
    int width;

    @EpoxyAttribute
    int selectIdx;

    @EpoxyAttribute
    int height;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener clickListener;

    @EpoxyAttribute(DoNotHash)
    View.OnClickListener selectListener;

    @Override
    public void bind(Holder holder) {
//        holder.name.setText(name);
        holder.button_select.setOnClickListener(selectListener);
        holder.image.setOnClickListener(clickListener);
        holder.image.setWidth(width).setHeight(height);
        holder.button_select.setChecked(selectIdx > 0);
        if (selectIdx > 0) {
            holder.button_select.setText("已选中");
        }else{
            holder.button_select.setText("请选我");
        }
        GlideApp
                .with(holder.image)
                .load(Uri.parse("file://" + path))
                .placeholder(R.drawable.ask28)
                .error(new ColorDrawable(Color.RED))
                .fallback(new ColorDrawable(Color.GRAY))
                .centerCrop()
                .transition(withCrossFade())
                .into(holder.image);

    }

    @Override
    public void unbind(Holder holder) {
        // Release resources and don't leak listeners as this view goes back to the view pool
        holder.button_select.setOnClickListener(null);

        holder.image.setOnClickListener(null);

        holder.image.setImageURI(null);

    }

    public static class Holder extends EpoxyHolder {
        @BindView(R2.id.image)
        AspectRatioImageView image;

        @BindView(R2.id.button_select)
        AppCompatCheckBox button_select;

//        @BindView(R2.id.name)
//        TextView name;

        View view;

        @Override
        protected void bindView(View itemView) {
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getSpanSize(int totalSpanCount, int position, int itemCount) {
        return 1;
    }

    @Override
    protected int getDefaultLayout() {
        return R.layout.photo_itemalbum_model;
    }
}
