import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class Clock {
    private int time;
    public Clock(){
        time = 0;
    }
    public void updateTimeWithSimulation(int runTime){
        time += runTime;
    }
    public int getTime() {return time;}

    public void updateTimeWaitingForReadyQueue(ArrayList<OurProcess> listOfProcess, int runTime){
        for (int i=0; i<listOfProcess.size();i++){
            OurProcess pr = listOfProcess.get(i);
            pr.updateArrivalTime(runTime);
        }
    }
    public void updateWaitingTimeInReadyQueue(Queue<OurProcess> q, int runTime, OurProcess pr){
        Iterator<OurProcess> iterator = q.iterator();
        while(iterator.hasNext()){
            OurProcess temp = iterator.next();
            if(temp!=pr){
                temp.setPresentWaitTime(runTime);
            }
        }
    }
}
