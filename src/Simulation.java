import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Simulation {
    private ArrayList<OurProcess> processArrayList;
    private int timeQuantum;
    private CPU cpu;
    private Clock clock;
    private Queue<OurProcess> readyQueue;
    private ArrayList<OurProcess> completedProcesses;
    private static int totalNoOfProcessesInSystem;

    public Simulation(ArrayList<OurProcess> processArrayList, int timeQuantum){
        this.processArrayList = processArrayList;
        this.timeQuantum = timeQuantum;
        cpu = new CPU(timeQuantum);
        readyQueue = new LinkedList<OurProcess>();
        clock = new Clock();
        completedProcesses = new ArrayList<OurProcess>();
        totalNoOfProcessesInSystem = 4;

        for (int i=0;i<processArrayList.size();i++){
            processArrayList.get(i).setArrivalTimeInitial();
            processArrayList.get(i).setBurstTimeInitial();
        }
    }

    public void executeRRSimulation(){
        processCreator(null);
        RoundRobin();
    }
    public void processCreator(OurProcess current){
        for(int i=0;i<processArrayList.size();i++){
            OurProcess pr = processArrayList.get(i);

            if(pr.verifyExecution() && pr.verifyIfFinished()==0 && pr!=current && !readyQueue.contains(pr)){
                readyQueue.add(pr);
                System.out.println("New Process "+pr.getProcessNumber()+" is being added to ready queue");
            }
        }
    }

    public void RoundRobin(){
        boolean check = false;
        while(!check) {
            OurProcess currentProcess = readyQueue.peek();
            if (currentProcess != null) {
                int runTimeOfProcess = cpu.run(currentProcess);
                clock.updateTimeWithSimulation(runTimeOfProcess);
                clock.updateTimeWaitingForReadyQueue(processArrayList, runTimeOfProcess);
                clock.updateWaitingTimeInReadyQueue(readyQueue, runTimeOfProcess, currentProcess);
                processCreator(currentProcess);

                if (currentProcess.verifyIfFinished() == 1) {
                    System.out.println("Process " + currentProcess.getProcessNumber() + " completed execution");
                    processArrayList.remove(currentProcess);
                    System.out.println("Process " + currentProcess.getProcessNumber() + " being removed from ready queue");
                    completedProcesses.add(readyQueue.remove());
                    if (completedProcesses.size() == totalNoOfProcessesInSystem) {
                        this.endSimulation();
                        check = true;
                    }
                }
                else{
                    currentProcess.updateContextSwitch();
                    currentProcess.getPresentProcessInfo();
                    readyQueue.remove();
                    readyQueue.add(currentProcess);
                    processCreator(currentProcess);
                }
            }
            else{
                clock.updateTimeWithSimulation(timeQuantum);
                clock.updateTimeWaitingForReadyQueue(processArrayList,timeQuantum);
                processCreator(null);
            }
        }
    }

    public void endSimulation(){
        int waitTimeOfProcessesTotal = 0;
        int turnAroundTimeOfProcessesTotal = 0;
        int contextSwitchesOfAllProcessesTotal = 0;
        int wait = 0;

        for (int i=0;i<completedProcesses.size();i++){
            completedProcesses.get(i).setTurnAroundTime();
        }
        int processOneTurnAround = completedProcesses.get(0).getTurnAroundTime();
        int processTwoTurnAround = completedProcesses.get(1).getTurnAroundTime();
        int processThreeTurnAround = completedProcesses.get(2).getTurnAroundTime();
        int processFourTurnAround = completedProcesses.get(3).getTurnAroundTime();

        int maxTurnAround = Math.max(processOneTurnAround,Math.max(processTwoTurnAround,Math.max(processThreeTurnAround,processFourTurnAround)));
        turnAroundTimeOfProcessesTotal = processOneTurnAround+processTwoTurnAround+processThreeTurnAround+processFourTurnAround;

        for(int i=0;i<completedProcesses.size();i++){
            wait += completedProcesses.get(i).getTotalWaitTime();
        }
        for (int i=0;i<completedProcesses.size();i++){
            waitTimeOfProcessesTotal = wait;
            contextSwitchesOfAllProcessesTotal += completedProcesses.get(i).getContextSwitch();
        }
        double avgWaitTime = waitTimeOfProcessesTotal/(double)totalNoOfProcessesInSystem;
        double avgTurnAroundTime = turnAroundTimeOfProcessesTotal/(double)totalNoOfProcessesInSystem;

        System.out.println();
        System.out.println("-----------RESULTS----------");
        System.out.println("Average Wait time: "+avgWaitTime);
        System.out.println("Average turn around time: "+avgTurnAroundTime);

        double finalThroughPut = ((double) cpu.getNoOfCompletedProcesses()/clock.getTime()) *100.0;
        double runTimeMinusContextSwitch = clock.getTime()-contextSwitchesOfAllProcessesTotal;
        double cpuUtilization = (runTimeMinusContextSwitch/clock.getTime())*100.0;
        System.out.println("CPU Utilization: "+cpuUtilization+" %");
    }
}
