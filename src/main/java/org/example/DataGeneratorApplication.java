package org.example;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.insertData.TariffRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class DataGeneratorApplication implements CommandLineRunner {

    private final TariffRepository tariffRepository;

    public static void main(String[] args) {
        SpringApplication.run(DataGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) {
//        tariffRepository.insertContractZone();
        tariffRepository.insertExternalLevel();
        log.info("Готово");
    }
}