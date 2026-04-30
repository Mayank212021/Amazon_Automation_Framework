Feature: Login to Amazon web application


Background: 
Given User navigate to chrome browser
When  I navigate to Home page of the Amazon web application
When Go to the Hello,Sign in option
And Click on the Sign in link
Then User move to the Login in page.



@Regression
Scenario: Login with valid credentials
When User enters the valid phone number "8630718216" into the fieldbox
And click on the Continue button
And Enter the valid password "Kaushik21#" into password field
And Click on the Sign In button
Then user should get successfully logged in



@smoke
Scenario: Login with invalid email address
When User enters the valid email or phone "@dghsad" into the fieldbox
And click on the Continue button
Then user get error message about invalid email


Scenario: Login with valid email and invalid password
When User enters the valid email or phone "8630718216" into the fieldbox
And click on the Continue button
And Enter the invalid password "wrong123" into password field
And Click on the Sign In button
Then User should not be logged in and get error message about invalid password.


Scenario: Login without providing any credentials
When Click on the continue button directly.
Then User gets the error message


#Searching for a product to order
@Regression
Scenario: Find a product that is able to be ordered
Given User clicks in the search field box
When User enters the product name "Room Thermometer"
And Clicks on the search icon button
Then All thermometer results should be shown on the page
And The user selects a thermometer product
Then The user navigates to the product details page



# Add the product into cart

@Regression
Scenario: Add the product into cart
  When User clicks on the Add to cart button
  Then User clicks on the No Thanks button
  And Product should add into the cart
  And The user receives a confirmation message on the page "Added to Cart"
  And User clicks on the Go to Cart button
  Then The user navigates to the Cart page
  
  
  
# Proceed to the cart page to purchase the product.
Scenario: Purchase the product by using the cart page.
Given User clicks on the Proceed to Buy button.
Then User navigate to Checkout page