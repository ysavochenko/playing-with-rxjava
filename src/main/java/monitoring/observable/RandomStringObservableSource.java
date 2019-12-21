package monitoring.observable;

import io.reactivex.Observer;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomStringObservableSource implements LogObservableSource {

    private volatile boolean isObserving;
    private int limit;
    private long delay;

    public RandomStringObservableSource(int limit, long delay) {
        this.limit = limit;
        this.delay = delay;
    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void subscribe(Observer<? super String> observer) {
        isObserving = true;

        executor.submit(() -> {
            try {
                System.out.println("Started Thread " + Thread.currentThread().getName());
                while(isObserving) {
                    String generatedString = RandomStringUtils.randomAlphanumeric(this.limit);
                    observer.onNext(generatedString);
                    Thread.sleep(delay);
                }
            }
            catch (InterruptedException e) {
                observer.onError(e);
            }
            observer.onComplete();
        });
    }

    public void unsubscribe() {
        try {
            System.out.println("Stopping Thread " + Thread.currentThread().getName());
            isObserving = false;
            this.executor.shutdownNow();
        } catch (Exception e) {
            System.out.println("Stopped thread...");
        }
    }



}
