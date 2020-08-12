package com.hdms.antivirus.feature;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.vavr.collection.HashMap;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ScannerBddStep extends SpringIntegrationTest{

    @When("^the client calls /diagnose with name as (.+) and file as (.+)$")
    public void theClientCallsDiagnoseWithNameAsNonvirusAndFileAsNewsTxt(String name, String file) {
        System.out.println (name +"----"+ file);
        file = "src/test/resources/file/" +file;
        String url = "http://localhost:8080/diagnose";
        Map<String, String> param = HashMap.of ( "name",name,"file", file ).toJavaMap ();
        sendPostWithAttachedFile (url, param);
    }

    @Then("^the virus scan response status code of (\\d+)$")
    public void theVirusScanResponseStatusCodeOf(int statusCode) {
        int currentStatusCode = virusScanResponse.getStatusCode();
        assertThat("status code is incorrect : "+
                currentStatusCode, currentStatusCode, is(statusCode));

    }

    @And("^the final test response (.+)$")
    public void theFinalTestResponseEverythingOkTrue(String expectedOutput) {
    String actualOutput = virusScanResponse.getResponseMsg ();
    assertThat ( "Response Msg is incorrect :" + actualOutput, actualOutput, is(expectedOutput) );
    }


}
