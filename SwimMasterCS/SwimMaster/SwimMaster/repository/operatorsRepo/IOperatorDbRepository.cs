using SwimMaster.domain;

namespace SwimMaster.repository.operatorsRepo
{
    internal interface IOperatorDbRepository : IRepository<int, Operator>
    {
        /**
         * Finds an entity with given ID
         * @param id - the id of the element to be found
         *           - must not be null
         * @return  - the entity with the given id if it exists
         *          - null if the entity does not exist
         */
        Operator getOneByUsername(string username);
    }
}
