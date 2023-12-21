package com.oh.votingdatamanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Delegate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Delegate(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Delegate() {

    }
}
