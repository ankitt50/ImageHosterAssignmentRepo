package ImageHoster.repository;

import ImageHoster.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class TagRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the Tag class object
    // which needs to be added in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    // save the instance in DB by using persist() method.
    public Tag createTag(Tag tag) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(tag);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return tag;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the tag from the database with corresponding name.
    //Returns the tag fetched from the database
    public Tag findTag(String tagName) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Tag> typedQuery = em.createQuery("SELECT t from Tag t where t.name =:tagName", Tag.class).setParameter("tagName", tagName);
            Tag tag = typedQuery.getSingleResult();
            transaction.commit();
            return tag;
        } catch (NoResultException nre) {
            transaction.rollback();
            return null;
        }
    }
}
