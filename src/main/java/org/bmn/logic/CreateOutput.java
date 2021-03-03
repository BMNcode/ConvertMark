package org.bmn.logic;

import javafx.scene.control.TextArea;
import org.bmn.model.Mark;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class CreateOutput {

    public static void outputLabel  (List<Mark> src, TextArea textArea) {

        for (Mark m : src) {
            m.setPositionX(BigDecimal.valueOf(m.getPositionX())
                    .setScale(3, RoundingMode.HALF_UP).doubleValue());

            m.setPositionY(BigDecimal.valueOf(m.getPositionY())
                    .setScale(3, RoundingMode.HALF_UP).doubleValue());
        }

        String result = src.stream()
                .map(n -> String.valueOf(n))
                .collect(Collectors.joining("\n"));

        textArea.setText(result);

    }
}
