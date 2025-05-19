package com.metacoding.springrocketdanv2.company;

import com.metacoding.springrocketdanv2.workfield.WorkField;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepository {
    private final EntityManager em;

    public void updateByCompanyId(Integer companyId, CompanyRequest.UpdateDTO reqDTO) {
        WorkField workFieldRef = em.getReference(WorkField.class, reqDTO.getWorkFieldId());

        em.createQuery("""
                            UPDATE Company c SET
                                c.nameKr = :nameKr,
                                c.nameEn = :nameEn,
                                c.oneLineIntro = :oneLineIntro,
                                c.introduction = :introduction,
                                c.startDate = :startDate,
                                c.businessNumber = :businessNumber,
                                c.email = :email,
                                c.contactManager = :contactManager,
                                c.phone = :phone,
                                c.ceo = :ceo,
                                c.address = :address,
                                c.workField = :workField
                            WHERE c.id = :companyId
                        """)
                .setParameter("nameKr", reqDTO.getNameKr())
                .setParameter("nameEn", reqDTO.getNameEn())
                .setParameter("oneLineIntro", reqDTO.getOneLineIntro())
                .setParameter("introduction", reqDTO.getIntroduction())
                .setParameter("startDate", reqDTO.getStartDate())
                .setParameter("businessNumber", reqDTO.getBusinessNumber())
                .setParameter("email", reqDTO.getEmail())
                .setParameter("contactManager", reqDTO.getContactManager())
                .setParameter("phone", reqDTO.getPhone())
                .setParameter("ceo", reqDTO.getCeo())
                .setParameter("address", reqDTO.getAddress())
                .setParameter("workField", workFieldRef)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    public Optional<Company> findByCompanyIdJoinFetchAll(Integer companyId) {
        Query query = em.createQuery("""
                                SELECT c FROM Company c
                                    JOIN FETCH c.user
                                    JOIN FETCH c.workField
                                    JOIN FETCH c.companyTechStacks cts
                                    JOIN FETCH cts.techStack ts
                                    WHERE c.id = :companyId
                        """)
                .setParameter("companyId", companyId);
        try {
            return Optional.ofNullable((Company) query.getSingleResult());
        } catch (Exception e) {
            return Optional.ofNullable(null);
        }
    }

    public Optional<Company> findByCompanyId(Integer companyId) {
        Company companyPS = em.find(Company.class, companyId);
        return Optional.ofNullable(companyPS);
    }

    public List<Company> findAll() {
        String q = "SELECT c FROM Company c ORDER BY c.id DESC";
        return em.createQuery(q, Company.class).getResultList();
    }

    public Company save(Company company) {
        em.persist(company);
        return company;
    }
}
