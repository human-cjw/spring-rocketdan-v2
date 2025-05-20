package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@Import(ApplicationRepository.class)
@DataJpaTest
public class ApplicationRepositoryTest {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findAllByUserIdAndStatus_test() {
        // given
        Integer id = 1;
        String status = "접수";

        // when
        List<Application> applicationsPS = applicationRepository.findAllByUserIdAndStatus(id, status);

        // eye
        System.out.println("------------------");
        for (Application application : applicationsPS) {
            System.out.println("Id: " + application.getUser().getId() + ", ResumeId: " + application.getResume().getId() + ", Status: " + application.getStatus() + ", Job : " + application.getJob().getId() + ", CreatedAt : " + application.getCreatedAt());
        }
    }

    @Test
    public void save_test() {
        // given
        Application application = Application.builder()
                .status("test")
                .resume(Resume.builder().id(1).build())
                .job(Job.builder().id(1).build())
                .user(User.builder().id(1).build())
                .build();

        // when
        Application applicationPS = applicationRepository.save(application);

        // eye
        System.out.println("지원 상태 : " + applicationPS.getStatus());
        System.out.println("지원 이력서: " + applicationPS.getResume().getId());
        System.out.println("지원한 채용 공고: " + applicationPS.getJob().getId());
        System.out.println("지원한 사람 : " + applicationPS.getUser().getId());
        System.out.println("지원한 시간 : " + applicationPS.getCreatedAt());

    }

    @Test
    public void findAllByJobIdJoinFetchAll_test() {
        // given
        Integer jobId = 1;
        String status = "접수";

        // when
        List<Application> applicationsPS = applicationRepository.findAllByJobIdJoinFetchAll(jobId, status);

        // eye
        if (applicationsPS.isEmpty()) {
            System.out.println("채용 공고 ID : " + jobId + "에 해당하는 데이터가 없습니다!!!");
        } else {
            for (Application application : applicationsPS) {
                System.out.println("Application ID : " + application.getId());
                System.out.println("Resume ID : " + application.getResume().getId());
                System.out.println("User ID : " + application.getUser().getId());
                System.out.println("Job ID : " + application.getJob().getId());
                System.out.println("Status : " + application.getStatus());
            }
        }
    }

    @Test
    public void findByApplicationId_test() {
        // given
        Integer applicationId = 1;

        // when
        Optional<Application> applicationOP = applicationRepository.findByApplicationId(applicationId);

        // eye
        if (applicationOP.isPresent()) {
            Application application = applicationOP.get();
            System.out.println("Application ID : " + application.getId());
            System.out.println("Resume ID : " + application.getResume().getId());
            System.out.println("User ID : " + application.getUser().getId());
            System.out.println("Job ID : " + application.getJob().getId());
            System.out.println("Job Title : " + application.getJob().getTitle());
            System.out.println("Status : " + application.getStatus());
            System.out.println("CreatedAt : " + application.getCreatedAt());

        } else {
            System.out.println("해당 지원서를 찾을 수 없습니다.");
        }
    }

    @Test
    public void findByJobIdAndUserId_test() {
        // given
        Integer jobId = 1;
        Integer userId = 1;

        // when
        Optional<Application> applicationOP = applicationRepository.findByJobIdAndUserId(jobId, userId);

        // eye
        if (applicationOP.isPresent()) {
            Application application = applicationOP.get();
            System.out.println("Application ID : " + application.getId());
            System.out.println("지원자 이름 : " + application.getUser().getUsername());
            System.out.println("채용 공고 ID : " + application.getJob().getId());
            System.out.println("채용 공고 제목 : " + application.getJob().getTitle());
            System.out.println("지원 상태 : " + application.getStatus());
        }
    }

    @Test
    public void deleteByJobId_test() {
        // given
        Integer jobId = 1;

        // when
        applicationRepository.deleteByJobId(jobId);

        // eye
        List<Application> applicationsPS = em.createQuery("SELECT a FROM Application a WHERE a.job.id = : jobId", Application.class)
                .setParameter("jobId", jobId)
                .getResultList();

        if (applicationsPS.isEmpty()) {
            System.out.println("삭제 성공!!!!!!!!");
        } else {
            System.out.println("삭제 실패ㅜㅜㅜㅜㅜ");
        }
    }

    @Test
    public void findByApplicationIdJoinResumeAndUser_test() {
        // given
        Integer applicationId = 1;

        // when
        Optional<Application> applicationOP = applicationRepository.findByApplicationId(applicationId);

        // eye
        if (applicationOP.isPresent()) {
            Application application = applicationOP.get();
            System.out.println("Application ID : " + application.getId());
            System.out.println("Resume ID : " + application.getResume().getId());
            System.out.println("User ID : " + application.getUser().getId());
            System.out.println("Job ID : " + application.getJob().getId());
        } else {
            System.out.println("해당 지원서를 찾을 수 없습니다!!");
        }
    }

    @Test
    public void findByApplicationIdJoinJobAndCompany_test() {
        // given

        // when

        // eye

    }

    @Test
    public void deleteByResumeId_test() {
        // given
        Integer resumeId = 1;

        // when
        applicationRepository.deleteByResumeId(resumeId);

        // eye
        List<Application> applicationsPS = em.createQuery("SELECT a FROM Application a WHERE a.resume.id = :resumeId", Application.class)
                .setParameter("resumeId", resumeId)
                .getResultList();

        if (applicationsPS.isEmpty()) {
            System.out.println("삭제 성공!!!!!!!!");
        } else {
            System.out.println("삭제 실패ㅜㅜㅜㅜㅜ");
        }
    }
}
