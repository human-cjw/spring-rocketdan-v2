package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.certification.CertificationRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupRepository;
import com.metacoding.springrocketdanv2.resume.bookmark.ResumeBookmark;
import com.metacoding.springrocketdanv2.resume.bookmark.ResumeBookmarkRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStack;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRequest;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeRepository;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final CertificationRepository certificationRepository;
    private final ResumeTechStackRepository resumeTechStackRepository;
    private final CareerRepository careerRepository;
    private final TechStackRepository techStackRepository;
    private final SalaryRangeRepository salaryRangeRepository;
    private final JobGroupRepository jobGroupRepository;
    private final ApplicationRepository applicationRepository;
    private final ResumeBookmarkRepository resumeBookmarkRepository;


    public ResumeResponse.DetailDTO 이력서상세보기(Integer resumeId, Integer sessionUserId) {
        Resume resumePS = resumeRepository.findByResumeIdJoinFetchAll(resumeId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 이력서 입니다"));

        List<Certification> certificationsPS = certificationRepository.findAllByResumeId(resumeId);
        List<Career> careersPS = careerRepository.findAllByResumeId(resumeId);

        return new ResumeResponse.DetailDTO(resumePS, certificationsPS, careersPS, sessionUserId);
    }

    @Transactional
    public ResumeResponse.UpdateDTO 이력서수정하기(Integer resumeId, ResumeRequest.UpdateDTO reqDTO, Integer sessionUserId) {
        // 이력서 조회하기
        Resume resumePS1 = resumeRepository.findByResumeIdId(resumeId)
                .orElseThrow(() -> new ExceptionApi400("해당 이력서는 존재하지 않습니다"));

        // 권한 체크
        if (!resumePS1.getUser().getId().equals(sessionUserId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // isDefault를 true로 받았을때
        if (reqDTO.getIsDefault() != null && reqDTO.getIsDefault()) {
            Resume resumeIsDefaultTruePS = resumeRepository.findByUserIdAndIsDefaultTrue(sessionUserId)
                    .orElseThrow(() -> new ExceptionApi400("터지면 이상한 것"));

            if (resumeIsDefaultTruePS.getId() != resumeId) {
                resumeIsDefaultTruePS.setIsDefault(false);
            }
        }

        // 업데이트 쿼리로 수정
        // 이력서 업데이트
        resumeRepository.updateByResumeId(resumeId, reqDTO);

        // resumeTechStack 삭제
        resumeTechStackRepository.deleteByResumeId(resumeId);

        // resumeTechStack 등록
        reqDTO.getTechStackIds().forEach(techStackId -> {
            resumeTechStackRepository.save(new ResumeTechStackRequest.UpdateDTO(techStackId).toEntity(resumePS1));
        });

        // 이력서 등록 전 삭제
        // 자격증 삭제 후 등록
        certificationRepository.deleteByResumeId(resumeId);

        List<Certification> certificationsPS = new ArrayList<>();

        reqDTO.getCertifications().forEach(certification -> {
            Certification certificationPS = certificationRepository.save(certification.toEntity(resumePS1));
            certificationsPS.add(certificationPS);
        });

        // 경력 삭제 후 등록
        careerRepository.deleteByResumeId(resumeId);

        List<Career> careersPS = new ArrayList<>();

        reqDTO.getCareers().forEach(career -> {
            Career careerPS = careerRepository.save(career.toEntity(resumePS1));
            careersPS.add(careerPS);
        });

        // 다시 이력서 조회, 경력과 자격증 조회한 이력서를 DTO에 넣는다
        Resume resumePS2 = resumeRepository.findByResumeIdJoinFetchAll(resumeId)
                .orElseThrow(() -> new ExceptionApi400("해당 이력서는 존재하지 않습니다"));

        return new ResumeResponse.UpdateDTO(resumePS2, certificationsPS, careersPS);
    }

    public ResumeResponse.ListDTO 이력서목록보기(Integer sessionUserId, boolean isDefault) {
        List<Resume> resumesPS = resumeRepository.findAllByUserId(sessionUserId, isDefault);

        return new ResumeResponse.ListDTO(resumesPS);
    }

    @Transactional
    public void 이력서삭제(Integer resumeId, Integer sessionUserId) {
        // 자격증 삭제
        Certification certificationPS = certificationRepository.findByResumeId(resumeId);
        if (certificationPS == null) {
            System.out.println("1");
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        certificationRepository.deleteByResumeId(resumeId);
        // 경력 삭제
        Career careerPS = careerRepository.findByResumeId(resumeId);
        if (careerPS == null) {
            System.out.println("2");
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        careerRepository.deleteByResumeId(resumeId);
        // 지원 업데이트 resume_id -> null, user_id -> null
        List<Application> applicationsPS = applicationRepository.findAllByResumeId(resumeId);
        if (applicationsPS.size() > 0) {
            applicationRepository.updateByResumeId(resumeId);
        }

        // 이력서가 가지고 있는 resume_tech_stack 전부 삭제
        List<ResumeTechStack> resumeTechStacksPS = resumeTechStackRepository.findAllByResumeId(resumeId);
        if (resumeTechStacksPS.size() < 1) {
            System.out.println("4");
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        resumeTechStackRepository.deleteByResumeId(resumeId);
        // 이력서 북마크 삭제
        List<ResumeBookmark> resumeBookmarksPS = resumeBookmarkRepository.findAllByResumeId(resumeId);
        if (resumeBookmarksPS.size() > 0) {
            resumeBookmarkRepository.deleteByResumeId(resumeId);
        }
        // 이력서 삭제
        Resume resumePS = resumeRepository.findById(resumeId);
        if (resumePS == null) {
            System.out.println("6");
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        resumeRepository.deleteById(resumeId);
    }

    public ResumeResponse.SaveDTO 이력서등록보기() {
        List<TechStack> techStacks = techStackRepository.findAll();
        List<SalaryRange> salaryRanges = salaryRangeRepository.findAll();
        List<JobGroup> jobGroups = jobGroupRepository.findAll();

        return new ResumeResponse.SaveDTO(techStacks, salaryRanges, jobGroups);
    }

    @Transactional
    public void 이력서등록(Integer sessionUserId, ResumeRequest.SaveDTO reqDTO) {
        // 엔티티 생성
        Resume resume = reqDTO.toEntity(sessionUserId);
        // isDefault가 true인 resume 다가져와서 false로 만들기
        Resume resumeIsDefaultTruePC = resumeRepository.findByUserIdAndIsDefaultTrue(sessionUserId);
        if (resumeIsDefaultTruePC != null) {
            resumeIsDefaultTruePC.setIsDefaultFalse();
        }
        // 이력서 등록
        Resume resumePS = resumeRepository.save(resume);
        // 자격증 생성
        Certification certification = Certification.builder()
                .resume(resumePS)
                .name(reqDTO.getCertificationName())
                .issuer(reqDTO.getCertificationIssuer())
                .issuedDate(reqDTO.getCertificationIssuedDate())
                .build();
        // 자격증 등록
        certificationRepository.save(certification);
        // 경력 생성
        Career career = Career.builder()
                .resume(resumePS)
                .companyName(reqDTO.getCareerCompanyName())
                .startDate(reqDTO.getCareerStartDate())
                .endDate(reqDTO.getCareerEndDate())
                .build();
        // 경력 등록
        careerRepository.save(career);
    }
}


