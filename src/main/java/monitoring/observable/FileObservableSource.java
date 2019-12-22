package monitoring.observable;

import io.reactivex.Observer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileObservableSource implements LogObservableSource<String> {

    private final String filename;

    public FileObservableSource(String filename) {
        this.filename = filename;
    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void subscribe(Observer<? super String> observer) {

        try {
            executor.submit(() -> {

                System.out.println("Started Thread " + Thread.currentThread().getName());
                try {
                    Files.lines(Paths.get(filename)).forEach(observer::onNext);
                } catch (IOException e) {
                    observer.onError(e);
                }

                observer.onComplete();
                unsubscribe();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe() {
        try {
            System.out.println("Started Thread " + Thread.currentThread().getName());
            this.executor.shutdownNow();
        } catch (Exception e) {
            System.out.println("Stopped thread...");
        }
    }
}
