package ru.javawebinar.topjava.util;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyJUnitStopWatch extends Stopwatch{

    private static final Logger log = LoggerFactory.getLogger(SpringJUnit4ClassRunner.class);
    public static Map<String, Long> methodsResults = new HashMap<>();

    private static void logInfo(String status, long nanos) {
        log.info(String.format("Test %s, spent %d miliseconds",
                status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void finished(long nanos, Description description) {
        methodsResults.put(description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        logInfo("finished", nanos);
    }

    public static void methodsResult() {
        log.info(methodsResults.toString());
    }
}
