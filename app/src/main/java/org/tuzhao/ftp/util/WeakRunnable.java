package org.tuzhao.ftp.util;

import java.lang.ref.WeakReference;

/**
 * zhaotu
 * 17-7-31
 */
public abstract class WeakRunnable<T> implements Runnable {

    private final WeakReference<T> wr;

    public WeakRunnable(T t) {
        wr = new WeakReference<>(t);
    }

    @Override
    public void run() {
        T t = wr.get();
        if (t != null) {
            weakRun(t);
        }
    }

    public abstract void weakRun(T t);

}
