package sm.persistance;

import sm.model.Operator;

public interface IOperatorsRepository extends IRepository<Integer, Operator> {
    /**
     * Finds an operator with given username
     * @param username - the username of the operator to be found
     * @param password - the password of the operator to be found
     * @return  - the entity with the given username and password if it exists
     *          - null if the operator does not exist
     */
    Operator getOneByUsernameAndPassword(String username, String password);
}
