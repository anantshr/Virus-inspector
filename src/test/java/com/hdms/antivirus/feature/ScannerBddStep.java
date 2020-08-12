package com.hdms.antivirus.feature;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.vavr.collection.HashMap;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScannerBddStep extends SpringIntegrationTest{

    @When("^user upload file (.+)$")
    public void userUploadFile(String file) {
        file = "src/test/resources/file/" +file;
        String url = "http://localhost:8080/diagnose";
        Map<String, String> param = HashMap.of ("file", file ).toJavaMap ();
        sendPostWithAttachedFile (url, param);
    }

    @And("^the final diagnose response (.+)$")
    public void theFinaldiagnoseResponse(String expectedOutput) {
    String actualOutput = virusScanResponse.getResponseMsg ();
    assertThat ( "Response Msg is incorrect :" + actualOutput, actualOutput, is(expectedOutput) );
    }


    @Given("^A (.+) Hilti application user$")
    public void aValidOrInvalidHiltiApplicationUser(String userType) {
        assertThat ( "The user is not valid", userType, is("valid") );

    }

    @Then("^the virus scan happens successfully with response code is (\\d+)$")
    public void theVirusScanHappensSuccessfullyWithResponseCodeIs(int statusCode) {
        int currentStatusCode = virusScanResponse.getStatusCode();
        assertThat("status code is incorrect : "+
                currentStatusCode, currentStatusCode, is(statusCode));
    }
}
