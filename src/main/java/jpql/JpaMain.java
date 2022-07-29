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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setAge(10);
            member1.setType(MemberType.ADMIN);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setAge(10);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setAge(10);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);

            em.flush();
            em.clear();

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

            //ENUM
            String query4 = "select m.username, 'HELLO', true from Member m where m.type = jpql.MemberType.USER";
            em.createQuery(query4,Member.class);

            //case식
            String query5 = "select" +
                    " case when m.age <= 10 then '학생요금'" +
                    " when m.age >= 60 then '경오요금'" +
                    " else '일반요금' end " +
                    " from Member m";
            em.createQuery(query5,String.class);

            //coalece
            String query6 = "select coalesce(m.username,'이름 없는 회원') from Member m";
            em.createQuery(query6,String.class);

            //nullif
            String query7 = "select NULLIF(m.username, '관리자') from Member m";
            em.createQuery(query7,String.class);

            String query8 = "select m from Member m";
            List<Member> result = em.createQuery(query8, Member.class).getResultList();
            for (Member member : result) {
                System.out.println("member = " + member.getUsername()+", "+member.getTeam().getName());
            }

            String query9 = "select m from Member m join fetch m.team";
            em.createQuery(query9, Member.class).getResultList();

            //엔티티 파라미터
            String query10 = "select m from Member m where m = :member";
            em.createQuery(query10, Member.class)
                    .setParameter("member",member1)
                    .getResultList();

            //named쿼리
            //flush 자동 호출된다.
            List<Member> resultList1 = em.createNamedQuery("Member.findByUsername", Member.class)
                    .setParameter("username", "회원1")
                    .getResultList();


            int i = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            //벌크연산 쓰고 em.clear() 호출 완료
            em.clear();
            Member member = em.find(Member.class, member1.getId());
            System.out.println("member = " + member.getAge());


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
