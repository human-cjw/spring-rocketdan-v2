package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmark;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStack;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupRepository;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeRepository;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import com.metacoding.springrocketdanv2.workfield.WorkFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final TechStackRepository techStackRepository;
    private final WorkFieldRepository workFieldRepository;
    private final SalaryRangeRepository salaryRangeRepository;
    private final JobGroupRepository jobGroupRepository;
    private final JobBookmarkRepository jobBookmarkRepository;
    private final ApplicationRepository applicationRepository;
    private final JobTechStackRepository jobTechStackRepository;

    public List<JobResponse.DTO> 글목록보기() {
        List<Job> jobs = jobRepository.findAll();  // 모든 Job 조회
        List<JobResponse.DTO> jobDTOs = new ArrayList<>();  // DTO 리스트 생성

        for (Job job : jobs) {
            JobResponse.DTO dto = new JobResponse.DTO();
            dto.setId(job.getId());
            dto.setTitle(job.getTitle());
            dto.setCareerLevel(job.getCareerLevel());

            dto.setNameKr(job.getCompany().getNameKr());
            jobDTOs.add(dto);  // DTO 리스트에 추가
        }

        return jobDTOs;  // 변환된 DTO 리스트 반환
    }

    public JobResponse.DetailDTO 글상세보기(Integer jobId, UserResponse.SessionUserDTO sessionUserDTO) {
        Job job = jobRepository.findById(jobId);
        if (job == null) throw new RuntimeException(jobId + "번 공고가 없습니다.");

        // 기본 DTO 구성
        SalaryRange salaryRange = job.getSalaryRange();
        SalaryRangeResponse.SalaryRangeDTO salaryRangeDTO = (salaryRange != null)
                ? new SalaryRangeResponse.SalaryRangeDTO(salaryRange.getMinSalary(), salaryRange.getMaxSalary())
                : null;

        JobResponse.DetailDTO dto = new JobResponse.DetailDTO(
                job.getTitle(),
                job.getDeadline(),
                job.getCareerLevel(),
                job.getCreatedAt(),
                job.getDescription(),
                job.getLocation(),
                job.getEmploymentType(),
                job.getWorkField().getName(),
                job.getCompany().getNameKr(),
                job.getSalaryRange(),
                job.getCompany().getId(),
                job.getId(),
                job.getCompany().getContactManager(),
                job.getCompany().getPhone(),
                job.getJobTechStacks(),
                sessionUserDTO
        );

        if (sessionUserDTO != null) {
            JobBookmark bookmark = jobBookmarkRepository.findByUserIdAndJobId(sessionUserDTO.getId(), job.getId());
            dto.setBookmarked(bookmark != null);
        }

        return dto;
    }

    public JobResponse.JobSaveDTO 등록보기() {
        List<TechStack> techStacks = techStackRepository.findAll();
        List<WorkField> workFields = workFieldRepository.findAll();
        List<SalaryRange> salaryRanges = salaryRangeRepository.findAll();
        List<JobGroup> jobGroups = jobGroupRepository.findAll();
        return new JobResponse.JobSaveDTO(
                techStacks,
                workFields,
                salaryRanges,
                jobGroups
        );
    }

    @Transactional
    public void 등록하기(JobRequest.JobSaveDTO reqDTO, Integer companyId) {
        Job job = reqDTO.toEntity(companyId);
        jobRepository.save(job);
    }

    @Transactional
    public void 수정하기(Integer jobId, JobRequest.JobUpdateDTO reqDTO) {
        Job jobPS = jobRepository.findByIdJoinJobTechStackJoinTechStack(jobId);
        if (jobPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        SalaryRange salaryRange = SalaryRange.builder().id(reqDTO.getSalaryRangeId()).build();
        WorkField workField = WorkField.builder().id(reqDTO.getWorkFieldId()).build();
        JobGroup jobGroup = JobGroup.builder().id(reqDTO.getJobGroupId()).build();

        List<JobTechStack> jobTechStacks = new ArrayList<>();
        for (Integer techStackId : reqDTO.getTechStackIds()) {
            TechStack techStack = TechStack.builder().id(techStackId).build();
            jobTechStacks.add(
                    JobTechStack.builder()
                            .job(jobPS)
                            .techStack(techStack)
                            .build()
            );
        }

        jobPS.update(
                reqDTO.getTitle(),
                reqDTO.getDescription(),
                reqDTO.getLocation(),
                reqDTO.getEmploymentType(),
                reqDTO.getDeadline(),
                reqDTO.getStatus(),
                reqDTO.getCareerLevel(),
                salaryRange,
                workField,
                jobGroup,
                jobTechStacks
        );
    }

    @Transactional
    public void 공고삭제(Integer jobId) {
        // 1. 공고 존재 여부 확인 (Optional 처리)
        jobRepository.findById(jobId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        // 2. 지원 내역 삭제
        applicationRepository.deleteApplicationsByJobId(jobId);

        // 3. 북마크 삭제
        jobBookmarkRepository.deleteJobBookmarksByJobId(jobId);

        // 4. 기술 스택 연결 삭제
        jobTechStackRepository.deleteJobTechStacksByJobId(jobId);

        // 5. 공고 삭제
        jobRepository.deleteJobById(jobId);
    }
}