package com.Omer.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="KULLANICI")
@Data
public class User extends BaseClass {
    @Id
    @SequenceGenerator(name="user_seq_gen",sequenceName = "user_gen",initialValue = 100,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="user_seq_gen")
    @Column(name="ID")
    private Long id;
    @Column(name="ISIM",length = 100)
    private String firstname;
    @Column(name="SOYISIM",length = 100)
    private String lastname;
}
