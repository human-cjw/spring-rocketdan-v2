package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobBookmarkService {
    private final JobBookmarkRepository jobBookmarkRepository;
    private final JobRepository jobRepository;

    @Transactional
    public JobBookmarkResponse.SaveDTO 북마크등록(JobBookmarkRequest.SaveDTO reqDTO, Integer sessionUserId) {
        JobBookmark jobBookmarkPS = jobBookmarkRepository.save(reqDTO.toEntity(sessionUserId));
        Long jobBookmarkCount = jobBookmarkRepository.countByUserId(sessionUserId);

        return new JobBookmarkResponse.SaveDTO(jobBookmarkPS.getId(), jobBookmarkCount.intValue());
    }

    public JobBookmarkResponse.ListDTO 북마크목록보기(Integer sessionUserId) {
        List<JobBookmark> jobBookmarksPS = jobBookmarkRepository.findAllByUserId(sessionUserId);
        return new JobBookmarkResponse.ListDTO(jobBookmarksPS);
    }

    @Transactional
    public void 북마크삭제(Integer jobBookmarkId, Integer sessionUserId) {
        JobBookmark bookmarkPS = jobBookmarkRepository.findById(jobBookmarkId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        if (bookmarkPS.getUser().getId() != sessionUserId) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        jobBookmarkRepository.delete(bookmarkPS);
    }
}
