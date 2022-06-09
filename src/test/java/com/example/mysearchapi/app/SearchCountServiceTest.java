package com.example.mysearchapi.app;

import com.example.mysearchapi.domain.Search;
import com.example.mysearchapi.util.TestContainerDatabaseCleanUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("동시성테스트")
@Tag("tctest")
@Testcontainers
@SpringBootTest(properties = "spring.profiles.active:tctest")
class SearchCountServiceTest {
    @Container
    static public MariaDBContainer mariadb = (MariaDBContainer) new MariaDBContainer("mariadb:10.3")
            .withUsername("jdragon")
            .withPassword("1111")
            .withDatabaseName("nhomedb")
            .withReuse(true)
            .withCommand("--character-set-server=utf8mb4", "--collation-server=utf8mb4_unicode_ci");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mariadb::getJdbcUrl);
        registry.add("spring.datasource.username", mariadb::getUsername);
        registry.add("spring.datasource.password", mariadb::getPassword);
    }

    @Autowired
    private SearchCountService service;

    @Autowired
    private TestContainerDatabaseCleanUp databaseCleanUp;

    @BeforeEach
    void setUp() {
        databaseCleanUp.execute();
    }

    //@RepeatedTest(3)
    @Test
    void 동시성테스트() throws InterruptedException {
        String keyword = "곱창";

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(100);

        service.saveSearchCount(keyword);

        for (int i = 0; i < 100; i++) {

            executorService.submit(() -> {
                try {
                    service.saveSearchCount(keyword);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();

        Search search = service.findSearchByKeyword(keyword);
        assertThat(search.getCount()).isEqualTo(101);
    }

}
