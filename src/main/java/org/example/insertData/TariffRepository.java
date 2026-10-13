package org.example.insertData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.getData.GetDoljnost;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TariffRepository {

    private final SqlExecutor sqlExecutor;

    private final GetDoljnost getDoljnost;


    /** Вставка в contract_zone_tariff */
    public int insertContractZone() {
        int rows = sqlExecutor.insert(InsertTariff.ContractZone.INSERT,
                TestData.ZONE_ID,
                getDoljnost.getDoljnostIds(),
                TestData.EXTERNAL_RATE,
                TestData.ONE_RATE,
                TestData.TWO_RATE,
                TestData.THREE_RATE,
                TestData.FOUR_RATE,
                TestData.TYPE_FROM_CONTRACT,
                TestData.FROM_ID,
                Date.valueOf(TestData.DATE_FROM),
                Date.valueOf(TestData.DATE_TO),
                Timestamp.valueOf(TestData.DATE_CREATE),
                TestData.IS_ACTIVE,
                TestData.ACCOUNT_ID,
                TestData.COMPANY_ID
        );
        log.info("contract_zone_tariff: вставлено {} строк", rows);
        return rows;
    }


    /** Вставка в external_level_tariff */
    public int insertExternalLevel() {
        int rows = sqlExecutor.insert(InsertTariff.ExternalLevel.INSERT,
                TestData.TERRITORY_ID,
                TestData.ZONE_ID,
                TestData.CITY_ID,
                TestData.OBJECT_X5,
                getDoljnost.loadDoljnostIds (),
                TestData.EXTERNAL_RATE,
                TestData.ONE_RATE,
                TestData.TWO_RATE,
                TestData.THREE_RATE,
                TestData.FOUR_RATE,
                Date.valueOf(TestData.DATE_FROM),
                Date.valueOf(TestData.DATE_TO),
                Timestamp.valueOf(TestData.DATE_CREATE),
                TestData.TYPE_FROM_EXTERNAL,
                TestData.FROM_ID,
                TestData.ACCOUNT_ID,
                TestData.IS_ACTIVE
        );
        log.info("external_level_tariff: вставлено {} строк", rows);
        return rows;
    }

}
