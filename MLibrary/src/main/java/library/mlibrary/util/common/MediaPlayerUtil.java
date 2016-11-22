package library.mlibrary.util.common;

import android.media.MediaPlayer;

import java.io.IOException;

import library.mlibrary.util.log.LogDebug;

/**
 * Created by Harmy on 2016/10/30 0030.
 */

public class MediaPlayerUtil {
    private static MediaPlayerUtil mUtil;

    private MediaPlayer mMediaPlayer;

    private MediaPlayerUtil() {
    }

    public static MediaPlayerUtil getInstance() {
        if (mUtil == null) {
            mUtil = new MediaPlayerUtil();
        }
        return mUtil;
    }

    public void play(String filepath) {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
                if (listener != null) {
                    listener.onReplace();
                }
                play(filepath);
            }
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(filepath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            if (listener != null) {
                listener.onStart();
            }
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stop();
                }
            });
        } catch (IOException e) {
            LogDebug.e(e);
            if (listener != null) {
                listener.onException();
            }
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (listener != null) {
                listener.onFinish();
            }
        }

    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class Listener {
        public void onStart() {
        }

        public void onException() {
        }

        public void onReplace() {
        }

        public void onFinish() {
        }
    }
}
