package com.metacoding.springrocketdanv2.certification;

import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(CertificationRepository.class)
@DataJpaTest
public class CertificationRepositoryTest {
    @Autowired
    private CertificationRepository certificationRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Test
    public void findCertificationsByResumeId_test() {
//        // given
//        Integer resumeId = 1;
//
//        // when
//        List<Certification> certList = certificationRepository.findCertificationsByResumeId(resumeId);
//
//        // then
//        if (certList.isEmpty()) {
//            System.out.println("자격증 없음");
//        } else {
//            for (Certification c : certList) {
//                System.out.println("자격증 이름: " + c.getName() + ", 발급기관: " + c.getIssuer());
//            }
//        }
    }

    @Test
    public void deleteByResumeId_test() {
//        // given
//        Integer resumeId = 1;
//
//        // when
//        certificationRepository.deleteByResumeId(resumeId);
//
//        // then
//        List<Certification> certList = certificationRepository.findCertificationsByResumeId(resumeId);
//        if (certList.isEmpty()) {
//            System.out.println("자격증 삭제 완료");
//        } else {
//            System.out.println("삭제 실패, 남은 자격증 수: " + certList.size());
//        }
    }

    @Test
    public void save_test() {
//        // given
//        Resume resume = resumeRepository.findById(1);
//        Certification cert = new Certification(null, "정보처리기사", "한국산업인력공단", "2022-06", null, resume);
//
//        // when
//        certificationRepository.save(cert);
//
//        // then
//        System.out.println("자격증 저장 완료! name = " + cert.getName());
    }

    @Test
    public void findByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        Career career = careerRepository.findByResumeId(resumeId);

        // then
        if (career == null) {
            System.out.println("조회 실패: 경력 없음");
        } else {
            System.out.println("조회 성공: 회사명 = " + career.getCompanyName() + ", 시작일 = " + career.getStartDate());
        }
    }
}
