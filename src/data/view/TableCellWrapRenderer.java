package data.view;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
/**
 * Wraps the table.
 * @author Camron
 * @version 1.0
 */
public class TableCellWrapRenderer extends JTextArea implements TableCellRenderer
{
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		this.setText(value.toString());
		this.setWrapStyleWord(true);
		this.setLineWrap(true);
		int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
		int textPixelLength = this.getFontMetrics(this.getFont()).stringWidth(this.getText());
		TableColumn columnSelected = table.getColumnModel().getColumn(column);
		int lines = (textPixelLength / (columnSelected.getWidth())) + 1;
		int height = fontHeight * lines;
		table.setRowHeight(row, height);
		return this;
	}
}