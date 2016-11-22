package library.mlibrary.view.recyclerview.quickguide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import library.mlibrary.R;
import library.mlibrary.view.recyclerview.RecyclerView;
import library.mlibrary.view.recyclerview.quickguide.listener.OnQuickSideBarTouchListener;

/**
 * 快速选择侧边栏
 * Created by Sai on 16/3/25.
 */
public class QuickSideBarView extends View {

    private OnQuickSideBarTouchListener listener;
    private ArrayList<String> mLetters;
    private LinkedHashMap<String, Integer> mLettersMap;
    private int mChoose = -1;
    private Paint mPaint = new Paint();
    private int mTextSize;
    private int mTextSizeChoose;
    private int mTextColor;
    private int mTextColorChoose;
    private int mWidth;
    private int mHeight;
    private int mItemHeight;
    private int mPaddingTop;//顶部留着间距

    public QuickSideBarView(Context context) {
        this(context, null);
    }

    public QuickSideBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickSideBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initLetters();

        mTextColor = context.getResources().getColor(android.R.color.black);
        mTextColorChoose = context.getResources().getColor(android.R.color.black);
        mTextSize = context.getResources().getDimensionPixelSize(R.dimen.textSize_quicksidebar);
        mPaddingTop = context.getResources().getDimensionPixelSize(R.dimen.height_quicksidebartips);
        mTextSizeChoose = context.getResources().getDimensionPixelSize(R.dimen.textSize_quicksidebar_choose);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.QuickSideBarView);
            mTextColor = a.getColor(R.styleable.QuickSideBarView_sidebarTextColor, mTextColor);
            mTextColorChoose = a.getColor(R.styleable.QuickSideBarView_sidebarTextColorChoose, mTextColorChoose);
            mTextSize = a.getDimensionPixelSize(R.styleable.QuickSideBarView_sidebarTextSize, mTextSize);
            mTextSizeChoose = a.getDimensionPixelSize(R.styleable.QuickSideBarView_sidebarTextSizeChoose, mTextSizeChoose);
            a.recycle();
        }
    }

    private void autoSetRelatedView() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent == null) {
            return;
        }
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View brother = parent.getChildAt(i);
            if (brother instanceof RecyclerView) {
                setListView((RecyclerView) brother);
            }
            if (brother instanceof QuickSideBarTipsView) {
                setTipView((QuickSideBarTipsView) brother);
            }
        }
    }

    private void initLetters() {
        mLetters = new ArrayList<>();
        mLetters.add("A");
        mLetters.add("B");
        mLetters.add("C");
        mLetters.add("D");
        mLetters.add("E");
        mLetters.add("F");
        mLetters.add("G");
        mLetters.add("H");
        mLetters.add("I");
        mLetters.add("J");
        mLetters.add("K");
        mLetters.add("L");
        mLetters.add("M");
        mLetters.add("N");
        mLetters.add("O");
        mLetters.add("P");
        mLetters.add("Q");
        mLetters.add("R");
        mLetters.add("S");
        mLetters.add("T");
        mLetters.add("U");
        mLetters.add("V");
        mLetters.add("W");
        mLetters.add("X");
        mLetters.add("Y");
        mLetters.add("Z");
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = mLetters.size();
        if (isSetLetters) {
            return;
        }
        for (int i = 0; i < size; i++) {
            mPaint.setColor(mTextColor);

            mPaint.setAntiAlias(true);
            mPaint.setTextSize(mTextSize);
            if (i == mChoose) {
                mPaint.setColor(mTextColorChoose);
                mPaint.setFakeBoldText(true);
                mPaint.setTypeface(Typeface.DEFAULT_BOLD);
                mPaint.setTextSize(mTextSizeChoose);
            }


            //计算位置
            Rect rect = new Rect();
            if (isSetLetters) {
                return;
            }
            String letter = mLetters.get(i);
            mPaint.getTextBounds(letter, 0, letter.length(), rect);
            float xPos = (int) ((mWidth - rect.width()) * 0.5);
            float yPos = mItemHeight * i + (int) ((mItemHeight - rect.height()) * 0.5) + mPaddingTop;


            canvas.drawText(letter, xPos, yPos, mPaint);
            mPaint.reset();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getHeight() - mPaddingTop;
        mWidth = getWidth();
        mItemHeight = mHeight / mLetters.size();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChoose = mChoose;
        final int newChoose = (int) (y / mHeight * mLetters.size()) - 1;

        switch (action) {
            case MotionEvent.ACTION_UP:
                mChoose = -1;
                if (listener != null) {
                    listener.onLetterTouching(false);
                }
                showTip(false);
                invalidate();
                break;
            default:
                if (oldChoose != newChoose) {
                    if (newChoose >= 0 && newChoose < mLetters.size()) {
                        mChoose = newChoose;
                        if (listener != null) {
                            listener.onLetterChanged(mLetters.get(newChoose), mChoose, mItemHeight);
                        }
                        showTip(mLetters.get(newChoose), mChoose, mItemHeight);
                        scrollListView(mLetters.get(newChoose));
                    }
                    invalidate();
                }
                //如果是cancel也要调用onLetterUpListener 通知
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    if (listener != null) {
                        listener.onLetterTouching(false);
                    }
                    showTip(false);
                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {//按下调用 onLetterDownListener
                    if (listener != null) {
                        listener.onLetterTouching(true);
                    }
                    showTip(true);
                }

                break;
        }
        return true;
    }

    private QuickSideBarTipsView mTip;

    public void setTipView(QuickSideBarTipsView quickSideBarTipsView) {
        mTip = quickSideBarTipsView;
    }

    private void showTip(boolean touch) {
        if (mTip != null) {
            mTip.setVisibility(touch ? View.VISIBLE : View.INVISIBLE);
        }
    }

    private void showTip(String letter, int position, int itemHeight) {
        if (mTip != null) {
            mTip.setText(letter, position, itemHeight);
        }
    }

    public OnQuickSideBarTouchListener getListener() {
        return listener;
    }

    public void setOnQuickSideBarTouchListener(OnQuickSideBarTouchListener listener) {
        this.listener = listener;
    }

    public List<String> getLetters() {
        return mLetters;
    }

    public void setLetters(ArrayList<String> letters) {
        this.mLetters = letters;
        invalidate();
        autoSetRelatedView();
    }

    private RecyclerView mListView;

    public void setListView(RecyclerView listview) {
        mListView = listview;
    }

    private void scrollListView(String letter) {
        if (mListView != null && mLettersMap != null) {
            if (mLettersMap.containsKey(letter)) {
                mListView.smoothScrollToPosition(mLettersMap.get(letter));
//                mListView.scrollToPositionWithOffset(mLettersMap.get(letter), 0);
            }
        }
    }

    private boolean isSetLetters = false;

    public void setLetters(LinkedHashMap<String, Integer> letters) {
        isSetLetters = true;
        mLettersMap = letters;
        if (mLetters == null) {
            mLetters = new ArrayList<>();
        } else {
            mLetters.clear();
        }
        for (Map.Entry<String, Integer> entry : mLettersMap.entrySet()) {
            mLetters.add(entry.getKey());
        }
        isSetLetters = false;
        invalidate();
        autoSetRelatedView();
    }
}

