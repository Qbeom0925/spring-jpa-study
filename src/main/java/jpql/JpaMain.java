package jpql;


import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username","memeber1");
            Member singleResult = query.getSingleResult();

            //임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o",Address.class);

            //스칼라 타입 프로젝션
            em.createQuery("select m.username, m.age from Member m");

            //페이징 API
            List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(10)
                    .setMaxResults(20)
                    .getResultList();

            //innerjoin
            String query1 = "select m from Member m inner join m.team t";
            em.createQuery(query1,Member.class);

            //left outer join
            String query2 = "select m from Member m left outer join m.team t";
            em.createQuery(query2,Member.class);

            //setajoin
            String query3 = "select m from Member m, Team t where m.username = t.name";
            em.createQuery(query3,Member.class);

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
