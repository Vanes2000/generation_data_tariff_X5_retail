package org.example.insertData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getData.GetDoljnost;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TariffRepository {

    private final SqlExecutor sqlExecutor;
    private final GetDoljnost getDoljnost;
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public int insertContractZoneForAllDoljnosti() {
        int total = 0;

        for (Integer doljnostId : getDoljnost.getDoljnostIds()) {
            total += sqlExecutor.insert(InsertTariff.ContractZone.INSERT,
                    TestData.ZONE_ID,
                    doljnostId,
                    TestData.EXTERNAL_RATE,
                    TestData.ONE_RATE,
                    TestData.TWO_RATE,
                    TestData.THREE_RATE,
                    TestData.FOUR_RATE,
                    TestData.TYPE_FROM_CONTRACT,
                    TestData.FROM_ID,
                    Date.valueOf(TestData.DATE_FROM),
                    Date.valueOf(TestData.DATE_TO),
                    Timestamp.valueOf( TestData.DATE_CREATE.atStartOfDay ( ) ),
                    TestData.IS_ACTIVE,
                    TestData.ACCOUNT_ID,
                    TestData.COMPANY_ID
            );
        }

        log.info("contract_zone_tariff: вставлено {} строк", total);
        return total;
    }

    @Transactional
    public int insertExternalLevelForAllDoljnosti() {
        int total = 0;

        for (Integer doljnostId : getDoljnost.getDoljnostIds()) {
            total += insertExternalLevel(doljnostId);
        }

        log.info("external_level_tariff: вставлено {} строк", total);
        return total;
    }

    private int insertExternalLevel(int doljnostId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
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
                .addValue("dateFrom", Date.valueOf(TestData.DATE_FROM))
                .addValue("dateTo", Date.valueOf(TestData.DATE_TO))
                .addValue("dateCreate", Timestamp.valueOf( TestData.DATE_CREATE.atStartOfDay ( ) ))
                .addValue("typeFrom", TestData.TYPE_FROM_EXTERNAL)
                .addValue("fromId", TestData.FROM_ID)
                .addValue("accountId", TestData.ACCOUNT_ID)
                .addValue("isActive", TestData.IS_ACTIVE);

        return sqlExecutor.insert(InsertTariff.ExternalLevel.INSERT, params);
    }

    @Transactional
    public int insertCoefficientsForAllDoljnosti() {
        int total = 0;

        for (Integer doljnostId : getDoljnost.getDoljnostIds()) {
            long tariffId = findExternalLevelId(doljnostId);

            List<Object[]> rows = buildCoefficientRows(tariffId);
            int[] result = sqlExecutor.insertBatch(
                    InsertTariff.CoefficientLevel.INSERT, rows);

            int inserted = 0;
            for (int r : result) {
                inserted += r;
            }

            if (inserted < rows.size()) {
                log.warn("doljnostId={}, tariffId={}: вставлено {}/{} строк",
                        doljnostId, tariffId, inserted, rows.size());
            }

            total += inserted;
        }

        log.info("coefficient_level_tariff: вставлено {} строк РЕАЛЬНО", total);
        return total;
    }

    private long findExternalLevelId(int doljnostId) {
        Long id = jdbcTemplate.query(
                """
                SELECT id FROM external_level_tariff
                WHERE doljnost_id = ?
                  AND zone_id = ?
                  AND from_id = ?
                ORDER BY id DESC
                LIMIT 1
                """,
                rs -> rs.next() ? rs.getLong("id") : null,
                doljnostId,
                TestData.ZONE_ID,
                TestData.FROM_ID
        );

        if (id == null) {
            throw new IllegalStateException(
                    "Нет external_level_tariff для doljnost_id=" + doljnostId
                            + ", zone_id=" + TestData.ZONE_ID
                            + ", from_id=" + TestData.FROM_ID);
        }
        return id;
    }

    private List<Object[]> buildCoefficientRows(long tariffId) {
        List<Object[]> rows = new ArrayList<>(CoefficientTemplate.ROWS.size());

        for (CoefficientTemplate.Row r : CoefficientTemplate.ROWS) {
            rows.add(new Object[]{
                    tariffId,
                    r.typeId(),
                    r.price(),
                    TestData.BASE_RATE,
                    TestData.BASE_RATE,
                    TestData.BASE_RATE,
                    TestData.BASE_RATE,
                    TestData.BASE_RATE,
                    r.categoryId(),
                    r.coefficient(),
                    Date.valueOf(TestData.DATE_FROM),
                    Date.valueOf(TestData.DATE_TO),
                    Timestamp.valueOf( TestData.DATE_CREATE.atStartOfDay ( ) ),
                    TestData.ACCOUNT_ID,
                    TestData.IS_ACTIVE
            });
        }

        return rows;
    }


}