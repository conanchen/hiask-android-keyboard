package org.ditto.keyboard.di;

import android.app.Application;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.bundled.BundledEmojiCompatConfig;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import org.ditto.keyboard.CommitKeyboard;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @see <a href="https://android.googlesource.com/platform/frameworks/testing/+/976c4234127805d7dc9a1cdbcb42b5d9f4585771/espresso/espresso-lib/src/main/java/com/google/android/apps/common/testing/ui/espresso/GraphHolder.java">com.google.android.apps.common.testing.ui.espresso.GraphHolder</a>
 *
 */
public class KeyboardInjector {
    private KeyboardInjector() {
    }

    private static final AtomicReference<KeyboardComponent> instance =
            new AtomicReference<KeyboardComponent>(null);

    public static void init(Application application) {
        checkNotNull(application);

        EmojiCompat.Config config = new BundledEmojiCompatConfig(application);
        EmojiCompat.init(config);

        KeyboardComponent instanceRef = instance.get();

        if (null == instanceRef) {
            instanceRef = DaggerKeyboardComponent.builder().application(application)
                    .build();
            checkState(instance.compareAndSet(null, instanceRef), "CommitKeyboard already initialized.");
        }

    }

    public static KeyboardComponent getKeyboardComponent() {
        return checkNotNull(instance.get(), "CommitKeyboard not initialized before with KeyboardInjector.init(CommitKeyboard commitKeyboard)");
    }
}