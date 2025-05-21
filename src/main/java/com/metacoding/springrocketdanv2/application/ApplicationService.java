package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Transactional
    public ApplicationResponse.SaveDTO 지원하기(ApplicationRequest.SaveDTO reqDTO, Integer sessionUserId) {
        Optional<Application> applicationOP = applicationRepository.findByJobIdAndUserId(reqDTO.getJobId(), sessionUserId);

        if (applicationOP.isPresent()) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        Job jobPS = jobRepository.findByJobId(reqDTO.getJobId())
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        Application application = reqDTO.toEntity(sessionUserId, jobPS.getCompany().getId());

        Application applicationPS = applicationRepository.save(application);

        return new ApplicationResponse.SaveDTO(applicationPS);
    }

    @Transactional
    public ApplicationResponse.UpdateDTO 지원상태변경(Integer applicationId, ApplicationRequest.UpdateDTO reqDTO, Integer sessionUserCompanyId) {
        Application applicationPS = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        if (!applicationPS.getCompany().getId().equals(sessionUserCompanyId)) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        applicationPS.updateStatus(reqDTO.getStatus());

        return new ApplicationResponse.UpdateDTO(applicationPS);
    }
}
