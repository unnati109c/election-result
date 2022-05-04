import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class election {
    public static void main(String[] args) throws Exception {

        File file = new File("src/input.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNext()) {
            String[] fullLine = sc.nextLine().split("-");
            String city = fullLine[0];
            String namesVotes = fullLine[1];

            String[] equalsCommaPresent = namesVotes.split(",");

            HashMap<String, Integer> votes = new HashMap<>();
            for (String s : equalsCommaPresent) {
                votes.put(s.substring(0, 3), Integer.parseInt(s.substring(4)));
            }

            int maxValue = Collections.max(votes.values());
            String fullName = "";
            for (Map.Entry<String, Integer> entry : votes.entrySet()) {

                if (entry.getValue() == maxValue) {
                    fullName = entry.getKey();
                    break;
                }
            }

            int total = 0;
            for (int val : votes.values()) {
                total += val;
            }
            double voteShare = ((maxValue * 1.0) / total) * 100;

            System.out.println(city + "," + getFullForm(fullName) + "," + String.format("%.1f", voteShare));
        }
    }

    private static String getFullForm(String fullName) {
        HashMap<String, String> names = new HashMap<>();
        names.put("BJP", "Bhartiya Janta Party");
        names.put("INC", "Indian National Congress");
        names.put("BSP", "Bahujan Samaj Party");
        names.put("CPI", "Communist Party of India");
        names.put("NCP", "Nationalist Congress Party");
        names.put("IND", "Independent");

        return names.get(fullName);
    }
}