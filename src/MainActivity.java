import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("How many quantums you want to test? ");
        int counter = input.nextInt();
        int count_index = 1;
        while (count_index<=counter) {
            System.out.print("Enter time quantum number "+count_index+" : ");
            int timeQuantum = input.nextInt();
            System.out.print("Enter file name: ");
            ArrayList<OurProcess> pr = new ArrayList<>();
            try {
                String fName = input.next();
                File inFile = new File(fName);
                Scanner fileInput = new Scanner(inFile);
                while (fileInput.hasNextLine()) {
                    String processes = fileInput.nextLine();
                    String[] lineArray = processes.split(",");
                    int processNumber = Integer.parseInt(lineArray[0]);
                    int arrivalTime = Integer.parseInt(lineArray[1]);
                    int burstTime = Integer.parseInt(lineArray[2]);
                    pr.add(new OurProcess(processNumber, arrivalTime, burstTime, timeQuantum));
                }
            } catch (FileNotFoundException e) {
                System.out.println("No file name found");
            }

            Simulation s = new Simulation(pr, timeQuantum);
            s.executeRRSimulation();
            System.out.println("End of simulation");
            count_index++;
        }
    }

}
