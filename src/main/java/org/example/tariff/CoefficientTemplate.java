package org.example.tariff;

import java.util.List;

public final class CoefficientTemplate {

    private CoefficientTemplate() {}

    public record Row(int typeId, double price, int categoryId, double coefficient) {}

    public static final List<Row> ROWS = List.of(
            // type_id = 1 — Аванс/Зарплата
            new Row(1, 104.50, 0, 0.550),
            new Row(1, 114.95, 1, 0.550),
            new Row(1, 125.40, 2, 0.550),
            new Row(1, 135.85, 3, 0.550),
            new Row(1, 146.30, 4, 0.550),

            // type_id = 2 — Ежедневная
            new Row(2, 167.20, 0, 0.880),
            new Row(2, 183.92, 1, 0.880),
            new Row(2, 200.64, 2, 0.880),
            new Row(2, 217.36, 3, 0.880),
            new Row(2, 234.08, 4, 0.880)
    );
}
