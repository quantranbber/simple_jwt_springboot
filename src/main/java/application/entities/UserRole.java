package application.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "tbl_user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;
}
