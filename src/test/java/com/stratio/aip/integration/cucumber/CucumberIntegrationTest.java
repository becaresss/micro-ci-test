package com.stratio.aip.integration.cucumber;


import com.stratio.aip.integration.AbstractIntegrationTest;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources")
public class CucumberIntegrationTest extends AbstractIntegrationTest {
}
