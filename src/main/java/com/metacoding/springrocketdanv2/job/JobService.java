package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi404;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackRepository;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackRequest;
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
    private final ApplicationRepository applicationRepository;
    private final JobTechStackRepository jobTechStackRepository;

    public JobResponse.ListDTO 글목록보기(Integer sessionUserId) {
        List<Job> jobsPS = jobRepository.findAll();

        List<JobBookmark> jobBookmarks = new ArrayList<>();

        if (sessionUserId != null) {
            jobBookmarks = jobBookmarkRepository.findByUserId(sessionUserId);
        }

        return new JobResponse.ListDTO(jobsPS, jobBookmarks);
    }

    public JobResponse.DetailDTO 글상세보기(Integer jobId, User sessionUser) {
        Job jobPS = jobRepository.findByJobIdJoinFetchAll(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        Optional<JobBookmark> jobBookmarkOP;

        if (sessionUser == null) {
            jobBookmarkOP = jobBookmarkRepository.findByUserIdAndJobId(null, jobId);
        } else {
            jobBookmarkOP = jobBookmarkRepository.findByUserIdAndJobId(sessionUser.getId(), jobId);
        }

        Integer jobBookmarkId = null;

        if (jobBookmarkOP.isPresent()) {
            jobBookmarkId = jobBookmarkOP.get().getId();
        }

        return new JobResponse.DetailDTO(jobPS, sessionUser, jobBookmarkId);
    }

    @Transactional
    public JobResponse.SaveDTO 등록하기(JobRequest.SaveDTO reqDTO, Integer sessionUserCompanyId) {
        Job job = reqDTO.toEntity(sessionUserCompanyId);

        Job jobPS = jobRepository.save(job);
        Job jobPS2 = jobRepository.findByJobIdJoinFetchAll(jobPS.getId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 공고입니다"));

        return new JobResponse.SaveDTO(jobPS2);
    }

    @Transactional
    public JobResponse.UpdateDTO 수정하기(Integer jobId, JobRequest.UpdateDTO reqDTO, Integer sessionUserCompanyId) {
        Job jobPS1 = jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        if (!jobPS1.getCompany().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        jobRepository.updateByJobId(jobId, reqDTO);

        jobTechStackRepository.deleteByJobId(jobId);

        reqDTO.getTechStackIds().forEach(techStackId -> {
            jobTechStackRepository.save(new JobTechStackRequest.UpdateDTO(techStackId).toEntity(jobPS1));
        });

        Job jobPS2 = jobRepository.findByJobIdJoinFetchAll(jobId)
                .orElseThrow(() -> new ExceptionApi400("터지면 안된다"));

        return new JobResponse.UpdateDTO(jobPS2);
    }

    @Transactional
    public void 삭제하기(Integer jobId, Integer sessionUserCompanyId) {
        Job jobPS = jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        // 0. 권한 체크
        if (!jobPS.getCompany().getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // 1. 지원 테이블 삭제
        applicationRepository.deleteByJobId(jobId);

        // 2. jobTechStack 삭제
        jobTechStackRepository.deleteByJobId(jobId);

        // jobBookmark 삭제 추가

        // 3. 공고 삭제
        jobRepository.deleteByJobId(jobId);
    }
}