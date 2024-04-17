package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name="blockeds")
public class Blockeds {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iId;

    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}
            ,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonManagedReference
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}
            ,fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private Users blocked;

    public Blockeds(){}

    public Blockeds(Users user, Users blocked) {
        this.user = user;
        this.blocked = blocked;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getBlocked() {
        return blocked;
    }

    public void setBlocked(Users blocked) {
        this.blocked = blocked;
    }
}
