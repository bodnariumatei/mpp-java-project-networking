package sm.persistance.orm;

import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistry;
import sm.model.Operator;
import sm.persistance.IOperatorsRepository;

import java.util.List;

public class OperatorsORMRepository implements IOperatorsRepository {
    private HibernateUtils hibernateUtils;

    public OperatorsORMRepository(StandardServiceRegistry registry){
        hibernateUtils = new HibernateUtils(registry);
    }

    public Operator getOneByUsernameAndPassword(String username, String password) {
        // create a couple of events...
        Session session = hibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        List<Operator> result = session.createQuery( "select op from Operator op" , Operator.class).list();
        Operator requested = null;
        for (Operator operator : result) {
            if(operator.getUsername().equals(username) && operator.getPassword().equals(password)){
                requested = operator;
                break;
            }
        }
        session.getTransaction().commit();
        session.close();
        return requested;
    }

    @Override
    public Operator getOne(Integer id) {
        return null;
    }

    @Override
    public Iterable<Operator> getAll() {
        return null;
    }

    @Override
    public Operator store(Operator entity) {
        return null;
    }

    @Override
    public Operator delete(Integer id) {
        return null;
    }

    @Override
    public Operator update(Operator entity) {
        return null;
    }
}
