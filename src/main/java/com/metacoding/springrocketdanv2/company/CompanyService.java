package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStack;
import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStackRepository;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRepository;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.user.UserResponse;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import com.metacoding.springrocketdanv2.workfield.WorkFieldRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final WorkFieldRepository workFieldRepository;
    private final CompanyTechStackRepository companyTechStackRepository;
    private final TechStackRepository techStackRepository;
    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    private final CareerRepository careerRepository;
    private final ResumeTechStackRepository resumeTechStackRepository;


    @PersistenceContext
    private EntityManager em;

    // 기업 상세보기
    public CompanyResponse.CompanyResponseDTO 기업상세(Integer companyId) {
        Company company = companyRepository.findById(companyId);

        List<TechStack> techStacks = companyTechStackRepository.findByCompanyId(companyId);
        List<String> techStackNames = techStacks.stream()
                .map(TechStack::getName)
                .collect(Collectors.toList());

        boolean isOwner = false;

        String workFieldName = workFieldRepository.findNameById(company.getWorkField().getId());
        return new CompanyResponse.CompanyResponseDTO(
                company.getNameKr(),
                company.getNameEn(),
                company.getCeo(),
                company.getBusinessNumber(),
                company.getEmail(),
                company.getPhone(),
                company.getAddress(),
                company.getIntroduction(),
                company.getOneLineIntro(),
                company.getHomepageUrl(),
                company.getLogoImageUrl(),
                company.getInfoImageUrl(),
                company.getContactManager(),
                company.getStartDate(),
                workFieldName,
                techStackNames,
                isOwner
        );
    }

    // 기업 리스트
    public List<Company> 기업리스트() {
        return companyRepository.findAll();
    }

    // 기업 등록
    @Transactional
    public UserResponse.SessionUserDTO 기업등록(CompanyRequest.CompanySaveDTO requestDTO, UserResponse.SessionUserDTO sessionUser) {
        // 산업분야 조회 또는 저장
        WorkField workField = workFieldRepository.findByName(requestDTO.getWorkFieldName());
        if (workField == null) {
            workField = workFieldRepository.save(WorkField.builder().name(requestDTO.getWorkFieldName()).build());
        }

        // 기술 스택 조회
        List<TechStack> techStackList = new ArrayList<>();
        if (requestDTO.getTechStack() != null) {
            for (String name : requestDTO.getTechStack()) {
                TechStack ts = techStackRepository.findByName(name);
                if (ts != null) {
                    techStackList.add(ts);
                }
            }
        }

        // 회사 + 연관 기술 스택 cascade 저장
        Company company = requestDTO.toEntity(sessionUser, workField, techStackList);
        Company companyPS = companyRepository.save(company);

        // 세션에서 넘어온 User가 아니라, DB에서 영속 객체를 다시 가져옴
        User user = em.find(User.class, sessionUser.getId());

        try {
            // userType, companyId 수정
            Field userTypeField = User.class.getDeclaredField("userType");
            userTypeField.setAccessible(true);
            userTypeField.set(user, "company");

            Field companyIdField = User.class.getDeclaredField("companyId");
            companyIdField.setAccessible(true);
            companyIdField.set(user, companyPS.getId());

        } catch (Exception e) {
            throw new RuntimeException("User 업데이트 실패", e);
        }
        UserResponse.SessionUserDTO sessionUserDTO = new UserResponse.SessionUserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getFileUrl(), user.getUserType(), companyPS.getId(), companyPS.getNameKr());

        return sessionUserDTO;
    }

    // 내 기업 조회 (업데이트 폼)
    public CompanyResponse.UpdateFormDTO 내기업조회(Integer userId) {
        Company company = companyRepository.findByUserId(userId);

        // 기술 스택 전체 조회 + 선택 여부 매핑
        List<TechStack> allTechStacks = techStackRepository.findAll();
        List<TechStack> selectedTechStacks = companyTechStackRepository.findByCompanyId(company.getId());

        List<String> selectedNames = selectedTechStacks.stream()
                .map(TechStack::getName)
                .collect(Collectors.toList());

        List<CompanyResponse.TechStackDTO> techStackDTOs = new ArrayList<>();
        for (TechStack ts : allTechStacks) {
            boolean isChecked = selectedNames.contains(ts.getName());
            techStackDTOs.add(new CompanyResponse.TechStackDTO(ts.getName(), isChecked));
        }

        // 산업 분야 전체 조회 + 선택 여부 매핑
        List<WorkField> allWorkFields = workFieldRepository.findAll();
        Integer selectedWorkFieldId = company.getWorkField().getId();

        List<CompanyResponse.WorkFieldDTO> workFieldDTOs = new ArrayList<>();
        for (WorkField wf : allWorkFields) {
            boolean isChecked = wf.getId().equals(selectedWorkFieldId);
            workFieldDTOs.add(new CompanyResponse.WorkFieldDTO(wf.getId(), wf.getName(), isChecked));
        }

        // DTO 조립
        CompanyResponse.UpdateFormDTO dto = new CompanyResponse.UpdateFormDTO();
        dto.setId(company.getId());
        dto.setNameKr(company.getNameKr());
        dto.setNameEn(company.getNameEn());
        dto.setOneLineIntro(company.getOneLineIntro());
        dto.setIntroduction(company.getIntroduction());
        dto.setStartDate(company.getStartDate());
        dto.setBusinessNumber(company.getBusinessNumber());
        dto.setEmail(company.getEmail());
        dto.setContactManager(company.getContactManager());
        dto.setAddress(company.getAddress());
        dto.setTechStacks(techStackDTOs);
        dto.setWorkFields(workFieldDTOs);
        dto.setPhone(company.getPhone());
        dto.setCeo(company.getCeo());

        return dto;
    }

    // 기업 수정
    @Transactional
    public void 기업수정(CompanyRequest.UpdateDTO dto) {

        Company company = companyRepository.findById(dto.getId());

        if (company == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        // workFieldId로 조회
        WorkField workField = workFieldRepository.findById(dto.getWorkFieldId());
        if (workField == null) {
            throw new RuntimeException("선택한 산업 분야가 존재하지 않습니다.");
        }

        company.update(dto, workField);

        // 기존 기술 스택 삭제 후 새로 저장
        companyTechStackRepository.deleteByCompanyId(company.getId());

        List<String> techStackList = dto.getTechStack();
        if (techStackList != null) {
            for (String techName : techStackList) {
                TechStack techStack = techStackRepository.findByName(techName);
                if (techStack != null) {
                    CompanyTechStack cts = new CompanyTechStack(company, techStack);
                    companyTechStackRepository.save(cts);
                }
            }
        }
    }

    public List<CompanyResponse.CompanyManageJobDTO> 기업공고관리(Integer companyId) {
        List<Job> jobList = companyRepository.findJobsByCompanyId(companyId);

        List<CompanyResponse.CompanyManageJobDTO> companyManageJobDTOS = new ArrayList<>();
        for (Job job : jobList) {
            companyManageJobDTOS.add(new CompanyResponse.CompanyManageJobDTO(
                    job.getId(),
                    job.getTitle(),
                    job.getCareerLevel(),
                    job.getCreatedAt().toLocalDateTime(),
                    job.getJobGroup().getName()
            ));
        }
        return companyManageJobDTOS;
    }

    public CompanyResponse.CompanyManageResumePageDTO 지원자조회(Integer jobId, String status) {
        List<Application> applications = applicationRepository.findByJobId(jobId, status);

        List<CompanyResponse.CompanyManageResumeDTO> applicationDTOs = new ArrayList<>();

        for (Application app : applications) {
            System.out.println(app);
            if (app.getResume() == null) continue;

            Resume resume = resumeRepository.findById(app.getResume().getId());  // lazy 대신 직접 조회
            applicationDTOs.add(new CompanyResponse.CompanyManageResumeDTO(
                    app.getId(),
                    app.getUser().getUsername(),
                    resume.getTitle(),
                    resume.getCareerLevel(),
                    app.getCreatedAt().toLocalDateTime(),
                    app.getStatus()
            ));
        }

        Job job = jobRepository.findById(jobId);  // 공고는 한번만 조회
        String jobTitle = (job != null) ? job.getTitle() : "공고 제목 없음";

        return new CompanyResponse.CompanyManageResumePageDTO(jobId, jobTitle, applicationDTOs);
    }

    @Transactional
    public CompanyResponse.CompanyacceptanceDTO 지원서상세보기(Integer applicationId) {
        // 1. 지원서 조회
        Application application = applicationRepository.findById(applicationId);

        if (application == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        // 2. 상태가 "접수"면 "검토"로 변경
        if ("접수".equals(application.getStatus())) {
            application.updateStatus("검토");
        }

        // 3. 이력서 조회
        Resume resume = resumeRepository.findById(application.getResume().getId());

        // 4. 커리어 조회
        List<Career> careers = careerRepository.findCareersByResumeId(resume.getId());

        // 5. 이력서 기술스택 조회
        List<TechStack> techStacks = resumeTechStackRepository.findAllByResumeIdWithTechStack(resume.getId());

        // 6. DTO 조립
        return new CompanyResponse.CompanyacceptanceDTO(resume, careers, techStacks, applicationId);
    }

    @Transactional
    public Integer 지원상태수정(Integer applicationId, String newStatus) {
        Application applicationPS = applicationRepository.findById(applicationId);
        if (applicationPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        applicationPS.updateStatus(newStatus);
        return applicationPS.getJob().getId();
    }

    @Transactional
    public void 공고삭제(Integer jobId) {
        // 1. 지원 내역 삭제
        Job jobPS = jobRepository.findById(jobId);
        if (jobPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        companyRepository.deleteApplicationsByJobId(jobId);

        // 2. 북마크 삭제
        companyRepository.deleteJobBookmarksByJobId(jobId);

        // 3. 기술스택 연결 삭제
        companyRepository.deleteJobTechStacksByJobId(jobId);

        // 4. 최종 공고 삭제
        companyRepository.deleteJobById(jobId);
    }

    public CompanyResponse.CompanySaveFormDTO 등록보기() {
        List<WorkField> workFields = workFieldRepository.findAll();
        List<TechStack> techStacks = techStackRepository.findAll();

        return new CompanyResponse.CompanySaveFormDTO(workFields, techStacks);
    }
}