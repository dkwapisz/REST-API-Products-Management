package com.pk.lab2;

import com.pk.lab2.config.MongoServer;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.pk.lab2.steps",
        tags = "@ProductAPI",
        plugin = {"pretty", "html:target/cucumber-reports.html"})
public class ProductStepsRunnerTest {
    private static MongoServer mongoServer;

    @BeforeClass
    public static void setUp() throws Exception {
        mongoServer = new MongoServer();
        mongoServer.start();
    }

    @AfterClass
    public static void tearDown() {
        mongoServer.stop();
    }
}