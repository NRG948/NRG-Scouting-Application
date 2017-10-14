package example.rankerandscanner;

public class Entry {
    enum Position{RED1,RED2,RED3,BLUE1,BLUE2,BLUE3};
    Position position;
    String teamName;
    int matchNumber;
    int gearsRetrieved;
    int ballsShot;
    int autoGearsRetrieved;
    int autoBallsShot;
    double rating;
    boolean death;//True if it died
    boolean crossedBaseline;
    boolean climbsRope;
}