package monitoring.observable;

import io.reactivex.Observer;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContinuousFileObservableSource implements LogObservableSource<String> {

    private final String filename;

    private volatile boolean isObserving = false;

    public ContinuousFileObservableSource(String filename) {
        this.filename = filename;
    }

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void subscribe(Observer<? super String> observer) {
        isObserving = true;
        executor.submit(() -> {

            while (!(new File(filename).isFile())) {
                System.out.println("File not found, skipping for 1 sec");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("Something went wrong");
                }
            }

            try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {

                System.out.println("Started Thread " + Thread.currentThread().getName());
                while (isObserving) {
                    try {
                        String line = reader.readLine();
                        if (line != null) {
                            observer.onNext(line);
                        }
                        Thread.sleep(10);
                    } catch (Exception e) {
                        System.out.println("Got exception while reading from file: " + e.getMessage());
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void unsubscribe() {
        try {
            isObserving = false;
            this.executor.shutdownNow();
        } catch (Exception e) {
            System.out.println("Stopped thread...");
        }
    }
}
