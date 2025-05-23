package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi404;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.company.Company;
import com.metacoding.springrocketdanv2.company.CompanyRepository;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupRepository;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeRepository;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import com.metacoding.springrocketdanv2.workfield.WorkFieldRepository;
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
    private final CompanyRepository companyRepository;
    private final JobBookmarkRepository jobBookmarkRepository;
    private final ApplicationRepository applicationRepository;
    private final JobTechStackRepository jobTechStackRepository;
    private final SalaryRangeRepository salaryRangeRepository;
    private final WorkFieldRepository workFieldRepository;
    private final JobGroupRepository jobGroupRepository;
    private final TechStackRepository techStackRepository;

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
        // 1. 기업 조회
        Company companyPS = companyRepository.findByCompanyId(sessionUserCompanyId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        // 2. 연봉범위 조회
        SalaryRange salaryRangePS = salaryRangeRepository.findBySalaryRangeId(reqDTO.getSalaryRangeId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 연봉범위 입니다"));

        // 3. 업무분야 조회
        WorkField workFieldPS = workFieldRepository.findByWorkFieldId(reqDTO.getWorkFieldId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 업무분야입니다"));

        // 4. 직무 조회
        JobGroup jobGroupPS = jobGroupRepository.findByJobGroupId(reqDTO.getJobGroupId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 직무입니다"));

        // 5. 기술스택들 조회
        List<TechStack> techStacksPS = reqDTO.getTechStackIds().stream()
                .map(techStackId -> techStackRepository.findByTechStackId(techStackId)
                        .orElseThrow(() -> new ExceptionApi404("존재하지 않는 기술스택입니다")))
                .toList();

        // 6. 엔티티 변환
        Job job = reqDTO.toEntity(companyPS, jobGroupPS, workFieldPS, salaryRangePS, techStacksPS);

        Job jobPS = jobRepository.save(job);

        return new JobResponse.SaveDTO(jobPS);
    }

    @Transactional
    public JobResponse.UpdateDTO 수정하기(Integer jobId, JobRequest.UpdateDTO reqDTO, Integer sessionUserCompanyId) {
        // 1. 공고 조회
        Job jobPS = jobRepository.findByJobId(jobId)
                .orElseThrow(() -> new ExceptionApi400("해당 공고는 존재하지 않습니다"));

        // 권한 체크
        if (!jobPS.getCompany().getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // 2. 연봉범위 조회
        SalaryRange salaryRangePS = salaryRangeRepository.findBySalaryRangeId(reqDTO.getSalaryRangeId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 연봉범위입니다"));

        // 3. 업무분야 조회
        WorkField workFieldPS = workFieldRepository.findByWorkFieldId(reqDTO.getWorkFieldId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 업무분야입니다"));

        // 4. 직무 조회
        JobGroup jobGroupPS = jobGroupRepository.findByJobGroupId(reqDTO.getJobGroupId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 직무입니다"));

        // 5. 기술스택들 조회
        List<TechStack> techStacksPS = reqDTO.getTechStackIds().stream()
                .map(techStackId -> techStackRepository.findByTechStackId(techStackId)
                        .orElseThrow(() -> new ExceptionApi404("존재하지 않는 기술스택입니다")))
                .toList();

        jobPS.update(reqDTO, salaryRangePS, workFieldPS, jobGroupPS, techStacksPS);

        // jobTechStack에 id를 넣어주기 위해
        jobRepository.save(jobPS);

        return new JobResponse.UpdateDTO(jobPS);
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
        jobBookmarkRepository.deleteByJobId(jobId);

        // 3. 공고 삭제
        jobRepository.deleteByJobId(jobId);
    }
}