package sm.services;

import sm.model.Operator;

public interface ISwimMasterServices {
    // all actions made by the service in the previous version of the app
    void login(Operator operator) throws SwimMasterException;
}
