package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi404;
import com.metacoding.springrocketdanv2._core.util.JwtUtil;
import com.metacoding.springrocketdanv2.application.Application;
import com.metacoding.springrocketdanv2.application.ApplicationRepository;
import com.metacoding.springrocketdanv2.company.techstack.CompanyTechStackRepository;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.techstack.TechStackRepository;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.user.UserRepository;
import com.metacoding.springrocketdanv2.user.UserResponse;
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
    private final CompanyTechStackRepository companyTechStackRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final WorkFieldRepository workFieldRepository;
    private final TechStackRepository techStackRepository;

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
    public CompanyResponse.SaveDTO 기업등록(CompanyRequest.SaveDTO reqDTO, User sessionUser) {
        // 1. workField 조회
        WorkField workFieldPS = workFieldRepository.findByWorkFieldId(reqDTO.getWorkFieldId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 업종입니다"));

        // 2. 기술스택 조회
        List<TechStack> techStacksPS = reqDTO.getTechStackIds().stream()
                .map(techStackId -> techStackRepository.findByTechStackId(techStackId)
                        .orElseThrow(() -> new ExceptionApi404("존재하지 않는 기술스택입니다")))
                .toList();

        Company company = reqDTO.toEntity(sessionUser, workFieldPS, techStacksPS);

        Company companyPS = companyRepository.save(company);

        User userPS = userRepository.findByUserId(sessionUser.getId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 유저 입니다"));

        userPS.typeUpdate(companyPS.getId());

        // 토큰 재발행
        String accessToken = JwtUtil.create(userPS);
        String refreshToken = JwtUtil.createRefresh(userPS);
        UserResponse.TokenDTO tokenDTO = UserResponse.TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new CompanyResponse.SaveDTO(companyPS, tokenDTO);
    }

    // 기업 수정
    @Transactional
    public CompanyResponse.UpdateDTO 기업수정(CompanyRequest.UpdateDTO reqDTO, Integer companyId, Integer sessionUserCompanyId) {
        // 1. 회사 엔티티 조회 (없는 경우 예외)
        Company companyPS = companyRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new ExceptionApi400("해당 기업은 존재하지 않습니다"));

        // 권한 체크
        if (!companyPS.getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        // 2. workField 조회
        WorkField workFieldPS = workFieldRepository.findByWorkFieldId(reqDTO.getWorkFieldId())
                .orElseThrow(() -> new ExceptionApi404("존재하지 않는 업종입니다"));

        // 3. 기술스택 조회
        List<TechStack> techStacksPS = reqDTO.getTechStackIds().stream()
                .map(techStackId -> techStackRepository.findByTechStackId(techStackId)
                        .orElseThrow(() -> new ExceptionApi404("존재하지 않는 기술스택입니다")))
                .toList();

        // 4. 컴퍼니 업데이트
        companyPS.update(reqDTO, workFieldPS, techStacksPS);

        // companyTechStack에 id를 넣어주기 위해
        companyRepository.save(companyPS);

        return new CompanyResponse.UpdateDTO(companyPS);
    }

    // 기업 공고관리
    public CompanyResponse.JobListDTO 기업공고관리(Integer sessionUserCompanyId) {
        List<Job> jobsPS = jobRepository.findAllByCompanyId(sessionUserCompanyId);

        return new CompanyResponse.JobListDTO(jobsPS);
    }

    // 지원자 조회
    public CompanyResponse.ApplicationListDTO 지원자조회(Integer jobId, String status) {
        List<Application> applicationsPS = applicationRepository.findAllByJobIdJoinFetchAll(jobId, status);

        return new CompanyResponse.ApplicationListDTO(applicationsPS, status);
    }

    @Transactional
    public CompanyResponse.ApplicationDetailDTO 지원서상세보기(Integer applicationId, Integer sessionUserCompanyId) {

        Application applicationPS = applicationRepository.findByApplicationIdJoinResumeAndUser(applicationId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        if (!applicationPS.getCompany().getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        return new CompanyResponse.ApplicationDetailDTO(applicationPS);
    }
}