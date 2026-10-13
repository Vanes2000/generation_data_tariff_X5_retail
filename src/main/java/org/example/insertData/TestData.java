package org.example.insertData;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestData {

    private void TariffTestData() {}

    // ID
    public static final int TERRITORY_ID = 1;
    public static final int ZONE_ID = 1;
    public static final int CITY_ID = 12;
    public static final int COMPANY_ID = 1;
    public static final int ACCOUNT_ID = 1;
    public static final int OBJECT_X5 = 1; // для одного INSERT

    // Ставки
    public static final double EXTERNAL_RATE = 120.2;
    public static final double ONE_RATE = 220.2;
    public static final double TWO_RATE = 320.2;
    public static final double THREE_RATE = 420.2;
    public static final double FOUR_RATE = 520.2;
    public static final double BASE_RATE = 0.0;

    // Даты — как LocalDate/LocalDateTime, не String
    public static final LocalDate DATE_FROM = LocalDate.of(2026, 1, 1);
    public static final LocalDate DATE_TO = LocalDate.of(2026, 12, 31);
    public static final LocalDateTime DATE_CREATE = LocalDateTime.of(2026, 12, 31, 0, 0);

    // Служебные
    public static final String TYPE_FROM_CONTRACT = "1";
    public static final String TYPE_FROM_EXTERNAL = "3";
    public static final String FROM_ID = "1";
    public static final String IS_ACTIVE = "1";

}
