package Client;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Resources {
	public static int versionID = 1;
	private static Font font = null;
	
	public Resources() {
		
	}
	
	public static Font font() {
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,Resources.class.getResource("montserrat.ttf").openStream());
			GraphicsEnvironment genv = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			genv.registerFont(font);
			font = font.deriveFont(9.49f);
			return font;
		} catch (Exception ex) {
		}
		return null;
	}

	public static class Uppercase extends DocumentFilter {
	  public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
		  fb.insertString(offset, text.toUpperCase(), attr);
	  }
	  public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {
		  fb.replace(offset, length, text.toUpperCase(), attrs);
	  }
	} 
}
