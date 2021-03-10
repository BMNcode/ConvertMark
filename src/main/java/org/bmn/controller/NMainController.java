package org.bmn.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class NMainController {

    public Button rotateClick;
    public Button shiftClick;

    public void onRotateDirect(ActionEvent event) {
        new RotateViewController().viewRotate();
    }

    public void onShiftDirect(ActionEvent event) {
        new ShiftViewController().viewShift();
    }
}
