package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // 하나만 생성해서 애플리케이션 전체에서 공유
        EntityManager em = emf.createEntityManager(); // 쓰레드 간 공유X
        //이 사이에 데이터 관련 코드가 들어감

        //트랜잭션 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /* jpa로 저장할 때
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");
            em.persist(member); // jpa에 저장
            */

            /* jpa로 조회할 때
            Member findMember = em.find(Member.class, 1L);
            */

            /* jpa로 수정할 때
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA"); // 놀랍게도 이후에 저장을 안해줘도 알아서 수정됨!
            */

            // JPQL로 전체 테이블 조회
            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();

            //정상적일 때 트랜잭션 커밋
            tx.commit();
        } catch (Exception e) {
            //문제가 있을 때 롤백
            tx.rollback();
        } finally {
            //이 사이에 데이터 관련 코드가 들어감
            em.close(); // 다 하고 꺼줌
        }

        emf.close(); // 다 하고 꺼줌
    }
}
