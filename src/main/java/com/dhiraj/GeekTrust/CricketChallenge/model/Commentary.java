package com.dhiraj.GeekTrust.CricketChallenge.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Commentary {

    private static String commentaryFileName;
    private PrintWriter commentaryPrint;
    private FileWriter commentaryFile;
    private static CricketProperties cricketProperties;

    public void addCommentary(String comment){
        commentaryPrint.print(comment);
    }

    public Commentary(CricketProperties cricketProperties) throws IOException {
        this.cricketProperties = cricketProperties;
        commentaryFileName = cricketProperties.getProperty("commentaryFileName");
        commentaryFile = new FileWriter(commentaryFileName,false);
        commentaryPrint = new PrintWriter(commentaryFile);
    }
    
    public void cleanUpCommentary() throws IOException {
        commentaryPrint.close();
        commentaryFile.close();
    }

    public static void printCommentary() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(commentaryFileName));
        lines.forEach(System.out::println);
        System.out.println();
    }

    public static CricketProperties getCricketProperties() {
        return cricketProperties;
    }

}
