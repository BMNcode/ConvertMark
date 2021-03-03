package org.bmn.controller;

import com.gembox.spreadsheet.ExcelColumnCollection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.*;
import org.bmn.logic.CreateOutput;
import org.bmn.logic.ParseFlexaTable;
import org.bmn.logic.Rotate;
import org.bmn.model.Mark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainController {
    public TableView mainTable;
    public RadioButton rotate90;
    public RadioButton rotate180;
    public RadioButton rotate270;
    public ToggleGroup gRotate;
    public TextField boardX;
    public TextField boardY;
    public TextArea outputText;
    public TableColumn colBoard;
    public TableColumn colType;
    public TableColumn colLevel;
    public TableColumn colRef;
    public TableColumn colPosX;
    public TableColumn colPosY;
    public TableColumn colMarkName;
    public TableColumn colMainMark;
    public TableColumn colSubMark;
    public TableColumn colSubMark1;
    public TableColumn colSubMark2;
    public TableColumn colSkip;
    public TableColumn colMemo;
    public TableColumn colTag;
    public TableColumn colOffsetY;
    public TableColumn colOffsetX;
    public TableColumn colAssign;
    private String bufferGlobal;
    private double nowAngle;

    public String getBufferGlobal() {
        return bufferGlobal;
    }

    public void setBufferGlobal(String bufferGlobal) {
        this.bufferGlobal = bufferGlobal;
    }

    //add flexa data in view table
    public void parseMainTable() {
        //обрабатываем отпуск клавишы Ctrl+V
        mainTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
            final KeyCodeCombination keyCodeCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);

            //при отпуске клавишы, содержимое буфера отображаем в таблице вьюшки
            @Override
            public void handle(KeyEvent event) {
                if (keyCodeCombination.match(event)) {
                    Clipboard systemClipboard = Clipboard.getSystemClipboard();
                    String buffer = systemClipboard.getString();
                    setBufferGlobal(buffer);

                    List<List<String>> clipboardDataTop = new ArrayList<>();

                    //разбиваем на строки
                    List<String> splitLineEnd = Arrays.asList(buffer.split("\\n"));
                    //разбиваем на колонки
                    for (String s : splitLineEnd) {
                        clipboardDataTop.add(Arrays.asList(s.split("\\t")));
                    }

                    //создаем таблицу
                    mainTable.getColumns().clear();



                        //добавляем содержимое в таблицу
                        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
                    for (int i = 1; i < clipboardDataTop.size(); i++) {
                        data.add(FXCollections.observableArrayList(clipboardDataTop.get(i)));
                    }

                    mainTable.setItems(data);

                    List<TableColumn> columns = Arrays.asList(colBoard, colType, colLevel, colRef,
                                                                colPosX, colPosY, colMarkName,
                                                                colMainMark, colSubMark, colSubMark1,
                                                                colSubMark2, colSkip, colMemo, colTag,
                                                                colOffsetY, colOffsetX, colAssign);


                    for (int i = 0; i < clipboardDataTop.get(0).size(); i++) {
                        final int currentColumn = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(ExcelColumnCollection.columnIndexToName(currentColumn));
                        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().get(currentColumn)));
                        column.setEditable(true);
                        column.setCellFactory(TextFieldTableCell.forTableColumn());
                        column.setOnEditCommit(
                                (TableColumn.CellEditEvent<ObservableList<String>, String> t) -> {
                                    t.getTableView().getItems().get(t.getTablePosition().getRow()).set(t.getTablePosition().getColumn(), t.getNewValue());
                                });
                        mainTable.getColumns().add(column);
                    }
                }
            }
        });
    }

    //generate result
    public void generateResult() {

        if (rotate90.isSelected()) {
            nowAngle = 90.0;
        } else if (rotate180.isSelected()) {
            nowAngle = 180.0;
        } else if (rotate270.isSelected()) {
            nowAngle = 270.0;
        }

        try {
            //getMark from buffer
            List<Mark> marks = ParseFlexaTable.getMark(bufferGlobal);
            //rotate coordinate mark
            for (Mark m : marks) {
                double tempX = m.getPositionX();
                double tempY = m.getPositionY();

                m.setPositionX(Rotate.rotateX(tempX,
                        tempY,
                        nowAngle,
                        Double.parseDouble(boardX.getText()),
                        Double.parseDouble(boardY.getText())));

                m.setPositionY(Rotate.rotateY(tempX,
                        tempY,
                        nowAngle,
                        Double.parseDouble(boardX.getText()),
                        Double.parseDouble(boardY.getText())));
            }

            //output result
            CreateOutput.outputLabel(marks, outputText);
        } catch (Exception e) {
            if (boardX.getText().isEmpty()) {
                outputText.setText("Panel size X = ???");

            }
            if (boardY.getText().isEmpty()) {
                outputText.setText("Panel size Y = ???");

            }
            if (mainTable.getItems().isEmpty()) {
                outputText.setText("Marks???");

            }
        }
    }


    //check input size board only float or digit
    public void inputSizeX(KeyEvent keyEvent) {

        Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        boardX.setTextFormatter(formatter);
    }

    public void inputSizeY(KeyEvent keyEvent) {
        Pattern pattern = Pattern.compile("\\d*|\\d+\\,\\d*|\\d+\\.\\d*");
        TextFormatter formatter = new TextFormatter((UnaryOperator<TextFormatter.Change>) change -> {
            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
        });

        boardY.setTextFormatter(formatter);
    }
}
