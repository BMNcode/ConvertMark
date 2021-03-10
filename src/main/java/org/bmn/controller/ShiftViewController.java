package org.bmn.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bmn.logic.CreateOutput;
import org.bmn.logic.ParseFlexaTable;
import org.bmn.logic.Rotate;
import org.bmn.logic.Shift;
import org.bmn.model.Mark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ShiftViewController {

    public TextField boardOutsideX;
    public TextField boardInnerX;
    public Label dangerLabel;
    public TableView shiftOutTable;
    public TableView shiftMainTable;
    public Label shiftDangerLabel;
    private String bufferGlobal;

    public String getBufferGlobal() {
        return bufferGlobal;
    }

    public void setBufferGlobal(String bufferGlobal) {
        this.bufferGlobal = bufferGlobal;
    }

    public void viewShift() {
        try {
            Stage stageViewShift = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/fxml/shiftView.fxml"));
            Parent root = loader.load();
            stageViewShift.setTitle("Shift");
            stageViewShift.setResizable(false);
            stageViewShift.setScene(new Scene(root));
            stageViewShift.initModality(Modality.APPLICATION_MODAL);
            stageViewShift.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //add flexa data in view table
    public void parseMainTable(MouseEvent mouseEvent) {
        //обрабатываем отпуск клавишы Ctrl+V
        shiftMainTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            final KeyCodeCombination keyCodeCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

            //при отпуске клавишы, содержимое буфера отображаем в таблице вьюшки
            @Override
            public void handle(KeyEvent event) {
                if (keyCodeCombination.match(event)) {
                    Clipboard systemClipboard = Clipboard.getSystemClipboard();
                    String buffer = systemClipboard.getString();
                    setBufferGlobal(buffer);

                    List<List<String>> clipboardDataTop = new ArrayList<>();


                    if (buffer.startsWith("Board")) {

                        //разбиваем на строки
                        List<String> splitLineEnd = Arrays.asList(buffer.split("\\n"));
                        //разбиваем на колонки
                        for (String s : splitLineEnd) {
                            clipboardDataTop.add(Arrays.asList(s.split("\\t")));
                        }

                        //добавляем содержимое в таблицу
                        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
                        for (int i = 1; i < clipboardDataTop.size(); i++) {
                            data.add(FXCollections.observableArrayList(clipboardDataTop.get(i)));
                        }

                        shiftMainTable.setItems(data);

                        List<TableColumn<ObservableList<String>, String>> columns = shiftMainTable.getColumns();

                        for (int i = 0; i < columns.size(); i++) {
                            final int currentColumn = i;
                            columns.get(i).setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(currentColumn)));
                            columns.get(i).setEditable(true);
                            columns.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
                            columns.get(i).setOnEditCommit(
                                    (TableColumn.CellEditEvent<ObservableList<String>, String> t) -> {
                                        t.getTableView().getItems().get(t.getTablePosition().getRow()).set(t.getTablePosition().getColumn(), t.getNewValue());
                                    });
                        }

                    } else {
                        shiftMainTable.getItems().clear();
                        shiftOutTable.getItems().clear();
                        shiftOutTable.setDisable(true);
                        shiftDangerLabel.setText("Marks???");
                    }
                }
            }
        });
    }

    public void generateResult(ActionEvent event) {
        shiftDangerLabel.setText("");
        shiftOutTable.setDisable(false);
        shiftOutTable.getColumns().clear();

        try {
            //getMark from buffer
            List<Mark> marks = ParseFlexaTable.getMark(bufferGlobal);
            //rotate coordinate mark
            for (Mark m : marks) {
                double tempX = m.getPositionX();

                m.setPositionX(Shift.shift(tempX,
                                           Double.parseDouble(boardOutsideX.getText()),
                                           Double.parseDouble(boardInnerX.getText())));
            }



            //output result
            CreateOutput.outputLabel(marks, shiftOutTable);
        } catch (Exception e) {
            if (boardInnerX.getText().isEmpty()) {
                shiftOutTable.setDisable(true);
                shiftDangerLabel.setText("Panel out size X = ???");

            }
            if (boardInnerX.getText().isEmpty()) {
                shiftOutTable.setDisable(true);
                shiftDangerLabel.setText("Panel inner size X = ???");

            }
            if (shiftMainTable.getItems().isEmpty()) {
                shiftOutTable.setDisable(true);
                shiftDangerLabel.setText("Marks???");

            }
        }
    }

    public void inputShiftOutSizeX(KeyEvent keyEvent) {
        Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        boardOutsideX.setTextFormatter(formatter);
    }

    public void inputShiftInnerSizeX(KeyEvent keyEvent) {
        Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        boardInnerX.setTextFormatter(formatter);
    }
}
