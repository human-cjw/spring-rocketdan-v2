package com.metacoding.springrocketdanv2.certification;

import com.metacoding.springrocketdanv2.resume.Resume;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

@DataJpaTest
@Import(CertificationRepository.class)
public class CertificationRepositoryTest {
    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findAllByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        List<Certification> certifications = certificationRepository.findAllByResumeId(resumeId);

        // eye
        for (Certification certification : certifications) {
            System.out.println("Certification: " + certification.getName() + ", Issuer" + certification.getIssuer() + ", IssueDate: " + certification.getIssuedDate() + ", CreatedAt: " + certification.getCreatedAt());
        }
    }

    @Test
    public void deleteByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        certificationRepository.deleteByResumeId(resumeId);

        // eye
        List<Certification> certificationsPS = em.createQuery("SELECT c FROM Certification c WHERE c.resume.id = :resumeId", Certification.class)
                .setParameter("resumeId", resumeId)
                .getResultList();

        if (certificationsPS.isEmpty()) {
            System.out.println("삭제 성공!!!!!!!!!!!");
        } else {
            System.out.println("삭제 실패ㅜㅜㅜㅜㅜㅜㅜ");
        }
    }

    @Test
    public void save_test() {
        // given
        Certification certification = Certification.builder()
                .name("새로운 자격증")
                .issuer("자격증 발급 기관!!")
                .issuedDate("발급일자!!")
                .resume(Resume.builder().id(1).build())
                .build();
        // when
        Certification certificationPS = certificationRepository.save(certification);
        // eye
        System.out.println("자격증 이름: " + certificationPS.getName());
        System.out.println("자격증 발급기관: " + certificationPS.getIssuer());
        System.out.println("자격증 발급 일자: " + certificationPS.getIssuedDate());
        System.out.println("이력서 아이디 : " + certificationPS.getResume().getId());
    }
}
