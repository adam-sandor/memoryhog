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

    private MemoryHog pinky = new MemoryHog("pinky", 100, 60, 5);
    private MemoryHog bacon = new MemoryHog("bacon", 100, 0, 5);

    @Override
    public void run(String... args) throws Exception {
        pinky = new MemoryHog("pinky",
                env.getProperty("PINKY_CONSUME_MB", Integer.class, 0),
                env.getProperty("PINKY_RELEASE_AFTER_SEC", Integer.class, 0),
                env.getProperty("PINKY_CONSUME_RATE", Integer.class, 5));
        bacon = new MemoryHog("bacon",
                env.getProperty("BACON_CONSUME_MB", Integer.class, 0),
                env.getProperty("BACON_RELEASE_AFTER_SEC", Integer.class, 0),
                env.getProperty("BACON_CONSUME_RATE", Integer.class, 5));

        new Thread(() -> {
            pinky.feed();
        }).start();

        new Thread(() -> {
            bacon.feed();
        }).start();

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
