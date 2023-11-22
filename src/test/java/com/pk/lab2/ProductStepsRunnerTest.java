package com.pk.lab2;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.pk.lab2.steps",
        tags = "@ProductAPI",
        plugin = {"pretty", "html:target/cucumber-reports.html"})
public class ProductStepsRunnerTest {}