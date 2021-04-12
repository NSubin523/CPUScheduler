public class CPU{
    private int timeQuantum;
    private int noOfCompletedProcesses;

    public CPU(int timeQuantum){
        this.timeQuantum = timeQuantum;
    }

    public int getNoOfCompletedProcesses() {return noOfCompletedProcesses;}

    public int run(OurProcess pr){
        if(pr.getBurstTimeRemaining() > timeQuantum){
            pr.updateBurstTimeRemaining();
            return timeQuantum;
        }
        else if (pr.getBurstTimeRemaining() < timeQuantum){
            int run = pr.getBurstTimeRemaining();
            pr.updateBurstTimeRemaining();
            noOfCompletedProcesses++;
            return run;
        }
        else{
            pr.updateBurstTimeRemaining();
            noOfCompletedProcesses++;
            return timeQuantum;
        }
    }
}