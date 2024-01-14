import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FirstComeFirstServed {
    public FirstComeFirstServed() {
    }

    double firstComeFirstServedAlgorithm() {
        System.out.println("Performing First Come First Serve Algorithm!\n");

        List<Job> jobs = new ArrayList<>();

        // Read the file to get the job information
        readJobFile(jobs);

        int timer = 0;
        double averageTurnAroundTime = 0.0;

        // For each job in the list
        for (Job currentJob : jobs) {
            // Make the job's start time based on the current timer
            currentJob.setStartTime(timer);

            // Add the burst time to the timer and retire the job
            timer += currentJob.getBurstTime();
            currentJob.setBurstTime(0);

            // End the job at this time and add the time to the averageTimer
            currentJob.setEndTime(timer);
            averageTurnAroundTime += currentJob.getEndTime();
        }

        // Print the Schedule Table
        printScheduleTable(jobs);

        // Return the average time
        return averageTurnAroundTime / jobs.size();
    }

    void readJobFile(List<Job> jobs) {
        try (BufferedReader inputFile = new BufferedReader(new FileReader("job.txt"))) {
            String line;
            while ((line = inputFile.readLine()) != null) {
                // Read in the job name
                String name = line;

                // Read in the burst time
                int burstTime = Integer.parseInt(inputFile.readLine());

                // Make the job
                Job job = new Job();
                job.setName(name);
                job.setBurstTime(burstTime);

                // Add it to the list
                jobs.add(job);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printScheduleTable(List<Job> jobs) {
        // Prints out the table headers
        System.out.printf("%-12s%-14s%-12s%-19s%n", "Job Name", "Start Time", "End Time", "Job Completion");

        // For each job in the job list
        for (Job job : jobs) {
            // Print out the name, starting time, end time
            System.out.printf("%-12s%-14s%-12s", job.getName(), job.getStartTime(), job.getEndTime());

            // If the job is completed, print out that it completed @end time
            if (job.getBurstTime() == 0) {
                System.out.printf("%-19s%n", job.getName() + " completed at @" + job.getEndTime());
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }
}
