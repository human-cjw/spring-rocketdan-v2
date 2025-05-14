package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.companyTechStack.CompanyTechStack;
import com.metacoding.springrocketdanv2.user.User;
import com.metacoding.springrocketdanv2.workfield.Workfield;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "company_tb")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nameKr; // 한글이름
    private String nameEn; // 영어이름
    private String ceo; // 대표명
    private String businessNumber; // 사업자등록번호
    private String email;
    private String phone; // 대표번호
    private String address; // 주소
    @Column(columnDefinition = "text")
    private String introduction; // 회사소개
    private String oneLineIntro; // 회사한줄소개
    private String homepageUrl;
    private String logoImageUrl;
    private String infoImageUrl; // 회사소개이미지
    private String contactManager; // 문의담당자
    private String startDate;

    @CreationTimestamp
    private Timestamp createdAt;


    // 유저 fk
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // 업무분야 FK
    @ManyToOne(fetch = FetchType.LAZY)
    private Workfield workField;

    @OneToMany(mappedBy = "company", cascade = CascadeType.PERSIST)
    private List<CompanyTechStack> companyTechStackList = new ArrayList<>();

    @Builder
    public Company(Integer id, String nameKr, String nameEn, String ceo, String businessNumber,
                   String email, String phone, String address, String introduction,
                   String oneLineIntro, String homepageUrl, String logoImageUrl,
                   String infoImageUrl, String contactManager, String startDate,
                   User user, Workfield workField) {
        this.id = id;
        this.nameKr = nameKr;
        this.nameEn = nameEn;
        this.ceo = ceo;
        this.businessNumber = businessNumber;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.introduction = introduction;
        this.oneLineIntro = oneLineIntro;
        this.homepageUrl = homepageUrl;
        this.logoImageUrl = logoImageUrl;
        this.infoImageUrl = infoImageUrl;
        this.contactManager = contactManager;
        this.startDate = startDate;
        this.user = user;
        this.workField = workField;
    }

    public void update(CompanyRequest.UpdateDTO dto, Workfield workField) {
        this.nameKr = dto.getNameKr();
        this.nameEn = dto.getNameEn();
        this.oneLineIntro = dto.getOneLineIntro();
        this.introduction = dto.getIntroduction();
        this.startDate = dto.getStartDate();
        this.businessNumber = dto.getBusinessNumber();
        this.email = dto.getEmail();
        this.contactManager = dto.getContactManager();
        this.phone = dto.getPhone();
        this.ceo = dto.getCeo();
        this.address = dto.getAddress();
        this.workField = workField;
    }

}
