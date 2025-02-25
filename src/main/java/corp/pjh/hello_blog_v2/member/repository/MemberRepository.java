package corp.pjh.hello_blog_v2.member.repository;

import corp.pjh.hello_blog_v2.member.domain.Member;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    public Optional<Member> findByEmail(String email) {
        String query = "select m from Member m where m.email = :email";

        List<Member> resultList = em.createQuery(query, Member.class)
                .setParameter("email", email)
                .getResultList();

        return resultList.stream().findAny();
    }
}