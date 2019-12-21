package monitoring;

import io.reactivex.Observable;
import monitoring.observable.LogObservableSource;
import monitoring.observer.StringObserver;

import java.time.Instant;
import java.util.TreeMap;

public class LogListener {

    private StringObserver observer;
    private LogObservableSource observableSource;

    public static LogListener getInstance() {
        return new LogListener();
    }

    private LogListener() {
    };

    public LogListener setObserver(StringObserver someFileObserver) {
        this.observer = someFileObserver;
        return this;
    }

    public LogListener setObservableSource(LogObservableSource observableSource) {
        this.observableSource = observableSource;
        return this;
    }

    public void observe() {
        Observable<String> observable = Observable.defer(() -> this.observableSource);
        observable.subscribe(observer);
    }

    public void stopObserving() {
        observableSource.unsubscribe();
    }

    public TreeMap<Instant, String> getStoredLogs() {
        return observer.getStoredLogs();
    }


}
