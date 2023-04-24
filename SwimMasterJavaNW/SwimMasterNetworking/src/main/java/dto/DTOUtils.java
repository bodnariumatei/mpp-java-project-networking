package dto;

import sm.model.Operator;

public class DTOUtils {
    public static Operator getFromDTO(OperatorDTO opdto){
        String username = opdto.getUsername();
        String password = opdto.getPassword();
        return new Operator(username, password);
    }

    public static OperatorDTO getDTO(Operator op){
        String username = op.getUsername();
        String password = op.getPassword();
        return new OperatorDTO(username, password);
    }
}
