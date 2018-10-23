import java.io.*;
        import java.util.*;

public class BigDataHelper {
    public static int numberOfRowsInFile(String filename, boolean subtractHeaderLine) throws IOException {
        return numberOfRowsInFile(filename) - (subtractHeaderLine ? 1 : 0);
    }

    public static int numberOfRowsInFile(String filename) throws IOException {
        // read from file
        Scanner fileScan = new Scanner(new FileReader(new File(filename)));

        int lineCount = 0;

        while (fileScan.hasNext()) {
            String line = fileScan.nextLine();
            lineCount++;
        }
        fileScan.close();

        return lineCount;
    }

    public static int readRelevantIncidentsFromNewFileUsingList(String filename, ArrayList<RelevantIncident> incidentList, int firstRow,
                                                       int lastRow, boolean verbose) throws IOException {

        int rowCount = 0;
        long startMillis = System.currentTimeMillis();
        long elapsed=0;
        int relevantTotal=0;

        if (firstRow >= 0 && lastRow>firstRow) {

            // open the file
            Scanner fileScan = new Scanner(new FileReader(new File(filename)));

            if (verbose) System.out.println("Reading from: " + filename);

            if (verbose) System.out.println("from row " + firstRow + " to row " + lastRow);

            if (verbose) System.out.println("verbose output enabled.");

            if (verbose) System.out.println("Skipping ahead to firstRow...");

            // skip to firstRow
            while (rowCount < firstRow) {
                fileScan.nextLine();
                rowCount++;
            }

            // read the data up to the last row
            while (fileScan.hasNext() && rowCount < lastRow) {
                String oneLine = fileScan.nextLine();

                // clean out commas within quotes
                boolean withinQuotes=false;
                for (int i=0;i<oneLine.length();i++) {

                    if (oneLine.charAt(i)=='"') {
                        withinQuotes=!withinQuotes;
                    } else if (withinQuotes&&oneLine.charAt(i)==',') {
                        oneLine=oneLine.substring(0,i)+'^'+oneLine.substring(i+1);
                    }
                }

                Scanner lineScan = new Scanner(oneLine);



                lineScan.useDelimiter(",");

                String Category =processNext(lineScan);
                String newDate = lineScan.next();
                int Date = Integer.parseInt(newDate);
                String PdDistrict = lineScan.next();

                incidentList.add(new RelevantIncident(Category, Date, PdDistrict));
                relevantTotal++;
                rowCount++;
                elapsed = System.currentTimeMillis() - startMillis;
                if (verbose) System.out.println("current row: " + Integer.toString(rowCount) +
                        "\ttime elapsed: " + Long.toString(elapsed) + "ms");
            }
            // close the file
            fileScan.close();
            if (verbose) System.out.println("file closed");
            elapsed = System.currentTimeMillis() - startMillis;
            System.out.println("Time elapsed: " + elapsed + "ms");
        } else {
            System.out.println("ERROR! Rows specified are outside of range");
        }

        return relevantTotal;
    }

    public static void printHeaders(String filename) throws IOException {
        Scanner fileScan = new Scanner(new FileReader(new File(filename)));

        // just read the first line
        String oneLine = fileScan.nextLine();

        Scanner lineScan = new Scanner(oneLine);
        lineScan.useDelimiter(",");
        while (lineScan.hasNext()) {

            System.out.println("String " + lineScan.next() + " = lineScan.next();");

        }
    }

    private static String processNext(Scanner scan) {
        String token =scan.next();
        token=token.replace("^",",");
        token=token.replace("\"","");
        return token;
    }

    public static <T> void printIncidents(T[] A, int first, int last) {

        for (int i = first; i < last; i++) {
            System.out.println(A[i].toString());
        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
