package com.futurum.marketing.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MoneyUtils {

    public static final BigDecimal ZERO = new BigDecimal("0.00");

    private MoneyUtils() {}

    public static BigDecimal normalizeMoney(BigDecimal value) {
        if (value == null) {
            return ZERO;
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
