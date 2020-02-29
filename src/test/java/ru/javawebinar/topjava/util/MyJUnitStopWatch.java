package ru.javawebinar.topjava.util;

import javassist.runtime.Desc;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MyJUnitStopWatch extends Stopwatch{

    private static final Logger log = LoggerFactory.getLogger(SpringJUnit4ClassRunner.class);
    public static Map<String, Long> methodsResults = new HashMap<>();

    private static void logInfo(String desc, String status, long nanos) {
        log.info(String.format("%s. Test %s, spent %d miliseconds",
                desc, status,TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void finished(long nanos, Description description) {
        methodsResults.put(description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        logInfo(description.getClassName(),"finished", nanos);
    }

    public static void methodsResult() {
        StringBuilder testsResult = new StringBuilder();
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_WHITE = "\u001B[37m";
        final String ANSI_CYAN = "\u001B[36m";
        testsResult.append(ANSI_GREEN);
        testsResult.append("\nClass Tests Summary Table");
        testsResult.append(ANSI_YELLOW);
        testsResult.append("\n№\t|\tTest Name\t|\tTime,ms");
        testsResult.append(ANSI_WHITE);
        testsResult.append("\n------------------------------");
        testsResult.append(ANSI_CYAN);
        AtomicInteger count = new AtomicInteger();
        methodsResults.forEach((k,v)->testsResult
                .append("\n")
                .append(count.incrementAndGet())
                .append("\t|\t")
                .append(k)
                .append("\t|\t")
                .append(v));
        testsResult.append(ANSI_WHITE);
        log.info(testsResult.toString());
    }
}
