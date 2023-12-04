package org.example;

import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        //YOUR CODE HERE
        int low = 0;
        int high = index - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (taskArray[mid].getFinishTime().compareTo(taskArray[index].getStartTime()) <= 0) {
                if (taskArray[mid+1].getFinishTime().compareTo(taskArray[index].getStartTime()) <= 0) {
                    low = mid + 1;
                }
                else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE
        for (int i = 0; i < taskArray.length; i++) {
            compatibility[i] = binarySearch(i);
        }

        System.out.println("Calculating max array\n---------------------");
        double tempResult = calculateMaxWeight(taskArray.length - 1);
        System.out.println();
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE
        calculateCompatibility();

        System.out.println("Calculating the dynamic solution\n" +
                "--------------------------------");
        solveDynamic(taskArray.length - 1);
        System.out.println();

        System.out.println("Dynamic Schedule\n" +
                "----------------");

        Collections.reverse(planDynamic);
        for (Task t : planDynamic) {
            System.out.println("At " + t.getStartTime() + ", " + t.getName() + ".");
        }

        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE

        if (i == -1) {
            return;
        }

        System.out.println("Called solveDynamic(" + i + ")");
        if (i ==0 ) { // Task 0 is the first task and its compatibility is -1 which is there is no any task
            planDynamic.add(taskArray[0]);
            return;
        }

        double includeTaskWeight = taskArray[i].getWeight();
        int compatibly = compatibility[i];
        if (compatibly != -1) {
            includeTaskWeight += maxWeight[compatibly];
        }
        double excludeTaskWeight = maxWeight[i - 1];
        if (includeTaskWeight >= excludeTaskWeight) {
            planDynamic.add(taskArray[i]);
            solveDynamic(compatibly);
        } else {
            solveDynamic(i - 1);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE

        if (i < 0) {
            System.out.println("Called calculateMaxWeight(-1)");
            return 0.0;
        }

        System.out.println("Called calculateMaxWeight(" + i + ")");
        if (maxWeight[i] != null) {
            if (i == 0) System.out.println("Called calculateMaxWeight(-1)");
            return maxWeight[i];
        }

        double includeCurrentTaskWeight = taskArray[i].getWeight();
        int compatible = compatibility[i];
        if (compatible == -1) {
            System.out.println("Called calculateMaxWeight(" + -1 + ")");
        }
        else {
            includeCurrentTaskWeight += calculateMaxWeight(compatible);
        }

        double excludeCurrentTaskWeight = calculateMaxWeight(i - 1);

        double result = Math.max(includeCurrentTaskWeight, excludeCurrentTaskWeight);
        maxWeight[i] = result;

        return result;
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE

        planGreedy.add(taskArray[0]); // First Task must be in the plan greedy for its algorithm

        for (int i = 1; i < taskArray.length; i++) {
            if (taskArray[i].getStartTime().compareTo( (planGreedy.get(planGreedy.size() - 1)).getFinishTime() ) >= 0) {
                planGreedy.add(taskArray[i]);
            }
        }

        System.out.println("Greedy Schedule\n" +
                "----------------");
        for (Task t : planGreedy) {
            System.out.println("At " + t.getStartTime() + ", " + t.getName() + ".");
        }

        return planGreedy;
    }
}
