package com.example.Motto.MD.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

public enum CnhType {
    A("A"),
    B("B"),
    AB("AB");

    private final String value;

    CnhType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }

    public static CnhType fromValue(String value) {
        for (CnhType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid CnhType value: " + value + ". Valid values are: " + Arrays.toString(values()));
    }
}
