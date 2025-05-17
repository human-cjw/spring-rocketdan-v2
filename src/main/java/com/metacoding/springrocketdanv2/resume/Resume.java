package com.metacoding.springrocketdanv2.resume;

import com.metacoding.springrocketdanv2.jobgroup.JobGroup;
import com.metacoding.springrocketdanv2.resume.techstack.ResumeTechStack;
import com.metacoding.springrocketdanv2.salaryrange.SalaryRange;
import com.metacoding.springrocketdanv2.techstack.TechStack;
import com.metacoding.springrocketdanv2.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@ToString
@NoArgsConstructor
@Getter
@Entity
@Table(name = "resume_tb")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    @Column(columnDefinition = "text")
    private String summary; // 자기소개
    private String gender; // 남 여
    private String careerLevel; // 신입, 경력
    private String education; // 학력사항
    private String birthdate; // 생년월일
    private String major; // 전공
    private String graduationType; // 졸업, 재학, 휴학
    private String phone; // 연락처
    private String portfolioUrl; // 포트폴리오 url
    private String enrollmentDate; // 입학날짜
    private String graduationDate; // 졸업날짜
    private Boolean isDefault; // 기본 이력서 인가?

    @CreationTimestamp
    private Timestamp createdAt;

    // 유저 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 연봉범위 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private SalaryRange salaryRange;

    // 직무 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private JobGroup jobGroup;

    // orphanRemoval = true -> 부모 엔티티와 관계가 끊어진 자식 요소는 삭제됨
    // cascade = CascadeType.ALL -> 부모 엔티티를 수정하면 자식 요소도 수정됨
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ResumeTechStack> resumeTechStacks = new ArrayList<>();

    @Builder
    public Resume(Integer id, String title, String summary, String gender, String careerLevel, String education, String birthdate, String major, String graduationType, String phone, String portfolioUrl, String enrollmentDate, String graduationDate, Boolean isDefault, Timestamp createdAt, User user, SalaryRange salaryRange, JobGroup jobGroup) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.gender = gender;
        this.careerLevel = careerLevel;
        this.education = education;
        this.birthdate = birthdate;
        this.major = major;
        this.graduationType = graduationType;
        this.phone = phone;
        this.portfolioUrl = portfolioUrl;
        this.enrollmentDate = enrollmentDate;
        this.graduationDate = graduationDate;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.user = user;
        this.salaryRange = salaryRange;
        this.jobGroup = jobGroup;
    }

    public void update(ResumeRequest.UpdateDTO reqDTO) {
        this.title = reqDTO.getTitle();
        this.summary = reqDTO.getSummary();
        this.portfolioUrl = reqDTO.getPortfolioUrl();
        this.gender = reqDTO.getGender();
        this.education = reqDTO.getEducation();
        this.birthdate = reqDTO.getBirthdate();
        this.major = reqDTO.getMajor();
        this.graduationType = reqDTO.getGraduationType();
        this.phone = reqDTO.getPhone();
        this.enrollmentDate = reqDTO.getEnrollmentDate();
        this.graduationDate = reqDTO.getGraduationDate();
        this.careerLevel = reqDTO.getCareerLevel();
        this.isDefault = reqDTO.getIsDefault() != null ? reqDTO.getIsDefault() : false;
        this.salaryRange = SalaryRange.builder().id(reqDTO.getSalaryRangeId()).build();
        this.jobGroup = JobGroup.builder().id(reqDTO.getJobGroupId()).build();
        this.resumeTechStacks.clear();
        this.resumeTechStacks.addAll(
                reqDTO.getTechStackIds().stream()
                        .map(techStackId -> ResumeTechStack.builder()
                                .techStack(TechStack.builder().id(techStackId).build())
                                .resume(this)
                                .build())
                        .toList()
        );
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
}