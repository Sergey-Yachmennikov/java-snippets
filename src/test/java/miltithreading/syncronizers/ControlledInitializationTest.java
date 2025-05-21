package miltithreading.syncronizers;

import org.junit.jupiter.api.Test;

class ControlledInitializationTest {

    @Test
    void controlledInitialization() {
        ControlledInitialization control = new ControlledInitialization();
        control.doTask();
    }

}