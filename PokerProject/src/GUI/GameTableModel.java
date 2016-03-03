package GUI;

import DateBase.GameData;
import java.util.*;
import javax.swing.table.AbstractTableModel;

/**
 * @author kory
 */
public class GameTableModel extends AbstractTableModel {

    private List<GameData> data;

    private String colHeading[] = {"ID", "Name", "Num Players", "Small", "Big", "Avg"};

    public GameTableModel() {
        data = new ArrayList<>();
    }
    
    @Override
    public String getColumnName(int colnum) {
        return colHeading[colnum];
    }
    
    public void setdata(List<GameData> data) {
        this.data.addAll(data);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        GameData gd = data.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return gd.getId();
            case 1:
                return gd.getName();
            case 2:
                return gd.getNumPlayers();
            case 3:
                return gd.getSmall();
            case 4:
                return gd.getBig();
            case 5:
                return gd.getAvg();

        }
        return null;
    }

    int getRowTotal() {
        return data.size();
    }

}
