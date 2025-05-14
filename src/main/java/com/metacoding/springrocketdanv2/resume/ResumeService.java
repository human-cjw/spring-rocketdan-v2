package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.certification.CertificationRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupRepository;
import com.metacoding.springrocketdanv2.jobgroup.JobGroupResponse;
import com.metacoding.springrocketdanv2.resumeBookmark.ResumeBookmark;
import com.metacoding.springrocketdanv2.resumeBookmark.ResumeBookmarkRepository;
import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStack;
import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStackRepository;
import com.metacoding.springrocketdanv2.resumeTechStack.ResumeTechStackResponse;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeRepository;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRangeResponse;
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


    public ResumeResponse.DetailDTO 이력서상세보기(Integer resumeId, Integer userId) {
        Resume resume = resumeRepository.findById(resumeId);
        List<Certification> certifications = certificationRepository.findCertificationsByResumeId(resumeId);
        List<ResumeTechStack> resumeTechStackList = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<Career> careers = careerRepository.findCareersByResumeId(resumeId);
        List<TechStack> techStacks = techStackRepository.findAll();
        List<Integer> resumeTechStackIds = new ArrayList<>();
        for (ResumeTechStack rts : resumeTechStackList) {
            resumeTechStackIds.add(rts.getTechStack().getId());
        }

        List<ResumeTechStackResponse.ResumeTechStackResponseDTO> resumeTechStackResponseDTOS = new ArrayList<>();
        for (TechStack techStack : techStacks) {
            resumeTechStackResponseDTOS.add(new ResumeTechStackResponse.ResumeTechStackResponseDTO(
                    techStack.getId(),
                    techStack.getName(),
                    resumeTechStackIds.contains(techStack.getId())));
        }

        // ResumeTechStack -> TechStack 변환

        List<TechStack> resumeTechStacks = new ArrayList<>();
        for (ResumeTechStack rts : resumeTechStackList) {
            resumeTechStacks.add(rts.getTechStack());
        }

        List<ResumeResponse.GraduationTypeDTO> graduationTypeDTOs = List.of(
                new ResumeResponse.GraduationTypeDTO("졸업", "졸업".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("재학", "재학".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("휴학", "휴학".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("졸업예정", "졸업예정".equals(resume.getGraduationType()))
        );

        List<ResumeResponse.CareerLevelTypeDTO> careerLevelTypeDTOs = List.of(
                new ResumeResponse.CareerLevelTypeDTO("신입", "신입".equals(resume.getCareerLevel())),
                new ResumeResponse.CareerLevelTypeDTO("경력", "경력".equals(resume.getCareerLevel()))
        );

        List<ResumeResponse.GenderTypeDTO> genderTypeDTOs = List.of(
                new ResumeResponse.GenderTypeDTO("남", "남".equals(resume.getGender())),
                new ResumeResponse.GenderTypeDTO("여", "여".equals(resume.getGender()))
        );

        return new ResumeResponse.DetailDTO(
                resume,
                certifications,
                resumeTechStacks,
                resume.getUser().getEmail(),
                resume.getUser().getUsername(),
                careers,
                resumeTechStackResponseDTOS,
                graduationTypeDTOs,
                careerLevelTypeDTOs,
                genderTypeDTOs,
                userId
        );
    }


    public ResumeResponse.UpdateDTO 이력서수정보기(Integer resumeId) {
        List<SalaryRange> salaryRanges = salaryRangeRepository.findAll();
        List<JobGroup> jobGroups = jobGroupRepository.findAll();
        Resume resume = resumeRepository.findById(resumeId);
        List<Certification> certifications = certificationRepository.findCertificationsByResumeId(resumeId);
        List<ResumeTechStack> resumeTechStackList = resumeTechStackRepository.findAllByResumeId(resumeId);
        List<Career> careers = careerRepository.findCareersByResumeId(resumeId);

        List<TechStack> techStacks = techStackRepository.findAll();
        List<Integer> resumeTechStackIds = new ArrayList<>();
        for (ResumeTechStack rts : resumeTechStackList) {
            resumeTechStackIds.add(rts.getTechStack().getId());
        }

        List<ResumeTechStackResponse.ResumeTechStackResponseDTO> resumeTechStackResponseDTOS = new ArrayList<>();
        for (TechStack techStack : techStacks) {
            resumeTechStackResponseDTOS.add(new ResumeTechStackResponse.ResumeTechStackResponseDTO(
                    techStack.getId(),
                    techStack.getName(),
                    resumeTechStackIds.contains(techStack.getId())));
        }

        // ResumeTechStack -> TechStack 변환

        List<TechStack> resumeTechStacks = new ArrayList<>();
        for (ResumeTechStack rts : resumeTechStackList) {
            resumeTechStacks.add(rts.getTechStack());
        }

        List<ResumeResponse.GraduationTypeDTO> graduationTypeDTOs = List.of(
                new ResumeResponse.GraduationTypeDTO("졸업", "졸업".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("재학", "재학".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("휴학", "휴학".equals(resume.getGraduationType())),
                new ResumeResponse.GraduationTypeDTO("졸업 예정", "졸업 예정".equals(resume.getGraduationType()))
        );

        List<ResumeResponse.CareerLevelTypeDTO> careerLevelTypeDTOs = List.of(
                new ResumeResponse.CareerLevelTypeDTO("신입", "신입".equals(resume.getCareerLevel())),
                new ResumeResponse.CareerLevelTypeDTO("경력", "경력".equals(resume.getCareerLevel()))
        );

        List<ResumeResponse.GenderTypeDTO> genderTypeDTOs = List.of(
                new ResumeResponse.GenderTypeDTO("남", "남".equals(resume.getGender())),
                new ResumeResponse.GenderTypeDTO("여", "여".equals(resume.getGender()))
        );

        List<SalaryRangeResponse.SalaryRangeUpdateDTO> salaryRangeUpdateDTOs = new ArrayList<>();
        for (SalaryRange salaryRange : salaryRanges) {
            salaryRangeUpdateDTOs.add(
                    new SalaryRangeResponse.SalaryRangeUpdateDTO(
                            salaryRange.getId(),
                            salaryRange.getLabel(),
                            resume.getSalaryRange().getId().equals(salaryRange.getId())
                    )
            );
        }

        List<JobGroupResponse.JobGroupUpdateDTO> jobGroupUpdateDTOs = new ArrayList<>();
        for (JobGroup jobGroup : jobGroups) {
            jobGroupUpdateDTOs.add(
                    new JobGroupResponse.JobGroupUpdateDTO(
                            jobGroup.getId(),
                            jobGroup.getName(),
                            resume.getJobGroup().getId().equals(jobGroup.getId())
                    )
            );
        }

        return new ResumeResponse.UpdateDTO(
                resume,
                certifications,
                resumeTechStacks,
                resume.getUser().getEmail(),
                resume.getUser().getUsername(),
                careers,
                resumeTechStackResponseDTOS,
                graduationTypeDTOs,
                careerLevelTypeDTOs,
                genderTypeDTOs,
                salaryRangeUpdateDTOs,
                jobGroupUpdateDTOs
        );
    }

    @Transactional
    public void 이력서수정하기(Integer resumeId, ResumeRequest.UpdateDTO requestDTO, Integer userId) {
        // isDefault를 true로 받았을때
        if (requestDTO.getIsDefault() != null && requestDTO.getIsDefault()) {
            Resume resumeIsDefaultTrue = resumeRepository.findByUserIdAndIsDefaultTrue(userId);
            if (resumeIsDefaultTrue == null) {
                throw new ExceptionApi400("잘못된 요청입니다");
            }
            if (resumeIsDefaultTrue.getId() != resumeId) {
                resumeIsDefaultTrue.setIsDefaultFalse();
            }
        }

        // 이력서 조회하기
        Resume resumePS = resumeRepository.findById(resumeId);

        if (resumePS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        List<ResumeTechStack> resumeTechStacks = new ArrayList<>();
        for (Integer techStackId : requestDTO.getTechStackIds()) {
            TechStack techStack = TechStack.builder().id(techStackId).build();
            resumeTechStacks.add(
                    ResumeTechStack.builder()
                            .resume(resumePS)
                            .techStack(techStack)
                            .build()
            );
        }

        resumePS.update(requestDTO, resumeTechStacks);

        // 이력서 등록 전 삭제
        Certification certificationPS = certificationRepository.findByResumeId(resumeId);
        if (certificationPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        certificationRepository.deleteByResumeId(resumeId);

        Certification certification = Certification.builder()
                .issuer(requestDTO.getCertificationIssuer())
                .name(requestDTO.getCertificationName())
                .issuedDate(requestDTO.getCertificationIssuedDate())
                .resume(resumePS)
                .build();
        certificationRepository.save(certification);

        // 경력 등록 전 삭제
        Career careerPS = careerRepository.findByResumeId(resumeId);
        if (careerPS == null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }
        careerRepository.deleteByResumeId(resumeId);

        Career career = Career.builder()
                .resume(resumePS)
                .companyName(requestDTO.getCareerCompanyName())
                .startDate(requestDTO.getCareerStartDate())
                .endDate(requestDTO.getCareerEndDate())
                .build();
        careerRepository.save(career);
    }

    public ResumeResponse.ResumeListDTO 이력서목록보기(Integer userId, boolean isDefault) {
        List<Resume> resumes = resumeRepository.findAllByUserId(userId, isDefault);

        return new ResumeResponse.ResumeListDTO(resumes, isDefault);
    }

    @Transactional
    public void 이력서삭제(Integer resumeId, Integer userId) {
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
    public void 이력서등록(Integer userId, ResumeRequest.SaveDTO reqDTO) {
        // 엔티티 생성
        Resume resume = reqDTO.toEntity(userId);
        // isDefault가 true인 resume 다가져와서 false로 만들기
        Resume resumeIsDefaultTruePC = resumeRepository.findByUserIdAndIsDefaultTrue(userId);
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


