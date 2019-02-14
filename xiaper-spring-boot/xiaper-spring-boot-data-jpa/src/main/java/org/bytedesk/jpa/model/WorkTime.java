package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工作时间
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "work_time")
public class WorkTime extends AuditModel {

    private static final long serialVersionUID = 6998065367142353284L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * columnDefinition=" COMMENT '上班时间' "
     */
    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm:ss", timezone="GMT+8")
    @Temporal(TemporalType.TIME)
    private Date startTime;

    /**
     * columnDefinition=" COMMENT '下班时间' "
     */
    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm:ss", timezone="GMT+8")
    @Temporal(TemporalType.TIME)
    private Date endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 是否工作时间
     *
     * @return
     */
    public boolean isWorkTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String timeString = formatter.format(new Date());

        Date now = null;
        try {
            now = formatter.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return now.after(startTime) && now.before(endTime);
    }

}
