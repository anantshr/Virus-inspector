Feature: the version can be retrieved

  Scenario: client makes call to scan file without virus
    When the client calls /diagnose with name as nonvirus and file as news.txt
    Then the virus scan response status code of 200
    And the final test response Everything ok : true

  Scenario: client makes call to scan file with virus
    When the client calls /diagnose with name as virus and file as virus.txt
    Then the virus scan response status code of 200
    And the final test response Everything ok : false

  Scenario: client makes call to scan file which is in another Language
    When the client calls /diagnose with name as anotherLang and file as anotherLangFile
    Then the virus scan response status code of 200
    And the final test response Everything ok : true

  Scenario: client makes call to scan file which is an image
    When the client calls /diagnose with name as anotherLang and file as brokenTool.jpg
    Then the virus scan response status code of 200
    And the final test response Everything ok : true