Account
  * setting where account will pull from other shared accounts if it gets too low.
  * setting to allow overdraft Withdrawals
  * apply for loan
  - Checkin
  - Savings
User
  - Customer
  - Worker
Password protection

Functionality
  - Customer Side
    - list accounts
    - transaction log
    - Transfer
    - Withdrawal
    - deposit
    - request account creation
    - request account closure


  - Workers side
    - reports
      - accounts
      - transaction per day
      - approve account
        - if so, automatically generate account based on request
      - deactivate accounts

  - Program side
    - check for interest
    - warnings for low balances and other notifications

store data in json files
accounts will be folder systems
  - <Customer identifier>
    - accounts
      -savings
        *each savings account file
      -checkings
        *each checking account file
