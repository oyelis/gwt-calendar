package com.luxoft.server.entity;

import com.luxoft.shared.Constants;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "EVENT")
public class EventEntity extends AbstractPersistable<Integer> {

    public EventEntity() {
    }

    public EventEntity(EventModel dto) {
        beginDate = dto.getBeginDate();
        endDate = dto.getEndDate();
        place = dto.getPlace();
        type = dto.getType();
        description = dto.getDescription();
    }

    public EventModel toDto() {
        EventModel eventModel = new EventModel();
        eventModel.setId(getId());
        eventModel.setBeginDate((Date) beginDate.clone());
        eventModel.setEndDate((Date) endDate.clone());
        eventModel.setPlace(place);
        eventModel.setType(type);
        eventModel.setDescription(description);
        return eventModel;
    }

    @Column(name = "BEGIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date beginDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date endDate;

    @Size(max = Constants.MAX_SIZE)
    private String place;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Size(max = Constants.MAX_SIZE)
    private String description;

    public Date getBeginDate() {
        return (Date) beginDate.clone();
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = (Date) beginDate.clone();
    }

    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        this.endDate = (Date) endDate.clone();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void update(Date beginDate, Date endDate, String place, Type type, String description) {
        this.beginDate = (Date) beginDate.clone();
        this.endDate = (Date) endDate.clone();
        this.place = place;
        this.type = type;
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        EventEntity rhs = (EventEntity) obj;
        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.description, rhs.description)
                .append(this.beginDate, rhs.beginDate)
                .append(this.endDate, rhs.endDate)
                .append(this.place, rhs.place)
                .append(this.type, rhs.type)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(description)
                .append(beginDate)
                .append(endDate)
                .append(place)
                .append(type)
                .toHashCode();
    }

    @AssertTrue
    public boolean getValidDate(){
        if (endDate.compareTo(beginDate) == 1){
            return true;
        }
        return false;
    }
}
