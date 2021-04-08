// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
// More packages may be imported in the space below

/**
 * @author  Anson Sy A Chin <335472759@gapps.yrdsb.ca> , Robert Todica <348889643@gapps.yrdsb.ca>
 * @version 1.0
 * @since 1.0
 */
class CustomerSystem{
    public static void main(String[] args) throws IOException{
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below
        String customerInfo = ("");

        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
		            // Any necessary variables may be added to this if section, but nowhere else in the code
                customerInfo = enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile(customerInfo);
            }
            else{
                System.out.println("Please type in a valid option (A number from 1-9)");
            }

        } while (!userInput.equals(exitCondition));         // Exits once the user types 
        
        reader.close();
        System.out.println("Program Terminated");
    }
    public static void printMenu(){
        System.out.println("Customer and Sales System\n"
        .concat("1. Enter Customer Information\n")
        .concat("2. Generate Customer data file\n")
        .concat("3. Report on total Sales (Not done in this part)\n")
        .concat("4. Check for fraud in sales data (Not done in this part)\n")
        .concat("9. Quit\n")
        .concat("Enter menu option (1-9)\n")
        );
    }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    /**
     * 
     * @return returns a compiled string of all the customer info
     * @throws IOException input and output errors are thrown
     */
    public static String enterCustomerInfo() throws IOException{
        Scanner reader = new Scanner(System.in);
        System.out.println("----------EnterCustomerInfo----------"); //Opening to the "enterCustomerInfo" method showing you have entered it
        
        System.out.println("\n-----What is your FIRST name?-----"); //Asks for first name
        String fn = reader.nextLine();  //Userinputted value for first name is stored as variable "fn"
        
        System.out.println("\n-----What is your LAST name?-----"); //Asks for last name
        String ln = reader.nextLine();//Userinputted value for last name is stored as variable "ln"
        
        System.out.println("\n-----What CITY do you reside in?-----"); // Asks for city of residence
        String city = reader.nextLine();//Userinputted value for city of residence is stored as variable "city"
        

        boolean postalCheck = false;
        String pc = ("");
        while (postalCheck == false){  //Allows for the re-entering of an incorrect postal code using a while loop

            System.out.println("\n-----What is your POSTAL CODE?-----"); //Asks user for postal code
            pc = reader.nextLine();
            postalCheck = validatePostalCode(pc); //Summons a further method responsible for checking the postal code to do so    
            
            if (postalCheck == false){ //Output if the postal code is incorrect
                System.out.println ("Invalid postal code, please retry with proper formatting or input a valid postal code (ex. OOO_OOO)");
            }
        }
        
        String CCnum = ("");
        boolean validateCreditCard = false;
        while (validateCreditCard == false){//Allows for the re-entering of an incorrect credit card number using a while loop

            System.out.println("\n-----Finally, what is your CREDIT CARD NUMBER?-----");
            CCnum = reader.nextLine();
    
            validateCreditCard = validateCreditCard(CCnum); //Summons a further down method responsible for checking the validity of a user inputted credit card number to do so                        
            if(validateCreditCard == true){
                System.out.println("Valid credit card number"); //Output for a valid credit card number
            }
            else{
                System.out.println("Invalid credit card number"); //Output for an invalid credit card number
            }
        }

        String compiledInfo = (fn+", "+ln+", "+city+", "+pc+", "+CCnum); //Outputs a compiled text including first name,last name,city,postal code and credit card number that the user previously inputted
        System.out.println ("Customer Information Recieved!\n"); //All inputs went through/postal code check and credit card check passed as valid
        return compiledInfo;  
    }
    
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */

    /**
     * Validates the inputted postal code.
     * 
     * @param text string contains the inputted postal code
     * @return true if the postal code is in the csv, false if not
     * @throws IOException Input and Output errors are thrown
     */
    public static boolean validatePostalCode(String text) throws IOException{
      
        int lengthVerification = text.length();  //This is used to make sure the postal code has the correct amount of characters, being 7 including the space ex. (ABC ABC)
        if (lengthVerification > 7 || lengthVerification < 7){
            return false;
        }
        
        char spaceCheck = text.charAt(3); //This grabs the charcter in the 3rd place, which should be a ' '

        if (spaceCheck != ' '){ //This is used to make sure that the space is the 3rd(4th) character
            return false;
        }

        text = text.toUpperCase();  //This makes all letters upper case

        String postalSlice = text.substring(0, 3); //This assigns the important portion of the postal code, being the first 3 characters, to a string 

        String filename = "postal_codes.csv"; //Sets a string as the file name
        BufferedReader csvReader = new BufferedReader(new FileReader(filename)); //Reads the string file
        String line = csvReader.readLine(); //Sets the first line as the string line
        
        while (line != null){ //While line still has something assigned to it
            if (line.contains(postalSlice)){ //Check if the postal code slice exists in the line
                return true; //if it does exist, return true
            }
            line = csvReader.readLine();//Advance to the next line
        }
      
        csvReader.close();
        return false; //If the statement above is not satisfied then the postal code is false
    }

    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */         
    /**
     * 
     * @param creditC is checked againsed the luhn algorithm to check for credit card authenticity 
     * @return returns either a boolean=true of a boolean=false, depending if the userinputted creditcardnumber was valid/invalid.
     */           
    public static boolean validateCreditCard(String creditC){
        int[] num = new int[creditC.length()];
        creditC = creditC.replaceAll("\\s+",""); //Removes any spaces that the user may have inputted, for some reason i couldnt manage to make it remove the spaces int he card number AND work with the rest of the code
        System.out.println(creditC);  //Useless output just showing that the program DOES remove spaces from a credit card number
        if(creditC.length()<9){           // If inputted credit card is less than 9 digits outputs this and ets the user re-input the credit card number
          System.out.println("Credit card must be at least 9 digits");
        }
        for (int i = 0; i < creditC.length(); i++) { //for loop that checks the lenght of the credit card number, and while the value of i is less than that, i will increase 
          num[i] = Integer.parseInt(creditC.substring(i, i + 1));//The parseint will temporary convert the int to a string, and then the substrign will take out one of the integers, getting increased by one each time (i+1)
        }                                                        //^^This is what takes out every other number necessary for the lugn algorithm
        for (int i = num.length - 2; i >= 0; i = i - 2) {  
            int l = num[i];
            l = l * (2); //Multiplies the number by two
            if ( l > 9) {     //If statement for if the number is greater than 9 
                l = l % (10) + 1; // If above if statement is entered, it adds the two numbers within the 2 digit number together
            }
            num [i] = l;
        } 
        int sum = (0);
        for (int i = 0; i < num.length; i++) {
            sum = sum + num[i];
        }
        if (sum % 10 == 0) { //Does modulus 10 of the two added parts (sum). If result is 0, credit card is valid/boolean returns true
            return true; //returns true (credit card is valid)
        } 
        else { // Modulus 10 of sum did not return 0, invalid credit card/boolean returns false
            return false; //returns false (credit card is not valid)
        }
    }
    
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */

    /**
     * Using other methods, this method takes a user's information and either saves it to an existing .csv file
     * or creates a new .csv file and saves it there 
     * 
     * @param customerInfo this string holds all the customer information
     * @throws IOException Input and Output errors are thrown
     */
    public static void generateCustomerDataFile(String customerInfo) throws IOException{
        Scanner reader = new Scanner(System.in);//initialize scanner

        if (!customerInfo.equals("")){//if there customer info is not blank
            System.out.println ("\nWhat directory would you like to write to? If only a file name is inputted, it will default to the folder this program is in.");//Ask user to input file directory/name
            System.out.println ("Please remember to put .csv at the end of the file name\n");
            String filename = reader.nextLine();//saves file name/directory to a String

            filename = csvCheck(filename);//Pass filename through a method that checks for .csv in the filename 

            File fileCheck = new File(filename);//Assign filename to file

            
            if (fileCheck.isFile()){//if file already exists
                existingFileWrite(filename, customerInfo);//Method that writes to existing file
            }
            else{
                newFileWrite(filename, customerInfo);//Method that makes a new file and writes to it
            }
        }
        else{//if there is no customer info
            System.out.println ("\nPlease input Customer Info first\n");//Ask the user to input customer info first
        }
    }

    /**
     * Ensures that the file is a .csv file.
     * 
     * @param filename this string has the file name/directory 
     * @return the filename is returned if the file name has .csv at the end
     */
    public static String csvCheck (String filename){

        Scanner reader = new Scanner(System.in);//initialize scanner

        boolean csvCheck = false;//set boolean to false for loop purposes 

        while (csvCheck == false){//While check is false   
            if (filename.contains(".csv")){//if the filename has .csv in it
                csvCheck = true;//leave loop
            }
            else{
                System.out.println ("\nPlease remember to attach .csv to the end of the file name (ex. CustomerFile.csv)");
                filename = reader.nextLine();//Ask user to reinput their filename
            }
        }
        return filename;//Once they passed the validation, return the filename 
    }

    /**
     * This method is for writing to a new file.
     * 
     * @param filename this string contains the file name
     * @param customerInfo this string contains all the customer information separated by commas
     * @throws IOException Input and Output errors are thrown
     */
    public static void newFileWrite (String filename, String customerInfo) throws IOException{
        PrintWriter writer = new PrintWriter(new File(filename)); //Initializes printwriter to a new file
        StringBuilder sb = new StringBuilder(); //Initialize StringBuilder

        
        sb.append("First Name, Last Name, City, Postal Code, Credit Card, ID\n");//Set up columns 
        sb.append(customerInfo+", 1");//append user info with preset initial id of 1

        

        writer.write(sb.toString());//write to file

        writer.close();//close writer

        System.out.println("New file named "+filename+" was made and customer information successfully stored\n");//give feedback that the method has finished
    }

    /**
     * This method is for writing to an existing file.
     * 
     * @param filename this string contains the file name
     * @param customerInfo  this string contains all the customer information separated by commas
     * @throws IOException Input and Output errors are thrown
     */
    public static void existingFileWrite (String filename, String customerInfo) throws IOException{
        FileWriter pw = new FileWriter(filename, true);//Initialize FileWriter

        int id = idAssign(filename);//Retrieve id using idAssign method

        pw.append("\n"+customerInfo+", "+id);//Print customer info along with generated id to file

        pw.close();//Close FileWriter

        System.out.println("Customer information successfully stored\n");//give feedback that the method has finished
    }

    /**
     * Finds last id in the file and adds 1 to it.
     * 
     * @param filename this string contains the file name
     * @return id after adding 1 to the last one
     * @throws IOException Input and Output errors are thrown
     */
    public static int idAssign (String filename) throws IOException{
        Scanner reader = new Scanner(new File(filename)); //Reads the string file
        reader.useDelimiter(",");//Uses "," as the delimiter
        String line = reader.next();//Assigns
        
        while (reader.hasNext()){ //While line still has something assigned to it
            line = reader.next();//Advance to the next line
        }
        
        line = line.replaceAll("\\s+","");//Remove the space that the number will have in front of it 
        int lastID = Integer.parseInt(line);//Convert the number from string to int
        return lastID + 1;//return the new id
    }

    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/
}