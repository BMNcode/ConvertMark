package org.bmn.model;

import java.util.Objects;

public class Mark {

    private String type;
    private String reference;
    private double positionX;
    private double positionY;

    public Mark() {
    }

    public Mark(String type, String reference, double positionX, double positionY) {
        this.type = type;
        this.reference = reference;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mark mark = (Mark) o;
        return Double.compare(mark.positionX, positionX) == 0 &&
                Double.compare(mark.positionY, positionY) == 0 &&
                Objects.equals(type, mark.type) &&
                Objects.equals(reference, mark.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, reference, positionX, positionY);
    }

    @Override
    public String toString() {
        return  "Type : " + type + "\t\t" +
                "Ref. : " + reference + "\t\t" +
                String.format("%-7s%7s", "Pos X : ", positionX) + "\t\t" +
                String.format("%-7s%7s", "Pos Y : ", positionY) + "\n";
    }
}
