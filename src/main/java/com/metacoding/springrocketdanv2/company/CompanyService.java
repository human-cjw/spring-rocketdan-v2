package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStackRepository;
import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStackRequest;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import com.metacoding.springrocketdanv2.job.bookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.job.techstack.JobTechStackRepository;
import com.metacoding.springrocketdanv2.resume.Resume;
import com.metacoding.springrocketdanv2.resume.ResumeRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRepository;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.workfield.WorkField;
import com.metacoding.springrocketdanv2.workfield.WorkFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final JobBookmarkRepository jobBookmarkRepository;
    private final JobTechStackRepository jobTechStackRepository;

    // 기업 상세보기
    public CompanyResponse.DetailDTO 기업상세(Integer companyId, Integer sessionUserCompanyId) {
        Company companyPS = companyRepository.findByCompanyIdJoinFetchAll(companyId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 회사입니다"));

        return new CompanyResponse.DetailDTO(companyPS, sessionUserCompanyId);
    }

    // 기업 리스트
    public CompanyResponse.ListDTO 기업리스트() {
        List<Company> companyList = companyRepository.findAll();
        return new CompanyResponse.ListDTO(companyList);
    }

    // 기업 등록
    @Transactional
    public CompanyResponse.SaveDTO 기업등록(CompanyRequest.SaveDTO reqDTO, Integer sessionUserId) {
        Company company = reqDTO.toEntity(sessionUserId);

        Company companyPS = companyRepository.save(company);

        return new CompanyResponse.SaveDTO(companyPS);
    }

    // 기업 수정
    @Transactional
    public CompanyResponse.UpdateDTO 기업수정(CompanyRequest.UpdateDTO reqDTO, Integer companyId, Integer sessionUserCompanyId) {
        // 1. 회사 엔티티 조회 (없는 경우 예외)
        Company companyPS1 = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        // 권한 체크
        if (!companyPS1.getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // 기업 수정
        companyRepository.updateByCompanyId(companyId, reqDTO);

        // 4. 기존 기술 스택 관계 모두 삭제
        companyTechStackRepository.deleteByCompanyId(companyId);

        // 5. 새로운 기술 스택 아이디 기반으로 CompanyTechStack 재생성 및 저장
        reqDTO.getTechStackIds().forEach(techStackId -> {
            companyTechStackRepository.save(new CompanyTechStackRequest.UpdateDTO(techStackId).toEntity(companyPS1));
        });

        Company companyPS2 = companyRepository.findByCompanyIdJoinFetchAll(companyId)
                .orElseThrow(() -> new ExceptionApi400("터지면 안된다"));

        return new CompanyResponse.UpdateDTO(companyPS2);
    }

    // 기업 공고관리
    public CompanyResponse.JobListDTO 기업공고관리(Integer sessionUserCompanyId) {
        List<Job> jobsPS = jobRepository.findAllByCompanyId(sessionUserCompanyId);

        return new CompanyResponse.JobListDTO(jobsPS);
    }

    // 지원자 조회
    public CompanyResponse.ApplicationListDTO 지원자조회(Integer jobId, String status) {
        List<Application> applicationsPS = applicationRepository.findByJobIdJoinFetchAll(jobId, status);

        return new CompanyResponse.ApplicationListDTO(applicationsPS);
    }

    // -------------------------여기까지 완료 옵셔널 처리 해야함-------------------------------------------------------
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
        applicationRepository.deleteApplicationsByJobId(jobId);

        // 2. 북마크 삭제
        jobBookmarkRepository.deleteJobBookmarksByJobId(jobId);

        // 3. 기술스택 연결 삭제
        jobTechStackRepository.deleteJobTechStacksByJobId(jobId);

        // 4. 최종 공고 삭제
        jobRepository.deleteJobById(jobId);
    }

    public CompanyResponse.CompanySaveFormDTO 등록보기() {
        List<WorkField> workFields = workFieldRepository.findAll();
        List<TechStack> techStacks = techStackRepository.findAll();

        return new CompanyResponse.CompanySaveFormDTO(workFields, techStacks);
    }
}