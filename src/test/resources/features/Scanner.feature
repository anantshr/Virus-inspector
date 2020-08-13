Feature: the version can be retrieved

  Scenario: user submit a valid file
    Given A valid Hilti application user
    When user upload file news.txt
    Then the virus scan happens successfully with response code is 200
    And the final diagnose response It is a Good File : true

  Scenario: user submit virus infected file
    Given A valid Hilti application user
    When user upload file virus.txt
    Then the virus scan happens successfully with response code is 200
    And the final diagnose response It is a Good File : false

  Scenario: User submit a valid file in local language
    Given A valid Hilti application user
    When user upload file anotherLangFile
    Then the virus scan happens successfully with response code is 200
    And the final diagnose response It is a Good File : true

  Scenario: User submit a image of a broken hilti tool
    Given A valid Hilti application user
    When user upload file brokenTool.jpg
    Then the virus scan happens successfully with response code is 200
    And the final diagnose response It is a Good File : true
