package com.conceptcoding.interviewquestions.elevator;


public class InternalButton {

    private final ElevatorController controller;

    public InternalButton(ElevatorController controller) {
        this.controller = controller;
    }

    public void pressButton(int destinationFloor) {
        // We can also remove InternalDispatcher, but keeping it for:
        // 1. Validation purposes
        // 2. Similar code flow as ExternalButton
        InternalDispatcher.getInstance().submitInternalRequest(destinationFloor, controller);
    }
}