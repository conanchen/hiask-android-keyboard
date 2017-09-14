package org.ditto.keyboard;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yescpu.keyboardchangelib.KeyboardChangeListener;

import org.ditto.keyboard.dbroom.gif.Gif;
import org.ditto.keyboard.dbroom.gift.Gift;
import org.ditto.keyboard.dbroom.photo.Photo;
import org.ditto.keyboard.dbroom.vo.Video;
import org.ditto.keyboard.panel.audio.AudioFragment;
import org.ditto.keyboard.panel.emoji.EmojigroupFragment;
import org.ditto.keyboard.panel.frequent.FrequentFragment;
import org.ditto.keyboard.panel.gif.GifgroupFragment;
import org.ditto.keyboard.panel.gift.GiftgroupFragment;
import org.ditto.keyboard.panel.more.Action;
import org.ditto.keyboard.panel.more.MoreFragment;
import org.ditto.keyboard.panel.photo.PhotoFragment;
import org.ditto.keyboard.panel.video.VideoFragment;
import org.ditto.keyboard.util.KeyboardPagerAdapter;
import org.ditto.keyboard.util.KeyboardPanelbaseFragment;
import org.ditto.keyboard.util.KeyboardUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class CommitKeyboard extends LinearLayout implements LifecycleOwner {
    private final static String TAG = "CommitKeyboard";
    private LifecycleRegistry registry = new LifecycleRegistry(this);

    @Override
    public Lifecycle getLifecycle() {
        return (registry);
    }

    class ThisLifecycleObserver implements LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume() {
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void onPause() {
        }
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        registry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }


    @BindView(R2.id.edit)
    CommitEditText mCommitEditText;

    @BindView(R2.id.send_button)
    ImageView mSendButton;

    @BindView(R2.id.keyboard_tablayout)
    TabLayout mKeyboardTabLayout;

    @BindView(R2.id.keyboard_view_pager)
    ViewPager mKeyboardViewPager;

    //More actions panel
    private int primaryColor;
    private int screenWidth;
    private int tenDp;
    private int maxFixedItemWidth;
    private static int tabXmlResource;

    List<KeyboardPanelbaseFragment> mFragmentList = new ArrayList<KeyboardPanelbaseFragment>() {{
        add(FrequentFragment
                .builder()
                .setTitle("常用")
                .setIcon(R2.drawable.ic_assistant_black_24dp)
                .setTagName("tabitem_frequent")
                .build());
        add(AudioFragment
                .builder()
                .setTitle("音频")
                .setIcon(R2.drawable.ic_keyboard_voice_black_24dp)
                .setTagName("tabitem_voice")
                .build());
        add(PhotoFragment
                .builder()
                .setTitle("图片")
                .setIcon(R2.drawable.ic_photo_black_24dp)
                .setTagName("tabitem_image")
                .build());
        add(VideoFragment
                .builder()
                .setTitle("视频")
                .setIcon(R2.drawable.ic_photo_camera_black_24dp)
                .setTagName("tabitem_video")
                .build());
        add(GiftgroupFragment
                .builder()
                .setTitle("礼物")
                .setIcon(R2.drawable.ic_card_giftcard_black_24dp)
                .setTagName("tabitem_gift")
                .build());
        add(GifgroupFragment
                .builder()
                .setTitle("斗图")
                .setIcon(R2.drawable.ic_gif_black_24dp)
                .setTagName("tabitem_gif")
                .build());
        add(EmojigroupFragment
                .builder()
                .setTitle("表情")
                .setIcon(R2.drawable.ic_insert_emoticon_black_24dp)
                .setTagName("tabitem_emoji")
                .build());
        add(MoreFragment
                .builder()
                .setTitle("更多")
                .setIcon(R2.drawable.ic_add_circle_outline_black_24dp)
                .setTagName("tabitem_less")
                .build());
    }};
    private int mPanelMorePosition = mFragmentList.size() - 1;


    private CommitListener mCommitListener;

    public CommitListener getCommitListener() {
        return mCommitListener;
    }

    public int getTabXmlResource() {
        return tabXmlResource;
    }

    // constructors
    public CommitKeyboard(Context context) {
        this(context, null, 0);
    }

    public CommitKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommitKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public CommitKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        checkNotNull(context);

        populateAttributes(context, attrs, defStyleAttr, defStyleRes);

        View keyboardView = LayoutInflater.from(context).inflate(R.layout.keyboard, this, true);
        ButterKnife.bind(this, keyboardView);

        mCommitEditText.setCommitKeyboard(this);
        new KeyboardChangeListener(context).setKeyBoardListener((isShow, keyboardHeight) -> {
            Log.d(TAG, "isShow = [" + isShow + "], keyboardHeight = [" + keyboardHeight + "]");
            ViewGroup.LayoutParams layoutParams = mKeyboardViewPager.getLayoutParams();
            layoutParams.height = keyboardHeight;
            mKeyboardViewPager.setLayoutParams(layoutParams);
        });

        this.getLifecycle().addObserver(new ThisLifecycleObserver());
    }


    private void populateAttributes(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        primaryColor = MiscUtils.getColor(getContext(), R.attr.colorPrimary);
        screenWidth = MiscUtils.getScreenWidth(getContext());
        tenDp = MiscUtils.dpToPixel(getContext(), 10);
        maxFixedItemWidth = MiscUtils.dpToPixel(getContext(), 168);

        TypedArray ta = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CommitKeyboard, defStyleAttr, defStyleRes);

        try {
            tabXmlResource = ta.getResourceId(R.styleable.CommitKeyboard_bb_tabXmlResource, 0);
        } finally {
            ta.recycle();
        }
    }

    /*
     * See @{url // http://nmp90.com/2015/07/getsupportfragmentmanager-vs-getchildfragmentmanager/ }
     * call from activity
     */
    public void setCommitListener(AppCompatActivity activity, CommitListener commitListener) {
        checkNotNull(activity);
        checkNotNull(commitListener);
        this.mCommitListener = commitListener;
        setupPanels(activity.getSupportFragmentManager());
    }


    /*
     * See @{url // http://nmp90.com/2015/07/getsupportfragmentmanager-vs-getchildfragmentmanager/ }
     * call from fragment
     */
    public void setCommitListener(Fragment fragment, CommitListener commitListener) {
        checkNotNull(fragment);
        checkNotNull(commitListener);
        this.mCommitListener = commitListener;
        setupPanels(fragment.getChildFragmentManager());
    }

    public void commitGif(Gif vo) {
        mCommitListener.onCommitGif(vo);
    }

    public void commitGift(Gift vo) {
        mCommitListener.onCommitGift(vo);
    }

    public void commitAction(Action action) {
        mCommitListener.onCommitAction(action);
    }

    public void commitPhoto(Photo photo) {
        mCommitListener.onCommitPhoto(photo);
    }

    public void commitVideo(Video video) {
        mCommitListener.onCommitVideo(video);
    }

    public void commitContent(String s, String mimeTypeGif, File mGifFile) {
        mCommitEditText.commitContent(s, mimeTypeGif, mGifFile);
    }

    public void deleteText() {
        mCommitEditText.deleteText();
    }

    public void upsertText(String s, int i) {
        mCommitEditText.upsertText(s, i);
    }


    @OnClick(R2.id.edit)
    protected void onEditTextClickedToToggleSoftInput(CommitEditText commitEditText) {
        KeyboardUtil.toggleSoftInput(this.getContext());
        Observable.just(true).delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    hideKeyboardPanel();
                });
    }

    @OnClick(R2.id.send_button)
    protected void onSendButtonClicked(ImageView sendButton) {
        mCommitListener.onCommitText(mCommitEditText.getText().toString());
        mCommitEditText.setText("");
    }


    private void setupPanels(FragmentManager fragmentManager) {
        //-----------setup ViewPager--------------
        KeyboardPagerAdapter fmAapter = new KeyboardPagerAdapter(this,fragmentManager, mFragmentList);
        mKeyboardViewPager.setAdapter(fmAapter);

        //-----------setup TabLayout------------
        mKeyboardTabLayout.setupWithViewPager(mKeyboardViewPager);
        for (int i = 0; i < mKeyboardTabLayout.getTabCount(); i++) {
            mKeyboardTabLayout.getTabAt(i).setIcon(mFragmentList.get(i).getIcon());
            mKeyboardTabLayout.getTabAt(i).setTag(mFragmentList.get(i).getTagName());
            mKeyboardTabLayout.getTabAt(i).setText("");
        }

        mKeyboardTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabSelected(tab);
            }


            private void tabSelected(TabLayout.Tab tab) {
                mKeyboardViewPager.setVisibility(View.VISIBLE);
                KeyboardUtil.hideSoftKeyboard(mKeyboardTabLayout.getContext());
                toggleActionMore(tab);
            }

            private void toggleActionMore(TabLayout.Tab tab) {
                if ("tabitem_more".equals(tab.getTag())) {
                    tab.setIcon(R2.drawable.ic_add_circle_outline_black_24dp);
                    hideKeyboardPanel();
                    tab.setTag("tabitem_less");
                } else if ("tabitem_less".equals(tab.getTag())) {
                    tab.setIcon(R2.drawable.ic_highlight_off_black_24dp);
                    showKeyboardPanel();
                    tab.setTag("tabitem_more");
                } else {
                    TabLayout.Tab tabItemMore = mKeyboardTabLayout.getTabAt(mPanelMorePosition);
                    tabItemMore.setIcon(R2.drawable.ic_add_circle_outline_black_24dp);
                    tabItemMore.setTag("tabitem_less");
                }
            }
        });

    }


    private void hideKeyboardPanel() {
        if (mKeyboardViewPager.getVisibility() != View.GONE) {
            mKeyboardViewPager.setVisibility(View.GONE);
            KeyboardUtil.updateSoftInputMethod(this.getContext(), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    private void showKeyboardPanel() {
        KeyboardUtil.updateSoftInputMethod(this.getContext(), WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        mKeyboardViewPager.setVisibility(View.VISIBLE);
        KeyboardUtil.hideSoftKeyboard(this.getContext());
    }

}
