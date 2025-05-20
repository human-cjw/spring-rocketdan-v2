package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.workfield.WorkField;
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
        SalaryRange salaryRangeRef = em.getReference(SalaryRange.class, reqDTO.getSalaryRangeId());
        WorkField workFieldRef = em.getReference(WorkField.class, reqDTO.getWorkFieldId());
        JobGroup jobGroupRef = em.getReference(JobGroup.class, reqDTO.getJobGroupId());

        em.createQuery("""
                        UPDATE Job j SET
                            j.title = :title,
                            j.description = :description,
                            j.location = :location,
                            j.employmentType = :employmentType,
                            j.deadline = :deadline,
                            j.status = :status,
                            j.careerLevel = :careerLevel,
                            j.salaryRange = :salaryRange,
                            j.workField = :workField,
                            j.jobGroup = :jobGroup
                        WHERE j.id = :jobId
                        """)
                .setParameter("title", reqDTO.getTitle())
                .setParameter("description", reqDTO.getDescription())
                .setParameter("location", reqDTO.getLocation())
                .setParameter("employmentType", reqDTO.getEmploymentType())
                .setParameter("deadline", reqDTO.getDeadline())
                .setParameter("status", reqDTO.getStatus())
                .setParameter("careerLevel", reqDTO.getCareerLevel())
                .setParameter("salaryRange", salaryRangeRef)
                .setParameter("workField", workFieldRef)
                .setParameter("jobGroup", jobGroupRef)
                .setParameter("jobId", jobId)
                .executeUpdate();
        em.flush();
        em.clear();
    }

    public List<Job> findAllByCompanyId(Integer companyId) {
        String q = "SELECT j FROM Job j WHERE j.company.id = :companyId ORDER BY j.id DESC";
        return em.createQuery(q, Job.class)
                .setParameter("companyId", companyId)
                .getResultList();
    }

}
