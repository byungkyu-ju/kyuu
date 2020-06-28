package me.kyuu.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Menu {
    @Id
    @GeneratedValue
    private Long menuId;

    private Long upMenuId;

    private String name;

    private int sortOrder;
}
