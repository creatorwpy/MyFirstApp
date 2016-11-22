package library.mlibrary.util.timecount;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * 重写CountDownTImer 增加 onStart()
 * Created by Harmy on 2015/9/2 0002.
 */
public abstract class CountDownTimer {

    /**
     * Millis since epoch when alarm should stop.
     */
    private long mMillisInFuture;

    /**
     * The interval in millis that the user receives callbacks
     */
    private long mCountdownInterval;

    private long mStopTimeInFuture;

    /**
     * boolean representing if the timer was cancelled
     */
    private boolean mCancelled = false;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountDownTimer(long millisInFuture, long countDownInterval) {
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    /**
     * Cancel the countdown.
     */
    public synchronized final void cancel() {
        mCancelled = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * Start the countdown.
     */
    public synchronized final CountDownTimer start() {
        mCancelled = false;
        if (mMillisInFuture <= 0) {
            onFinish();
            return this;
        }
        mHandler.sendMessage(mHandler.obtainMessage(START));
        mStopTimeInFuture = SystemClock.elapsedRealtime() + mMillisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    public abstract void onStart();

    public void reStart() {
        cancel();
        start();
    }


    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * Callback fired when the time is up.
     */
    public abstract void onFinish();


    private static final int MSG = 1;
    private static final int START = 2;

    // handles counting down
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CountDownTimer.this) {
                switch (msg.what) {
                    case MSG:
                        if (mCancelled) {
                            return;
                        }

                        final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();

                        if (millisLeft <= 0) {
                            onFinish();
                        } else if (millisLeft < mCountdownInterval) {
                            // no tick, just delay until done
                            sendMessageDelayed(obtainMessage(MSG), millisLeft);
                        } else {
                            long lastTickStart = SystemClock.elapsedRealtime();
                            onTick(millisLeft);

                            // take into account user's onTick taking time to execute
                            long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                            // special case: user's onTick took more than interval to
                            // complete, skip to next interval
                            while (delay < 0) delay += mCountdownInterval;

                            sendMessageDelayed(obtainMessage(MSG), delay);
                        }
                        break;
                    case START:
                        onStart();
                        break;
                }
            }
        }
    };
}
