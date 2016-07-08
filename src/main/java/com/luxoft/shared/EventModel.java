package com.luxoft.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

public class EventModel implements Serializable, IsSerializable {
    public EventModel() {
    }

    public EventModel(Date beginDate, Date endDate, String place, Type type, String description) {
        this.beginDate = (Date) beginDate.clone();
        this.endDate = (Date) endDate.clone();
        this.place = place;
        this.type = type;
        this.description = description;
    }

    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotNull(message = Constants.ERROR_NULL_BEGIN_DATE)
    private Date beginDate;

    @NotNull(message = Constants.ERROR_NULL_END_DATE)
    private Date endDate;

    @Size(max = Constants.MAX_SIZE, message = Constants.ERROR_SIZE)
    private String place;

    private Type type;

    @Size(max = Constants.MAX_SIZE, message = Constants.ERROR_SIZE)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getBeginDate() {
        if (beginDate != null) {
            return (Date) beginDate.clone();
        }
        return null;
    }

    public void setBeginDate(Date beginDate) {
        if (beginDate != null) {
            this.beginDate = (Date) beginDate.clone();
        }
    }

    public Date getEndDate() {
        if (endDate != null) {
            return (Date) endDate.clone();
        }
        return null;
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = (Date) endDate.clone();
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventModel event = (EventModel) o;

        if (!id.equals(event.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.intValue();
        }
        return 0;
    }

    @AssertTrue(message = Constants.ERROR_DATE_VALID)
    public boolean getValidDate() {
        if (endDate != null && beginDate != null && endDate.compareTo(beginDate) == 1) {
            return true;
        }
        return false;
    }
}
