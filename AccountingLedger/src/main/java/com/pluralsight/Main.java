package com.pluralsight;
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
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Welcome");
        System.out.println();
        System.out.println("Menu:");
        System.out.println();
        System.out.println("Please Select The Following:");
        System.out.println(" [D] Would you like to deposit today?");
        System.out.println(" [P] Would you like to make a payment today?");
        System.out.println(" [L] Would you like to view the Ledger?");
        System.out.println(" [X] Exit");
        System.out.println(("-").repeat(50));


        while(true)
        {
            try {
                String userChoice = userInput.nextLine().strip().toLowerCase();

                switch (userChoice) {
                    case "d":
                        makeDeposit();
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

    private static void makeDeposit()
    {
        System.out.println();
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println(" Enter the following information ");
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
            System.out.println(("-").repeat(50));
            System.out.println();

            logger.logTransactions(depositDescription, vendorDeposit, depositAmount);


        } catch (NumberFormatException ex) {
            System.out.println("Invalid input only type in the number");
            makeDeposit();
        }

        promptDepositChoice();
    }

    public static void promptDepositChoice()
    {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" [I] insert another deposit");
        System.out.println(" [X] Go to the home screen");
        System.out.print("Select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "i":
                makeDeposit();
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
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Payment Information");
        System.out.println();

        try {
            System.out.println("Enter the payment Amount");
            double paymentAmount = Double.parseDouble(userInput.nextLine().strip());
            paymentAmount = paymentAmount * -1;

            System.out.println("Enter the description of what you are paying.");
            String paymentDescription = userInput.nextLine();

            System.out.println("Enter the vendor to whom you are paying. (e.g. Amazon, Discovery credit card)  ");
            String vendorPayment = userInput.nextLine();

            System.out.println(("-").repeat(50));
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
        System.out.println("What would you like to do?");
        System.out.println(" [I] insert another payment");
        System.out.println(" [X] Go to the home screen");
        System.out.print("Select an option: ");
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
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Menu");
        System.out.println();
        System.out.println("Entries:");
        System.out.println();
        System.out.println("Will you like to view :");
        System.out.println(" [A] All transactions");
        System.out.println(" [D] Deposits entries only");
        System.out.println(" [P] Payments entries only");
        System.out.println(" [R] Reports");
        System.out.println(" [H] Home page");
        System.out.println(("-").repeat(50));

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
        System.out.printf("%-15s %-10s %-40s %-25s %-20s",date,time,description,vendor,amount);



    }
    private static void allEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("All Transactions");
        System.out.println();
        displayHeader();



        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {

            System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());

        }

        Collections.reverse(transactions);
        userChoice();

    }

    private static void depositEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("ALL Deposit Entries: ");
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();
        for (AccountingLedger transaction : transactions)
        {
            if (transaction.getAmount() > 0)
            {
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        Collections.reverse(transactions);

        userChoice();
    }

    private static void paymentEntries()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("ALL Payments Entries: ");
        System.out.println();
        displayHeader();

        Collections.reverse(transactions);
        System.out.println();

        for (AccountingLedger transaction : transactions)
        {
            if (transaction.getAmount() < 0)
            {
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }

        Collections.reverse(transactions);
        userChoice();
    }

    private static void userChoice()
    {
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" [L] Go back to View entries ");
        System.out.println(" [X] Go to the Home page");
        System.out.print("Select an option: ");
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
        System.out.println(("-").repeat(50));
        System.out.println("Report Search:");
        System.out.println();
        System.out.println("Please Select what you'll like to Search today");
        System.out.println(" [1] Month To Date");
        System.out.println(" [2] Previous Month ");
        System.out.println(" [3] Year To Date");
        System.out.println(" [4] Previous Year");
        System.out.println(" [5] Search by Vendor");
        System.out.println(" [0] Back");
        System.out.println(("-").repeat(50));

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
                        report();
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
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("Transactions made this month");
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

                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }

        }
        Collections.reverse(transactions);
        userChoiceReport();

    }

    private static void previousMonth()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println("Transactions made previous month");
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

                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }


        }
        Collections.reverse(transactions);
        userChoiceReport();

    }

    private static void yearToDate()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println("Transactions made this Year to Today's Date");
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

                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }
        }
        Collections.reverse(transactions);
        userChoiceReport();

    }


    private static void userChoiceReport()
    {
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" [R] Go to Back to Report ");
        System.out.println(" [L] Go to Back to Ledger");
        System.out.print("Select an option: ");
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



    private static void previousYear()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println();
        LocalDate date = LocalDate.now();
        System.out.println("Transactions made previous Year");
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
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
            }


        }

        Collections.reverse(transactions);
        userChoiceReport();
    }

    private static void searchByVendor()
    {
        ArrayList<AccountingLedger> transactions = logger.readLog();
        System.out.println(("-".repeat(50)));
        System.out.println("Enter the name of the Vendor");
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
                System.out.printf("%-15tF %-10tR %-40s %-25s %-20.2f \n", transaction.getDate(), transaction.getTime(), transaction.getDescription(), transaction.getVendor(), transaction.getAmount());
                found = true;
            }

        }
        System.out.println();

        if (!found) {
            System.out.println("No transaction found under " + vendorName);
        }


        Collections.reverse(transactions);
        userSearchChoice();
    }

    private static void userSearchChoice()
    {
        System.out.println();
        System.out.println(("-".repeat(50)));
        System.out.println("What would you like to do?");
        System.out.println(" [V] Search another vendor");
        System.out.println(" [R]  Go to Back to Report");
        System.out.println(" [L] Go to Back to Ledger Entries");
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



