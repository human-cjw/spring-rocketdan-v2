package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

@Import(JobBookmarkRepository.class)
@DataJpaTest
public class JobBookmarkRepositoryTest {
    @Autowired
    private JobBookmarkRepository jobBookmarkRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        User user = User.builder()
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .userType("user")
                .build();
        em.persist(user);

        Job job = Job.builder()
                .title("백엔드 개발자")
                .description("Spring 개발자")
                .location("서울")
                .employmentType("정규직")
                .deadline("2025-06-30")
                .status("OPEN")
                .careerLevel("신입")
                .build();
        em.persist(job);

        JobBookmark jobBookmark = JobBookmark.builder()
                .user(user)
                .job(job)
                .build();

        // when
        JobBookmark saved = jobBookmarkRepository.save(jobBookmark);
        em.flush();
        em.clear();

        // eye
        JobBookmark result = em.find(JobBookmark.class, saved.getId());
        System.out.println("ID: " + result.getId());
        System.out.println("생성일: " + result.getCreatedAt());
        System.out.println("User ID: " + result.getUser().getId());
        System.out.println("Job ID: " + result.getJob().getId());
    }

    @Test
    public void findByUserIdAndJobId_test() {
        // given
        Integer userId = 1;
        Integer jobId = 1;

        // when
        Optional<JobBookmark> jobBookmark = jobBookmarkRepository.findByUserIdAndJobId(userId, jobId);

        // eye
        if (jobBookmark.isPresent()) {
            System.out.println("userId: " + jobBookmark.get().getUser().getId());
            System.out.println("jobId: " + jobBookmark.get().getJob().getId());
        } else {
            System.out.println("jobBookmark is null!");
        }
    }

    @Test
    public void countByUserId_test() {
        // given
        Integer userId = 1;

        // when
        Long count = jobBookmarkRepository.countByUserId(userId);

        // eye
        System.out.println("userId " + userId + "의 북마크 수: " + count);
    }

    @Test
    public void findAllByUserId_test() {
        // given
        Integer userId = 1;

        // when
        List<JobBookmark> bookmarks = jobBookmarkRepository.findAllByUserId(userId);

        // eye
        for (JobBookmark jb : bookmarks) {
            System.out.println("북마크 ID: " + jb.getId());
            System.out.println("공고 제목: " + jb.getJob().getTitle());
            System.out.println("회사명: " + jb.getJob().getCompany().getNameKr());
            System.out.println("----------");
        }
    }

    @Test
    public void findById_test() {
        // given
        Integer bookmarkId = 1;

        // when
        JobBookmark jobBookmark = jobBookmarkRepository.findById(bookmarkId).orElse(null);

        // eye
        if (jobBookmark == null) {
            System.out.println("jobBookmark is null!");
        } else {
            System.out.println("북마크 ID: " + jobBookmark.getId());
            System.out.println("유저 ID: " + jobBookmark.getUser().getId());
            System.out.println("공고 제목: " + jobBookmark.getJob().getTitle());
        }
    }

    @Test
    public void findByUserId_test() {
        // given
        Integer userId = 1;

        // when
        List<JobBookmark> bookmarks = jobBookmarkRepository.findByUserId(userId);

        // eye
        for (JobBookmark bookmark : bookmarks) {
            System.out.println("북마크 ID: " + bookmark.getId());
            System.out.println("공고 제목: " + bookmark.getJob().getTitle());
        }
    }

    @Test
    public void delete_test() {
        // given
        Integer bookmarkId = 1;
        JobBookmark bookmark = em.find(JobBookmark.class, bookmarkId);

        // when
        jobBookmarkRepository.delete(bookmark);
        em.flush();
        em.clear();

        // eye
        JobBookmark result = em.find(JobBookmark.class, bookmarkId);
        if (result == null) {
            System.out.println("삭제 성공: 북마크가 존재하지 않습니다.");
        } else {
            System.out.println("삭제 실패: 북마크가 아직 존재합니다.");
        }
    }
}
