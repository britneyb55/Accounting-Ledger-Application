package com.pluralsight;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;

public class Main

{
    private static Logger logger = new Logger("transactions");
    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args)
    {
        homeScreen();
    }

    public static void homeScreen() {
        System.out.println();
        System.out.println( Colors.YELLOW + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println( Colors.BLUE + "Tracking Financial Footprints" + Colors.RESET);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(Colors.PURPLE + "Welcome");
        System.out.println();
        System.out.println("Menu:" );
        System.out.println();
        System.out.println("Please Select The Following:" + Colors.RESET);
        System.out.println(Colors.ORANGE + " [D] " + Colors.CYAN + "Would you like to deposit today?" + Colors.RESET);
        System.out.println(Colors.ORANGE + " [P] " + Colors.CYAN + "Would you like to make a payment today?" + Colors.RESET);
        System.out.println(Colors.ORANGE + " [L] " + Colors.CYAN + "Would you like to view the Ledger?" +  Colors.RESET );
        System.out.println(Colors.ORANGE + " [X] " + "Exit" +  Colors.RESET);
        System.out.println(Colors.RED + ("-").repeat(50) + Colors.RESET);


        while(true)
        {
            try {
                String userChoice = userInput.nextLine().strip().toLowerCase();

                switch (userChoice) {
                    case "d":
                        addDeposit();
                        break;
                    case "p":
                        makePayment();
                        break;
                    case "l":
                        ledger();
                        break;
                    case "x":
                        System.out.println("Have a good day");
                        break;
                    default:
                        System.out.println("Invalid Selection");
                        homeScreen();
                        break;
                }

            }catch (InputMismatchException ex) {
                System.out.println("Not a valid input Entered ");
            }
        }

    }

    private static void addDeposit()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println(Colors.PURPLE + "Enter the following information " + Colors.RESET);
        System.out.println();

        try {

            System.out.println("Enter the amount to deposit");
            double depositAmount = Double.parseDouble(userInput.nextLine().strip());
            System.out.println();

            System.out.println("Enter the Source where the fund are coming (e.g. paycheck, savings, gift)");
            String depositDescription = userInput.nextLine();
            System.out.println();

            System.out.println("Enter the name of the vendor to who is paying you.");
            String vendorDeposit = userInput.nextLine();
            System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
            System.out.println();

            logger.logTransactions(depositDescription, vendorDeposit, depositAmount);


        } catch (NumberFormatException ex) {
            System.out.println("Invalid input only type in the number");
            addDeposit();
        }

        promptDepositChoice();
    }

    public static void promptDepositChoice()
    {
        System.out.println();
        System.out.println(Colors.PURPLE + "What would you like to do?" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [I] " + Colors.CYAN + "insert another deposit" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [X] " + Colors.CYAN + "Go to the home screen" + Colors.RESET);
        System.out.print(Colors.PURPLE + "Select an option: " + Colors.RESET);
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "i":
                addDeposit();
                promptDepositChoice();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                homeScreen();
                break;
            default:
                System.out.println("invalid input");
        }
    }


    private static void makePayment() {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println(Colors.PURPLE + "Payment Information" + Colors.RESET);
        System.out.println();

        try {
            System.out.println("Enter the payment Amount");
            double paymentAmount = Double.parseDouble(userInput.nextLine().strip());
            paymentAmount = paymentAmount * -1;

            System.out.println("Enter the description of what you are paying.");
            String paymentDescription = userInput.nextLine();

            System.out.println("Enter the vendor to whom you are paying. (e.g. Amazon, Discovery credit card)  ");
            String vendorPayment = userInput.nextLine();

            System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
            System.out.println();

            logger.logTransactions(paymentDescription,vendorPayment, paymentAmount);


        } catch (NumberFormatException ex) {
            System.out.println("Only enter number");
            makePayment();
        }

        promptPaymentChoice();
    }

    public static void promptPaymentChoice()
    {
        System.out.println();
        System.out.println( Colors.PURPLE + "What would you like to do?" + Colors.RESET);
        System.out.println(Colors.ORANGE + " [I] " + Colors.CYAN + "insert another payment" + Colors.RESET);
        System.out.println(Colors.ORANGE + " [X] " + Colors.CYAN + "Go to the home screen" + Colors.RESET);
        System.out.print(Colors.PURPLE + "Select an option: " + Colors.RESET);
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "i":
                makePayment();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                homeScreen();
                break;
            default:
                System.out.println("invalid input");
        }
    }


    private static void ledger()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println( Colors.PURPLE + "Menu");
        System.out.println();
        System.out.println("Entries:");
        System.out.println();
        System.out.println(  "Will you like to view :" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [A] " + Colors.CYAN + "All transactions" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA +" [D] " + Colors.CYAN + "Deposits entries only" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA +" [P] " + Colors.CYAN + "Payments entries only" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA +" [R] " + Colors.CYAN + "Reports" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA +" [H] " +  "Home page" + Colors.RESET);
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);

        try{

            while(true)
            {
            String entriesChoice = userInput.nextLine().strip().toLowerCase();

                switch (entriesChoice)
                {
                    case "a":
                        allEntries();
                        break;
                    case "d":
                        depositEntries();
                        break;
                    case "p":
                        paymentEntries();
                        break;
                    case "r":
                        report();
                        break;
                    case "h":
                        System.out.println(" Redirecting to home page");
                        homeScreen();
                        break;

                    default:
                        System.out.println("Invalid Selection");
                        ledger();
                        break;
                }
            }

       }catch(NumberFormatException ex)
        {
            System.out.println("Not a string");
        }

    }

    public static void displayHeader()
    {
        String  date, time, description , vendor ,amount;
        date = "Date";
        time = "Time";
        description = "Description";
        vendor = "Vendor";
        amount = "Amount";
        System.out.printf(Colors.PURPLE + "%-15s %-10s %-40s %-25s %-20s",date,time,description,vendor,amount + Colors.RESET);



    }
    private static void allEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50));
        System.out.println();
        System.out.println( "All Transactions" + Colors.RESET );
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();


        for (AccountingLedger transaction : transactions)
        {
            System.out.println(Colors.PINK_BACKGROUND);
            System.out.println(Colors.BLACK);
            System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            System.out.println(Colors.RESET);
        }
        System.out.println(Colors.BLUE + "|#######====================#######|\n" +
                "|#(1)*UNITED STATES OF AMERICA*(1)#|\n" +
                "|#**          /===\\   ********  **#|\n" +
                "|*# {G}      | (\") |             #*|\n" +
                "|#*  ******  | /v\\ |    O N E    *#|\n" +
                "|#(1)         \\===/            (1)#|\n" +
                "|##=========ONE DOLLAR===========##|\n" +
                "------------------------------------" + Colors.RESET);
        userChoice();

    }

    private static void depositEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50));
        System.out.println();
        System.out.println("ALL Deposit Entries: " + Colors.RESET);
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();
        for (AccountingLedger transaction : transactions)
        {
            if (transaction.getAmount() > 0)
            {
                System.out.println(Colors.BLUE_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }
        }
        userChoice();
    }

    private static void paymentEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50));
        System.out.println();
        System.out.println("ALL Payments Entries: " + Colors.RESET);
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (transaction.getAmount() < 0)
            {
                System.out.println(Colors.CYAN_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }
        }
        userChoice();
    }

    private static void userChoice()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println(Colors.PURPLE + "What would you like to do?" + Colors.RESET );
        System.out.println(Colors.LIGHT_MAGENTA + " [L] " + Colors.CYAN + "Go back to View entries" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [X] " + Colors.CYAN + "Go to the Home page" + Colors.RESET);
        System.out.print(Colors.PURPLE + "Select an option: " + Colors.RESET);
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "l":
                ledger();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                homeScreen();
                break;
            default:
                System.out.println("invalid input");
                userChoice();

        }

    }


    private static void report()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50));
        System.out.println();
        System.out.println("Report Search:" + Colors.RESET);
        System.out.println();
        System.out.println(Colors.PURPLE + "Please Select what you'll like to Search today" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [1] " + Colors.CYAN + "Month To Date" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA  + " [2] " + Colors.CYAN + "Previous Month" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA  + " [3] " + Colors.CYAN + "Year To Date" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA  + " [4] " + Colors.CYAN + "Previous Year" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA  + " [5] " + Colors.CYAN + "Search by Vendor" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA  + " [0] " + Colors.CYAN + "Back" +  Colors.RESET);
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);

        try
        {
            while(true) {
                int userChoice = Integer.parseInt(userInput.nextLine());

                switch (userChoice) {
                    case 1:
                        monthToDate();
                        break;
                    case 2:
                        previousMonth();
                        break;
                    case 3:
                        yearToDate();
                        break;
                    case 4:
                        previousYear();
                        break;
                    case 5:
                        searchByVendor();
                        break;
                    case 0:
                        ledger();
                        break;
                    default:
                        System.out.println("Invalid Selection");
                        report();
                        break;
                }
            }
        }catch(NumberFormatException ex)
        {
            System.out.println("Not a string");
            System.out.println("Please try again");
            report();
        }

    }

    private static void monthToDate()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println( Colors.RED + "Transactions made this month" + Colors.RESET);
        System.out.println();
        displayHeader();

        LocalDate date = LocalDate.now();

        LocalDate start = LocalDate.of( date.getYear() , date.getMonth() , 1 ) ;
        LocalDate stop = LocalDate.of( date.getYear() , date.getMonth(), date.getDayOfMonth() ) ;

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (!transaction.getDate().isBefore(start) && !transaction.getDate().isAfter(stop))
            {
                System.out.println(Colors.PURPLE_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }

        }
        userChoiceReport();

    }

    private static void previousMonth()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println( Colors.RED + "Transactions made previous month" + Colors.RESET);
        System.out.println();


        LocalDate previousMonthStart = LocalDate.of( date.getYear() , date.minusMonths(1).getMonth() , 1 ) ;
        YearMonth previousMonthEnd = YearMonth.of(date.getYear(),  date.minusMonths(1).getMonth());
        LocalDate lastDayOfMonth = previousMonthEnd.atEndOfMonth();
        System.out.println(previousMonthStart + " - " + lastDayOfMonth);
        System.out.println();

        displayHeader();

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (!transaction.getDate().isBefore(previousMonthStart) && !transaction.getDate().isAfter(lastDayOfMonth))
            {
                System.out.println(Colors.BLUE_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }


        }
        userChoiceReport();

    }

    private static void yearToDate()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println( Colors.RED + "Transactions made this Year to Today's Date" +  Colors.RESET);
        System.out.println();

        LocalDate year = LocalDate.of(date.getYear(), 1, 1);

        System.out.println(year + " - " + date);
        System.out.println();

        displayHeader();

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (!transaction.getDate().isBefore(year) && !transaction.getDate().isAfter(date))
            {
                System.out.println(Colors.CYAN_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }
        }
        userChoiceReport();

    }

    private static void previousYear()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println( Colors.RED + "Transactions made previous Year" +  Colors.RESET);
        System.out.println();


        LocalDate previousYear = LocalDate.of(date.minusYears(1).getYear(), 1, 1);
        YearMonth previousYearLastMonth = YearMonth.of(date.minusYears(1).getYear(),  12);
        LocalDate lastDayOfMonth = previousYearLastMonth.atEndOfMonth();
        System.out.println(previousYear + " - " + lastDayOfMonth);
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (!transaction.getDate().isBefore(previousYear) && !transaction.getDate().isAfter(lastDayOfMonth))
            {
                System.out.println(Colors.YELLOW_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                System.out.println(Colors.RESET);
            }


        }
        userChoiceReport();
    }

    private static void userChoiceReport()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println();
        System.out.println(Colors.PURPLE + "What would you like to do?"  + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [R] " + Colors.CYAN + "Go to Back to Report" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [L] " + Colors.CYAN + "Go to Back to Ledger" + Colors.RESET);
        System.out.print(Colors.PURPLE + "Select an option: " + Colors.RESET);
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "r":
                report();
                break;
            case "l":
                System.out.println("Redirecting to home screen");
                ledger();
                break;
            default:
                System.out.println("invalid input");
                userChoiceReport();

        }

    }

    private static void searchByVendor()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println( Colors.RED + "Enter the name of the Vendor" +  Colors.RESET);
        String vendorName = userInput.nextLine();
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();
        boolean found = false;

        for( AccountingLedger transaction : transactions)
        {
            if (transaction.getVendor().equalsIgnoreCase(vendorName))
            {
                System.out.println(Colors.LIGHT_PINK_BACKGROUND);
                System.out.println(Colors.BLACK);
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                found = true;
                System.out.println(Colors.RESET);
            }

        }
        System.out.println();

        if (!found) {
            System.out.println("No transaction found under " + vendorName);
        }
        userSearchChoice();
    }

    private static void userSearchChoice()
    {
        System.out.println();
        System.out.println(Colors.GREEN + ("-").repeat(50) + Colors.RESET);
        System.out.println(Colors.PURPLE + "What would you like to do?" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [V] " + Colors.CYAN + "Search another vendor" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [R] " + Colors.CYAN + "Go to Back to Report" + Colors.RESET);
        System.out.println(Colors.LIGHT_MAGENTA + " [L] " + Colors.CYAN + "Go to Back to Ledger Entries" + Colors.RESET);
        String userChoice = userInput.nextLine().strip().toLowerCase();

        switch(userChoice)
        {
            case "v":
                searchByVendor();
            case "r":
                report();
            case "l":
                ledger();
            default:
                System.out.println("Invalid input");
                System.out.println("Please try again");
                userSearchChoice();
        }
    }
}


//    public static void writeToFile()
//    {
//        File file = new File("files/transactions.csv");
//
//        boolean fileExists = file.exists();
//
//
//
//        try (FileWriter fileWriter = new FileWriter(file,true);
//             PrintWriter writer = new PrintWriter(fileWriter);
//        )
//        {
//            if (!fileExists)
//            {
//                writer.write(" date | time | Description | Vendor  | Amount \n");
//            }
//
//            for (AccountingLedger transaction : transactions )
//            {
//                writer.write(transaction.getDate().format(DATE_FORMAT) + " | " + transaction.getTime().format(TIME_FORMAT) +" | " + transaction.getDescription() + " | " +
//                        transaction.getVendor() + " | " +
//                        transaction.getAmount() + "\n");
//            }
//
//
//
//        } catch (IOException ex) {
//            System.out.println("File failed");
//        }
//    }

//    public static void loadTrasactions()
//    {
//
//        File file = new File("files/transactions.csv");
//
//        try(Scanner fileScanner = new Scanner(file))
//        {
//
//            fileScanner.nextLine();
//
//            while(fileScanner.hasNext())
//            {
//                String line = fileScanner.nextLine();
//                String[] columns = line.split("[|]");
//
//
//                String date = columns[0].strip();
//                String time = columns[1].strip();
//                String description = columns[2].strip();
//                String vendor = columns[3].strip();
//                double amount = Double.parseDouble(columns[4].strip());
//
//                LocalDate dateTransaction = LocalDate.parse(date, DATE_FORMAT);
//                LocalTime timeTransaction = LocalTime.parse(time, TIME_FORMAT);
//
//                AccountingLedger information = new AccountingLedger(dateTransaction , timeTransaction , description, vendor, amount);
//                transactions.add(information);
//
//            }
//
//        } catch (IOException ex)
//        {
//            System.out.println("Unable to load file");
//
//        }
//
//    }



