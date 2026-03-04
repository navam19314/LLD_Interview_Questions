package com.conceptcoding.interviewquestions.elevator;

import java.util.Arrays;

public class Demo {

    public static void main(String[] args) {

        try {


            // 1. Create elevator cars and their controllers
            ElevatorCar car1 = new ElevatorCar(1);
            ElevatorController controller1 = new ElevatorController(car1);

            ElevatorCar car2 = new ElevatorCar(2);
            ElevatorController controller2 = new ElevatorController(car2);


            // 2. Create internal buttons for each elevator
            InternalButton internalButtonElevator1 = new InternalButton(controller1);
            InternalButton internalButtonElevator2 = new InternalButton(controller2);

            // 3. Create scheduler with Nearest Strategy
            ElevatorScheduler elevatorScheduler = new ElevatorScheduler(
                    Arrays.asList(controller1, controller2),
                    new NearestElevatorStrategy()
            );

            // 4. Create External Dispatcher
            ExternalDispatcher externalDispatcher = new ExternalDispatcher(elevatorScheduler);

            // 5. Create a 5-floor building
            Building building = new Building(5, externalDispatcher);

            // 6. Start both elevator controller threads
            new Thread(controller1, "Elevator-1").start();
            new Thread(controller2, "Elevator-2").start();




            // Submit elevator requests
            /*
                1. External Call: Floor 3 UP
                2. External Call: Floor 5 DOWN
                3. Internal Call: Elevator 1 (press 4)
                4. Internal Call: Elevator 1 (press 5)
                5. External Call: Floor 1 DOWN
                6. External Call: Floor 2 UP
             */

            building.getFloor(3).pressUpButton();
            Thread.sleep(5);

            building.getFloor(5).pressDownButton();
            Thread.sleep(5);

            internalButtonElevator1.pressButton(4); // User inside elevator 1 presses floor 4
            Thread.sleep(5);

            internalButtonElevator1.pressButton(5); // User inside elevator 1 presses floor 5
            Thread.sleep(5);

            building.getFloor(1).pressDownButton();
            Thread.sleep(5);

            building.getFloor(2).pressUpButton();
            Thread.sleep(5);

            internalButtonElevator1.pressButton(2); // User inside elevator 1 presses floor 2
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
