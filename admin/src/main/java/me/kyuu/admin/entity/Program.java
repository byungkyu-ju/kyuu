package me.kyuu.admin.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Program {
    @Id
    @GeneratedValue
    @Column(name = "program_id")
    private Long id;

    private String name;
    private String filePath;
    private String attachPath;

}
