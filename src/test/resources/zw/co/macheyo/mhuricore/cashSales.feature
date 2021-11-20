Feature: Cash Sales

  Mhuri allows a cashier to record cash sales

  Rule: Cash sale should only be possible if inventory is available

    Scenario: Inventory available
      Given inventory availability is true
      And people are available
        | name    | Chipo   | Tendai    |
        | role    | cashier | customer  |
      When Chipo receives cash
      Then increase cash, sales account and adjust inventory