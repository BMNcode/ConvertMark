package org.bmn.logic;

import org.bmn.model.Mark;

import java.util.*;
import java.util.stream.Collectors;

public class ParseFlexaTable {

    //find all components buffer to List
    public static List<Mark> getMark(String buffer) {
        return Arrays.stream(buffer.split("\\n"))
                .filter(s -> !s.startsWith("Board"))
                .map(str -> Arrays.asList(str.split("\\t")))
                .map(getList -> new Mark(
                        getList.get(1),
                        getList.get(3),
                        Double.parseDouble(getList.get(4).replace(",", ".")),
                        Double.parseDouble(getList.get(5).replace(",", "."))))
                .collect(Collectors.toList());
    }

}
