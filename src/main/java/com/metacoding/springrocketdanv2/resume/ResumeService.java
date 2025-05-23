package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.career.Career;
import com.metacoding.springrocketdanv2.career.CareerRepository;
import com.metacoding.springrocketdanv2.certification.Certification;
import com.metacoding.springrocketdanv2.certification.CertificationRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRepository;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final CertificationRepository certificationRepository;
    private final ResumeTechStackRepository resumeTechStackRepository;
    private final CareerRepository careerRepository;
    private final ApplicationRepository applicationRepository;

    public ResumeResponse.DetailDTO 이력서상세보기(Integer resumeId, Integer sessionUserId) {
        Resume resumePS = resumeRepository.findByResumeIdJoinFetchAll(resumeId)
                .orElseThrow(() -> new ExceptionApi400("존재하지 않는 이력서 입니다"));

        List<Certification> certificationsPS = certificationRepository.findAllByResumeId(resumeId);
        List<Career> careersPS = careerRepository.findAllByResumeId(resumeId);

        return new ResumeResponse.DetailDTO(resumePS, certificationsPS, careersPS, sessionUserId);
    }

    // 수정 코드 JPA방식으로 엔티티 조회 -> 엔티티 수정 -> 엔티티 돌려주기
    @Transactional
    public ResumeResponse.UpdateDTO 이력서수정하기(Integer resumeId, ResumeRequest.UpdateDTO reqDTO, Integer sessionUserId) {
        // 이력서 조회하기
        Resume resumePS = resumeRepository.findByResumeId(resumeId)
                .orElseThrow(() -> new ExceptionApi400("해당 이력서는 존재하지 않습니다"));

        // 권한 체크
        if (!resumePS.getUser().getId().equals(sessionUserId)) {
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
            resumeTechStackRepository.save(new ResumeTechStackRequest.UpdateDTO(techStackId).toEntity(resumePS));
        });

        // 이력서 등록 전 삭제
        // 자격증 삭제 후 등록
        certificationRepository.deleteByResumeId(resumeId);

        List<Certification> certificationsPS = reqDTO.getCertifications().stream()
                .map(certification -> certificationRepository.save(certification.toEntity(resumePS)))
                .toList();

        // 경력 삭제 후 등록
        careerRepository.deleteByResumeId(resumeId);

        List<Career> careersPS = reqDTO.getCareers().stream()
                .map(career -> careerRepository.save(career.toEntity(resumePS)))
                .toList();

        return new ResumeResponse.UpdateDTO(reqDTO, certificationsPS, careersPS, resumeId, sessionUserId);
    }

    public ResumeResponse.ListDTO 이력서목록보기(Integer sessionUserId, boolean isDefault) {
        List<Resume> resumesPS = resumeRepository.findAllByUserId(sessionUserId, isDefault);

        return new ResumeResponse.ListDTO(resumesPS);
    }

    @Transactional
    public void 이력서삭제(Integer resumeId, Integer sessionUserId) {
        // 1. 이력서 조회
        Resume resumePS = resumeRepository.findByResumeId(resumeId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        // 2. 권한 체크
        if (!resumePS.getUser().getId().equals(sessionUserId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // 자격증 삭제
        certificationRepository.deleteByResumeId(resumeId);

        // 경력 삭제
        careerRepository.deleteByResumeId(resumeId);

        // 지원 삭제
        applicationRepository.deleteByResumeId(resumeId);

        // 이력서가 가지고 있는 resume_tech_stack 전부 삭제
        resumeTechStackRepository.deleteByResumeId(resumeId);

        // 이력서 삭제
        resumeRepository.deleteByResumeId(resumeId);
    }

    @Transactional
    public ResumeResponse.SaveDTO 이력서등록(ResumeRequest.SaveDTO reqDTO, Integer sessionUserId) {
        // 엔티티 생성
        Resume resume = reqDTO.toEntity(sessionUserId);

        // isDefault가 true인 resume 다가져와서 false로 만들기
        Optional<Resume> resumeIsDefaultTruePC = resumeRepository.findByUserIdAndIsDefaultTrue(sessionUserId);

        if (resumeIsDefaultTruePC.isPresent()) {
            resumeIsDefaultTruePC.get().setIsDefault(false);
        }

        // 이력서 등록
        Resume resumePS = resumeRepository.save(resume);

        List<Certification> certificationsPS = reqDTO.getCertifications().stream()
                .map(certification -> certificationRepository.save(certification.toEntity(resumePS)))
                .toList();

        List<Career> careersPS = reqDTO.getCareers().stream()
                .map(career -> careerRepository.save(career.toEntity(resumePS)))
                .toList();

        return new ResumeResponse.SaveDTO(resumePS, certificationsPS, careersPS);
    }
}


