import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class MFunctionDisplay extends Canvas
{
  public MathematicalFunction func;
  
  public MFunctionDisplay ( MathematicalFunction f )
  {
    super();
    func = f;
  }
  
  @Override
  public void paint ( Graphics g1 )
  {
    g1.clearRect(0,0,getSize() . width,getSize() . height);
    Dimension d = getSize ();
    Image img = new BufferedImage ( getSize() . width , getSize() . height , BufferedImage . TYPE_INT_ARGB );
    Graphics g = img . getGraphics ();
    
    Graphics2D g2d = (Graphics2D) g;
    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    
    
    drawGuidLines(g);
    if ( func . correct )
      drawFunction(g);
    
    
    
    g1.drawImage(img,0,0,null);
  }
  
  private void drawGuidLines ( Graphics g )
  {
    g.setColor(Color.BLACK);
    g . drawLine ( 0 , getSize().height/2 , getSize().width , getSize().height/2 );
    g . drawLine ( getSize().width / 2 , 0 , getSize().width / 2 , getSize().height );
  }
  
  
  private void drawFunction ( Graphics g )
  {
    
    double scale = 60d;
    
    int x = -1;
    int y = getSize().height - ((int) func.f(x*1d / scale - getSize().width / (2d * scale)) + getSize().height/2);
    
    g.setColor ( Color.RED );
    for ( int i = 0 ; i < getSize().width ; i ++ )
    {
      int newX = x + 1;
      int newY = getSize().height - ((int) func.f(newX*1d / scale - getSize().width / (2d * scale)) + getSize().height/2);
      
      if ( ! ( (int) func.f(newX*1d / scale - getSize().width / (2d * scale)) > getSize().height || (int) func.f(newX*1d / scale - getSize().width / (2d * scale)) + getSize().height/2 < 0 ) )
      {        
        g.drawLine ( x , y , newX , newY );
      }
      
      x = newX;
      y = newY;
    }
  }
  
}