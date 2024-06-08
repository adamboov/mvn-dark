package com.adam.dark.base.enmus;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author adamboov
 */
@AllArgsConstructor
public enum SexTypeEnum {
    /**
     * 未知
     */
    UNKNOWN("未知"),
    /**
     * 男
     */
    MALE("男"),
    /**
     * 女
     */
    FEMALE("女");

    private String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
