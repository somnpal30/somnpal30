package com.sample.natsproducer.utils;

import io.nats.client.*;
import io.nats.client.impl.ErrorListenerLoggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

public class NatsUtils {

    public static String getServer(String[] args) {
        if (args.length == 1) {
            return args[0];
        } else if (args.length == 2 && args[0].equals("-s")) {
            return args[1];
        }
        return Options.DEFAULT_URL;
    }

    private static Logger logger = LoggerFactory.getLogger(NatsUtils.class);
    public static final ConnectionListener NATS_CONNECTION_LISTENER = (conn, type) -> logger.info("Status change " + type);

    public static final ErrorListener NATS_ERROR_LISTENER = new ErrorListenerLoggerImpl();

    public static Options createNATSOptions(String[] args) {
        String server = getServer(args);
        return createNATSOptions(server, false, NATS_ERROR_LISTENER, NATS_CONNECTION_LISTENER);
    }

    public static Options createNATSOptions(String[] args, boolean allowReconnect) {
        String server = getServer(args);
        return createNATSOptions(server, allowReconnect, NATS_ERROR_LISTENER, NATS_CONNECTION_LISTENER);
    }

    public static Options createNATSOptions(String server) {
        return createNATSOptions(server, false, NATS_ERROR_LISTENER, NATS_CONNECTION_LISTENER);
    }

    public static Options createNATSOptions(String server, ErrorListener el) {
        return createNATSOptions(server, false, el, NATS_CONNECTION_LISTENER);
    }

    public static Options createNATSOptions(String server, boolean allowReconnect) {
        return createNATSOptions(server, allowReconnect, NATS_ERROR_LISTENER, NATS_CONNECTION_LISTENER);
    }

    public static Options createNATSOptions(String server, boolean allowReconnect, ErrorListener el, ConnectionListener cl) {

        if (el == null) {
            el = new ErrorListener() {
            };
        }

        Options.Builder builder = new Options.Builder()
                .server(server)
                .connectionTimeout(Duration.ofSeconds(5))
                .pingInterval(Duration.ofSeconds(10))
                .reconnectWait(Duration.ofSeconds(1))
                .connectionListener(cl)
                .errorListener(el);

        if (!allowReconnect) {
            builder = builder.noReconnect();
        } else {
            builder = builder.maxReconnects(-1);
        }

        if (System.getenv("NATS_NKEY") != null && System.getenv("NATS_NKEY") != "") {
            AuthHandler handler = null;
            try {
                handler = new NatsAuthHandler(System.getenv("NATS_NKEY"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            builder.authHandler(handler);
        } else if (System.getenv("NATS_CREDS") != null && System.getenv("NATS_CREDS") != "") {
            builder.authHandler(Nats.credentials(System.getenv("NATS_CREDS")));
        }
        return builder.build();
    }

    public static NatsArgs optionalServer(String[] args, String usageString) {
        NatsArgs ea = new NatsArgs(args, null, usageString);
        if (ea.containedUnknown) {
            usage(usageString);
        }
        return ea;
    }

    public static NatsArgs expectSubjectAndMessage(String[] args, String usageString) {
        NatsArgs ea = new NatsArgs(args, NatsArgs.Trail.MESSAGE, usageString);
        if (ea.containedUnknown || ea.message == null) {
            usage(usageString);
        }
        return ea;
    }

    public static NatsArgs expectSubjectAndMsgCount(String[] args, String usageString) {
        NatsArgs ea = new NatsArgs(args, NatsArgs.Trail.COUNT, usageString);
        if (ea.containedUnknown || ea.msgCount < 1) {
            usage(usageString);
        }
        return ea;
    }

    public static NatsArgs expectSubjectQueueAndMsgCount(String[] args, String usageString) {
        NatsArgs ea = new NatsArgs(args, NatsArgs.Trail.COUNT, usageString);
        if (ea.containedUnknown || ea.msgCount < 1) {
            usage(usageString);
        }
        return ea;
    }

    public static void sleep(long millis) {
        try {
            if (millis > 0) {
                Thread.sleep(millis);
            }
        } catch (InterruptedException e) {
            // ignore
        }
    }

    public static void sleepRandom(long millis) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextLong(millis));
        } catch (InterruptedException e) {
            // ignore
        }
    }

    private static void usage(String usageString) {
        logger.info(usageString);
        System.exit(-1);
    }

    public static String uniqueEnough() {
        return NUID.nextGlobal();
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length) {
            sb.append(Long.toHexString(ThreadLocalRandom.current().nextLong()));
        }
        return sb.substring(0, length);
    }
}
