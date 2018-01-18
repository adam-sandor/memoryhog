package adamsandor.memoryhog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class MemoryHog {

    private List<byte[]> data = Collections.synchronizedList(new LinkedList<>());

    private int consumeMb;

    private int releaseAfterSec;

    private int consumeRate;

    private Logger logger;

    public MemoryHog(String name, int consumeMb, int releaseAfterSec, int consumeRate) {
        this.consumeMb = consumeMb;
        this.releaseAfterSec = releaseAfterSec;
        this.consumeRate = consumeRate;
        this.logger = LoggerFactory.getLogger(name);
    }

    public void feed() {
        try {
            if (consumeMb == 0) return;

            logger.info("Started consuming {}MB at a rate of {}MB per second", consumeMb, consumeRate);

            while (true) {
                if (data.size() <= consumeMb) {
                    data.add(new byte[1024 * 1024]);
                } else {
                    logger.info("Finished consuming {}MB of memory", data.size());
                    if (releaseAfterSec > 0) {
                        logger.info("Releasing memory after {} sec", releaseAfterSec);
                        Thread.sleep(releaseAfterSec * 1000);
                        data.clear();
                        logger.info("Released memory");
                    }
                    break;
                }

                Thread.sleep(1000 / consumeRate);
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
