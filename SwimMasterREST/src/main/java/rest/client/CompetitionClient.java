package rest.client;

import sm.model.Competition;
import sm.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;


public class CompetitionClient {
    public static final String URL = "http://localhost:9091/sm/competitions";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Competition[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Competition[].class));
    }

    public Competition getById(int id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Competition.class));
    }

    public Competition create(Competition competition) {
        return execute(() -> restTemplate.postForObject(URL, competition, Competition.class));
    }

    public void update(Competition competition) {
        execute(() -> {
            restTemplate.put(String.format("%s", URL), competition);
            return null;
        });
    }

    public void delete(int id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}