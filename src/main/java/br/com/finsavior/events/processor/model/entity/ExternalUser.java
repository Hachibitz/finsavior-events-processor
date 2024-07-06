package br.com.finsavior.events.processor.model.entity;

import br.com.finsavior.events.processor.model.constant.ExternalProvider;
import br.com.finsavior.events.processor.model.constant.Flag;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "external_user")
@Data
public class ExternalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscription_id")
    private String subscriptionId;

    @Column(name = "external_user_id")
    private String externalUserId;

    @Column(name = "external_user_email")
    private String externalUserEmail;

    @Column(name = "service_name")
    private ExternalProvider externalProvider;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "del_fg")
    @Enumerated(EnumType.STRING)
    private Flag delFg;
    @Column(name = "eu_insert_dtm")
    private LocalDateTime userInsDtm;
    @Column(name = "eu_insert_id")
    private String userInsId;
    @Column(name = "eu_update_dtm")
    private LocalDateTime userUpdDtm;
    @Column(name = "eu_update_id")
    private String userUpdId;
}
