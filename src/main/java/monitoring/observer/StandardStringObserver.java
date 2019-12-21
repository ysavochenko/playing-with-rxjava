package monitoring.observer;


import io.reactivex.disposables.Disposable;

import java.time.Instant;
import java.util.TreeMap;

public class StandardStringObserver implements StringObserver {

    private TreeMap<Instant, String> storage = new TreeMap<>();

    @Override
    public void onSubscribe(Disposable d) {
        System.out.println("OK, I've subscribed");
    }

    @Override
    public void onNext(String s) {
//        System.out.println("Got such string from observable: " + s + " inside Thread " + Thread.currentThread().getName());
        this.storage.put(Instant.now(), s + " inside Thread " + Thread.currentThread().getName());
    }

    @Override
    public void onError(Throwable e) {
        System.out.println("Sorry GOT SOME ERRORS HERE");
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
