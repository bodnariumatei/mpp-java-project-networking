package sm.persistance.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sm.model.Competition;
import sm.model.CompetitionStyle;
import sm.model.utils.CompetitionItem;
import sm.persistance.ICompetitionRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public class CompetitionDbRepository implements ICompetitionRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public CompetitionDbRepository(){
        Properties serverProps=new Properties();
        try {
            serverProps.load(CompetitionDbRepository.class.getResourceAsStream("/smserver.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
            this.dbUtils = new JdbcUtils(serverProps);
        } catch (IOException e) {
            System.err.println("Cannot find smserver.properties "+e);
            return;
        }
    }

    public CompetitionDbRepository(Properties props) {
        logger.info("Initializing CompetitionDbRepository with properties: {}", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Competition getOne(Integer id) {
        logger.traceEntry("extracting competition with id {}", id);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from competitions where id=?")){
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                int cid = result.getInt("id");
                int distance = result.getInt("distance");
                String style = result.getString("style");

                Competition competition = new Competition(distance, CompetitionStyle.valueOf(style));
                competition.setId(cid);
                logger.traceExit();
                return competition;
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        return null;
    }

    @Override
    public Iterable<Competition> getAll() {
        logger.traceEntry("extracting competitons");
        Connection con = dbUtils.getConnection();
        List<Competition> competitions = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from competitions")){
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    int distance = result.getInt("distance");
                    String style = result.getString("style");

                    Competition competition = new Competition(distance, CompetitionStyle.valueOf(style));
                    competition.setId(id);
                    competitions.add(competition);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return competitions;
    }

    @Override
    public Competition store(Competition entity) {
        logger.traceEntry("saving competition {} ", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into competitions (distance, style) values (?,?)")){
            preparedStatement.setInt(1, entity.getDistance());
            preparedStatement.setString(2, entity.getStyle().toString());

            int result=preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
            return entity;
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Competition delete(Integer id) {
        logger.traceEntry("deleting competition {} ", id);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preparedStatement = con.prepareStatement("delete from competitions where id=?")){
            Competition competition = getOne(id);
            preparedStatement.setInt(1, id);

            int result=preparedStatement.executeUpdate();
            logger.trace("Competition {} deleted", result);
            return competition;
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Competition update(Competition entity) {
        logger.traceEntry("updating competition {} ", entity);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preparedStatement = con.prepareStatement("update competitions SET distance=?, style=? where id=?")){
            preparedStatement.setInt(1, entity.getDistance());
            preparedStatement.setString(2, entity.getStyle().toString());
            preparedStatement.setInt(3, entity.getId());

            int result=preparedStatement.executeUpdate();
            logger.trace("Competition {} deleted", result);
            return entity;
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Iterable<CompetitionItem> getAllWithNrOfParticipants() {
        logger.traceEntry("extracting competitions with nr of participants");
        Connection con = dbUtils.getConnection();
        List<CompetitionItem> competitions = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement(
                "select id, style, distance, COUNT(participant_id) as no_part from competitions c INNER JOIN registrations r ON c.id=r.competition_id GROUP BY id,style, distance")){
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String style = result.getString("style");
                    int distance = result.getInt("distance");
                    int noParticipants = result.getInt("no_part");

                    CompetitionItem competition = new CompetitionItem(distance, CompetitionStyle.valueOf(style), noParticipants);
                    competition.setId(id);
                    competitions.add(competition);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return competitions;
    }

    @Override
    public Iterable<Competition> getCompetitionsForParticipant(int participant_id) {
        logger.traceEntry("extracting competitions for participant with id {}", participant_id);
        Connection con = dbUtils.getConnection();
        List<Competition> competitions = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement(
                "select id, style, distance from competitions c INNER JOIN registrations r ON c.id=r.competition_id where r.participant_id = ?")){
            preparedStatement.setInt(1, participant_id);
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String style = result.getString("style");
                    int distance = result.getInt("distance");

                    Competition competition = new Competition(distance, CompetitionStyle.valueOf(style));
                    competition.setId(id);
                    competitions.add(competition);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return competitions;
    }

    @Override
    public void registerAtCompetition(int participant_id, int competition_id) {
        logger.traceEntry(
                "entering registration of participant with id {} at competition with id {} ", participant_id, competition_id);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into registrations values (?,?)")){
            preparedStatement.setInt(1, participant_id);
            preparedStatement.setInt(2, competition_id);

            int result=preparedStatement.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    public Competition getOneByStyleAndDistance(CompetitionStyle style, int distance) {
        logger.traceEntry("extracting competition with style {} and distance {}m", style, distance);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from competitions where style=? and distance=?")){
            preparedStatement.setString(1,style.toString());
            preparedStatement.setInt(2, distance);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) {
                int cid = result.getInt("id");
                int cDistance = result.getInt("distance");
                String cStyle = result.getString("style");

                Competition competition = new Competition(cDistance, CompetitionStyle.valueOf(cStyle));
                competition.setId(cid);
                logger.traceExit();
                return competition;
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        return null;
    }
}
