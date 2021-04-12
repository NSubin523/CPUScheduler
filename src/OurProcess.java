import java.sql.SQLOutput;

public class OurProcess{
    private int processNumber;
    private int arrivalTime;
    private int burstTime;
    private int timeQuantum;
    private int contextSwitch;
    private int waitTime;
    private int turnAroundTime;
    private int arrivalTimeInitial;
    private int burstTimeInitial;

    public OurProcess(int processNumber, int arrivalTime, int burstTime, int timeQuantum) {
        this.processNumber = processNumber;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        contextSwitch = 0;
        waitTime = 0;
        turnAroundTime = 0;
        arrivalTimeInitial = 0;
        burstTimeInitial = 0;
    }

    public int getProcessNumber() {return processNumber;}
    public void setArrivalTimeInitial() {arrivalTimeInitial = arrivalTime;}
    public int getArrivalTimeInitial() {return arrivalTimeInitial;}
    public int getArrivalTime() {return arrivalTime;}

    public void updateArrivalTime(int runTime){
        arrivalTime = arrivalTime - runTime;
        if(arrivalTime<0) arrivalTime = 0;
    }

    public void setBurstTimeInitial () {burstTimeInitial = burstTime;}
    public int getBurstTimeInitial() {return burstTimeInitial;}

    public void setPresentWaitTime(int runTime) {
        waitTime = waitTime + runTime;
    }
    public int getTotalWaitTime() {return waitTime;}

    public void updateBurstTimeRemaining() {
        burstTime = burstTime - timeQuantum;
        if (burstTime<0)    burstTime = 0;
    }
    public int getBurstTimeRemaining() {return burstTime;}

    public void updateContextSwitch() {
        contextSwitch++;
    }
    public int getContextSwitch() {return contextSwitch;}

    public void setTurnAroundTime() {
        turnAroundTime = this.getTotalWaitTime();
        this.getBurstTimeInitial();
    }
    public int getTurnAroundTime() {return turnAroundTime;}

    public int verifyIfFinished(){
        if (getBurstTimeRemaining() == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean verifyExecution() {
        return getArrivalTime() == 0;
    }

    public void getPresentProcessInfo() {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Process Name: "+processNumber);
        System.out.println("Remaining Burst Time: "+getBurstTimeRemaining());
        System.out.println("Time remaining to enter queue: "+getArrivalTime());
        System.out.println("Process ready to enter ready queue: "+ verifyExecution());
        System.out.println("Process context switch: "+contextSwitch);
        System.out.println("Total waiting time: "+getTotalWaitTime());
        System.out.println("Total Turn around time: "+getTurnAroundTime());
        System.out.println("--------------------------------------------------------------");
    }
}