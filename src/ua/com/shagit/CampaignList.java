package ua.com.shagit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Class to work with Campaigns
 */
public class CampaignList {
    private HashSet<Character> segments = new HashSet(); //Set to store segments for each campaign
    private HashMap<String, Integer> campaignPopularity = new HashMap<>(); //Map to spread the popularity
    private LinkedList<String> campaignWeight; //List to store the campaigns most sutable for each combination of segments
    private HashMap<String, Set> campaignHashMap = new HashMap<>(); //Map to store all the Campaigns
    void readCampaignsList (String filename) {//Reading campaigns from file
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseCampaign (line);
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    private void parseCampaign (String line) {//Parsing campaign line
        String [] parts = line.split(" ");
        segments = new HashSet();
        for (int i=1; i<parts.length; i=i+1) {
            segments.add((char)Integer.parseInt(parts[i]));
        }
        campaignHashMap.put(parts[0],segments); //fills the map of campaigns
        campaignPopularity.put(parts[0],0); //fills the map of popularity
    }

    private void parseSegments (String line) {//Parsing segments line
        String [] parts = line.split(" ");
        segments = new HashSet();
        for (int i=0; i<parts.length; i=i+1) {
            segments.add((char)Integer.parseInt(parts[i]));
        }
    }

    String findCampaign (String listOfSegments) {//Finding sutable campaign
        campaignWeight = new LinkedList();//To store most sutable campaigns
        Integer max = 0;
        Integer min;
        String campaignMinId;
        parseSegments(listOfSegments);
        for (Map.Entry<String, Set> entry: campaignHashMap.entrySet()) {//Iterate on Map
            HashSet<Character> tmp = new HashSet<Character>();
            tmp.addAll(entry.getValue());
            tmp.retainAll(segments);//get only common elements
            Integer size = tmp.size();
            String key = entry.getKey();
            if (size>max) {//new favourite found
                max = size;
                campaignWeight = new LinkedList();
                campaignWeight.add(key);

            } else if (size.equals(max)&size!=0) {//the same amount
                campaignWeight.add(key);
                }
            }
        if (campaignWeight.size()==0) {//no campaign
            return "no campaign";
        }
        else {
            min = campaignPopularity.get(campaignWeight.getFirst()); //Spreading the popularity
            campaignMinId = campaignWeight.getFirst();
            for (String campaign : campaignWeight) {
                if (campaignPopularity.get(campaign)<min) {
                   min = campaignPopularity.get(campaign);
                   campaignMinId = campaign;
                }
            }
            campaignPopularity.put(campaignMinId, (campaignPopularity.get(campaignMinId)+1));//Updating popularity
            return campaignMinId;
        }
    }
}
