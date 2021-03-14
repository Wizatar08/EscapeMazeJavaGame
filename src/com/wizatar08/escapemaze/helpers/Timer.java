package com.wizatar08.escapemaze.helpers;

import org.jetbrains.annotations.Contract;

public class Timer {
    private TimerModes mode;
    private float totalSeconds, startingSeconds;
    private int seconds, minutes, hours;
    private boolean paused;

    public Timer() {
        this(TimerModes.COUNT_UP);
    }

    public Timer(TimerModes mode) {
        this(mode, 0);
    }

    public Timer(int hours, int minutes, int seconds) {
        this(TimerModes.COUNT_DOWN, calculateTotalSeconds(hours, minutes, seconds));
    }

    public Timer(int totalSeconds) {
        this (TimerModes.COUNT_DOWN, totalSeconds);
    }

    public Timer(TimerModes mode, int hours, int minutes, int seconds) {
        this(mode, calculateTotalSeconds(hours, minutes, seconds));
    }

    public Timer(TimerModes mode, int totalSeconds) {
        this.mode = mode;
        this.totalSeconds = totalSeconds;
        this.startingSeconds = totalSeconds;
        this.paused = true;
    }

    public enum TimerModes {
        COUNT_UP,
        COUNT_DOWN,
    }

    private static int calculateTotalSeconds(int hours, int minutes, int seconds) {
        int hoursToSeconds = hours * 3600;
        int minutesToSeconds = minutes * 60;
        return hoursToSeconds + minutesToSeconds + seconds;
    }

    private static int totalHours(int totalSeconds) {
        return (int) Math.floor(totalSeconds / 3600);
    }

    private static int totalMinutes(int totalSeconds) {
        int m = (int) Math.floor(totalSeconds / 60);
        while (m >= 60) {
            m -= 60;
        }
        return m;
    }

    public String getString() {
        float seconds;
        int h = totalHours((int) totalSeconds);
        seconds = totalSeconds % 3600;
        int m = totalMinutes((int) totalSeconds);
        seconds = seconds % 60;
        return h + ":" + toTwoDigits(m) + ":" + toTwoDigits((int) Math.floor(seconds));
    }

    private String toTwoDigits(int num) {
        String numAsString = String.valueOf(num);
        if (numAsString.length() == 1) {
            numAsString = "0" + numAsString;
        }
        return numAsString;
    }

    public void update() {
        if (!paused) {
            if (mode == TimerModes.COUNT_DOWN) {
                totalSeconds -= Clock.Delta();
            } else if (mode == TimerModes.COUNT_UP) {
                totalSeconds += Clock.Delta();
            }
        }
    }

    public void pause() {
        paused = true;
    }

    public void unpause() {
        paused = false;
    }

    public void flipPause() {
        paused = !paused;
    }

    public void setTime(int totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    public void setTime(int hours, int minutes, int seconds) {
        this.totalSeconds = calculateTotalSeconds(hours, minutes, seconds);
    }

    public void reset() {
        this.paused = true;
        if (mode == TimerModes.COUNT_DOWN) {
            this.totalSeconds = startingSeconds;
        } else if (mode == TimerModes.COUNT_UP) {
            this.totalSeconds = 0;
        }
    }

    public boolean isAtTime(int totalSeconds) {
        return Math.floor(this.totalSeconds) == totalSeconds;
    }

    public boolean isAtTime(int hours, int minutes, int seconds) {
        return isAtTime(calculateTotalSeconds(hours, minutes, seconds));
    }

    public int getMinutes() {
        return totalMinutes((int) totalSeconds);
    }

    public int getHours() {
        return totalHours((int) totalSeconds);
    }

    public int getSeconds() {
        return (int) Math.floor(totalSeconds % 60);
    }

    public float getTotalSeconds() {
        return totalSeconds;
    }
}
