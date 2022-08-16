/**
 * It's a class that contains methods for serializing and deserializing a ArrayList and writing it to a file and retrieving
 * it
 */
/**
 * It's a class that contains methods for serializing and deserializing a ArrayList and writing it to a file and retrieving
 * it
 */
package emailCLient;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
// Importing the java.util package.
import java.util.*;


/**
 * This class is the main class of the project. It is responsible for the main functionality of the project
 */
public class EmailClientFinal {
     public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {
        AddRecipients add_recipients = new AddRecipients();
        emailSender emailSender = new emailSender();

        System.out.println("Welcome to the emailCLient.Email Client\n" +
                "Please Be Patient Till Check and Send Wishes for Those who have birthday\n");
        ArrayList recipients = new ArrayList<>();
        String clientPath = "clientList.ser";
        try {
            File myObj = new File(clientPath);
            if (myObj.length() != 0) {
                recipients = (SerializeDeserialize.Deserialize(clientPath));
            }

        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }

        new emailSender();
        StartRestart.start(recipients);

        System.out.println("Ok All Set to Go");


        boolean start = true;

        while (start) {
            // create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)
            Scanner scanner;
            scanner = new Scanner(System.in);
            System.out.println("\nEnter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n"
                    + "6 - End the Programme\n");
            int option = 0;
            try {
                option = scanner.nextInt();

            } catch (InputMismatchException e) {
                System.out.println("Invalid Choice");
            }


            switch (option) {
                case 1:
                    // input format - Official: nimal,nimal@gmail.com,ceo
                    // Use a single input to get all the details of a recipient
                    // code to add a new recipient
                    // store details in clientList.txt file
                    // Hint: use methods for reading and writing files
                    System.out.println("input format - Official: nimal,nimal@gmail.com,ceo  or\n" +
                            "               Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12   or\n" +
                            "               Personal: sunil,<nick-name>,sunil@gmail.com,2000/10/10\n");

                    String[] typeDetails;
                    String[] nameEmail;

                    Scanner sc = new Scanner(System.in);
                    String details = sc.nextLine();


                    typeDetails = details.split(": ");
                    try {
                        nameEmail = typeDetails[1].split(",");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid Input");
                        break;
                    }

                    try {
                        recipients.add(add_recipients.addRecipients(typeDetails, nameEmail));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    SerializeDeserialize.Serialize(recipients);
                    StartRestart.restart(Objects.requireNonNull(add_recipients.addRecipients(typeDetails, nameEmail)));
                    System.out.println("Recipient added successfully");

                    String Path1 = "clientList.txt";
                    String newdetails = details + "\n";

                    File yourFile = new File("clientList.txt");
                    yourFile.createNewFile(); // if file already exists will do nothing
                    FileOutputStream oFile = new FileOutputStream(yourFile, false);

                    Files.write(Paths.get(Path1), newdetails.getBytes(), StandardOpenOption.APPEND);

                    break;
                case 2:
                    // input format - email, subject, content
                    // code to send an email
                    System.out.println("input format - email, subject, content");
                    Scanner scnew = new Scanner(System.in);
                    String recipient = scnew.nextLine();
                    String[] recarray = recipient.split(", ");
                    try {
                        emailSender.emailSender(recarray[0], recarray[1], recarray[2]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid Input");
                        break;
                    }

                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print recipients who have birthdays on the given date
                    System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
                    Scanner scc = new Scanner(System.in);
                    String date = scc.nextLine();
                    ArrayList<Recipients> arrayList = Birthday_Checker.checkBirthday(recipients, date);

                    for (Recipients r : arrayList) {
                        System.out.println(r.getName() + " " + r.getEmail());
                    }

                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print the details of all the emails sent on the input date
                    System.out.println("input format - yyyy/MM/dd (ex: 2018/09/17)");
                    Scanner sccc = new Scanner(System.in);
                    String datee = sccc.nextLine();

                    try {
                        if (AlternativeMethods.StringToDate(datee) == null) {
                            break;
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        for (Email e : emailSender.getEmailrecords()) {

                            if (e.getDate().equals(Birthday_Checker.StringToDate(datee))) {
                                System.out.println(e.getRecipient() + " " + e.getSubject());
                            }
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    break;
                case 5:
                    // code to print the number of recipient objects in the application

                    System.out.println(recipients.size());
                    break;

                case 6:
                    start = false;
            }

            // start email client
            // code to create objects for each recipient in clientList.txt
            // use necessary variables, methods and classes

        }
    }
}
//class for emailCLient.Email Object
class Email implements Serializable {

    /**
     *
     */
    Date date ;
    String recipient ;

    String subject;

    public Date getDate() {
        return date;
    }
    public Email(Date date, String recipient, String subject) {
        this.date = date;
        this.recipient = recipient;
        this.subject = subject;
    }
    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }


}
//Class For Adding Recipient methods
class AddRecipients {
    public  Date StringToDate(String datesrting) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(datesrting);
    }

    //method to create new Recipient objects
    public Recipients addRecipients(String[] type_details, String[] name_email) throws ParseException {
        switch (type_details[0]) {
            case "Personal":
                return new Personal_Recipints(type_details[0], name_email[0], name_email[1], name_email[2],
                        StringToDate(name_email[3]) );

            case "Office_friend":
                return new OfficialPersonalRecipients(type_details[0], name_email[0], name_email[1], name_email[2],
                        StringToDate(name_email[3]) );
            case "Official":
                return new OfficialRecipients(type_details[0], name_email[0], name_email[1], name_email[2]);

            default:
                System.out.println("Invalid Recipient Type");

        }
        return null;
    }

    //method to nenewing the previously entered Recipient objects
    public static void renewRecipients(Recipients pastRecipient) throws ParseException {
        switch (pastRecipient.getType()) {
            case "Personal":
                Personal_Recipints newRecipient = (Personal_Recipints) pastRecipient;
                new Personal_Recipints(pastRecipient.getType(), pastRecipient.getName(),
                        ((Personal_Recipints) pastRecipient).getNickname(), pastRecipient.getEmail(),
                        ((Personal_Recipints) pastRecipient).getBirthday());

                break;
            case "Office_friend":
                new OfficialPersonalRecipients(pastRecipient.getType(), pastRecipient.getName(),
                        pastRecipient.getEmail(), ((OfficialPersonalRecipients) pastRecipient).getDesignation(),
                        ((OfficialPersonalRecipients) pastRecipient).getBirthday());

                break;
            case "Official":
                new OfficialRecipients(pastRecipient.getType(), pastRecipient.getName(), pastRecipient.getEmail(),
                        ((OfficialRecipients) pastRecipient).getDesignation());

                break;
        }

    }
}



//all the relavent additional methods includes here
class AlternativeMethods {

    //method to nenewing previosly sent emailCLient.Email objects
    public static Email renewEmailRecords(Email emailrecords) throws ParseException {
        return (new Email(emailrecords.getDate() , emailrecords.getRecipient() , emailrecords.getSubject()));
    }

    public static Date toDaydate() throws ParseException {
        // Creating the LocalDatetime object
        LocalDate currentLocalDate = LocalDate.now();

        // Getting system timezone
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // converting LocalDateTime to ZonedDateTime with the system timezone
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);

        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
        return Date.from(zonedDateTime.toInstant());

    }
    public static Date StringToDate(String datesrting) throws ParseException {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(datesrting);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        return null;
    }
}
//class containing all the methods for checking birthdays
class Birthday_Checker {
    public static Date StringToDate(String datesrting) throws ParseException {
        try {
            return new SimpleDateFormat("yyyy/MM/dd").parse(datesrting);
        } catch (ParseException e) {
            System.out.println("Invalid date format");
        }
        return null;
    }

    //method to check if the birthday is on a given date or not
    public static boolean isBirthday(Date birthday, Date date) {
        if(date != null) {
            return birthday.getMonth() == date.getMonth() && birthday.getDate() == date.getDate();
        }
        return false;
    }

    //method to return a list of recipients who have birthday on a given date
    public static ArrayList<Recipients> checkBirthday(ArrayList<Recipients> recipients , String Date) throws ParseException {
        Date date = StringToDate(Date);
        ArrayList<Recipients> recipientsList = new ArrayList<Recipients>();
        for (Recipients recipient : recipients) {
            switch (recipient.getType()) {
                case "Personal": {
                    Personal_Recipints newRecipient = (Personal_Recipints) recipient;
                    if (isBirthday(newRecipient.birthday, date)) {
                        recipientsList.add(newRecipient);
                    }
                    break;
                }
                case "Office_friend": {
                    OfficialPersonalRecipients newRecipient = (OfficialPersonalRecipients) recipient;
                    if (isBirthday(newRecipient.birthday, date)) {
                        recipientsList.add(newRecipient);
                    }
                    break;
                }
            }
        }
        return recipientsList;
    }
}
class EmailRecChecker {
    // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
    static Date date;

    static {
        try {
            date = AlternativeMethods.toDaydate();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    //this method checks if a wish to a recipient is already sent or not
    public static boolean isSent(Date birthday , String email , String subject , ArrayList<Email> emailRecords){
        try{
            if(emailRecords != null) {
                for (Email i : emailRecords) {
                    if (i.getDate().getYear() == date.getYear() && i.getDate().getMonth() == birthday.getMonth() &&
                            i.getDate().getDate() == birthday.getDate() && Objects.equals(subject, i.getSubject()) &&
                            i.getRecipient().equals(email)) {
                        return false;
                    }
                }

            }

        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }


        return true;
    }
}
class emailSender {

    //method for sending and email
    public emailSender() throws IOException, ClassNotFoundException {
        String Path5 = "Mail_Records.txt";

        File newFile = new File(Path5);
        if (newFile.length() != 0) {
            emailrecords = SerializeDeserialize.deserializeEmailRecords(Path5);
        }
    }

    static ArrayList emailrecords = new ArrayList<>();

    public static void setEmailrecords(ArrayList<Email> emailrecords) {
        emailSender.emailrecords = emailrecords;
    }

    public static void emailSender(String recipient, String mess, String content) throws IOException, ClassNotFoundException {

        final String username = "mohamedmuaadh231@gmail.com";
        final String password = "meyccryjdmbgrrex";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("mohamedmuaadh231@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipient)
            );
            message.setSubject(mess);
            message.setText(content);
            Transport.send(message);


            System.out.println("Done");


        } catch (MessagingException e) {
            e.printStackTrace();
        }


        // Creating the LocalDatetime object
        LocalDate currentLocalDate = LocalDate.now();

        // Getting system timezone
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // converting LocalDateTime to ZonedDateTime with the system timezone
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);

        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
        Date utilDate = Date.from(zonedDateTime.toInstant());

        try {
            emailrecords.add(new Email(utilDate, recipient, content));

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            SerializeDeserialize.serializeEmails(emailrecords);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Email> getEmailrecords() {
        return emailrecords;
    }
}
//class for Official_Personal_Recipients
class OfficialPersonalRecipients extends OfficialRecipients implements Serializable {

    /**
     *
     */

    Date birthday ;
    Date last_wished_date = null ;

    public OfficialPersonalRecipients(String type, String name, String email, String designation , Date birthday ) {
        super(type, name, email, designation);
        this.birthday = birthday;
    }
    public Date getBirthday() {
        return birthday;
    }

    public Date getLast_wished_year() {
        return last_wished_date;
    }

    public void setLastWishedYear(Date last_wished_year) {
        this.last_wished_date = last_wished_year;
    }
}
class OfficialRecipients extends Recipients implements Serializable {
    private final String designation ;

    public OfficialRecipients(String type, String name, String email, String designation) {
        super(type, name, email);
        this.designation = designation;
    }
    public String getDesignation() {
        return designation;
    }
}

class Personal_Recipints extends Recipients implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public final  Date birthday ;

    private Date lastWishedDate = null ;

    private final String nickname ;

    public Date getBirthday() {
        return birthday;
    }

    public String getNickname() {
        return nickname;
    }



    public Personal_Recipints(String type, String name,String nickname , String email, Date birthday  ) {
        super(type, name, email);
        this.birthday = birthday;
        this.nickname = nickname;

    }
    public Date getLast_wished_year() {
        return lastWishedDate;
    }

    public void setLastWishedYear(Date last_wished_year) {
        this.lastWishedDate = last_wished_year;

    }
}
class Recipients implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String type;
    private String name ;
    private String email ;

    public Recipients(String type, String name, String email) {
        this.setType(type);
        this.setName(name);
        this.setEmail(email);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
//this class contains methods for Serializing and deserializing a ArrayList and writing it to a file and retrieving it
class SerializeDeserialize {

    //method for serializing an ArrayList of emailCLient.Recipients and writing it
    public static void Serialize(ArrayList<Recipients> recipients) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream("clientList.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(recipients);
            oos.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }


    //method for deserializing an ArrayList of emailCLient.Recipients
    public static ArrayList Deserialize(String Path) throws IOException, ClassNotFoundException {
        ArrayList Recipients = null;
        try {
            FileInputStream is = new FileInputStream(Path);
            ObjectInputStream ois = new ObjectInputStream(is);

            Recipients = (ArrayList) ois.readObject();

            for (Object r : Recipients) {
                AddRecipients.renewRecipients((Recipients) r);
            }

            ois.close();
            is.close();



        } catch (IOException i) {
            i.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return Recipients;
    }


    //method for serializing an ArrayList of Emails and writing it
    public static void serializeEmails(ArrayList<Email> emailrecords) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream("Mail_Records.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            // write object to file
            oos.writeObject(emailrecords);
            oos.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    //method for deserializing an ArrayList of Emails
    public static ArrayList<Email> deserializeEmailRecords(String Path) throws IOException, ClassNotFoundException {
        ArrayList<Email> TempEmailRecords = new ArrayList<Email>();

        try {
            FileInputStream is = new FileInputStream(Path);
            ObjectInputStream ois = new ObjectInputStream(is);

            ArrayList<Email> EmailRecords = (ArrayList<Email>) ois.readObject();

            for (Email r : EmailRecords) {
                TempEmailRecords.add(AlternativeMethods.renewEmailRecords(r));
            }

            ois.close();
            is.close();


        } catch (EOFException e) {
            // ... this is fine
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return TempEmailRecords;
    }
}
class StartRestart {

    public static Date dateReturner() {
        // Creating the LocalDatetime object
        LocalDate currentLocalDate = LocalDate.now();

        // Getting system timezone
        ZoneId systemTimeZone = ZoneId.systemDefault();

        // converting LocalDateTime to ZonedDateTime with the system timezone
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);

        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()


        return Date.from(zonedDateTime.toInstant());
    }

    //this method starts the program by recreating the previously saved Recipient objects
    public static void start(ArrayList<Recipients> recipients) throws ParseException, IOException, NullPointerException,
            ClassNotFoundException {


        // converting ZonedDateTime to Date using Date.from() and ZonedDateTime.toInstant()
        Date date = dateReturner();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String strDate = formatter.format(date);
        ArrayList<Recipients> recipients_list = Birthday_Checker.checkBirthday(recipients, strDate);

        for (Recipients recipient : recipients_list) {
            restart(recipient);
        }

    }

    //this methods will check a given Recipient's bithday is on today and if it's today this will call the method to send the email
    // only if previously the wish hasn't been sent
    public static void restart(Recipients recipient) {
        try {
            if (recipient.getType().equals("Personal")) {
                Personal_Recipints newRecipient = (Personal_Recipints) recipient;
                if (EmailRecChecker.isSent(newRecipient.getBirthday(), newRecipient.getEmail(),
                        "Happy Birthday! \n Muaadh", emailSender.emailrecords)) {
                    newRecipient.setLastWishedYear(dateReturner());
                    if (Birthday_Checker.isBirthday(newRecipient.birthday, AlternativeMethods.toDaydate())) {
                        emailSender.emailSender(newRecipient.getEmail(), "Hug and Love on Your Birthday " +
                                newRecipient.getName() + "!", "Happy Birthday! \n Muaadh");
                    }
                }
            } else if (recipient.getType().equals("Office_friend")) {
                OfficialPersonalRecipients newRecipient = (OfficialPersonalRecipients) recipient;
                if (EmailRecChecker.isSent(newRecipient.getBirthday(), newRecipient.getEmail(),
                        "Happy Birthday! \n Muaadh", emailSender.emailrecords)) {
                    newRecipient.setLastWishedYear(dateReturner());
                    if (Birthday_Checker.isBirthday(newRecipient.birthday, AlternativeMethods.toDaydate())) {
                        emailSender.emailSender(newRecipient.getEmail(), "Wish you a Happy Birthday " +
                                newRecipient.getName() + "!", "Happy Birthday! \n Muaadh");
                    }
                }
            }

        } catch (ParseException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}








