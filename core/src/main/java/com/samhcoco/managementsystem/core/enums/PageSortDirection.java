package com.samhcoco.managementsystem.core.enums;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public enum PageSortDirection {
    ASC,
    DESC;

    public static PageSortDirection fromString(String value) {
        try {
            if (isNotEmpty(value)) value = value.toUpperCase();
            return PageSortDirection.valueOf(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
