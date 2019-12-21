package monitoring.observable;

import io.reactivex.ObservableSource;

public interface LogObservableSource extends ObservableSource<String> {

    void unsubscribe();
}
