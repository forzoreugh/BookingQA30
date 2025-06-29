Feature: Search hotel
  Scenario: Looking for 'Вильнюс'
    Given booking search page is opened
    When user searches for "Вильнюс"
    Then "Comfort Hotel LT - Rock 'n' Roll Vilnius" hotel is shown
    And hotel name "Comfort Hotel LT - Rock 'n' Roll Vilnius" from rating "9,0"
