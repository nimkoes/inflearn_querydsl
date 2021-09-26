package study.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.HelloEntity;
import study.querydsl.entity.QHelloEntity;
import static org.assertj.core.api.Assertions.*;

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

}
