import java.util.ArrayList;

import java.awt.LayoutManager2;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Button;

public class MResizeLayout implements LayoutManager2
{
  private ArrayList<Component> components = new ArrayList<>();
  private ArrayList<Constraint> constraints = new ArrayList<>();
  
  private int minWidth;
  private int minHeight;
  
  public MResizeLayout ( int width , int height )
  {
    minWidth = width;
    minHeight = height;
  }
  
  public MResizeLayout ( Dimension size )
  {
    minWidth = size.width;
    minHeight = size.height;
  }

//=================================================================================================
  
  public void addLayoutComponent ( String name , Component comp )
  {
    components . add ( comp );
    constraints . add ( new Constraint ( comp . getLocation() . x , comp . getLocation() . y , comp . getSize () . width , comp . getSize () . height ) );
  }
  
  public void addLayoutComponent ( Component comp , Object constraint )
  {
    if ( constraint instanceof Constraint )
    {
      components . add ( comp );
      constraints . add ( (Constraint) constraint );
    }
  }

//=================================================================================================
  
  public void layoutContainer ( Container parent )
  {
    for ( int i = 0 ; i < components . size () ; i ++ )
    {
      Component component = components . get ( i );
      Constraint constraint = constraints . get ( i );
      
      /*System.out.println ( component );
      System.out.println ( constraint );*/
      
      component . setSize ( new Dimension ((int)( parent . getWidth() * constraint . d . width / minWidth ) - 1 , (int)( parent . getHeight() * constraint . d . height / minHeight ) - 1 ) );
      component . setMinimumSize ( new Dimension ((int)( parent . getWidth() * constraint . d . width / minWidth ) - 1 , (int)( parent . getHeight() * constraint . d . height / minHeight ) - 1 ) );
      component . setPreferredSize ( new Dimension ((int)( parent . getWidth() * constraint . d . width / minWidth ) - 1 , (int)( parent . getHeight() * constraint . d . height / minHeight ) - 1 ) );
      component . setMaximumSize ( new Dimension ((int)( parent . getWidth() * constraint . d . width / minWidth ) - 1 , (int)( parent . getHeight() * constraint . d . height / minHeight ) - 1 ) );
      
      component . setLocation ( (int)( parent . getWidth() * constraint . p . x / minWidth ) , (int)( parent . getHeight() * constraint . p . y / minHeight ) );
      
      parent . add ( component );
      //System.out.println(component.getLocation() + " " + component.getSize() +" " + component );
    }
    
    //System.out.printf ( "Components: %d, Constraints: %d\n" , components . size () , constraints . size () );
  }
  
//=================================================================================================  

  public void removeLayoutComponent ( Component comp )
  {
  }
  
  public void invalidateLayout ( Container target )
  {
  }

//=================================================================================================  
  
  public Dimension minimumLayoutSize ( Container parent )
  {
    return new Dimension ( minWidth , minHeight );
  }
  
  public Dimension preferredLayoutSize ( Container parent )
  {
    return parent . getSize();
  }
  
  public float 	getLayoutAlignmentX ( Container target )
  {
    return 0.0f;
  }
  
  public float 	getLayoutAlignmentY ( Container target )
  {
    return 0.0f;
  }
  
  public Dimension 	maximumLayoutSize ( Container target )
  {
    return target . getMaximumSize();
  }
  

//=================================================================================================
  
  private class Pair
  {
    private Component component;
    private Constraint constraint;
    
    private Pair ( Component component , Constraint constraint )
    {
      this . component = component;
      this . constraint = constraint;
    }
  }
  
  public static class Constraint
  {
    private Dimension d;
    private Point p;
    
    public Constraint ( int x , int y , int width , int height )
    {
      d = new Dimension ( width , height );
      p = new Point ( x , y );
    }
    
    void setX ( int x )
    {
      p . x = x;
    }
    
    void setY ( int y )
    {
      p . y = y;
    }
    
    void setWidth ( int w )
    {
      d . width = w;
    }
    
    void setHeight ( int h )
    {
      d . height = h;
    }
    
    @Override
    public String toString()
    {
      return "x: " + p.x + ", y: " + p.y + ", w: " + d.width + ", h: " + d.height;
    }
  }
}