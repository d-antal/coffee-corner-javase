   Coffee Corner Java SE app description
   --------------------------------------
   Please find the use cases (saved choices) in the ProductSelectionUseCases file to see the required steps and the expected results.
   
   Customers can register and collect points for discount. The fifth beverage is free. Points can be collected in one or more transactions.
   The customers need to choose between 1. "Registered customer" and 2. "New customer" options when the Coffee Corner app starts. In case of a 
   new customer the app creates a new customer ID when the customer pays. This Registered customer ID is saved and needs to be used later in order
   to collect points for the discount and/or to login as a "Registered customer".
   If the Customer chooses the "Registered customer" option, the registration ID needs to be entered. If the customer enters wrong ID:
   "This registration ID does not exist. Please try again or register", the Customer needs to choose again between the options. 
   In case of a wrong input format (the correct input format is cc+number) : "Please try again! Wrong input: entered input".
   
   It is possible to buy Coffees, Bacon rolls and Orange juices. In case of Coffee (1.) the Customer needs to choose coffee type (Small, Medium, Large).
   If the entered number is not between 1-3: "Please Select between 1-3", the customer can continue and choose again between 1-3.
   In case of a wrong input: error message- "Please try again! Wrong input: input", the selection is cancelled, the customer is redirected to the 
   main menu to select again (the previous selections are not cancelled).
   
   If the coffee type is selected, the Customer can add extra or Finish (4) and select other products/pay. 
   It is possible to select 1 Extra milk/foamed milk and 1 extra Extra special roast for the coffee. If both are selected then the customer is 
   redirected to the main menu. It is not possible to choose two milks. If 1 milk has already been added (extra milk or foamed milk) then it is not 
   possible to choose 1 more milk (it does not matter if it is foamed or not): "You can add only one extra milk to the coffee" and it is not possible 
   to choose two "Extra special roast" either: "You can add only one extra roast to the coffee". 
   Input range (in this case 1-4) and input format are checked here as well (error handling is the same as in the previous step). 
   
   If an extra (in case of two then the last one) is selected for the coffee and the Customer buys a bacon roll in the same transaction then the
   extra is free: "You have ordered a beverage with extra(s) and a snack. One extra is free!"
  
   If the customer buys 5 beverages (in one or more transactions ), the fifth beverage is free : "You have collected 5 beverages, the last one is free!"
   The Customer can buy 1 or more of the products and Pay (4) : "Registered customer ID:  ...  Final price :  ...CHF"
   After Pay (4) it is possible to start a new transaction or Exit (5) - login page.
   
   Saved choices (selected products/steps)-> the application calculates the final price based on the inputs from the saved choices Queue 
   (currently it is used only in tests. UI+service is not implemented yet for this option)
      
   Main steps
   ----------
   
    I. Build (mvn clean install) and Start the application -> run the CoffeeCornerApplication class

	II. Choose (registered, not registered customer)
			============================
		        Coffee Corner       
			 ============================
			1. Registered customer
			2. New customer
			============================
		
		a. Registered: enter the registration id
		   If the registration id does not exist - > error message (try again or register), select again
		   
			==================================
			Please enter your registration id :
			===================================
		
		This registration ID does not exist. Please try again or register
		   
		b. New customer- > new registration id is created/saved (it needs to be used later for the discount)
			
		If not 1-2 or not number is pressed- > error message: "Please select 1 or 2" , select again
		If not number is entered -> "Please try again! Wrong input: abc", select again
		In case of any server/application related error the application is stopped (selection/pay is not available)
		-> error message (try again later)
	
	III. Select product(s)
	
				 Product 
			============================
			1. Coffee from  2.5 CHF 
			2. Bacon roll  4.5 CHF 
			3. Orange juice  3.95 CHF 
			============================
			4. Pay
			============================
			5. Exit
			
		
		a. Coffee:	
		  - press 1 and:
		  
			1. Select coffee (Small, Medium, Large) 
							
			============================
			 Coffee 
			============================
			1. Small coffee  2.5 CHF 
			2. Medium coffee  3.0 CHF 
			3. Large coffee  3.5 CHF 
			============================
			 
			============================
			   If Selected -> redirected go Select Extra
				-If not 1-3 is selected- > error message (out of range), select again
				-If not number is selected- > selection is cancelled, error message (wrong input- ) + select again or pay the finished selection(s)
				(redirect to pay)
			
			2. Select extra 
			
			============================
			 Extra 
			============================
			1. Extra milk  0.3 CHF 
			2. Extra foamed milk  0.5 CHF 
			3. Extra special roast  0.9 CHF 
			============================
			4. Finish
			============================
			
			   Choose extra milk, extra foamed milk and/or extra special roast 
			   Two milk (2 extra milk or 1 extra milk + 1 extra foamed milk ) or two Extra special roast are not allowed 
			   -> error message, select again.
			   If an extra milk (normal or foamed) and the special roast are selected -> the selection is finished, redirected to pay 
			   It is possible to choose 1 extra (milk or roast) and go to pay - >  press 4. 			
			   -If not 1-4 is selected- > error message (out of range), select again
			   -If not number is selected- > selection is cancelled, error message (wrong input ) + select again or pay the finished selection(s) 
			   (redirect to pay)

		b. Bacon roll
		  - press 2 then pay or select more products
		
		b. Orange Juice
		 -  press 3 then pay or select more products (collect 5 beverages for the discount)
				
	 IV.Pay
		It is possible to choose and pay more products in the same transaction 
		Product/Products are selected -> Press 4. to pay (message - customer id + price)

     V. Exit
        press 5. -> redirect to "login page" + message
                

		
	
