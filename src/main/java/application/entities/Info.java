package application.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "tbl_info")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String value;
}
