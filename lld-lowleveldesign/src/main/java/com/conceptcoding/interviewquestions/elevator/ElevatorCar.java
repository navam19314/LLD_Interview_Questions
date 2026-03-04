package com.conceptcoding.interviewquestions.elevator;

import com.conceptcoding.interviewquestions.elevator.enums.ElevatorDirection;

public class ElevatorCar {

    int id;
    int currentFloor;
    int nextFloorStoppage;
    ElevatorDirection movingDirection;
    Door door;

    public ElevatorCar(int id) {
        this.id = id;
        currentFloor = 0;
        movingDirection = ElevatorDirection.IDLE;
        door = new Door();
    }

    public void showDisplay() {
        System.out.println("Elevator: " + id + " Current floor: " + currentFloor + 
                          " Direction: " + movingDirection);
    }

    public void moveElevator(int destinationFloor) {
        // This is a simplified elevator that moves directly to destination
        // regardless of current state or floor

        this.nextFloorStoppage = destinationFloor;
        
        // If already at destination, just open door
        if (this.currentFloor == nextFloorStoppage) {
            door.openDoor(id);
            return;
        }

        int startFloor = this.currentFloor;
        door.closeDoor(id);

        // Move UP
        if (nextFloorStoppage >= currentFloor) {
            movingDirection = ElevatorDirection.UP;
            showDisplay();
            
            // Start from next floor (startFloor+1) because we're already at startFloor
            // For example: moving from floor 1 to 2 means moving 1 floor, not 2
            for (int i = startFloor + 1; i <= nextFloorStoppage; i++) {
                try {
                    Thread.sleep(5); // Simulate floor-to-floor movement time
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setCurrentFloor(i);
                showDisplay();
            }
        } 
        // Move DOWN
        else {
            movingDirection = ElevatorDirection.DOWN;
            showDisplay();
            
            for (int i = startFloor - 1; i >= nextFloorStoppage; i--) {
                try {
                    Thread.sleep(5); // Simulate floor-to-floor movement time
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setCurrentFloor(i);
                showDisplay();
            }
        }
        
        door.openDoor(id);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }
}

