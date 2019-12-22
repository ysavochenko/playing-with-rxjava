package monitoring.observable;

import io.reactivex.ObservableSource;

public interface LogObservableSource<T> extends ObservableSource<T> {

    void unsubscribe();
}
