package obsevable.test;

import monitoring.observable.FileObservableSource;
import monitoring.LogListener;
import monitoring.observable.RandomStringObservableSource;
import monitoring.observer.StandardStringObserver;

@org.testng.annotations.Test
public class Test {

    @org.testng.annotations.Test
    public void test() throws InterruptedException {

        LogListener logListener1 = LogListener.getInstance()
                .setObserver(new StandardStringObserver())
                .setObservableSource(new FileObservableSource("f:\\apache-tomcat-8.0.39_6.4.4\\logs\\localhost_access_log.2018-06-01.txt"));

        LogListener logListener2 = LogListener.getInstance()
                .setObserver(new StandardStringObserver())
                .setObservableSource(new RandomStringObservableSource(100, 250));

        LogListener logListener3 = LogListener.getInstance()
                .setObserver(new StandardStringObserver())
                .setObservableSource(new RandomStringObservableSource(100, 150));

        LogListener logListener4 = LogListener.getInstance()
                .setObserver(new StandardStringObserver())
                .setObservableSource(new RandomStringObservableSource(100, 500));

        System.out.println("Before first sleep");
        Thread.sleep(1000);
        System.out.println("After first sleep");

        logListener1.observe();
        logListener2.observe();
        logListener3.observe();
        logListener4.observe();

        for (int i=1; i<60; i++) {
            System.out.println("-----------");
            System.out.println("Doing some work in main thread " + i + " " + Thread.currentThread().getName());
            System.out.println("-----------");
            Thread.sleep(1000);
        }

        logListener1.stopObserving();
        logListener2.stopObserving();
        logListener3.stopObserving();
        logListener4.stopObserving();

        System.out.println("Got such strings from 1st observer: \n");
        logListener1.getStoredLogs().values().forEach(System.out::println);

        System.out.println("Got such strings from 2nd observer: \n");
        logListener2.getStoredLogs().values().forEach(System.out::println);

        System.out.println("Got such strings from 3d observer: \n");
        logListener3.getStoredLogs().values().forEach(System.out::println);

        System.out.println("Got such strings from 4th observer: \n");
        logListener4.getStoredLogs().values().forEach(System.out::println);

    }

}
