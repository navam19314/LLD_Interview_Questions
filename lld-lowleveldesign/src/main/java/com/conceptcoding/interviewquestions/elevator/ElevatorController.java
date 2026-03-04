package com.conceptcoding.interviewquestions.elevator;

import com.conceptcoding.interviewquestions.elevator.enums.ElevatorDirection;

import java.util.concurrent.PriorityBlockingQueue;

public class ElevatorController implements Runnable {

    PriorityBlockingQueue<Integer> upMinPQ;
    PriorityBlockingQueue<Integer> downMaxPQ;

    ElevatorCar elevatorCar;

    private final Object monitor = new Object();

    ElevatorController(ElevatorCar elevatorCar) {
        this.elevatorCar = elevatorCar;
        upMinPQ = new PriorityBlockingQueue<>();
        // Max heap for down direction (highest floor first)
        downMaxPQ = new PriorityBlockingQueue<>(10, (a, b) -> b - a);
    }

    public void submitRequest(int destinationFloor) {
        enqueueRequest(destinationFloor);
    }

    private void enqueueRequest(int destinationFloor) {
        System.out.println("Request details -> destinationFloor: " + destinationFloor + 
                          " accepted by elevator: " + elevatorCar.id);

        // Skip if already at destination
        if (destinationFloor == elevatorCar.nextFloorStoppage) {
            return;
        }

        // Add to appropriate queue based on direction
        if (destinationFloor >= elevatorCar.nextFloorStoppage) {
            if (!upMinPQ.contains(destinationFloor)) {
                upMinPQ.offer(destinationFloor);
            }
        } else {
            if (!downMaxPQ.contains(destinationFloor)) {
                downMaxPQ.offer(destinationFloor);
            }
        }

        // Wake up elevator thread to process request
        synchronized (monitor) {
            monitor.notify();
        }
    }

    @Override
    public void run() {
        controlElevator();
    }

    public void controlElevator() {
        while (true) {
            // Wait if no requests - elevator goes to sleep
            synchronized (monitor) {
                while (upMinPQ.isEmpty() && downMaxPQ.isEmpty()) {
                    try {
                        System.out.println("Elevator: " + elevatorCar.id + " is IDLE");
                        elevatorCar.movingDirection = ElevatorDirection.IDLE;
                        monitor.wait(); // Sleep until request arrives
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            // Process all UP requests
            while (!upMinPQ.isEmpty()) {
                int floor = upMinPQ.poll();
                System.out.println("Serving floor: " + floor + " by elevator: " + 
                                 elevatorCar.id + " currentFloor: " + elevatorCar.currentFloor);
                elevatorCar.moveElevator(floor);
            }

            // Process all DOWN requests
            while (!downMaxPQ.isEmpty()) {
                int floor = downMaxPQ.poll();
                System.out.println("Serving floor: " + floor + " by elevator: " + 
                                 elevatorCar.id + " currentFloor: " + elevatorCar.currentFloor);
                elevatorCar.moveElevator(floor);
            }
        }
    }
}

