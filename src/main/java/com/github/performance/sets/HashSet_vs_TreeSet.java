package com.github.performance.sets;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeSet;

import static com.github.performance.PerformanceUtil.measure;

/**
 * https://www.baeldung.com/java-hashset-vs-treeset
 * https://stackoverflow.com/questions/23168490/hashset-and-treeset-performance-test
 */
public class HashSet_vs_TreeSet {
    private static final DecimalFormat df = new DecimalFormat("#.#####");
    private static double hashTime, treeTime, hashToTreeTime;
    private static int numbers = 0;
    private static final boolean debugLog = true;
    private static final int numbersTo = 500;

    public static void main(String[] args){
        start();
        hashTime = hashSetTest();
        treeTime = treeSetTest();
        hashToTreeTime = fromHashToTreeSet();
        difference();
    }

    /**
     * in this method, instead of "System.exit(1)" i try'ed "start()" but when i did lets say three mistakes, after correct input the
     * methods hashSetTest();treeSetTest();fromHashToTreeSet();difference(); were running 4 times... i made this method exactly for the
     * purpose to repeat itself in case of a input mistake.
     */
    public static void start(){
        System.out.print("Enter a number(a advise bigger or equal to 50 000): ");
        Scanner scan = new Scanner(System.in);
        try{
            numbers = scan.nextInt();
        }catch(InputMismatchException e){
            System.out.println("ERROR: You need to enter a number");
            System.exit(1);
        }
        System.out.println(numbers + " numbers in range from 0-99 was randomly generated into " +
                "\n1)HashSet\n2)TreeSet\n3)HashSet and then moved to TreeSet");
    }

    public static long hashSetTest() {
        /**
         * i try'ed HashSet<Integer> hashSet = new HashSet<Integer>();
         * but changing the load factor didn't make any difference, what its good for then ?
         */
        return measure(debugLog, "HASH_SET:", () -> {
            HashSet<Integer> hashSet = new HashSet<>();
            for (int i = 0; i < numbers; i++) {
                hashSet.add((int)(Math.random() * numbersTo));
            }
        });
    }

    public static long treeSetTest(){
        return measure(debugLog, "TREE_SET:", () -> {
            TreeSet<Integer> treeSet = new TreeSet<>();
            for (int i = 0; i < numbers; i++) {
                treeSet.add((int)(Math.random() * numbersTo));
            }
        });
    }

    public static long fromHashToTreeSet(){
        return measure(debugLog, "FROM_HASH_SET_TO_TREE_SET:", () -> {
            HashSet<Integer> hashSet = new HashSet<>();
            for (int i = 0; i < numbers; i++) {
                hashSet.add((int)(Math.random() * numbersTo));
            }
            TreeSet<Integer> treeSet = new TreeSet<>(hashSet);
        });
    }

    public static void difference() {

        System.out.println();
        System.out.println("HashSet takes " + df.format(hashTime) + " msecs");
        System.out.println("TreeSet takes " + df.format(treeTime) + " msecs");
        System.out.println("TreeSet from HashSet takes " + df.format(hashToTreeTime) + " msecs");

        double differenceSec;
        double differenceTimes;

        if (hashTime < treeTime) {
            differenceSec = (treeTime - hashTime);
            differenceTimes = (treeTime / hashTime);
            System.out.println("\nHashSet takes " + df.format(differenceSec) + " msecs less then TreeSet, it is " + df.format(differenceTimes) + " times faster");
        } else {
            differenceSec = (hashTime - treeTime);
            differenceTimes = (hashTime / treeTime);
            System.out.println("\nTreeSet takes " + df.format(differenceSec) + " msecs less then HashSet, it is " + df.format(differenceTimes) + " times faster");
        }
    }
}
