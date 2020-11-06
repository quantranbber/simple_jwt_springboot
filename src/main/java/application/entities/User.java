package application.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Column(name = "created_date")
    private Date createdDate;
}
