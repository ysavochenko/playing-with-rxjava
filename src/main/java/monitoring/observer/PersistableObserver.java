package monitoring.observer;

import io.reactivex.Observer;

import java.time.Instant;
import java.util.TreeMap;

public interface PersistableObserver<T> extends Observer<T> {

    TreeMap<Instant, T> getStoredLogs();

}
