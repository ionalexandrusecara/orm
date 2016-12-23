import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * This class is for retrieving a single item from the Employee table, and creating
 * a new Employee object from this information
 *
 * Since the content of the database is not being changed, this does not require a transaction
 */
public class Retrieve {

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

        // This returns the information of an Employee whose primary key is 158. 
        // find always returns 0 or 1 objects - as you search on the primary key
        //Employee employee1 = entity_manager.find(Employee.class, 158);
        Query query = entity_manager.createQuery("SELECT r.sampleID FROM Record r WHERE spectrometerName='Alec'");
        List<String> sampleIDs = query.getResultList();
        try {
            PrintWriter writer = new PrintWriter("retrieve.txt");
            writer.println("----Sample IDs using spectrometer Alec----");
            // output the values
            for (String str : sampleIDs) {
                writer.println(str);
            }

            writer.println();

            query = entity_manager.createQuery("SELECT r.solventAbbr FROM Record r WHERE spectrometerName='Alec'");
            List<String> solvent = query.getResultList();
            writer.println("----Solvents using spectrometer Alec----");
            // output the values
            for (String str : solvent) {
                writer.println(str);
            }

            writer.println();

            query = entity_manager.createQuery("SELECT r.expName FROM Record r WHERE spectrometerName='Alec'");
            List<String> experiment = query.getResultList();
            writer.println("----Experiments using spectrometer Alec----");
            // output the values
            for (String str : experiment) {
                writer.println(str);
            }

            writer.println();

            query = entity_manager.createQuery("SELECT r.expName FROM Record r WHERE expName='proton.a.and'");
            List<String> samples = query.getResultList();
            writer.println("----Number of samples using experiment proton.a.and----");
            // output the values
            writer.println(samples.size());

            writer.println();

            query = entity_manager.createQuery("SELECT r.sampleDuration FROM Record r");
            List<String> durations = query.getResultList();
            long totalDuration = 0;
            long hours, minutes, seconds;
            writer.println("----Durations----");
            // output the values
            String shortestDuration = durations.get(0), longestDuration = durations.get(0);
            for (String str : durations) {
                if (shortestDuration.compareTo(str) > 0) {
                    shortestDuration = str;
                }
                if (longestDuration.compareTo(str) < 0) {
                    longestDuration = str;
                }
                hours = Integer.parseInt(str.substring(0, str.indexOf(":")));
                str = str.substring(str.indexOf(",") + 1);
                minutes = Integer.parseInt(str.substring(0, str.indexOf(":")));
                str = str.substring(str.indexOf(",") + 1);
                seconds = Integer.parseInt(str.substring(0, str.indexOf(":")));
                totalDuration = hours * 60 * 60 + minutes * 60 + seconds + totalDuration;
            }
            minutes = totalDuration / 60;
            seconds = totalDuration - 60 * minutes;
            hours = minutes / 60;
            minutes = totalDuration / 60 - 60 * hours;


            writer.println("Shortest Duration: " + shortestDuration);
            writer.println("Longest Duration: " + longestDuration);
            writer.println("Total Duration " + hours + " hours : " + minutes + " minutes : " + seconds + " seconds");

            writer.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
