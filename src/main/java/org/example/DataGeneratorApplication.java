package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tariff.DoljnostProvider;
import org.example.tariff.TariffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DataGeneratorApplication implements CommandLineRunner {

    private final DoljnostProvider doljnostProvider;
    private final TariffRepository tariffRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Integer> doljnostIds = doljnostProvider.loadDoljnostIds();

        int contractZone = tariffRepository.insertContractZone(doljnostIds);
        int externalLevel = tariffRepository.insertExternalLevel(doljnostIds);
        int coefficients = tariffRepository.insertCoefficients(doljnostIds);

        log.info("Итого: contract_zone={}, external_level={}, coefficient_level={}",
                contractZone, externalLevel, coefficients);
    }
}