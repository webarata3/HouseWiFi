package link.webarata3.dro.housewifi;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

public class AppExecutors {
    private final Executor diskIo;
    private final Executor mainThread;

    private static AppExecutors appExecutors;

    public static synchronized AppExecutors getInstance() {
        if (appExecutors == null) {
            appExecutors = new AppExecutors();
        }
        return appExecutors;
    }

    private AppExecutors() {
        this.diskIo = Executors.newSingleThreadExecutor();
        this.mainThread = new MainThreadExecutor();
    }

    public Executor diskIo() {
        return diskIo;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
