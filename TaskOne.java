import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;




public class TaskOne {
// Zdes' ya ustanavlivayu svoystva potoka i kolichestvo pulov potokov.


        private static final int size_of_threads = 10000000, number_of_threads = 10;
        private static long counter1 = 0, counter2 = 0;
        private static final Object lock = new Object();
    
        public static void main(String[] args) throws InterruptedException {
            ExecutorService executor = Executors.newFixedThreadPool(number_of_threads);
    
    // etot sikl prednaznachen dlya vypolneniya vsex potokov potokov.

            for (int i = 0; i < size_of_threads; i++) {
                executor.submit(new CounterIncrementTask());
            }
    
    // Zdes' ExecutorService otklyuchaetsya.
            executor.shutdown();
    
    // Zdes' nam pridetsya podozhdat, poka vse potoki zakonchatsya.
            executor.awaitTermination(1, TimeUnit.MINUTES);
    
    //Eto dlya otkazaniya kolichestva schetchikov.
            System.out.println("Counter 1: " + counter1);
            System.out.println("Counter 2: " + counter2);
        }
        static class CounterIncrementTask implements Runnable {
            @Override
            public void run() {
    //Zdes' vypolnyayutsya operatsii inkrementa schetchikov bezopasno dlya potokov.
                synchronized (lock) {
    // Zdes' generiruyutsya sluchaynye chisla dlya inkrementa schetchikov. 
    // Eto    poddel'nye dannye, eto prosto dlya togo, chtoby schetchiki rabotali pravil'no.
                    Random rand = new Random();
                    int max=1000000,min=10;
                    int random_paycheck = rand.nextInt(max - min + 1) + min;
    
                    counter1 += random_paycheck;
                    counter2 += random_paycheck;
                }
            }
        }
}
