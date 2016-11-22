/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mathiasbattistella.vista.jpanels;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import mathiasbattistella.dto.MovimientoMensual;

/**
 *
 * @author user6
 */
public class MiRender extends DefaultTableCellRenderer
{
   @Override
   public Component getTableCellRendererComponent(JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column)
   {
      super.getTableCellRendererComponent(table, value, isSelected, hasFocus, 2, 1);
      
       System.out.println(value.toString() );
      if (value instanceof MovimientoMensual )
      {
         this.setOpaque(true);
         this.setBackground(Color.RED);
         this.setForeground(Color.YELLOW);
      } else {
         // Restaurar los valores por defecto
      }

      return this;
   }
}
