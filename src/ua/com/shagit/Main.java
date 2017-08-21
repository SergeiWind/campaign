package ua.com.shagit;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
	if (args.length<1) {
        System.out.println("No filename in arguments");
        return;
    }
    CampaignList campaignList = new CampaignList();
    campaignList.readCampaignsList(args[0]);

    while (true) {
        Scanner scan = new Scanner(System.in);
        String listOfSegments = scan.nextLine();
        System.out.println(campaignList.findCampaign(listOfSegments));
    }
    }
}
