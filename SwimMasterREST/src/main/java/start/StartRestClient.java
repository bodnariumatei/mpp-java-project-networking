package start;

import org.springframework.web.client.RestClientException;
import rest.client.CompetitionClient;
import sm.model.Competition;
import sm.model.CompetitionStyle;
import sm.services.rest.ServiceException;

public class StartRestClient {
    private final static CompetitionClient competitionClient = new CompetitionClient();
    public static void main(String[] args) {
        //  RestTemplate restTemplate=new RestTemplate();
        try{
            int workId = 1;
            Competition competition = new Competition(120, CompetitionStyle.liber);

            System.out.println("Create new competition: " + competition);
            show(()-> System.out.println(competitionClient.create(competition)));

            System.out.println("\nGet all competitions: ");
            Competition[] res = competitionClient.getAll();
            for(Competition c:res){
                System.out.println(c.getId()+": "+c.getDistance()+": "+c.getStyle().toString());
                    if(c.getId() >= workId){
                        workId = c.getId();
                    }
                }
            System.out.println("\nGet competition with id " + workId);
            System.out.println(competitionClient.getById(workId));

            Competition newCompetition = new Competition(workId, 540,CompetitionStyle.fluture);
            System.out.println("\nUpdate competition with id " + workId + " to new competition: " + newCompetition);
            competitionClient.update(newCompetition);

            System.out.println("\nGet all competitions: ");
            Competition[] resFinal = competitionClient.getAll();
            for(Competition c:resFinal){
                System.out.println(c.getId()+": "+c.getDistance()+": "+c.getStyle().toString());
            }

            System.out.println("\nDelete competition with id " + workId);
            competitionClient.delete(workId);

//            System.out.println("\nGet all competitions: ");
//            Competition[] resFinal = competitionClient.getAll();
//            for(Competition c:resFinal){
//                System.out.println(c.getId()+": "+c.getDistance()+": "+c.getStyle().toString());
//            }
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }

    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            //  LOG.error("Service exception", e);
            System.out.println("Service exception"+ e);
        }
    }
}
