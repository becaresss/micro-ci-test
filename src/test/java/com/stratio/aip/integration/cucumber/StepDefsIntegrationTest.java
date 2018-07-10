package com.stratio.aip.integration.cucumber;

import com.stratio.aip.integration.AbstractIntegrationTest;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefsIntegrationTest extends AbstractIntegrationTest {

    @Given("^client makes call to GET /$")
    public void the_client_issues_GET_hello() throws Throwable {
        executeGet("/");
    }

    @When("^the client calls /$")
    public void the_client_issues_GET() throws Throwable {
        executeGet("/");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) throws Throwable {
        final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
        assertThat("status code is incorrect : " + latestResponse.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @And("^the client receives message (.*)$")
    public void theClientReceivesHelloFromCIWorldMessage(String message) {
        assertThat(latestResponse.getBody(), is(message));
    }
}