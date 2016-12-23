/**
 * Created by user on 3/8/2016.
 */
import java.io.*;
import java.util.Properties;
import java.sql.*;
import java.util.Scanner;

public class CS1003P3 {
    //public static final String PROP_FILE_NAME = "database.properties";
    public static String contents;

    private static String INPUT_FILE_NAME;
    private static String PROP_FILE_NAME;

    private static void getInputFromUser() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter property file name: ");
        PROP_FILE_NAME = in.next();
        System.out.print("Enter input (data) file name: ");
        INPUT_FILE_NAME = in.next();
        in.close();
    }

    private static void getInputFromCmdLine(String[] args) {
        if (args.length!=2) {
            System.out.println("Incorrect number of command line arguments!");
            System.out.println("Please run as follows: ");
            System.out.println("\tjava <ClassName> <property-file-name> <input-file-name>");
            System.out.println("An example would be (assuming both files are in the same folder): ");
            System.out.println("\tjava CommandLineArgsExample database.properties data-small.csv");
            System.exit(1);
        }
        PROP_FILE_NAME = args[0];
        INPUT_FILE_NAME = args[1];
    }

    public static void main(String[] args) throws Exception {

        if (args.length == 0)
            getInputFromUser();
        else
            getInputFromCmdLine(args);

        System.out.println("Property file name: " + PROP_FILE_NAME);
        System.out.println("Input file name: " + INPUT_FILE_NAME);

        try {
            FileInputStream propInputStream = new FileInputStream(PROP_FILE_NAME);
            Properties properties = new Properties();
            properties.load(propInputStream);
            propInputStream.close();

            String host = properties.getProperty("host");
            String port = properties.getProperty("port");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            String db = properties.getProperty("db");

            StringBuilder builder = new StringBuilder();
            builder.append("jdbc:mysql://");
            builder.append(host);
            builder.append(":");
            builder.append(port);
            builder.append("/");
            builder.append(db);
            String URL = builder.toString();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
                try {
                    PrintWriter writer = new PrintWriter("moreTables.txt");
                    int i = 0;
                    contents = reader.readLine();
                    contents = reader.readLine();
                    Record record;

                    while (contents != null) {
                        i++;
                        contents = reader.readLine();
                    }
                    reader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
                    contents = reader.readLine();
                    contents = reader.readLine();

                    Connection connectionTable1 = null;
                    Connection connectionTable2 = null;
                    try {
                        while (contents != null) {
                            record = new Record();
                            record.setSampleID(extractSampleID());
                            record.setRecordSubmitTime(extractRecordSubmitTime());
                            record.setSampleHolderNo(extractSampleHolderNo());
                            record.setSampleDuration(extractSampleDuration());
                            record.setExpID(extractExpID());
                            record.setExpName(extractExpName());
                            record.setExpDescr(extractExpDescr());
                            record.setUserID(extractUserID());
                            record.setGroupAbbr(extractGroupAbbr());
                            record.setSolventAbbr(extractSolventAbbr());
                            record.setSpectrometerID(extractSpectrometerID());
                            record.setSpectrometerName(extractSpectrometerName());
                            record.setSpectrometerCapacity(extractSpectrometerCapacity());
                            try {
                                connectionTable1 = DriverManager.getConnection(URL, username, password);
                                createTableRecord(connectionTable1, i);
                                addDataToTableRecord(connectionTable1, record.getSampleID(), record.getRecordSubmitTime(),
                                        record.getSampleHolderNo(), record.getSampleDuration());
                                printTableRecord(connectionTable1, writer);

                            } finally {
                                if (connectionTable1 != null) connectionTable1.close();
                            }

                            try {
                                connectionTable2 = DriverManager.getConnection(URL, username, password);
                                createTableExperiment(connectionTable2, i);
                                addDataToTableExperiment(connectionTable2, record.getSampleID(), record.getExpID(),
                                        record.getExpName(), record.getExpDescr(), record.getUserID(), record.getGroupAbbr(),
                                        record.getSolventAbbr(), record.getSpectrometerID(), record.getSpectrometerName(), record.getSpectrometerCapacity());
                                printTableExperiment(connectionTable2, writer);

                            } finally {
                                if (connectionTable2 != null) connectionTable2.close();
                            }
                            contents = reader.readLine();
                        }
                    }catch(Exception e){
                        System.out.println("Input File Not Formatted Properly!");
                    }
                    writer.close();
                } catch(Exception e){
                    System.out.println("Couldn't create output file!");
                }
            } catch(FileNotFoundException e){
                System.out.println(e.getMessage());
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void createTableRecord(Connection connection, int i) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS records");
            statement.executeUpdate("CREATE TABLE records (sampleID VARCHAR(" + i + "), recordSubmitTime VARCHAR(" + i + "), " +
                    "sampleHolderNo VARCHAR(" + i + "), sampleDuration VARCHAR(" + i + "))");

        } finally {
            if (statement != null) statement.close();
        }
    }

    private static void createTableExperiment(Connection connection, int i) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS experiments");
            statement.executeUpdate("CREATE TABLE experiments (sampleID VARCHAR(" + i + "), expID VARCHAR(" + i + "), " +
                    "expName VARCHAR(" + i + "), expDescr VARCHAR(" + i + "), userID VARCHAR(" + i +"), " +
                    "groupAbbr VARCHAR(" + i +"), solventAbbr VARCHAR(" + i +"), spectrometerID VARCHAR(" + i +"), " +
                    "spectrometerName VARCHAR(" + i +"), " +
                    "spectrometerCapacity VARCHAR(" + i +"))");

        } finally {
            if (statement != null) statement.close();
        }
    }



    private static void addDataToTableRecord(Connection connection, String sampleID, String recordSubmitTime,
                                       String sampleHolderNo, String sampleDuration) throws SQLException {

        insertRecordUsingStatement(connection, sampleID, recordSubmitTime, sampleHolderNo, sampleDuration);
    }

    private static void addDataToTableExperiment(Connection connection, String sampleID, String expID, String expName,
                                                 String expDescr, String userID, String groupAbbr,
                                                 String solventAbbr, String spectrometerID,
                                                 String spectrometerName, String spectrometerCapacity) throws SQLException {

        insertExperimentUsingStatement(connection, sampleID, expID, expName, expDescr, userID, groupAbbr,
                solventAbbr, spectrometerID, spectrometerName, spectrometerCapacity);
    }

    private static void insertRecordUsingStatement(Connection connection, String sampleID, String recordSubmitTime,
                                                   String sampleHolderNo, String sampleDuration) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();

            String update_string = "INSERT INTO records VALUES ('" + sampleID + "','" + recordSubmitTime + "'," +
                    "'" + sampleHolderNo + "','" + sampleDuration + "')";

            statement.executeUpdate(update_string);

        } finally {
            if (statement != null) statement.close();
        }
    }

    private static void insertExperimentUsingStatement(Connection connection, String sampleID,
                                                       String expID, String expName, String expDescr,
                                                       String userID, String groupAbbr,
                                                       String solventAbbr, String spectrometerID,
                                                       String spectrometerName, String spectrometerCapacity) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();

            String update_string = "INSERT INTO experiments VALUES ('" + sampleID + "','" + expID + "'," +
                    "'" + expName + "','" + expDescr + "','" + userID + "','" + groupAbbr + "','" + solventAbbr +
                    "','" + spectrometerID + "','" + spectrometerName + "','" + spectrometerCapacity + "')";

            statement.executeUpdate(update_string);

        } finally {
            if (statement != null) statement.close();
        }
    }

    private static void insertPersonUsingPreparedStatement(String name, int age, Connection connection) throws SQLException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO records VALUES (?, ?)");

            statement.setString(1, name);
            statement.setInt(2, age);

            System.out.println("executing SQL update: " + statement);

            statement.execute();

        } finally {
            if (statement != null) statement.close();
        }
    }

    private static void printTableRecord(Connection connection, PrintWriter writer) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM records");

            while (resultSet.next()) {

                String sampleID = resultSet.getString(1);
                String recordSubmitTime = resultSet.getString(2);
                String sampleHolderNo = resultSet.getString(3);
                String sampleDuration = resultSet.getString(4);

                writer.println("----Records Table----");
                writer.println("sampleID: " + sampleID);
                writer.println("recordSubmitTime: " + recordSubmitTime);
                writer.println("sampleHolderNo: " + sampleHolderNo);
                writer.println("sampleDuration: " + sampleDuration);
                writer.println();
            }
        } finally {
            if (statement != null) statement.close();
        }
    }

    private static void printTableExperiment(Connection connection, PrintWriter writer) throws SQLException {

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM experiments");

            while (resultSet.next()) {

                String sampleID = resultSet.getString(1);
                String expID = resultSet.getString(2);
                String expName = resultSet.getString(3);
                String expDescr = resultSet.getString(4);
                String userID = resultSet.getString(5);
                String groupAbbr = resultSet.getString(6);
                String solventAbbr = resultSet.getString(7);
                String spectrometerID = resultSet.getString(8);
                String spectrometerName = resultSet.getString(9);
                String spectrometerCapacity = resultSet.getString(10);

                writer.println("----Experiment Table----");
                writer.println("sampleID: " + sampleID);
                writer.println("expID: " + expID);
                writer.println("expName: " + expName);
                writer.println("expDescr: " + expDescr);
                writer.println("userID: " + userID);
                writer.println("groupAbbr: " + groupAbbr);
                writer.println("solventAbbr: " + solventAbbr);
                writer.println("spectrometerID: " + spectrometerID);
                writer.println("spectrometerName: " + spectrometerName);
                writer.println("spectrometerCapacity: " + spectrometerCapacity);
                writer.println();
            }
        } finally {
            if (statement != null) statement.close();
        }
    }

    public static String extractSampleID() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractRecordSubmitTime() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSampleHolderNo() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSampleDuration() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractExpID() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractExpName() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractExpDescr() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractUserID() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractGroupAbbr() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSolventAbbr() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSpectrometerID() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSpectrometerName() {
        String str=contents.substring(0,contents.indexOf(','));
        contents = contents.substring(contents.indexOf(",") + 1);
        return str;
    }

    public static String extractSpectrometerCapacity() {
        String str=contents;
        return str;
    }
}
