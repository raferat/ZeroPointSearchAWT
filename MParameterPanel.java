import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;

public class MParameterPanel extends Panel
{
  private static Font f = new Font ( Font.SERIF , Font.BOLD , 50 );
  private final ArrayList<Pair> fields = new ArrayList<>();
  private final ArrayList<TextField> errorTextField = new ArrayList<>();
  
  public final int parameters;
  
  public MParameterPanel ( int parameters , Dimension sizeEach , boolean functionParameters )
  {
    super();
    this . parameters = parameters;
    setSize ( sizeEach . width , sizeEach . height * parameters );
    setLayout ( new MResizeLayout(getSize()) );    
    FocusListener focus = new FocusAdapter ()
    {
      @Override
      public void focusGained ( FocusEvent e )
      {
        TextField f = (TextField) e . getSource();
        if ( f.getText().equals("0") )
          f.setText("");
        
        f.setForeground(Color.BLACK);
        
      }
      
      @Override
      public void focusLost ( FocusEvent e )
      {
        TextField f = (TextField) e . getSource();
        if ( f.getText().equals("") )
        {
          f.setForeground(Color.GRAY);
          f.setText("0");
        }
        
      }
    };
    
    TextListener listener = (TextEvent e)->
    {
      TextField d = (TextField) e.getSource();
      String n = d.getText();
      boolean match = n . matches ( "^-?[0-9]+(\\.[0-9]+)?([eE][+-]?[0-9]+)?$" );
      //String m = n . replaceAll ( "[^0123456789.-]" , "" ); //-5-9-7  "^-?[0-9]+(\\.[0-9]+)?([eE][+-]?[0-9]+)?$"
      if ( ! match )
      {
        d.setBackground(Color.RED);
        System.out.println ( "Error" );
        if (! errorTextField . contains ( d ) )
          errorTextField.add(d);
      }
      else if ( match && errorTextField . contains ( d ) )
      {
        errorTextField . remove ( d );
        d.setBackground(Color.WHITE);
      }
      
    };
    
    for ( int i = 0 ; i < parameters ; i ++ )
    {
      MLabel l = new MLabel ((char)('A'+i)+":");
      TextField d = new TextField ("0");
      d.setForeground(Color.GRAY);
      d.addFocusListener(focus);
      d.addTextListener(listener);
      Pair p;
      if ( functionParameters )
      {
        p = new FunctionParameterPair(sizeEach , l , d );
        d.addTextListener(this::textListener);
      }
      else
        p = new SearchParameterPair(sizeEach , l , d );
      add ( p , new MResizeLayout.Constraint(1 , (sizeEach.height*i) + 1 , sizeEach.width -1 , sizeEach.height) );
      fields.add(p);
    }
    
  }
  
  public void textListener ( TextEvent e )
  {
    Window.instance.updateFunction();
  }
  
  public double[] getParameters()
  {
    if ( errorTextField . size () != 0 ) return null;
    
    double[] result = new double[fields.size()];
    for ( int i = 0 ; i < fields.size() ; i ++ )
    {
      Pair p = fields.get(i);
      String s = p.t.getText();
      if ( s.length() == 0 )
        s+= '0';
        
      result[i] = Double.parseDouble(s);
    }
    
    
    return result;
  }
  
  private static abstract class Pair extends Panel
  {
    final Component l;
    final TextField t;
    
    Pair ( Dimension size, Component l , TextField t )
    {
      super ();
      setLayout ( new MResizeLayout(size) );
      setSize(size);
      this . l = l;
      this . t = t;
    }
  }
  
  private static class FunctionParameterPair extends Pair
  {
    FunctionParameterPair ( Dimension size, Component l , TextField t )
    {
      super (size,l,t);
      add ( l , new MResizeLayout.Constraint( 1                , 1  , (size.width/3)                  , size.height));
      add ( t , new MResizeLayout.Constraint( (size.width/5)*2 , 1  , size . width - (size.width/5)*2 , size.height));      
    }
  }
  
  private static class SearchParameterPair extends Pair
  {
    SearchParameterPair ( Dimension size, Component l , TextField t )
    {
      super (size,l,t);
      
      Dimension label = new Dimension(20,0);
      Dimension textField = new Dimension(150,0);
      
      int gab = 5;
      
      int labelX = (size.width / 2) - (label.width + gab + textField.width)/2;
      int textFieldX = labelX + label.width + gab;
      
      add ( l , new MResizeLayout.Constraint( labelX     , 1 , label.width     , size.height));
      add ( t , new MResizeLayout.Constraint( textFieldX , 1 , textField.width , size.height));      
    }
  }
}