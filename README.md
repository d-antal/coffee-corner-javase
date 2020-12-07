    I.  Start the application- >  run CoffeeCornerApplication class

	II. Choose (registered, not registered customer)
		
		a. Registered: enter the registration id
		   If the registration id does not exist - > error message (try again or register), select again
		   
		b. New customer- > new registration id is created/saved (it needs to be used later for the discount)
			
		If not 1-2 or not number is pressed- > error message (out of range or wrong input), select again
		In case of any server/application related error the application is stopped (selection/pay is not available)
		-> error message (try again later)
	
	III. Select product(s)
		
		a. Coffee:
		  - press 1 and:
			1. Select coffee (Small, Medium, Large) 
			   If Selected -> redirected go Select Extra
				-If not 1-3 is selected- > error message (out of range), select again
				-If not number is selected- > selection is cancelled, error message (wrong input- ) + select again or pay the finished selection(s)
				(redirect to pay)
			2. Select extra 
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
		 -  press 3 then pay or select more products (collect 5 juices for the discount)
				
	 IV. Pay
		It is possible to choose and pay more products in the same transaction 
		Product/Products are selected -> Press 4. to pay (message - customer id + price)

     V. Exit
        press 5. -> redirect to "login page" + message
                
		Discount:
		Select a coffee and a snack "together" : Select a coffee with extra(s) and then a snack -> the last extra will be reduced 
		from the final price + discount message 
		If 5 orange juices are collected -> 1 juice is free + discount message
		(it can be collected in 1 or more transactions - select registered customer and provide the registration id for the validation 
		if you would like to collect them in more than 1 transaction)
		
		Saved choices (selected products/steps)-> the application calculates the final price based on the inputs from the saved choices Queue 
	    (currently it is used only in tests. UI+service is not implemented yet for this option)
