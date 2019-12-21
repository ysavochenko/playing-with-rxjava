package monitoring.observer;

import io.reactivex.Observer;

import java.time.Instant;
import java.util.TreeMap;

public interface StringObserver extends Observer<String> {

    TreeMap<Instant, String> getStoredLogs();

}
