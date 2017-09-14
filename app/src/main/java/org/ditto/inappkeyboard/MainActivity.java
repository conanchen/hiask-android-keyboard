package org.ditto.inappkeyboard;

import android.content.Intent;
import android.support.text.emoji.widget.EmojiAppCompatTextView;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import org.ditto.keyboard.CommitKeyboard;
import org.ditto.keyboard.CommitListener;
import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.dbroom.vo.Video;
import org.ditto.keyboard.panel.more.Action;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private final static Gson gson = new Gson();
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EmojiAppCompatTextView textView = (EmojiAppCompatTextView) findViewById(R.id.text);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        CommitKeyboard keyboard = (CommitKeyboard) findViewById(R.id.keyboard);
        keyboard.setCommitListener(this, new CommitListener() {
            @Override
            public void onCommitContent(InputContentInfoCompat info, int flags, Bundle opts) {
                imageView.setImageURI(info.getContentUri());
                textView.setText(gson.toJson(info));
            }

            @Override
            public void onCommitText(String text) {
                textView.setText(text);
            }

            @Override
            public void onCommitGif(Gif gif) {
                textView.setText(gson.toJson(gif));
            }

            @Override
            public void onCommitGift(Gift gift) {
                textView.setText(gson.toJson(gift));
            }

            @Override
            public void onCommitAction(Action action) {
                textView.setText(gson.toJson(action));
            }

            @Override
            public void onCommitPhoto(Photo photo) {
                textView.setText(gson.toJson(photo));
            }

            @Override
            public void onCommitVideo(Video video) {
                textView.setText(gson.toJson(video));
            }
        });

        Button button_openfragmentactivity = (Button) findViewById(R.id.button_openfragmentactivity);
        button_openfragmentactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainFragmentActivity.class));
            }
        });

    }
}