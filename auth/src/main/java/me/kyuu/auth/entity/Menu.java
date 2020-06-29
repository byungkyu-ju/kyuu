package me.kyuu.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    private Long upMenuId;
    private String name;
    private int sortOrder;
}
