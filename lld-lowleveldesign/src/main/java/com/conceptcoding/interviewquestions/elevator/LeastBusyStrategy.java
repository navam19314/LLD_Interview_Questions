package com.conceptcoding.interviewquestions.elevator;

import com.conceptcoding.interviewquestions.elevator.enums.ElevatorDirection;

import java.util.List;

public class LeastBusyStrategy implements ElevatorSelectionStrategy {

    @Override
    public ElevatorController selectElevator(List<ElevatorController> controllers,
                                             int requestFloor,
                                             ElevatorDirection direction) {

        ElevatorController bestController = null;
        int minLoad = Integer.MAX_VALUE;

        // Find elevator with minimum pending requests
        for (ElevatorController controller : controllers) {
            int upQueueSize = controller.upMinPQ.size();
            int downQueueSize = controller.downMaxPQ.size();
            int totalLoad = upQueueSize + downQueueSize;

            if (totalLoad < minLoad) {
                minLoad = totalLoad;
                bestController = controller;
            }
        }
        
        return bestController;
    }
}
