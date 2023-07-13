package com.maksnurgazy.consumerservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceType {
    INFORMATION("Information"),
    TRACE("Trace");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ServiceType fromValue(String value) {
        for (ServiceType serviceType : ServiceType.values()) {
            if (serviceType.value.equalsIgnoreCase(value)) {
                return serviceType;
            }
        }

        throw new IllegalArgumentException("Invalid ServiceType value: " + value);
    }
}