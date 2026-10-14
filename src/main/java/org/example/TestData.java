package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class TestData {

    private TestData() {}

    // ID
    public static final int TERRITORY_ID = 1;
    public static final int ZONE_ID = 1;
    public static final int CITY_ID = 1;
    public static final int COMPANY_ID = 1;
    public static final int ACCOUNT_ID = 1;
    public static final int OBJECT_X5 = 11;

    // Ставки
    public static final double EXTERNAL_RATE = 190.00;
    public static final double ONE_RATE = 209.00;
    public static final double TWO_RATE = 228.00;
    public static final double THREE_RATE = 247.00;
    public static final double FOUR_RATE = 266.00;
    public static final double BASE_RATE = 0.0;

    // Даты — сразу нужных типов, без valueOf
    public static final LocalDate DATE_FROM = LocalDate.of(2026, 1, 1);
    public static final LocalDate DATE_TO = LocalDate.of(2026, 12, 31);
    public static final LocalDateTime DATE_CREATE = LocalDateTime.of(2026, 12, 31, 0, 0);

    // Служебные — правильные типы
    public static final int TYPE_FROM_CONTRACT = 1;
    public static final int TYPE_FROM_EXTERNAL = 3;
    public static final int FROM_ID = 1;
    public static final boolean IS_ACTIVE = true;
}
