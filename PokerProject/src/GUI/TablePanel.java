package GUI;

import DateBase.GameData;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * @author kory
 */
public class TablePanel extends JPanel {

    private JTable table;
    private GameTableModel tm;

    public TablePanel() {
        super();
        tm = new GameTableModel();
        table = new JTable(tm) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new CustomTableCellRenderer(tm.getRowTotal());
            }

        };

        // table.setDefaultRenderer(Object.class, cr);
        table.setIntercellSpacing(new Dimension(0, 0));
        this.add(new JScrollPane(table));
    }

    public void refresh() {
        tm.fireTableDataChanged();
    }

    public void addData(List<GameData> gd) {
        tm.setdata(gd);
    }

}

class CustomTableCellRenderer extends DefaultTableCellRenderer {

    private int rowTotal = 0;
    
    public CustomTableCellRenderer(int rowTotal){
        super();
        this.rowTotal = rowTotal - 1;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        JComponent cell = (JComponent) super.getTableCellRendererComponent(
                table, obj, isSelected, hasFocus, row, column);
        Border b = BorderFactory.createCompoundBorder();

        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
        
        if(column == 5){
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));
        }
        
        if(row == this.rowTotal){
            b = BorderFactory.createCompoundBorder(b, BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        }
        
        cell.setBorder(b);
        return cell;
    }
}
