package adamsandor.memoryhog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MemoryHogApplication implements CommandLineRunner {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(MemoryHogApplication.class, args);
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String... args) throws Exception {
        if (env.getProperty("FEED_PINKY", Boolean.class, false)) {
            Pinky pinky = new Pinky("pinky",
                    env.getRequiredProperty("PINKY_CONSUME_MB", Integer.class),
                    env.getRequiredProperty("PINKY_RELEASE_AFTER_SEC", Integer.class),
                    env.getProperty("PINKY_CONSUME_RATE", Integer.class, 5));

            new Thread(pinky::feed).start();
        }

        if (env.getProperty("FEED_BACON", Boolean.class, false)) {
            Bacon bacon = new Bacon("bacon",
                    env.getRequiredProperty("BACON_CONSUME_UNTIL_MINUTES", Integer.class),
                    env.getRequiredProperty("BACON_CONSUME_RATE_MB_PER_SECOND", Integer.class));

            new Thread(bacon::feed).start();
        }


        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }
}
