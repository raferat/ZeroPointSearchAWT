import java.awt.*;

public class MLabel extends Component
{
  private String text;
  
  public MLabel ( String s )
  {
    text = s;
  }
  
  public void setText ( String s )
  {
    text = s;
    repaint();
  }
  
  @Override
  public void paint(Graphics g)
  {
    Graphics2D g2d = (Graphics2D) g;
    
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.drawString(text , 4 , getFontMetrics(getFont()).getAscent()/2 + getSize().height/2 );
    
                   
  }
}