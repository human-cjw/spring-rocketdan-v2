package com.metacoding.springrocketdanv2.application;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2.job.Job;
import com.metacoding.springrocketdanv2.job.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationResponse.ListForUserDTO 내지원목록보기(Integer sessionUserId, String status) {
        List<Application> applicationsPS = applicationRepository.findByUserIdAndStatus(sessionUserId, status);

        return new ApplicationResponse.ListForUserDTO(applicationsPS);
    }

    @Transactional
    public ApplicationResponse.SaveDTO 지원하기(ApplicationRequest.SaveDTO reqDTO, Integer sessionUserId) {
        applicationRepository.findByJobIdAndUserId(reqDTO.getJobId(), sessionUserId)
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        Job jobPS = jobRepository.findByJobId(reqDTO.getJobId())
                .orElseThrow(() -> new ExceptionApi400("잘못된 요청입니다"));

        Application application = reqDTO.toEntity(sessionUserId, jobPS.getCompany().getId());

        Application applicationPS = applicationRepository.save(application);

        return new ApplicationResponse.SaveDTO(applicationPS);
    }

    @Transactional
    public void 지원상태변경() {
        
    }
}
