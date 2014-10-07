/*
 * This is the program to get recent tweets and output to a file.
 * Created by Mayur Kale on 10/07/2014
 * API : Twitter4j
 */

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.io.*;
import java.util.List;

public class LiveTwitter {
	
	// Main entry of this application.
    public static void main(String[] args) throws IOException {
        
    	// gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            List<Status> statuses;
            String user;
            String strTweet = null;
            
            if (args.length == 1) {
                user = args[0];
                statuses = twitter.getUserTimeline(user);
            } else {
                user = twitter.verifyCredentials().getScreenName();
                statuses = twitter.getHomeTimeline();
            }
                        
            // Using OutputStreamWriter you don't have to convert the String to byte[]
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("recentTweets.txt"), "utf-8"));

            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
            	strTweet = "@" + status.getUser().getScreenName() + " - " + 
            			   status.getText() + " - " + status.getCreatedAt() + "\n";
            	
                System.out.println(strTweet);
                writer.write(strTweet);
            }
            writer.close();
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}