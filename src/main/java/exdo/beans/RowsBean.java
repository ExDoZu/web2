package exdo.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RowsBean implements Serializable {
    private List<RowBean> rows;

    public RowsBean() {
        rows =new ArrayList<>();
    }

    public RowsBean(List<RowBean> rows) {
        this.rows = rows;
    }

    public List<RowBean> getRows() {
        return rows;
    }

    public void setEntries(List<RowBean> rows) {
        this.rows = rows;
    }
}
