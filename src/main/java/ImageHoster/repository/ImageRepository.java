package ImageHoster.repository;

import ImageHoster.model.Comment;
import ImageHoster.model.Image;
import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class ImageRepository {

    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;


    //The method receives the Image object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public Image uploadImage(Image newImage) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(newImage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return newImage;
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch all the images from the database
    //Returns the list of all the images fetched from the database
    public List<Image> getAllImages() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Image> query = em.createQuery("SELECT i from Image i", Image.class);
            List<Image> resultList = query.getResultList();
            transaction.commit();
            return resultList;
        }
        catch (Exception e) {
            transaction.rollback();
            return new ArrayList<Image>();
        }
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the image from the database with corresponding title
    //Returns the image in case the image is found in the database
    //Returns null if no image is found in the database
    public Image getImageByTitle(String title, Integer id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i " +
                    "where i.title =:title " +
                    "and i.id =:id", Image.class).setParameter("title", title).setParameter("id", id);
            Image image = typedQuery.getSingleResult();
            transaction.commit();
            return image;
        } catch (NoResultException nre) {
            transaction.rollback();
            return null;
        }
    }

    //The method creates an instance of EntityManager
    //Executes JPQL query to fetch the image from the database with corresponding id
    //Returns the image fetched from the database
    public Image getImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            TypedQuery<Image> typedQuery = em.createQuery("SELECT i from Image i " +
                    "where i.id =:imageId", Image.class).setParameter("imageId", imageId);
            Image image = typedQuery.getSingleResult();
            transaction.commit();
            return image;
        }
        catch (Exception e) {
            transaction.rollback();
            return null;
        }

    }

    //The method receives the Image object to be updated in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void updateImage(Image updatedImage) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(updatedImage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    //The method receives the Image id of the image to be deleted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //Get the image with corresponding image id from the database
    // Compares whether the Current user owns this image or not and,
    // then call deleteImage() method which deletes the image with the given Id,
    // only if user owns the image.
    public Boolean deleteImage(Integer imageId, Integer currentUserId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        Integer imageUserId = -1;
        try {
            transaction.begin();
            Image image = em.find(Image.class, imageId);
            imageUserId = image.getUser().getId();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
        }
        if (imageUserId.equals(currentUserId)) { // Compares whether
            // the Current user owns this image or not
            deleteImage(imageId);
            return true;
        }
        else {
            return false;
        }
    }

    // this method deletes the image with a given image Id.
    public void deleteImage(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Image image = em.find(Image.class, imageId);
            em.remove(image);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    //The method receives the comment object
    // which needs to be added in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    // save the instance in DB by using persist() method.
    public void saveComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

}
