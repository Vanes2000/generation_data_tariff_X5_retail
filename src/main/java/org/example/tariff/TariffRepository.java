package org.example.tariff;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.TestData;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TariffRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Transactional
    public int insertContractZone(List<Integer> doljnostIds) {
        SqlParameterSource[] batch = doljnostIds.stream()
                .map(this::contractZoneParams)
                .toArray(SqlParameterSource[]::new);

        int[] result = jdbc.batchUpdate(InsertTariff.ContractZone.INSERT, batch);
        int total = sum(result);
        log.info("contract_zone_tariff: вставлено {} строк", total);
        return total;
    }

    @Transactional
    public int insertExternalLevel(List<Integer> doljnostIds) {
        SqlParameterSource[] batch = doljnostIds.stream()
                .map(this::externalLevelParams)
                .toArray(SqlParameterSource[]::new);

        int[] result = jdbc.batchUpdate(InsertTariff.ExternalLevel.INSERT, batch);
        int total = sum(result);
        log.info("external_level_tariff: вставлено {} строк", total);
        return total;
    }

    @Transactional
    public int insertCoefficients(List<Integer> doljnostIds) {
        int total = 0;

        for (Integer doljnostId : doljnostIds) {
            long tariffId = findExternalLevelId(doljnostId);

            SqlParameterSource[] batch = CoefficientTemplate.ROWS.stream()
                    .map(r -> coefficientParams(tariffId, r))
                    .toArray(SqlParameterSource[]::new);

            int[] result = jdbc.batchUpdate(InsertTariff.CoefficientLevel.INSERT, batch);
            int inserted = sum(result);

            if (inserted < batch.length) {
                log.warn("doljnostId={}, tariffId={}: вставлено {}/{} строк",
                        doljnostId, tariffId, inserted, batch.length);
            }
            total += inserted;
        }

        log.info("coefficient_level_tariff: вставлено {} строк", total);
        return total;
    }

    // --- helpers ---

    private MapSqlParameterSource contractZoneParams(int doljnostId) {
        return new MapSqlParameterSource()
                .addValue("zoneId", TestData.ZONE_ID)
                .addValue("doljnostId", doljnostId)
                .addValue("externalRate", TestData.EXTERNAL_RATE)
                .addValue("oneRate", TestData.ONE_RATE)
                .addValue("twoRate", TestData.TWO_RATE)
                .addValue("threeRate", TestData.THREE_RATE)
                .addValue("fourRate", TestData.FOUR_RATE)
                .addValue("typeFrom", TestData.TYPE_FROM_CONTRACT)
                .addValue("fromId", TestData.FROM_ID)
                .addValue("dateFrom", TestData.DATE_FROM)
                .addValue("dateTo", TestData.DATE_TO)
                .addValue("dateCreate", TestData.DATE_CREATE)
                .addValue("isActive", TestData.IS_ACTIVE)
                .addValue("accountId", TestData.ACCOUNT_ID)
                .addValue("companyId", TestData.COMPANY_ID);
    }

    private MapSqlParameterSource externalLevelParams(int doljnostId) {
        return new MapSqlParameterSource()
                .addValue("territoryId", TestData.TERRITORY_ID)
                .addValue("zoneId", TestData.ZONE_ID)
                .addValue("cityId", TestData.CITY_ID)
                .addValue("objectId", TestData.OBJECT_X5)
                .addValue("doljnostId", doljnostId)
                .addValue("externalRate", TestData.EXTERNAL_RATE)
                .addValue("oneRate", TestData.ONE_RATE)
                .addValue("twoRate", TestData.TWO_RATE)
                .addValue("threeRate", TestData.THREE_RATE)
                .addValue("fourRate", TestData.FOUR_RATE)
                .addValue("dateFrom", TestData.DATE_FROM)
                .addValue("dateTo", TestData.DATE_TO)
                .addValue("dateCreate", TestData.DATE_CREATE)
                .addValue("typeFrom", TestData.TYPE_FROM_EXTERNAL)
                .addValue("fromId", TestData.FROM_ID)
                .addValue("accountId", TestData.ACCOUNT_ID)
                .addValue("isActive", TestData.IS_ACTIVE);
    }

    private MapSqlParameterSource coefficientParams(long tariffId, CoefficientTemplate.Row r) {
        return new MapSqlParameterSource()
                .addValue("tariffId", tariffId)
                .addValue("typeId", r.typeId())
                .addValue("price", r.price())
                .addValue("baseRate", TestData.BASE_RATE)
                .addValue("oneRate", TestData.BASE_RATE)
                .addValue("twoRate", TestData.BASE_RATE)
                .addValue("threeRate", TestData.BASE_RATE)
                .addValue("fourRate", TestData.BASE_RATE)
                .addValue("categoryId", r.categoryId())
                .addValue("coefficient", r.coefficient())
                .addValue("dateFrom", TestData.DATE_FROM)
                .addValue("dateTo", TestData.DATE_TO)
                .addValue("dateCreate", TestData.DATE_CREATE)
                .addValue("accountId", TestData.ACCOUNT_ID)
                .addValue("isActive", TestData.IS_ACTIVE);
    }

    private long findExternalLevelId(int doljnostId) {
        Long id = jdbc.queryForObject("""
                SELECT id FROM external_level_tariff
                WHERE doljnost_id = :doljnostId
                  AND zone_id = :zoneId
                  AND from_id = :fromId
                ORDER BY id DESC
                LIMIT 1
                """,
                new MapSqlParameterSource()
                        .addValue("doljnostId", doljnostId)
                        .addValue("zoneId", TestData.ZONE_ID)
                        .addValue("fromId", TestData.FROM_ID),
                Long.class);

        if (id == null) {
            throw new IllegalStateException(
                    "Нет external_level_tariff для doljnost_id=" + doljnostId
                            + ", zone_id=" + TestData.ZONE_ID
                            + ", from_id=" + TestData.FROM_ID);
        }
        return id;
    }

    private static int sum(int[] arr) {
        int s = 0;
        for (int v : arr) s += v;
        return s;
    }
}