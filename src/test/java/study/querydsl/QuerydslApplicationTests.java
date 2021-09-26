package study.querydsl;

import static org.assertj.core.api.Assertions.assertThat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.HelloEntity;
import study.querydsl.entity.Member;
import study.querydsl.entity.QHelloEntity;
import study.querydsl.entity.Team;

@SpringBootTest
@Transactional
class QuerydslApplicationTests {

    @PersistenceContext
    EntityManager em;

    @Test
    void contextLoads() {
        HelloEntity helloEntity = new HelloEntity();
        em.persist(helloEntity);

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QHelloEntity qHelloEntity = new QHelloEntity("h");

        HelloEntity result = queryFactory.selectFrom(qHelloEntity).fetchOne();

        assertThat(result).isEqualTo(helloEntity);
        assertThat(result.getId()).isEqualTo(helloEntity.getId());

    }

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println(" -> member.team = " + member.getTeam());
        }
    }

}
