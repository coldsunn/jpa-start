package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
            Order order = new Order();
            em.persist(order);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);

            em.persist(orderItem);


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
