package sm.server;

import sm.model.Operator;
import sm.persistance.repository.OperatorsDbRepository;
import sm.services.ISwimMasterObserver;
import sm.services.ISwimMasterServices;
import sm.services.SwimMasterException;

import java.util.List;
import java.util.Map;

public class ServicesImpl implements ISwimMasterServices {
    OperatorsDbRepository opRepository;
    private List<String> loggedClients;

    public ServicesImpl(OperatorsDbRepository operatorsRepository){
        this.opRepository = operatorsRepository;
    }

    @Override
    public synchronized void login(Operator operator) throws SwimMasterException {
        Operator op = opRepository.getOneByUsernameAndPassword(operator.getUsername(), operator.getPassword());
        if (op!=null){
            if(loggedClients.contains(op.getUsername()))
                throw new SwimMasterException("User already logged in.");
            loggedClients.add(op.getUsername());
        }else
            throw new SwimMasterException("Authentication failed.");
    }
}
