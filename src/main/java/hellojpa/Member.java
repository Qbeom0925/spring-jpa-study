//package hellojpa;
//
//import net.bytebuddy.dynamic.loading.InjectionClassLoader;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Entity
//public class Member extends BaseEntity{
//
//    @Id @GeneratedValue
//    @Column(name = "MEMBER_ID")
//    private Long id;
//
//    @Column(name = "USERNAME")
//    private String username;
//
//    @ManyToOne
//    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
//    private Team team;
//
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public Team getTeam() {
//        return team;
//    }
//
//    public void setTeam(Team team) {
//        this.team = team;
//    }
//
//}
