package com.mpp.javaproject.swimmasterjfx.repository.participant;

import com.mpp.javaproject.swimmasterjfx.domain.Participant;
import com.mpp.javaproject.swimmasterjfx.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantDbRepository implements IParticipantRepository{
    private JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public ParticipantDbRepository(Properties props) {
        logger.info("Initializing ParticipantRepository with properties: {}", props);
        this.dbUtils = new JdbcUtils(props);
    }
    @Override
    public Participant getOne(Integer id) {
        logger.traceEntry("extracting participant with id {}", id);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from participants where id=?")){
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int pid = result.getInt("id");
                String name = result.getString("name");
                LocalDateTime dateOfBirth = LocalDateTime.parse(result.getString("date_of_birth"));

                Participant participant = new Participant(name, dateOfBirth);
                participant.setId(pid);
                logger.traceExit();
                return participant;
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        return null;
    }

    @Override
    public Iterable<Participant> getAll() {
        logger.traceEntry("extracting participants");
        Connection con = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from participants")){
            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    LocalDateTime dateOfBirth = LocalDateTime.parse(result.getString("date_of_birth"));

                    Participant participant = new Participant(name, dateOfBirth);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return participants;
    }

    @Override
    public Participant store(Participant entity) {
        logger.traceEntry("saving participant {} ", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("insert into participants (name, date_of_birth) values (?,?)")){
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDateOfBirth().toString());

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
    public Participant delete(Integer id) {
        logger.traceEntry("deleting participant {} ", id);
        Connection con = dbUtils.getConnection();

        try(PreparedStatement preparedStatement = con.prepareStatement("delete from participants where id=?")){
            Participant participant = getOne(id);
            preparedStatement.setInt(1, id);

            int result=preparedStatement.executeUpdate();
            logger.trace("Participant {} deleted", result);
            return participant;
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return null;
    }

    @Override
    public Participant update(Participant entity) {
        return null;
    }

    @Override
    public Participant getOneByName(String name) {
        logger.traceEntry("extracting participant with name {}", name);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = con.prepareStatement("select * from participants where name=?")){
            preparedStatement.setString(1,name);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                String pName = result.getString("name");
                LocalDateTime dateOfBirth = LocalDateTime.parse(result.getString("date_of_birth"));

                Participant participant = new Participant(pName, dateOfBirth);
                participant.setId(id);
                logger.traceExit();
                return participant;
            }
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB" + ex);
        }
        return null;
    }

    @Override
    public Iterable<Participant> getAllFromCompetition(int competition_id) {
        logger.traceEntry("extracting participants from competition with id {}", competition_id);
        Connection con = dbUtils.getConnection();
        List<Participant> participants = new ArrayList<>();
        try(PreparedStatement preparedStatement = con.prepareStatement(
                "select id,name,date_of_birth from participants p inner join registrations r on p.id=r.participant_id where r.competition_id=?")){
            preparedStatement.setInt(1, competition_id);

            try(ResultSet result = preparedStatement.executeQuery()) {
                while (result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    LocalDateTime dateOfBirth = LocalDateTime.parse(result.getString("date_of_birth"));

                    Participant participant = new Participant(name, dateOfBirth);
                    participant.setId(id);
                    participants.add(participant);
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.err.println("Error DB "+ex);
        }
        logger.traceExit();
        return participants;
    }
}
