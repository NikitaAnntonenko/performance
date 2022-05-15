package com.github.performance.strings.domain;

import lombok.EqualsAndHashCode;

import java.util.Locale;

@EqualsAndHashCode
public final class Key {
    private final String code;
    private final Locale locale;

    public Key(String code, Locale locale) {
        this.code = code;
        this.locale = locale;
    }
}
