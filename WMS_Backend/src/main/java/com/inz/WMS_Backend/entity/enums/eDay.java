package com.inz.WMS_Backend.entity.enums;

public enum eDay {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(0);

    private final int value;

    eDay(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Method to get eDay from int value
     * @param value day represented as int
     * @return eDay object
     * @throws IllegalArgumentException if there is no such day (almost impossible)
     */
    public static eDay fromInt(int value) {
        value %= 7;
        for (eDay day : eDay.values()) {
            if (day.getValue() == value) {
                return day;
            }
        }
        throw new IllegalArgumentException("No such day");
    }
}
