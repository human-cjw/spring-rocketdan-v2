package com.metacoding.springrocketdanv2.job;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmark;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.jobTechStack.JobTechStack;
import com.metacoding.springrocketdanv2.jobTechStack.JobTechStackResponse;
import com.metacoding.springrocketdanv2.jobgroup.Jobgroup;
import com.metacoding.springrocketdanv2.jobgroup.JobgroupRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobgroupResponse;
import com.metacoding.springrocketdanv2.salaryrange.Salaryrange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryrangeRepository;
import com.metacoding.springrocketdanv2.salaryrange.SalaryrangeResponse;
import com.metacoding.springrocketdanv2.techstack.Techstack;
import com.metacoding.springrocketdanv2.techstack.TechstackRepository;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.Workfield;
import com.metacoding.springrocketdanv2.workfield.WorkfieldRepository;
import com.metacoding.springrocketdanv2.workfield.WorkfieldResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final TechstackRepository techStackRepository;
    private final WorkfieldRepository workFieldRepository;
    private final SalaryrangeRepository salaryRangeRepository;
    private final JobgroupRepository jobGroupRepository;
    private final JobBookmarkRepository jobBookmarkRepository;

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
        Salaryrange salaryRange = job.getSalaryRange();
        SalaryrangeResponse.SalaryRangeDTO salaryRangeDTO = (salaryRange != null)
                ? new SalaryrangeResponse.SalaryRangeDTO(salaryRange.getMinSalary(), salaryRange.getMaxSalary())
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
        List<Techstack> techstacks = techStackRepository.findAll();
        List<Workfield> workFields = workFieldRepository.findAll();
        List<Salaryrange> salaryranges = salaryRangeRepository.findAll();
        List<Jobgroup> jobgroups = jobGroupRepository.findAll();
        return new JobResponse.JobSaveDTO(
                techstacks,
                workFields,
                salaryranges,
                jobgroups
        );
    }

    @Transactional
    public void 등록하기(JobRequest.JobSaveDTO reqDTO, Integer companyId) {
        Job job = reqDTO.toEntity(companyId);
        jobRepository.save(job);
    }


    public JobResponse.JobUpdateDTO 수정보기(Integer jobId) {
        List<Techstack> techstacks = techStackRepository.findAll();
        List<Workfield> workFields = workFieldRepository.findAll();
        List<Salaryrange> salaryranges = salaryRangeRepository.findAll();
        List<Jobgroup> jobgroups = jobGroupRepository.findAll();
        Job job = jobRepository.findByIdJoinJobTechStackJoinTechStack(jobId);

        // 선택된 상태를 표시하기 위한 List
        List<JobResponse.JobUpdateDTO.CareerLevel> careerLevels = List.of(
                new JobResponse.JobUpdateDTO.CareerLevel("", "선택", job.getCareerLevel().equals("")),
                new JobResponse.JobUpdateDTO.CareerLevel("신입", "신입", job.getCareerLevel().equals("신입")),
                new JobResponse.JobUpdateDTO.CareerLevel("경력", "경력", job.getCareerLevel().equals("경력"))
        );

        // 선택된 상태를 표시하기 위한 List
        List<JobResponse.JobUpdateDTO.EmploymentType> employmentTypes = List.of(
                new JobResponse.JobUpdateDTO.EmploymentType("", "선택", job.getEmploymentType().equals("")),
                new JobResponse.JobUpdateDTO.EmploymentType("정규직", "정규직", job.getEmploymentType().equals("정규직")),
                new JobResponse.JobUpdateDTO.EmploymentType("계약직", "계약직", job.getEmploymentType().equals("계약직")),
                new JobResponse.JobUpdateDTO.EmploymentType("인턴", "인턴", job.getEmploymentType().equals("인턴")),
                new JobResponse.JobUpdateDTO.EmploymentType("프리랜서", "프리랜서", job.getEmploymentType().equals("프리랜서"))
        );

        // job이 가지고 있는 techStack id 값만 들어있는 리스트
        List<Integer> jobTechStackIds = new ArrayList<>();
        for (JobTechStack jobTechStack : job.getJobTechStacks()) {
            jobTechStackIds.add(jobTechStack.getTechStack().getId());
        }

        // 선택된 상태를 표시하기 위한 List
        List<JobTechStackResponse.JobTechStackUpdateDTO> jobTechStackUpdateDTOs = new ArrayList<>();
        for (Techstack techStack : techstacks) {
            jobTechStackUpdateDTOs.add(
                    new JobTechStackResponse.JobTechStackUpdateDTO(
                            techStack.getId(),
                            techStack.getName(),
                            jobTechStackIds.contains(techStack.getId())
                    )
            );
        }

        // 선택된 상태를 표시하기 위한 List
        List<WorkfieldResponse.WorkFieldUpdateDTO> workFieldUpdateDTOs = new ArrayList<>();
        for (Workfield workField : workFields) {
            workFieldUpdateDTOs.add(
                    new WorkfieldResponse.WorkFieldUpdateDTO(
                            workField.getId(),
                            workField.getName(),
                            job.getWorkField().getId().equals(workField.getId())
                    )
            );
        }

        // 선택된 상태를 표시하기 위한 List
        List<SalaryrangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs = new ArrayList<>();
        for (Salaryrange salaryRange : salaryranges) {
            salaryRangeUpdateDTOs.add(
                    new SalaryrangeResponse.SalaryRangeUpdateDTO(
                            salaryRange.getId(),
                            salaryRange.getLabel(),
                            job.getSalaryRange().getId().equals(salaryRange.getId())
                    )
            );
        }

        // 선택된 상태를 표시하기 위한 List
        List<JobgroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs = new ArrayList<>();
        for (Jobgroup jobGroup : jobgroups) {
            jobGroupUpdateDTOs.add(
                    new JobgroupResponse.JobGroupUpdateDTO(
                            jobGroup.getId(),
                            jobGroup.getName(),
                            job.getJobGroup().getId().equals(jobGroup.getId())
                    )
            );
        }

        JobResponse.JobUpdateDTO respDTO = new JobResponse.JobUpdateDTO(
                jobId,
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getDeadline(),
                job.getStatus(),
                careerLevels,
                employmentTypes,
                jobTechStackUpdateDTOs,
                workFieldUpdateDTOs,
                salaryRangeUpdateDTOs,
                jobGroupUpdateDTOs
        );

        System.out.println(respDTO);

        return respDTO;
    }

    @Transactional
    public void 수정하기(Integer jobId, JobRequest.JobUpdateDTO reqDTO) {
        Job jobPS = jobRepository.findByIdJoinJobTechStackJoinTechStack(jobId);
        if (jobPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        Salaryrange salaryRange = Salaryrange.builder().id(reqDTO.getSalaryRangeId()).build();
        Workfield workField = Workfield.builder().id(reqDTO.getWorkFieldId()).build();
        Jobgroup jobGroup = Jobgroup.builder().id(reqDTO.getJobGroupId()).build();

        List<JobTechStack> jobTechStacks = new ArrayList<>();
        for (Integer techStackId : reqDTO.getTechStackIds()) {
            Techstack techStack = Techstack.builder().id(techStackId).build();
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
}