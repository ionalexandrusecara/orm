import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * This class is for retrieving all items in the Record table. The List of Record objects returned
 * is the equivalent of a JDBC ResultSet. 
 *
 * Like the 'Retrieve' class, a Transaction is not required because the content of the database is unchanged
 *
 */
public class RetrieveAll {

    public static void main(String[] args) throws IOException{
        /*
        This is the initial code required to create an Entity Manager
        You do not need to understand the underlying implementation of this code (as its out of scope for CS1003)
        Simply use this code to obtain an EntityManager
        */
        Properties.setup();

        // This uses getPropertiesForTableValidation: to use the existing tables
        Map<String, String> properties = Properties.getPropertiesForTableValidation();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Record", properties);
        EntityManager entity_manager = factory.createEntityManager();

        // Simple query that returns all data from the Employee table. 
        // If just names were required, a similar query would read "SELECT e.name FROM Employee e"
        Query query = entity_manager.createQuery("SELECT r FROM Record r");
        List<Record> records = query.getResultList();

        // Since this is a Java collection, a simple for loop would be enough
        try {
            PrintWriter writer = new PrintWriter("retrieveAll.txt");
            try {
                for (Record record : records) {
                    writer.println("Sample ID: " + record.getSampleID());
                    writer.println("Record Submit Time: " + record.getRecordSubmitTime());
                    writer.println("Sample Holder Number: " + record.getSampleHolderNo());
                    writer.println("Sample Duration: " + record.getSampleDuration());
                    writer.println("Experiment ID: " + record.getExpID());
                    writer.println("Experiment Name: " + record.getExpName());
                    writer.println("Experiment Description: " + record.getExpDescr());
                    writer.println("User ID: " + record.getUserID());
                    writer.println("Group Abbreviation: " + record.getGroupAbbr());
                    writer.println("Solvent Abbreviation: " + record.getSolventAbbr());
                    writer.println("Spectrometer ID: " + record.getSpectrometerID());
                    writer.println("Spectrometer Name: " + record.getSpectrometerName());
                    writer.println("Spectrometer Capacity: " + record.getSpectrometerCapacity());
                    writer.println();
                    writer.println("---------------------------------------");
                    writer.println();
                }
            } catch(Exception e){
                System.out.println("Input file not formatted properly!");
            }
            writer.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
