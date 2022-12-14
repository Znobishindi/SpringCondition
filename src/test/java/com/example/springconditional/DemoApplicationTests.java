package com.example.springconditional;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    private static final String LAST = "/profile";
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    public static GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();

    }

    @Test
    void contextLoadsDev() {
        final String expected = "Current profile is dev";
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + devApp.getMappedPort(8080) + LAST, String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals(expected,forEntity.getBody());
    }

    @Test
    void contextLoadsProd() {
        final String expected = "Current profile is production";
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" + prodApp.getMappedPort(8081) + LAST, String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals(expected,forEntity.getBody());
    }

}