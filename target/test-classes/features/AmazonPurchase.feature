Feature: End to End Amazon Shopping Flow

Background:
  Given User is on browser
  When I navigate to Home page of the Amazon web application
  And Go to the Hello,Sign in option
  And Click on the Sign in link


# ✅ VALID USER FLOW
@Regression
Scenario Outline: Valid user adds product to cart

  Given User enters the valid phone number "<phone>" into the fieldbox
  And click on the Continue button
  And Enter the valid password "<password>" into password field
  And Click on the Sign In button
  Then user should get "success"

  When User clicks in the search field box
  And User enters the product name "<productName>"
  And Clicks on the search icon button
  Then All "<productName>" results should be shown on the page
  And The user selects a "<productName>" product
  Then The user navigates to the product details page

  When User clicks on the Add to cart button
  Then User clicks on the No Thanks button
  And Product should add into the cart
  And User clicks on the Go to Cart button

  Then The user verifies product "<productName>" is present in cart
  And The user verifies cart is not empty

  When User clicks on Proceed to Buy button
  Then User should navigate to the checkout page
  And User selects delivery address
  Then User should be redirected to payment page
  And User selects the payment method

Examples:
| phone      | password   | productName |
| 8630718216 | Kaushik21# | Thermometer |


# ❌ INVALID USER FLOW
@Regression
Scenario Outline: Invalid user should not login

  Given User enters the valid phone number "<phone>" into the fieldbox
  And click on the Continue button
  Then user should get "error"

Examples:
| phone      |
| 7520720735 |




@Regression
Scenario Outline: Valid user enters wrong password

  Given User enters the valid phone number "<phone>" into the fieldbox
  And click on the Continue button
  And Enter the valid password "<password>" into password field
  And Click on the Sign In button
  Then user should get "wrong_password"

Examples:
| phone      | password  |
| 8630718216 | Test@123  |
