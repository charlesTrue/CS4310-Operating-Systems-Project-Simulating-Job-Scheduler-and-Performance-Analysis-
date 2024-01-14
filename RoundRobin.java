import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class RoundRobin {
    public RoundRobin() {
    }

    double roundRobin(int roundRobinSlice) {
        System.out.println("Performing Round Robin with time slice " + roundRobinSlice + " or RR-" + roundRobinSlice + "\n");

        List<Job> jobs = new ArrayList<>();

        // Read the jobs from the file
        readJobFile(jobs);

        // Make a copy of the job list so we can keep adding to it
        List<Job> jobList = new ArrayList<>(jobs);

        int timer = 0;
        double averageTurnAroundTime = 0.0;
        int tasksRemoved = 0;
        int index = 0;

        // While we have tasks to be done still
        while (tasksRemoved != jobs.size()) {
            // Get the job at the current index of the list and set its starting time
            Job currentJob = jobList.get(index);
            currentJob.setStartTime(timer);

            // Does the current job not require all of the round robin time?
            if (currentJob.getBurstTime() - roundRobinSlice < 0) {
                // Add to the timer the burst time we have left
                timer += currentJob.getBurstTime();
                // Set the ending time and retire the job
                currentJob.setEndTime(timer);
                currentJob.setBurstTime(0);
                // Add to the average time and increment since a job was removed
                averageTurnAroundTime += currentJob.getEndTime();
                tasksRemoved++;
            }
            // Does it take all the time?
            else if (currentJob.getBurstTime() - roundRobinSlice == 0) {
                // Add the slice time to the timer since it used all the time
                timer += roundRobinSlice;
                // Retire the job and set its ending time
                currentJob.setBurstTime(0);
                currentJob.setEndTime(timer);
                // Add to the average time and increment since a job was removed
                averageTurnAroundTime += currentJob.getEndTime();
                tasksRemoved++;
            }
            // Does it have time left?
            else {
                // Increment timer since it used all the time
                timer += roundRobinSlice;
                // Take away the burst time by slice amount
                currentJob.setBurstTime(currentJob.getBurstTime() - roundRobinSlice);
                // The job will be switched out, so set the end time of the job to right now
                // and add it to the back of the list
                currentJob.setEndTime(timer);
                jobList.add(currentJob);
            }
            // Next job
            index++;
        }

        // Print the Schedule Table
        printScheduleTable(jobList);

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

            // If the job is completed print out that it completed @end time
            if (job.getBurstTime() == 0) {
                System.out.printf("%-19s%n", job.getName() + " completed at @" + job.getEndTime());
            } else {
                System.out.println();
            }
        }
        System.out.println();
    }
}
