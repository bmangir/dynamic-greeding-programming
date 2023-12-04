package org.example;

import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {
        // YOUR CODE HERE

        String[] startTimeArray = getStartTime().split(":");
        int finishHour;
        String finishMinute = startTimeArray[1];
        if (startTimeArray[0].charAt(0) != '0') { // If time is like this -> 14:30
            finishHour = Integer.parseInt(startTimeArray[0]) + getDuration();
        }
        else { // If time is like this -> 02:30
            finishHour = Integer.parseInt(String.valueOf(startTimeArray[0].charAt(1))) + getDuration();
        }

        String stringFinishHour;
        if (finishHour < 10) {
            stringFinishHour = "0" + (finishHour);
        }
        else stringFinishHour = String.valueOf(finishHour);

        String finishTime = stringFinishHour + ":" + finishMinute;
        return finishTime;
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {
        // YOUR CODE HERE
        double weight = (((double) getImportance() * (isUrgent() ? 2000.0 : 1.0) ) / (double) getDuration());
        return weight;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE
        int result = getFinishTime().compareTo( ((Task) o).getFinishTime());
        if (result > 0) return 1;
        else if (result == 0) return 0;
        else return -1;
    }
}
