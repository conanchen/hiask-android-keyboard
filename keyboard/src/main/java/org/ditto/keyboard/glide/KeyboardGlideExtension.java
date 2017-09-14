package org.ditto.keyboard.glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;

import static com.bumptech.glide.request.RequestOptions.decodeTypeOf;

@GlideExtension
public class KeyboardGlideExtension {
    private static final RequestOptions DECODE_TYPE_GIF = decodeTypeOf(GifDrawable.class).lock();
    // Size of mini thumb in pixels.
    private static final int MINI_THUMB_SIZE = 100;

    private KeyboardGlideExtension() { } // utility class

    @GlideOption
    public static void miniThumb(RequestOptions options) {
        options
                .fitCenter()
                .override(MINI_THUMB_SIZE);
    }
}