Feature: the version can be retrieved
  Scenario: client makes call to GET /version
    When the client calls /version
    Then the client receives status code of 200
    And the client receives server version 1.0

  Scenario: client makes call to scan file
    When the client calls /scan
    And the client pass