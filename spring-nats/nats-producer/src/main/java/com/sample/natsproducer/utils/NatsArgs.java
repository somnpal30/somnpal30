package com.sample.natsproducer.utils;

import io.nats.client.Options;
import io.nats.client.impl.Headers;

public class NatsArgs {

    public enum Trail {MESSAGE, COUNT, QUEUE_AND_COUNT}

    private String title;
    public String server = Options.DEFAULT_URL;
    public String subject;
    public String queue;
    public String message;
    public int msgCount = Integer.MIN_VALUE;
    public int msgSize = Integer.MIN_VALUE;
    private boolean msgCountUnlimitedFlag;
    public int subCount = Integer.MIN_VALUE;
    public String stream;
    public String mirror;
    public String consumer;
    public String durable;
    public String deliverSubject;
    public int pullSize = Integer.MIN_VALUE;
    public Headers headers;
    public boolean containedUnknown = false;
    public String bucket;
    public String description;

    public boolean hasHeaders() {
        return headers != null && !headers.isEmpty();
    }

    private NatsArgs(String title) {
        this.title = title;
    }

    public NatsArgs(String[] args, Trail trail, String usageString) {
        parse(args, trail, usageString);
    }

    public void parse(String[] args, Trail trail, String usageString) {
        try {
            if (args != null) {
                String lastKey = null;
                for (int x = 0; x < args.length; x++) {
                    String arg = args[x];
                    if (arg.startsWith("-")) {
                        if (++x >= args.length) {
                            usageThenExit(usageString);
                        }
                        handleKeyedArg(arg, args[x]);
                        lastKey = arg;
                    }
                    else if (trail == null) {
                        if (lastKey != null) {
                            handleKeyedArg(lastKey, arg);
                        }
                    }
                    else {
                        handleTrailingArg(trail, arg);
                        lastKey = null;
                    }
                }
            }
        } catch (RuntimeException e) {
            System.err.println("Exception while processing command line arguments: " + e + "\n");
            usageThenExit(usageString);
        }
    }

    private void handleTrailingArg(Trail trail, String arg) {
        if (subject == null) { // subject always the first expected
            subject = arg;
        }
        else if (trail == Trail.MESSAGE) {
            if (message == null) {
                message = arg;
            }
            else {
                message = message + " " + arg;
            }
        }
        else if (trail == Trail.QUEUE_AND_COUNT) {
            if (queue == null) {
                queue = arg;
            }
            else {
                msgCount = Integer.parseInt(arg);
            }
        }
        else { // Expect.COUNT
            msgCount = Integer.parseInt(arg);
        }
    }

    private void handleKeyedArg(String key, String value) {
        switch (key) {
            case "-s" -> server = value;
            case "-sub" -> subject = value;
            case "-q" -> queue = value;
            case "-m" -> {
                if (message == null) {
                    message = value;
                } else {
                    message = message + " " + value;
                }
            }
            case "-con" -> consumer = value;
            case "-strm" -> stream = value;
            case "-mir" -> mirror = value;
            case "-pull" -> pullSize = Integer.parseInt(value);
            case "-mcnt" -> msgCount = Integer.parseInt(value);
            case "-msize" -> msgSize = Integer.parseInt(value);
            case "-scnt" -> subCount = Integer.parseInt(value);
            case "-dur" -> durable = value;
            case "-buk" -> bucket = value;
            case "-desc" -> description = value;
            case "-deliver" -> deliverSubject = value;
            case "-r" -> {
                if (headers == null) {
                    headers = new Headers();
                }
                String[] hdr = value.split(":");
                headers.add(hdr[0], hdr[1]);
            }
            default -> containedUnknown = true;
        }
    }

    public void displayBanner() {
        if (title == null) {
            System.out.println("\nExample");
        }
        else {
            System.out.format("\n%s Example\n", title);
        }

        _banner("server", server);
        _banner("stream", stream);
        _banner("subject", subject);
        _banner("bucket", bucket);
        _banner("description", description);
        _banner("queue", queue);
        _banner("message", message);
        _banner("mirror", mirror);
        _banner("consumer", consumer);
        _banner("durable", durable);
        _banner("deliver", deliverSubject);
        _banner("msgCount", msgCount, msgCountUnlimitedFlag);
        _banner("msgSize", msgSize);
        _banner("subCount", subCount);
        _banner("pullSize", pullSize);
        _banner("Headers", headers == null || headers.isEmpty() ? Integer.MIN_VALUE : headers.size());
        System.out.println();
    }

    private void _banner(String label, String value) {
        if (value != null) {
            System.out.format("  %s: %s\n", label, value);
        }
    }

    private void _banner(String label, int value) {
        _banner(label, value, false);
    }

    private void _banner(String label, int value, boolean unlimited)
    {
        if (unlimited && (value == Integer.MAX_VALUE || value < 1)) {
            System.out.format("  %s: Unlimited\n", label);
        }
        else if (value > Integer.MIN_VALUE) {
            System.out.format("  %s: %s\n", label, value);
        }
    }

    public static Builder builder(String title, String[] args, String usage) {
        return new Builder(title, args, usage);
    }

    public static class Builder {

        private final NatsArgs ea;
        private final String[] args;
        private final String usage;

        public Builder(String title, String[] args, String usage)
        {
            ea = new NatsArgs(title);
            this.args = args;
            this.usage = usage;
        }

        public Builder defaultSubject(String subject) {
            ea.subject = subject;
            return this;
        }

        public Builder defaultQueue(String queue) {
            ea.queue = queue;
            return this;
        }

        public Builder defaultMessage(String message) {
            ea.message = message;
            return this;
        }

        public Builder defaultMsgCount(int msgCount) {
            return defaultMsgCount(msgCount, false);
        }

        public Builder defaultMsgCount(int msgCount, boolean unlimitedFlag) {
            ea.msgCount = msgCount;
            ea.msgCountUnlimitedFlag = unlimitedFlag;
            return this;
        }

        public Builder defaultMsgSize(int msgSize) {
            ea.msgSize = msgSize;
            return this;
        }

        public Builder defaultSubCount(int subCount) {
            ea.subCount = subCount;
            return this;
        }

        public Builder defaultStream(String stream) {
            ea.stream = stream;
            return this;
        }

        public Builder defaultMirror(String mirror) {
            ea.mirror = mirror;
            return this;
        }

        public Builder defaultDurable(String durable) {
            ea.durable = durable;
            return this;
        }

        public Builder defaultBucket(String bucket) {
            ea.bucket = bucket;
            return this;
        }

        public Builder defaultDescription(String description) {
            ea.description = description;
            return this;
        }

        public Builder defaultDeliverSubject(String deliver) {
            ea.deliverSubject = deliver;
            return this;
        }

        public Builder defaultPullSize(int pullSize) {
            ea.pullSize = pullSize;
            return this;
        }

        public NatsArgs build() {
            ea.parse(args, null, usage);
            if (ea.containedUnknown && usage != null) {
                usageThenExit(usage);
            }
            ea.displayBanner();
            return ea;
        }
    }

    private static void usageThenExit(String usageString) {
        if (usageString != null) {
            System.out.println(usageString);
        }
        System.exit(-1);
    }
}
