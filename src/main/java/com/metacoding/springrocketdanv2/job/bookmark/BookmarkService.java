package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmark;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRequest;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkResponse;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final JobBookmarkRepository jobBookmarkRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Transactional
    public void 북마크토글(JobBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {
        JobBookmark bookmark = jobBookmarkRepository.findByUserIdAndJobId(sessionUserId, reqDTO.getJobId());

        if (bookmark == null) {
            User user = userRepository.findById(sessionUserId);
            jobBookmarkRepository.save(reqDTO.toEntity(user));
        } else {
            jobBookmarkRepository.delete(bookmark);
        }
    }

    public List<JobBookmarkResponse.JobListWithBookmarkDTO> getAllJobsWithBookmarkInfo(Integer sessionUserId) {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> {
                    boolean isBookmarked = jobBookmarkRepository.findByUserIdAndJobId(sessionUserId, job.getId()) != null;
                    return new JobBookmarkResponse.JobListWithBookmarkDTO(job, isBookmarked);
                })
                .toList();
    }

    public Long count(Integer sessionUserId) {
        return jobBookmarkRepository.countByUserId(sessionUserId);
    }

    public List<JobBookmarkResponse.BookmarkListDTO> getBookmarkList(Integer userId) {
        List<JobBookmark> bookmarkList = jobBookmarkRepository.findAllByUserId(userId);

        return bookmarkList.stream()
                .map(bookmark -> new JobBookmarkResponse.BookmarkListDTO(bookmark))
                .toList();
    }

    @Transactional
    public void 북마크삭제(Integer bookmarkId) {
        JobBookmark bookmarkPS = jobBookmarkRepository.findById(bookmarkId);
        if (bookmarkPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        jobBookmarkRepository.delete(bookmarkPS);
    }

}
