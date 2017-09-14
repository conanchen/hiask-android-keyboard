package org.ditto.keyboard.di;

import android.app.Application;

import org.ditto.keyboard.panel.audio.AudioFragment;
import org.ditto.keyboard.panel.audio.AudioRecordFragment;
import org.ditto.keyboard.panel.audio.AudioTalkFragment;
import org.ditto.keyboard.panel.audio.di.AudioModule;
import org.ditto.keyboard.panel.emoji.EmojiFragment;
import org.ditto.keyboard.panel.emoji.EmojigroupFragment;
import org.ditto.keyboard.panel.emoji.di.EmojiModule;
import org.ditto.keyboard.panel.frequent.FrequentFragment;
import org.ditto.keyboard.panel.frequent.di.FrequentModule;
import org.ditto.keyboard.panel.gif.GifFragment;
import org.ditto.keyboard.panel.gif.GifgroupFragment;
import org.ditto.keyboard.panel.gif.di.GifModule;
import org.ditto.keyboard.panel.gift.GiftFragment;
import org.ditto.keyboard.panel.gift.GiftgroupFragment;
import org.ditto.keyboard.panel.gift.di.GiftModule;
import org.ditto.keyboard.panel.more.MoreFragment;
import org.ditto.keyboard.panel.more.di.MoreModule;
import org.ditto.keyboard.panel.photo.PhotoFragment;
import org.ditto.keyboard.panel.photo.di.PhotoModule;
import org.ditto.keyboard.panel.video.VideoFragment;
import org.ditto.keyboard.panel.video.VideoPlaybackFragment;
import org.ditto.keyboard.panel.video.VideoRecordFragment;
import org.ditto.keyboard.panel.video.VideoScanFragment;
import org.ditto.keyboard.panel.video.VideoTalkFragment;
import org.ditto.keyboard.panel.video.di.VideoModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        KeyboardModule.class,
        PhotoModule.class,
        EmojiModule.class,
        VideoModule.class,
        AudioModule.class,
        GifModule.class,
        GiftModule.class,
        MoreModule.class,
        FrequentModule.class
})
public interface KeyboardComponent {
    void inject(AudioFragment fragment);

    void inject(FrequentFragment frequentFragment);

    void inject(GiftFragment giftFragment);

    void inject(GifgroupFragment gifgroupFragment);

    void inject(AudioTalkFragment audioTalkFragment);

    void inject(AudioRecordFragment audioRecordFragment);

    void inject(VideoFragment videoFragment);

    void inject(VideoRecordFragment videoRecordFragment);

    void inject(VideoTalkFragment videoTalkFragment);

    void inject(VideoPlaybackFragment videoPlaybackFragment);

    void inject(EmojigroupFragment emojigroupFragment);

    void inject(PhotoFragment photoFragment);

    void inject(VideoScanFragment videoScanFragment);

    void inject(GiftgroupFragment giftgroupFragment);

    void inject(EmojiFragment emojiFragment);

    void inject(MoreFragment moreFragment);

    void inject(GifFragment gifFragment);




    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        KeyboardComponent build();
    }

}