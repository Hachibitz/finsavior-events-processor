package br.com.finsavior.events.processor.model.entity;

import br.com.finsavior.events.processor.model.constant.Flag;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_plan")
@Data
public class UserPlan {
    @Id
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "plan_id")
    private String planId;
    @Column(name = "del_fg")
    @Enumerated(EnumType.STRING)
    private Flag delFg;
    @Column(name = "up_insert_dtm")
    private LocalDateTime userInsDtm;
    @Column(name = "up_insert_id")
    private String userInsId;
    @Column(name = "up_update_dtm")
    private LocalDateTime userUpdDtm;
    @Column(name = "up_update_id")
    private String userUpdId;

    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
