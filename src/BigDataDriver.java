import java.io.IOException;
import java.util.*;

// counts the rows in a csv file and prints them to the console
public class BigDataDriver {
    public static void main(String args[]) throws IOException {

        String filename="violent-crimes-sf.csv";

        // new file:
        final int TOTAL_ROWS = 267649;
        final int FIRST_DATA_ROW=0; // this is a 1 if we have row headers but in this case, we do not.
        final int TOTAL_DATA_ROWS = TOTAL_ROWS-FIRST_DATA_ROW;
        final int LAST_DATA_ROW=TOTAL_DATA_ROWS+FIRST_DATA_ROW;

        ArrayList<RelevantIncident> incidents = new ArrayList<>();

        int first=FIRST_DATA_ROW;
        int last=LAST_DATA_ROW;

        System.out.println("Processing violent crime data for San Francisco 2003-2108 by District and Year:");

        System.out.println("For this study, I am including only reported Assaults, Robberies, Sexual Assaults, and Kidnappings");

        System.out.println("Also for several reasons, the dataset for 2018 is incomplete.\n");

        System.out.println("I am hoping to answer two questions: \n"+
                " * Which district has had the most violent crimes in the last 16 years?\n" +
                " * Which year (excluding 2018) has been the most violent?\n");


        System.out.println("Reading from database:");
        int relevantCount = BigDataHelper.readRelevantIncidentsFromNewFileUsingList(filename, incidents, first, last, false);

        System.out.println(relevantCount+ " Total Violent Crimes since 2003\n");



        System.out.println("Mapping Violent Crimes by District:\n");

        Map<String, Integer> totalViolentCrimesByDistrict = new HashMap<>();

        for (int i=0;i<relevantCount;i++) {
            String district=incidents.get(i).getPdDistrict();
            Integer freq = totalViolentCrimesByDistrict.get(district);
            totalViolentCrimesByDistrict.put(district, (freq == null) ? 1 : freq + 1);
        }

        totalViolentCrimesByDistrict = BigDataHelper.sortByValue(totalViolentCrimesByDistrict);

        System.out.println(totalViolentCrimesByDistrict.size() + " districts");
        System.out.println(totalViolentCrimesByDistrict);

        System.out.println("From this data, we can see that the Southern District has had the Most Violent Crimes.\n\n");


        System.out.println("Mapping Violent Crimes by Year:\n");

        Map<String, Integer> totalViolentCrimesByYear = new TreeMap<>();

        for (int i=0;i<relevantCount;i++) {
            String date=Integer.toString(incidents.get(i).getDate());
            Integer freq = totalViolentCrimesByYear.get(date);
            totalViolentCrimesByYear.put(date, (freq == null) ? 1 : freq + 1);
        }

        System.out.println(totalViolentCrimesByYear.size() + " years (sorted by year)");
        System.out.println(totalViolentCrimesByYear);

        System.out.println("From this data, we can see that the 2017 had the Most Violent Crimes.\n\n");
    }
}
