package com.mpp.javaproject.swimmasterjfx.repository.competition;

import com.mpp.javaproject.swimmasterjfx.domain.Competition;
import com.mpp.javaproject.swimmasterjfx.domain.CompetitionStyle;
import com.mpp.javaproject.swimmasterjfx.domain.Operator;
import com.mpp.javaproject.swimmasterjfx.utils.JdbcUtils;
import com.mpp.javaproject.swimmasterjfx.utils.tableview_items.CompetionTableItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CompetitionDbRepository implements ICompetitionRepository{

    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

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
        return null;
    }

    @Override
    public Iterable<CompetionTableItem> getAllWithNrOfParticipants() {
        logger.traceEntry("extracting competitions with nr of participants");
        Connection con = dbUtils.getConnection();
        List<CompetionTableItem> competitions = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement(
                "select id, style, distance, COUNT(participant_id) as no_part from competitions c INNER JOIN registrations r ON c.id=r.competition_id GROUP BY id,style, distance")){
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String style = result.getString("style");
                    int distance = result.getInt("distance");
                    int noParticipants = result.getInt("no_part");

                    CompetionTableItem competition = new CompetionTableItem(distance, CompetitionStyle.valueOf(style), noParticipants);
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
}
