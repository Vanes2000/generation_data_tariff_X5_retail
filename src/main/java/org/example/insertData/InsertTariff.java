package org.example.insertData;

public interface InsertTariff {

    interface ContractZone {
        String INSERT = """
            INSERT INTO contract_zone_tariff (
                zone_id, doljnost_id, external_rate,
                `1_rate`, `2_rate`, `3_rate`, `4_rate`,
                type_from, from_id, date_from, date_to, date_create,
                is_active, account_id, company_id
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    }

    interface ExternalLevel {
        String INSERT = """
        INSERT INTO external_level_tariff (
            territory_id, zone_id, city_id, object_id,
            doljnost_id, external_rate,
            `1_rate`, `2_rate`, `3_rate`, `4_rate`,
            date_from, date_to, date_create,
            type_from, from_id, account_id, is_active
        ) VALUES (
            :territoryId, :zoneId, :cityId, :objectId,
            :doljnostId, :externalRate,
            :oneRate, :twoRate, :threeRate, :fourRate,
            :dateFrom, :dateTo, :dateCreate,
            :typeFrom, :fromId, :accountId, :isActive
        )
        """;
    }

    interface CoefficientLevel {
        String INSERT = """
        INSERT INTO coefficient_level_tariff (
            tariff_id, type_id, price,
            base_rate, `1_rate`, `2_rate`, `3_rate`, `4_rate`,
            category_id, coefficient, date_from, date_to, date_create,
            account_id, is_active
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    }

}
