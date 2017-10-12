import java.io.IOException;

public interface SiteStatistics {

    int getNumberAnswers() throws IOException;

    int getNumberQuestions() throws IOException;

    int getNumberUpvotes() throws IOException;  //???
}
