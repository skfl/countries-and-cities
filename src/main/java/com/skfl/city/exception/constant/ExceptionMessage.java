package com.skfl.city.exception.constant;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

    CITY_NOT_FOUND_MESSAGE("There is no such city");

    private final String value;

    ExceptionMessage(String value) {
        this.value = value;
    }
}
