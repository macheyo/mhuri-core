Feature: Cash Sales
  Scenario: Tendai wants to buy a spanner
    Given spanner is available in inventory
    When Chipo receives cash
    Then increase cash, sales account and adjust inventory