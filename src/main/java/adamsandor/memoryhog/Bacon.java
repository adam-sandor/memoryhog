package adamsandor.memoryhog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.WeakHashMap;

public class Bacon {

    private int consumeUntilMinutes;

    private int consumeRateMbPerSec;

    private Logger logger;

    private WeakHashMap<Instant, byte[]> dataStore = new WeakHashMap<>();

    public Bacon(String name, int consumeUntilMinutes, int consumeRateMbPerSec) {
        this.consumeUntilMinutes = consumeUntilMinutes;
        this.consumeRateMbPerSec = consumeRateMbPerSec;
        logger = LoggerFactory.getLogger(name);
    }

    public void feed() {
        try {
            logger.info("Started consuming at a rate of {}MB per second", consumeRateMbPerSec);

            Instant t1 = Instant.now().plusSeconds(consumeUntilMinutes * 60);

            while (t1.isAfter(Instant.now())) {
                byte[] b = new byte[1024 * 1024 * consumeRateMbPerSec];
                dataStore.put(Instant.now(), b);
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
