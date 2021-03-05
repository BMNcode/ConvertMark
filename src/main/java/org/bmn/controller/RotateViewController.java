package org.bmn.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class RotateViewController {

    public void viewRotate() {
        try {
//            Stage stageViewRotate = new Stage();
//            FXMLLoader loader = new FXMLLoader();
////            URL xmlUrl = getClass().getResource("/view/fxml/rotateView.fxml");
////            loader.setLocation(xmlUrl);
////            loader.setLocation(getClass().getResource("/view/fxml/rotateView.fxml"));
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            stageViewRotate.setScene(scene);
//            stageViewRotate.setResizable(false);
//            stageViewRotate.initStyle(StageStyle.UNIFIED);
//            stageViewRotate.setTitle("Rotation Mark");
//            stageViewRotate.show();
            Stage stageViewRotate = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/fxml/rotateView.fxml"));
            Parent root = loader.load();
            stageViewRotate.setTitle("Rotate");
            stageViewRotate.setResizable(false);
            stageViewRotate.initStyle(StageStyle.TRANSPARENT);
            stageViewRotate.setScene(new Scene(root));
            stageViewRotate.initModality(Modality.NONE);
            stageViewRotate.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
