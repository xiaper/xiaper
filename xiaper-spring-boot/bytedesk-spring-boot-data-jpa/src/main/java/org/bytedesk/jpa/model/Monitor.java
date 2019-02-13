package org.bytedesk.jpa.model;

import javax.persistence.*;

/**
 * 监控
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "monitor")
public class Monitor extends AuditModel {

    private static final long serialVersionUID = 7761418382160016325L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;




}
