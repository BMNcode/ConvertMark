package org.bmn.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class NMainController {

    public Button rotateClick;

    public void onRotateDirect(ActionEvent event) {
        new RotateViewController().viewRotate();
    }
}
