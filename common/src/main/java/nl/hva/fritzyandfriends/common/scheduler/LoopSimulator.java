package nl.hva.fritzyandfriends.common.scheduler;

import nl.hva.fritzyandfriends.common.TransactionConfirmation;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class functions as a demo for looping certain behavior of Fritzy, Batty and Sunny. You can use the Scheduler
 * class to change the behavior of the loops.
 *
 * In each loop, the /relayData endpoint of the respective actor is called. This endpoint sends information to
 * LocalNetty's inbox. This information is eventually used by the transaction loop. This is where LocalNetty takes
 * the most recent information from its inbox and determines if it should buy or sell electricity.
 *
 * You can run the main function of this class when all actors are started.
 */
public class LoopSimulator {

    private static int fritzyCounter = 0;
    private static int battyCounter = 0;
    private static int sunnyCounter = 0;

    private static int fritzyAmount = 0;
    private static int battyAmount = 0;
    private static int sunnyAmount = 0;

    public static void main(String[] args) {
        loopFritzy();
        loopBatty();
        loopSunny();

        loopTransaction();
    }

    private static void loopFritzy() {
        Scheduler.loop(() -> {
            try {
                Random random = new Random();
                fritzyAmount = random.nextInt(400) + 300;
                System.out.println("Sending Fritzy data " + ++fritzyCounter + ": " + fritzyAmount);
                getApi("http://localhost:8082").fritzyRelayData(fritzyAmount).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void loopBatty() {
        Scheduler.loop(() -> {
            try {
                Random random = new Random();
                battyAmount = random.nextInt(300) + 200;
                System.out.println("Sending Batty data " + ++battyCounter + ": " + battyAmount);
                getApi("http://localhost:8083").battyRelayData(battyAmount).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void loopSunny() {
        Scheduler.loop(() -> {
            try {
                Random random = new Random();
                sunnyAmount = random.nextInt(200) + 100;
                System.out.println("Sending Sunny data " + ++sunnyCounter + ": " + sunnyAmount);
                getApi("http://localhost:8084").sunnyRelayData(sunnyAmount).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void loopTransaction() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Executing transaction...");
            
            try {
                TransactionConfirmation result = getApi("http://localhost:8081").executeTransaction().execute().body();
                if (result != null) {
                    System.out.println(result.getDescription() + " " + result.getTransactionType() + " " + result.getKwh());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 15, 25, TimeUnit.SECONDS);
    }

    private static Api getApi(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(Api.class);
    }

}