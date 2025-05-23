package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public Optional<Resume> findByResumeId(Integer resumeId) {
        return Optional.ofNullable(em.find(Resume.class, resumeId));
    }

    public Optional<Resume> findByResumeIdJoinFetchAll(Integer resumeId) {
        String sql = """
                SELECT res FROM Resume res
                    JOIN FETCH res.salaryRange sr
                    JOIN FETCH res.jobGroup jg
                    JOIN FETCH res.user u
                    JOIN FETCH res.resumeTechStacks rts
                    JOIN FETCH rts.techStack ts
                WHERE res.id = :resumeId
                """;
        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("resumeId", resumeId);

        try {
            return Optional.of((Resume) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    // 오버라이드 이력서 관리 페이지에서 사용
    public List<Resume> findAllByUserId(Integer userId, boolean isDefault) {
        String sql;

        if (isDefault) {
            sql = "SELECT res FROM Resume res WHERE res.user.id = :userId and res.isDefault = true";

        } else {
            sql = "SELECT res FROM Resume res WHERE res.user.id = :userId ORDER BY res.id DESC";
        }

        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public Optional<Resume> findByUserIdAndIsDefaultTrue(Integer userId) {
        String sql = "SELECT res FROM Resume res WHERE res.user.id = :userId AND res.isDefault = true";
        Query query = em.createQuery(sql, Resume.class);
        query.setParameter("userId", userId);

        try {
            return Optional.ofNullable((Resume) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public void deleteByResumeId(Integer resumeId) {
        em.createQuery("DELETE FROM Resume r WHERE r.id = :resumeId")
                .setParameter("resumeId", resumeId)
                .executeUpdate();
    }

    public Resume save(Resume resume) {
        em.persist(resume);
        return resume;
    }

    public void updateByResumeId(Integer resumeId, ResumeRequest.UpdateDTO reqDTO) {
        SalaryRange salaryRangeRef = em.getReference(SalaryRange.class, reqDTO.getSalaryRangeId());
        JobGroup jobGroupRef = em.getReference(JobGroup.class, reqDTO.getJobGroupId());

        em.createQuery("""
                        UPDATE Resume r SET
                            r.title = :title,
                            r.summary = :summary,
                            r.portfolioUrl = :portfolioUrl,
                            r.gender = :gender,
                            r.education = :education,
                            r.birthdate = :birthdate,
                            r.major = :major,
                            r.graduationType = :graduationType,
                            r.phone = :phone,
                            r.enrollmentDate = :enrollmentDate,
                            r.graduationDate = :graduationDate,
                            r.careerLevel = :careerLevel,
                            r.isDefault = :isDefault,
                            r.salaryRange = :salaryRange,
                            r.jobGroup = :jobGroup
                        WHERE r.id = :resumeId
                        """)
                .setParameter("title", reqDTO.getTitle())
                .setParameter("summary", reqDTO.getSummary())
                .setParameter("portfolioUrl", reqDTO.getPortfolioUrl())
                .setParameter("gender", reqDTO.getGender())
                .setParameter("education", reqDTO.getEducation())
                .setParameter("birthdate", reqDTO.getBirthdate())
                .setParameter("major", reqDTO.getMajor())
                .setParameter("graduationType", reqDTO.getGraduationType())
                .setParameter("phone", reqDTO.getPhone())
                .setParameter("enrollmentDate", reqDTO.getEnrollmentDate())
                .setParameter("graduationDate", reqDTO.getGraduationDate())
                .setParameter("careerLevel", reqDTO.getCareerLevel())
                .setParameter("isDefault", reqDTO.getIsDefault())
                .setParameter("salaryRange", salaryRangeRef)
                .setParameter("jobGroup", jobGroupRef)
                .setParameter("resumeId", resumeId)
                .executeUpdate();
        em.flush();
        em.clear();
    }
}
