import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.List;

public class Main {
    static final int NUM_TESTS = 1;
    static final int[] array = {5, 10, 15};//changes

    public static void main(String[] args) {
        FirstComeFirstServed firstComeFirstServed = new FirstComeFirstServed();
        ShortestJobFirst shortestJobFirst = new ShortestJobFirst();
        RoundRobin roundRobin = new RoundRobin();

        System.out.println();

        double totalFCFSTime = 0;
        double totalSJFTime = 0;
        double totalRoundRobin2Time = 0;
        double totalRoundRobin5Time = 0;

        for (int jobSize : array) {
            for (int j = 0; j < NUM_TESTS; j++) {
                generateRandomJobsInFile(jobSize);

                double average;

                average = firstComeFirstServed.firstComeFirstServedAlgorithm();
                System.out.println("The average turnaround time for FCFS is: " + average + "\n");
                totalFCFSTime += average;

                average = shortestJobFirst.shortestJobFirst();
                System.out.println("The average turnaround time for SJF is: " + average + "\n");
                totalSJFTime += average;

                average = roundRobin.roundRobin(2);
                System.out.println("The average turnaround time for RR-2 is: " + average + "\n");
                totalRoundRobin2Time += average;

                average = roundRobin.roundRobin(5);
                System.out.println("The average turnaround time for RR-5 is: " + average + "\n");
                totalRoundRobin5Time += average;
            }

            System.out.println("The average time for FCFS of size " + jobSize + ": " + totalFCFSTime / NUM_TESTS);
            System.out.println("The average time for SJF of size " + jobSize + ": " + totalSJFTime / NUM_TESTS);
            System.out.println("The average time for RR-2 of size " + jobSize + ": " + totalRoundRobin2Time / NUM_TESTS);
            System.out.println("The average time for RR-5 of size " + jobSize + ": " + totalRoundRobin5Time / NUM_TESTS);

            totalFCFSTime = 0;
            totalSJFTime = 0;
            totalRoundRobin2Time = 0;
            totalRoundRobin5Time = 0;

            System.out.println("----------End of Test--------------------\n\n");
            // Pause
            System.out.println("-----------------------------------------\n\n");
        }
    }

    static void generateRandomJobsInFile(int jobCount) {
        try {
            FileWriter jobTextFile = new FileWriter("job.txt");

            Random random = new Random();

            for (int i = 0; i < jobCount; i++) {
                jobTextFile.write("Job" + (i + 1) + "\n"); // Write Job name to the text file
                if (i < jobCount - 1) {
                    jobTextFile.write(random.nextInt(21) + "\n"); // write number and a newline, more to come
                } else {
                    jobTextFile.write(String.valueOf(random.nextInt(21))); // Last number to generate just output the number to the file
                }
            }

            jobTextFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

