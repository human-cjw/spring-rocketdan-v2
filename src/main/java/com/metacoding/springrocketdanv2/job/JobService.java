package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobBookmarkRepository jobBookmarkRepository;

    public JobResponse.ListDTO 글목록보기(Integer userId) {
        List<Job> jobsPS = jobRepository.findAll();

        List<JobBookmark> jobBookmarks = new ArrayList<>();

        if (userId != null) {
            jobBookmarks = jobBookmarkRepository.findByUserId(userId);
        }

        return new JobResponse.ListDTO(jobsPS, jobBookmarks);
    }

    public JobResponse.DetailDTO 글상세보기(Integer jobId, User sessionUser) {
        Job jobPS = jobRepository.findByIdJoinJobTechStackJoinTechStack(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        Optional<JobBookmark> jobBookmarkOP = jobBookmarkRepository.findByUserIdAndJobId(sessionUser.getId(), jobId);

        return new JobResponse.DetailDTO(jobPS, sessionUser, jobBookmarkOP);
    }

    @Transactional
    public JobResponse.SaveDTO 등록하기(JobRequest.SaveDTO reqDTO, Integer companyId) {
        Job job = reqDTO.toEntity(companyId);

        Job jobPS = jobRepository.save(job);

        return new JobResponse.SaveDTO(jobPS);
    }

    @Transactional
    public JobResponse.UpdateDTO 수정하기(Integer jobId, JobRequest.UpdateDTO reqDTO) {
        Job jobPS = jobRepository.findByIdJoinJobTechStackJoinTechStack(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        jobPS.update(reqDTO);

        return new JobResponse.UpdateDTO(jobPS);
    }

    // 공고 삭제가 없음
}