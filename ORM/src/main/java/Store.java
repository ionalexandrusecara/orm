import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Map;
import java.io.*;
import java.util.*;


public class Store {

    static String contents;

    private static String INPUT_FILE_NAME;

    private static void getInputFromUser() {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter input (data) file name: ");
        INPUT_FILE_NAME = in.next();
        in.close();
    }

    private static void getInputFromCmdLine(String[] args) {
        if (args.length!=1) {
            System.out.println("Incorrect number of command line arguments!");
            System.out.println("Please run as follows: ");
            System.out.println("\tjava <ClassName> <input-file-name>");
            System.out.println("An example would be : ");
            System.out.println("\tjava CommandLineArgsExample data-small.csv");
            System.exit(1);
        }
        INPUT_FILE_NAME = args[0];
    }

    public static void main(String[] args) throws IOException{

        if (args.length == 0)
            getInputFromUser();
        else
            getInputFromCmdLine(args);

        System.out.println("Input file name: " + INPUT_FILE_NAME);

        Properties.setup();

        Map<String, String> properties = Properties.getPropertiesForTableAutoCreation();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Record", properties);
        EntityManager entity_manager = factory.createEntityManager();

        entity_manager.getTransaction().begin();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE_NAME));
            int i = 0;
            contents = reader.readLine();
            contents = reader.readLine();
            Record record;
            try {
                while (contents != null) {
                    i++;
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
                    entity_manager.persist(record);
                    contents = reader.readLine();
                }
            } catch(Exception e){
                System.out.println("Input file not formatted properly!");
            }
        } catch(FileNotFoundException e){
            System.out.println(e.getMessage());
        }

        entity_manager.getTransaction().commit();
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
