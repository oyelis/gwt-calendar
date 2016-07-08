package com.luxoft.client.tableview;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.luxoft.shared.Constants;
import com.luxoft.shared.EventModel;

import java.util.Date;

/**
 * Cell table
 */
public class Table extends CellTable<EventModel> {
    public Table() {
        super(Constants.TABLE_PAGE_SIZE, TableResources.IMPL);
        final DateCell dateCell = new DateCell(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_SHORT));
        Column<EventModel, Date> beginDateColumn = new Column<EventModel, Date>(dateCell) {
            @Override
            public Date getValue(EventModel event) {
                return event.getBeginDate();
            }
        };
        addColumn(beginDateColumn, "Дата начала");
        Column<EventModel, Date> endDateColumn = new Column<EventModel, Date>(dateCell) {
            @Override
            public Date getValue(EventModel event) {
                return event.getEndDate();
            }
        };
        addColumn(endDateColumn, "Дата окончания");
        TextColumn<EventModel> placeColumn = new TextColumn<EventModel>() {
            @Override
            public String getValue(EventModel event) {
                return event.getPlace();
            }
        };
        addColumn(placeColumn, "Место");
        TextColumn<EventModel> typeColumn = new TextColumn<EventModel>() {
            @Override
            public String getValue(EventModel event) {
                return event.getType().name();
            }
        };
        addColumn(typeColumn, "Тип");
        TextColumn<EventModel> descriptionColumn = new TextColumn<EventModel>() {
            @Override
            public String getValue(EventModel event) {
                return event.getDescription();
            }
        };
        addColumn(descriptionColumn, "Описание");
    }
}
