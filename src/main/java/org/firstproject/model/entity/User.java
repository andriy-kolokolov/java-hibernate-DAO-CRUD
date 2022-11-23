package org.firstproject.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", schema = "usersdb")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userName", nullable = false, length = 50)
    private String userName;

    @Column(name = "userLastName", nullable = false, length = 50)
    private String userLastName;

    @Column(name = "userAge", nullable = false)
    private Byte userAge;

}
