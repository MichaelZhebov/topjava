package ru.javawebinar.topjava.util;

import java.util.concurrent.TimeUnit;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.service.MealServiceTest;

public class MyJUnitStopWatch extends Stopwatch{

    private static final Logger log = LoggerFactory.getLogger(MyJUnitStopWatch.class);

    private static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        log.info(String.format("Test %s, spent %d miliseconds",
                status, TimeUnit.NANOSECONDS.toMillis(nanos)));
        System.out.println(String.format("Test %s %s, spent %d miliseconds",
                testName, status, TimeUnit.NANOSECONDS.toMillis(nanos)));
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        logInfo(description, "failed", nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        logInfo(description, "skipped", nanos);
    }

    @Override
    protected void finished(long nanos, Description description) {
        logInfo(description, "finished", nanos);
    }
}
