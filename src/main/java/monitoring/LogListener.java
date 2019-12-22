package monitoring;

import io.reactivex.Observable;
import monitoring.observable.LogObservableSource;
import monitoring.observer.PersistableObserver;

import java.time.Instant;
import java.util.TreeMap;

public class LogListener {

    private PersistableObserver<String> observer;
    private LogObservableSource<String> observableSource;

    public static LogListener getInstance() {
        return new LogListener();
    }

    private LogListener() {
    }

    public LogListener setObserver(PersistableObserver<String> someFileObserver) {
        this.observer = someFileObserver;
        return this;
    }

    public LogListener setObservableSource(LogObservableSource<String> observableSource) {
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
