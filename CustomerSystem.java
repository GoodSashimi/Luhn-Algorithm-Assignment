// Throughout this project, the use of data structures are not permitted such as methods like .split and .toCharArray



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
// More packages may be imported in the space below

class CustomerSystem{
    public static void main(String[] args) throws IOException{
        // Please do not edit any of these variables
        Scanner reader = new Scanner(System.in);
        String userInput, enterCustomerOption, generateCustomerOption, exitCondition;
        enterCustomerOption = "1";
        generateCustomerOption = "2";
        exitCondition = "9";

        // More variables for the main may be declared in the space below


        do{
            printMenu();                                    // Printing out the main menu
            userInput = reader.nextLine();                  // User selection from the menu

            if (userInput.equals(enterCustomerOption)){
                // Only the line below may be editted based on the parameter list and how you design the method return
		            // Any necessary variables may be added to this if section, but nowhere else in the code
                enterCustomerInfo();
            }
            else if (userInput.equals(generateCustomerOption)) {
                // Only the line below may be editted based on the parameter list and how you design the method return
                generateCustomerDataFile();
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
    public static void enterCustomerInfo() throws IOException{
        Scanner reader = new Scanner(System.in);
        System.out.println("----------EnterCustomerInfo----------");
        System.out.println();
        System.out.println("-----What is your FIRST name?-----");
        String fn = reader.nextLine();
        System.out.println();
        System.out.println("-----What is your LAST name?-----");
        String ln = reader.nextLine();
        System.out.println();
        System.out.println("-----What CITY do you reside in?-----");
        String city = reader.nextLine();
        System.out.println();

        boolean postalCheck = false;
        while (postalCheck == false){
            System.out.println("-----What is your POSTAL CODE?-----");
            String pc = reader.nextLine();
            postalCheck = validatePostalCode(pc);
        }
        
        System.out.println();
        System.out.println("-----Finally, what is your CREDIT CARD NUMBER?-----");
        String CCnum = reader.nextLine();

        boolean validateCreditCard = validateCreditCard(CCnum);                          //I AM STUCK ON HOW TO IMPLEMENT A WHILE LOOP TO ALLOW THEM TO RE-ENTER AN INVALID INPUT AT A CREDIT CARD NUMBER
            if(validateCreditCard == true){
              System.out.println("Valid credit card number");
            }
            else{
              System.out.println("Invalid credit card number");
            }
      }
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
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
        BufferedReader reader = new BufferedReader(new FileReader(filename)); //Reads the string file
        String line = reader.readLine(); //Sets the first line as the string line
        
        while (line != null){ //While line still has something assigned to it
            if (line.contains(postalSlice)){ //Check if the postal code slice exists in the line
                return true; //if it does exist, return true
            }
            line = reader.readLine();//Advance to the next line
        }
      
        reader.close();
        return false; //If the statement above is not satisfied then the postal code is false
    }

    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */                    
  
    public static boolean validateCreditCard(String creditC){                            //Is this ok? did i mess up the method? PLEASE CHECK IF POSSIBLE. (I did caps to grab attention, no anger!)
        int[] num = new int[creditC.length()];
        creditC = creditC.replaceAll("\\s+",""); //Removes any spaces that the user may have inputted
        System.out.println(creditC);
        if(creditC.length()<9){           // If inputted credit card is less than 9 digits
          System.out.println("Credit card must be at least 9 digits");
        }
        for (int i = 0; i < creditC.length(); i++) {
          num[i] = Integer.parseInt(creditC.substring(i, i + 1));
        }
        for (int i = num.length - 2; i >= 0; i = i - 2) {  //Takes every other number from the credit card and doubles it
            int l = num[i];
            l = l * (2); 
            if ( l > 9) {     //If the number is greater than 9, it adds the two numbers within the 2 digit number together 
                l = l % (10) + 1;
            }
            num [i] = l;
        } 
        int sum = (0);
        for (int i = 0; i < num.length; i++) {
            sum = sum + num[i];
        }
        if (sum % 10 == 0) { //Does modulus 10 of the two added parts (sum). If result is 0, credit card is valid/boolean returns true
            return true;
        } 
        else { // Modulus 10 of sum did not return 0, invalid credit card/boolean returns false
            return false;
        }
    }
    
    /*
    * This method may be edited to achieve the task however you like.
    * The method may not nesessarily be a void return type
    * This method may also be broken down further depending on your algorithm
    */
    public static void generateCustomerDataFile() throws IOException{
        Scanner reader = new Scanner(System.in);

        System.out.println ("\nWhat file would you like to write to? If the file name already exists, it will write to that one instead.");
        System.out.println ("Please remember to put .csv at the end of the file name\n");
        String filename = reader.nextLine();

        filename = csvCheck(filename);

        File fileCheck = new File(filename);

        
        if (fileCheck.isFile()){
            existingFileWrite(filename, fn, ln, city, pc, cc);
        }
        else{
            newFileWrite(filename, fn, ln, city, pc, cc);
        }

        reader.close();
    }

    public static String csvCheck (String filename){

        Scanner reader = new Scanner(System.in);

        boolean csvCheck = false;

        while (csvCheck == false){   
            if (filename.contains(".csv")){
                csvCheck = true;
            }
            else{
                System.out.println ("\nPlease remember to attach .csv to the end of the file name (ex. CustomerFile.csv)");
                filename = reader.nextLine();
            }
        }
        reader.close();
        return filename;
    }

    public static void newFileWrite (String filename, String fn, String ln, String city, String pc, String cc) throws IOException{
        PrintWriter writer = new PrintWriter(new File(filename));
        StringBuilder sb = new StringBuilder();

        
        sb.append("First Name, Last Name, City, Postal Code, Credit Card, ID\n");
        sb.append(fn+", "+ln+", "+city+", "+pc+", "+cc+", 1");

        

        writer.write(sb.toString());

        writer.close();
        
    }

    public static void existingFileWrite (String filename, String fn, String ln, String city, String pc, String cc) throws IOException{
        FileWriter pw = new FileWriter(filename, true);

        int id = idAssign(filename);

        pw.append("\n"+fn+", "+ln+", "+city+", "+pc+", "+cc+", "+id);

        pw.close();
    }

    public static int idAssign (String filename) throws IOException{
        Scanner reader = new Scanner(new File(filename)); //Reads the string file
        reader.useDelimiter(",");
        String line = reader.next();
        
        while (reader.hasNext()){ //While line still has something assigned to it
            line = reader.next();//Advance to the next line
        }
        
        line = line.replaceAll("\\s+","");
        int lastID = Integer.parseInt(line);
        int id = lastID + 1;
        System.out.println(lastID);

        reader.close();
        return id;
    }
    /*******************************************************************
    *       ADDITIONAL METHODS MAY BE ADDED BELOW IF NECESSARY         *
    *******************************************************************/
}