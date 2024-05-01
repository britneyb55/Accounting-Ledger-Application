package com.pluralsight;
import java.io.*;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main

{
    private static ArrayList<AccountingLedger> depositPayment = new ArrayList<>();
    private static ArrayList<AccountingLedger> pastDepositPayment = new ArrayList<>();
    private static Scanner userInput = new Scanner(System.in);

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");



    public static void main(String[] args)
    {
        loadTrasactions();
        HomeScreen();
    }

    public static void HomeScreen() {
        System.out.println();
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Welcome");
        System.out.println();
        System.out.println("Menu:");
        System.out.println();
        System.out.println("Please Select The Following:");
        System.out.println("D) Would you like to deposit today?");
        System.out.println("P) Would you like to make a payment today?");
        System.out.println("L) Would you like to view the Ledger?");
        System.out.println("X) Exit");
        System.out.println(("-").repeat(50));

        try {
            while (true) {
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
                        HomeScreen();
                        break;
                }

            }
        } catch (InputMismatchException ex) {
            System.out.println("Not a valid input Entered ");
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

            LocalDate date = LocalDate.now();
            LocalTime time =  LocalTime.now();

            AccountingLedger information = new AccountingLedger(date,time , paymentDescription,vendorPayment, paymentAmount);
            depositPayment.add(information);

            writeToFile();

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
        System.out.println(" I) insert another payment");
        System.out.println(" X) Go to the home screen");
        System.out.print("Select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "i":
                makePayment();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                HomeScreen();
                break;
            default:
                System.out.println("invalid input");
        }
    }

    private static void addDeposit() {
        System.out.println();
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Enter the following information");
        System.out.println();

        try {

            System.out.println("Enter the amount to deposit");
            double amountDeposit = Double.parseDouble(userInput.nextLine().strip());
            System.out.println();

            System.out.println("Enter the Source where the fund are coming (e.g. paycheck, savings, gift)");
            String depositDescription = userInput.nextLine();
            System.out.println();

            System.out.println("Enter the name of the vendor to who is paying you.");
            String vendorDeposit = userInput.nextLine();
            System.out.println(("-").repeat(50));
            System.out.println();

            LocalDate date = LocalDate.now();
            LocalTime time =  LocalTime.now();

            AccountingLedger information = new AccountingLedger(date, time, depositDescription,vendorDeposit,amountDeposit);
            depositPayment.add(information);

            writeToFile();


        } catch (NumberFormatException ex) {
            System.out.println("Invalid input only type in the number");
            addDeposit();
        }

        promptDepositChoice();

    }

    public static void promptDepositChoice()
    {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" I) insert another deposit");
        System.out.println(" X) Go to the home screen");
        System.out.print("Select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "i":
                addDeposit();
                promptDepositChoice();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                HomeScreen();
                break;
            default:
                System.out.println("invalid input");
        }
    }

    public static void writeToFile()
    {
        File file = new File("files/transactions.csv");

        boolean fileExists = file.exists();


        try (FileWriter fileWriter = new FileWriter(file,true);
             PrintWriter writer = new PrintWriter(fileWriter);
        )
        {
            if (!fileExists)
            {
                writer.write(" date | time | Description | Vendor  | Amount \n");
            }

            for (AccountingLedger amount : depositPayment )
            {
                writer.write(amount.getDate().format(DATE_FORMAT) + " | " + amount.getTime().format(TIME_FORMAT) +" | " + amount.getDescription() + " | " +
                        amount.getVendor() + " | " +
                        amount.getAmount() + "\n");
            }


        } catch (IOException ex) {
            System.out.println("File failed");
        }
    }

    public static void loadTrasactions()
    {

        File file = new File("files/transactions.csv");

        try(Scanner fileScanner = new Scanner(file))
        {
             fileScanner.nextLine();

            while(fileScanner.hasNext())
            {
                String line = fileScanner.nextLine();
                String[] columns = line.split("[|]");


                String date = columns[0].strip();
                String time = columns[1].strip();
                String description = columns[2].strip();
                String vendor = columns[3].strip();
                double amount = Double.parseDouble(columns[4].strip());

                LocalDate dateTransaction = LocalDate.parse(date, DATE_FORMAT);
                LocalTime timeTransaction = LocalTime.parse(time, TIME_FORMAT);

                AccountingLedger information = new AccountingLedger(dateTransaction , timeTransaction , description, vendor, amount);
                pastDepositPayment.add(information);

            }
        } catch (IOException ex)
        {
            System.out.println("Unable to load file");

        }

    }

    private static void ledger()
    {
        System.out.println();
        System.out.println(("-").repeat(50));
        System.out.println();
        System.out.println("Menu Entries:");
        System.out.println();
        System.out.println("Will you like to view :");
        System.out.println("A) All - Display all entries");
        System.out.println("D) Deposits entries only");
        System.out.println("P) Payments entries only");
        System.out.println("R) Reports");
        System.out.println("H) Home page");
        System.out.println(("-").repeat(50));

        while (true)
        {
            String entriesChoice = userInput.nextLine().strip().toLowerCase();

            switch (entriesChoice) {
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
                    break;

                default:
                    System.out.println("Invalid Selection");
                    HomeScreen();
                    break;
            }
        }

    }

    private static void allEntries()
    {


        System.out.println("All Transactions");
        System.out.println();

        for (AccountingLedger amount : depositPayment )
        {
            System.out.printf(" %-15tF %-10tR %-20s %-25s %-20.2f \n" ,amount.getDate(), amount.getTime(), amount.getDescription(), amount.getVendor(), amount.getAmount());
        }
        pastEntries();
        userChoice();

    }

    private static void pastEntries()
    {

        for (AccountingLedger transactions : pastDepositPayment)
        {
            System.out.printf(" %-15tF %-10tR %-20s %-25s %-20.2f \n" , transactions.getDate(), transactions.getTime(), transactions.getDescription(), transactions.getVendor(), transactions.getAmount());
        }

    }


    private static void depositEntries()
    {
        System.out.println("Deposit: ");
        System.out.println();
        for (AccountingLedger amount : depositPayment )
        {
            if (amount.getAmount() > 0)
            {

                System.out.printf("%-13tF %-15.2f \n",amount.getDate(), amount.getAmount());
            }

        }

        for (AccountingLedger transactions : pastDepositPayment)
        {
            if (transactions.getAmount() > 0)
            {

                System.out.printf("%-13tF %-15.2f\n", transactions.getDate(), transactions.getAmount());
            }
        }
        userChoice();

    }

    private static void paymentEntries()
    {
        System.out.println("Payments: ");


        for (AccountingLedger amount : depositPayment )
        {
            if (amount.getAmount() < 0)
            {

                System.out.printf("%-13tF %-15.2f \n" ,amount.getDate(), amount.getAmount());
            }

        }

        for (AccountingLedger transactions : pastDepositPayment)
        {
            if (transactions.getAmount() < 0)
            {

                System.out.printf("%-13tF %-15.2f \n", transactions.getDate(), transactions.getAmount());
            }
        }
        userChoice();
    }

    private static void userChoice()
    {
        System.out.println();
        System.out.println("What would you like to do?");
        System.out.println(" L) View entries ");
        System.out.println(" X) Go to the home screen");
        System.out.print("Select an option: ");
        String choice = userInput.nextLine().strip().toLowerCase();

        switch(choice)
        {
            case "l":
                ledger();
                break;
            case "x":
                System.out.println("Redirecting to home screen");
                HomeScreen();
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
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month ");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search by Vendor");
        System.out.println("0) Back");
        System.out.println(("-").repeat(50));


        while (true)
        {
            int userChoice = Integer.parseInt(userInput.nextLine());

            switch (userChoice)
            {
                case 1:
                    monthToDate();
                    break;
                case 2:
                    //previousMonth();
                    break;
                case 3:
                   // yearToDate();
                    break;
                case 4:
                   // previousYear();
                    break;
                case 5:
                   // searchByVendor();
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


    }

    private static void monthToDate()
    {
        System.out.println();
        System.out.println("Transactions made this month");

        LocalDate date = LocalDate.now();
        date.getMonth();
        date.getYear();

        LocalDate start = LocalDate.of ( date.getYear() , date.getMonth() , 1 ) ;
        LocalDate stop = LocalDate.of ( date.getYear() , date.getMonth(), date.getDayOfMonth() ) ;
        Period period = Period.between ( start , stop ) ;

        for (AccountingLedger amount : depositPayment)
        {
            if (!amount.getDate().isBefore(start) && !amount.getDate().isAfter(stop))
            {
                System.out.printf(" %-15tF %-10tR %-20s %-25s %-20.2f \n" ,amount.getDate(), amount.getTime(), amount.getDescription(), amount.getVendor(), amount.getAmount());

            }
        }

        for (AccountingLedger pastTransactions : pastDepositPayment)
        {
            if (!pastTransactions.getDate().isBefore(start) && !pastTransactions.getDate().isAfter(stop))
            {
                System.out.printf(" %-15tF %-10tR %-20s %-25s %-20.2f \n" , pastTransactions.getDate(), pastTransactions.getTime(), pastTransactions.getDescription(), pastTransactions.getVendor(), pastTransactions.getAmount());

            }
        }


    }

}



