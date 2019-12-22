package monitoring.observer;


import io.reactivex.disposables.Disposable;

import java.time.Instant;
import java.util.TreeMap;

public class StandardPersistableObserver implements PersistableObserver<String> {

    private TreeMap<Instant, String> storage = new TreeMap<>();

    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("OK, I've successfully subscribed to source");
    }

    @Override
    public void onNext(String s) {
        this.storage.put(Instant.now(), s);
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("Looks like some errors where thrown in observable:");
        System.out.println(e.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("I'm DONE!!!!!!");
    }

    public TreeMap<Instant, String> getStoredLogs() {
        return this.storage;
    }

}
