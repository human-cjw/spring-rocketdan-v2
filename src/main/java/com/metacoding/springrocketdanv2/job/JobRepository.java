package com.metacoding.springrocketdanv2.job;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JobRepository {
    private final EntityManager em;

    public List<Job> findAll() {
        String sql = "SELECT * FROM job_tb ORDER BY id DESC";  // 네이티브 SQL 쿼리
        Query query = em.createNativeQuery(sql, Job.class);
        return query.getResultList();
    }

    public Optional<Job> findByJobId(Integer jobId) {
        return Optional.ofNullable(em.find(Job.class, jobId));
    }

    public Job save(Job job) {
        em.persist(job);
        return job;
    }

    public Optional<Job> findByJobIdJoinFetchAll(Integer jobId) {
        String sql = """
                SELECT j FROM Job j
                    JOIN FETCH j.jobTechStacks jts
                    JOIN FETCH jts.techStack ts
                    JOIN FETCH j.company c
                    JOIN FETCH j.salaryRange sr
                    JOIN FETCH j.workField w
                    JOIN FETCH j.jobGroup jg
                WHERE j.id = :jobId
                """;
        Query query = em.createQuery(sql, Job.class);
        query.setParameter("jobId", jobId);
        try {
            return Optional.ofNullable((Job) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public void deleteByJobId(Integer jobId) {
        em.createQuery("DELETE FROM Job j WHERE j.id = :jobId")
                .setParameter("jobId", jobId)
                .executeUpdate();
    }

    public void updateByJobId(Integer jobId, JobRequest.UpdateDTO reqDTO) {
        em.createQuery("""
                        UPDATE Job j SET
                            j.title = :title,
                            j.description = :description,
                            j.location = :location,
                            j.employmentType = :employmentType,
                            j.deadline = :deadline,
                            j.status = :status,
                            j.careerLevel = :careerLevel,
                            j.salaryRange = (SELECT s FROM SalaryRange s WHERE s.id = :salaryRangeId),
                            j.workField = (SELECT w FROM WorkField w WHERE w.id = :workFieldId),
                            j.jobGroup = (SELECT g FROM JobGroup g WHERE g.id = :jobGroupId)
                        WHERE j.id = :jobId
                        """)
                .setParameter("title", reqDTO.getTitle())
                .setParameter("description", reqDTO.getDescription())
                .setParameter("location", reqDTO.getLocation())
                .setParameter("employmentType", reqDTO.getEmploymentType())
                .setParameter("deadline", reqDTO.getDeadline())
                .setParameter("status", reqDTO.getStatus())
                .setParameter("careerLevel", reqDTO.getCareerLevel())
                .setParameter("salaryRangeId", reqDTO.getSalaryRangeId())
                .setParameter("workFieldId", reqDTO.getWorkFieldId())
                .setParameter("jobGroupId", reqDTO.getJobGroupId())
                .setParameter("jobId", jobId)
                .executeUpdate();
    }
}
