package com.conceptcoding.interviewquestions.elevator;

import com.conceptcoding.interviewquestions.elevator.enums.ElevatorDirection;

import java.util.List;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public ElevatorController selectElevator(List<ElevatorController> controllers,
                                             int requestFloor,
                                             ElevatorDirection direction) {

        ElevatorController bestController = null;
        int minDistance = Integer.MAX_VALUE;

        // Step 1: Find elevator going in same direction with minimum distance
        for (ElevatorController controller : controllers) {
            int nextFloorStoppage = controller.elevatorCar.nextFloorStoppage;
            ElevatorDirection elevatorDirection = controller.elevatorCar.movingDirection;

            // Check if elevator is moving in same direction
            boolean isSameDirection = (elevatorDirection == direction);
            
            // Check if elevator hasn't passed the requested floor
            boolean hasNotPassedFloor = false;
            if (direction == ElevatorDirection.UP && nextFloorStoppage <= requestFloor) {
                hasNotPassedFloor = true;
            } else if (direction == ElevatorDirection.DOWN && nextFloorStoppage >= requestFloor) {
                hasNotPassedFloor = true;
            }

            boolean isGoodCandidate = isSameDirection && hasNotPassedFloor;
            int distance = Math.abs(nextFloorStoppage - requestFloor);

            if (isGoodCandidate && distance < minDistance) {
                minDistance = distance;
                bestController = controller;
            }
        }

        // Step 2: Fallback - pick idle elevator if no same-direction elevator found
        if (bestController == null) {
            for (ElevatorController controller : controllers) {
                if (controller.elevatorCar.movingDirection == ElevatorDirection.IDLE) {
                    bestController = controller;
                    break;
                }
            }

            // Step 3: Final fallback - pick any elevator if none is idle
            if (bestController == null) {
                bestController = controllers.get(0);
            }
        }
        
        return bestController;
    }
}
