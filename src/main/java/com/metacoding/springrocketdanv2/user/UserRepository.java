package com.metacoding.springrocketdanv2.user;

import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findByUsername(String username) {
        try {
            return em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findById(Integer id) {
        Query query = em.createQuery(
                "select u  from User u " +
                        "where u.id = :id", User.class);
        query.setParameter("id", id);
        return (User) query.getSingleResult();
    }

    public List<JobBookmark> findJobBookmarksByUserId(Integer userId) {
        String q = "SELECT jb FROM JobBookmark jb " +
                "WHERE jb.user.id = :userId";
        return em.createQuery(q, JobBookmark.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}