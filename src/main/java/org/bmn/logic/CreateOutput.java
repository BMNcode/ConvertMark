package org.bmn.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.bmn.model.Mark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CreateOutput {

    public static void outputLabel  (List<Mark> src, TableView tableView) {

        for (Mark m : src) {
            m.setPositionX(BigDecimal.valueOf(m.getPositionX())
                    .setScale(3, RoundingMode.HALF_UP).doubleValue());

            m.setPositionY(BigDecimal.valueOf(m.getPositionY())
                    .setScale(3, RoundingMode.HALF_UP).doubleValue());
        }

        TableColumn<Mark, String> markTypeCol = new TableColumn<>("Type");
        TableColumn<Mark, String> markRefCol = new TableColumn<>("Ref.");
        TableColumn<Mark, String> markPosXCol = new TableColumn<>("Pos X");
        TableColumn<Mark, String> markPosYCol = new TableColumn<>("Pos Y");

        markTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        markRefCol.setCellValueFactory(new PropertyValueFactory<>("reference"));
        markPosXCol.setCellValueFactory(new PropertyValueFactory<>("positionX"));
        markPosYCol.setCellValueFactory(new PropertyValueFactory<>("positionY"));

        markPosXCol.setEditable(true);
        markPosYCol.setEditable(true);

        markPosXCol.setStyle("-fx-text-fill: green");
        markPosYCol.setStyle("-fx-text-fill: green");


        tableView.getColumns().addAll(markTypeCol, markRefCol, markPosXCol, markPosYCol);
        tableView.setEditable(true);

        ObservableList<Mark> data = FXCollections.observableArrayList(src);
        tableView.setItems(data);

    }
}
