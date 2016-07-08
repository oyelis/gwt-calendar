package com.luxoft.client.tableview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;

public interface TableResources extends CellTable.Resources {

    public static TableResources IMPL = GWT.create(TableResources.class);

    public interface MyCellTable extends CellTable.Style {
    }

    @Override
    @Source({CellTable.Style.DEFAULT_CSS, "Table.css"})
    MyCellTable cellTableStyle();
}
