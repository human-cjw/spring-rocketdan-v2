package com.metacoding.springrocketdanv2.resume.techstack;

import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(ResumeTechStackRepository.class)
@DataJpaTest
public class ResumeTechStackRepositoryTest {
    @Autowired
    private ResumeTechStackRepository resumeTechStackRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void deleteByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        resumeTechStackRepository.deleteByResumeId(resumeId);
        em.flush();
        em.clear();

        // eye
        List<ResumeTechStack> result = em.createQuery("""
                        SELECT rts FROM ResumeTechStack rts 
                        WHERE rts.resume.id = :resumeId
                    """, ResumeTechStack.class)
                .setParameter("resumeId", resumeId)
                .getResultList();

        if (result.isEmpty()) {
            System.out.println("삭제 성공 : ResumeTechStack이 없습니다.");
        } else {
            System.out.println("삭제 실패 : 아직 ResumeTechStack이 존재합니다.");
        }
    }

    @Test
    public void save_test() {
        // given
        Resume resume = em.find(Resume.class, 1);
        TechStack techStack = em.find(TechStack.class, 1);

        ResumeTechStack resumeTechStack = ResumeTechStack.builder()
                .resume(resume)
                .techStack(techStack)
                .build();

        // when
        ResumeTechStack saved = resumeTechStackRepository.save(resumeTechStack);
        em.flush();
        em.clear();

        // eye
        ResumeTechStack result = em.find(ResumeTechStack.class, saved.getId());
        System.out.println("id: " + result.getId());
        System.out.println("resumeId: " + result.getResume().getId());
        System.out.println("techStackId: " + result.getTechStack().getId());
    }
}
