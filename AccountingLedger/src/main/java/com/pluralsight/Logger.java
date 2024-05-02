package com.pluralsight;


import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Logger
{
    private final String LOG_DIRECTORY_PATH = "files";
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");

    private String fileName;
    private String filePath;

    public Logger(String fileName)
    {
        // this makes sure that the logs folder is created
        File directory = new File(LOG_DIRECTORY_PATH);
        if(!directory.exists())
        {
            directory.mkdir();
        }

        // create a variable with the correct file path
        this.fileName = fileName;
        this.filePath = LOG_DIRECTORY_PATH + "/" + fileName;
        if(!this.filePath.toLowerCase().endsWith(".csv"))
        {
            this.filePath += ".csv";
        }
    }

    public void logTransactions(String description, String vendor, double amount)
    {

        File logFile = new File(filePath);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        boolean fileExists = logFile.exists();

        try(FileWriter fileWriter = new FileWriter(logFile, true);
            PrintWriter writer = new PrintWriter(fileWriter)
        )
        {
            if (!fileExists)
            {
                writer.write("date|time|Description|Vendor|Amount \n");
            }

            writer.write(String.format("%s|%s|%s|%s|%.2f\n", date.format(DATE_FORMAT), time.format(TIME_FORMAT),
                    description,vendor,amount));
        }
        catch (IOException ex)
        {
            System.out.println("Data transaction failed");
        }


    }

    public ArrayList<AccountingLedger> readLog()
    {
        ArrayList<AccountingLedger> transactions = new ArrayList<>();

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

                AccountingLedger information = new AccountingLedger(dateTransaction, timeTransaction, description, vendor, amount);

                transactions.add(information);
            }

        }
        catch (IOException ex)
        {
            System.out.println("Error reading log file: " + ex.getMessage());

        }

        return transactions;
    }
}
