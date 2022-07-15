package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //영속
            Member member = em.find(Member.class, 150L); //영속 상태
            member.setName("AAAAA"); //업데이트 상태

            em.detach(member);//준영속 상태
            em.clear();//영속성 컨텍스트 전체 초기화
            em.close();//영속성 컨텍스트 종료

            System.out.println("==================");
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
