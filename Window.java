import java.awt.*;
import java.awt.event.*;

public class Window extends Frame
{
  private final Dimension size = new Dimension ( 800 , 900 );
  
  private final Dimension panelSize = new Dimension ( 800 , 30 );
  
  private final MParameterPanel linearni      = new MParameterPanel ( 2  , panelSize , true  );
  private final MParameterPanel mocninna      = new MParameterPanel ( 10 , panelSize , true  );
  private final MParameterPanel exponencialni = new MParameterPanel ( 2  , panelSize , true  );
  private final MParameterPanel logaritmicka  = new MParameterPanel ( 4  , panelSize , true  );
  private final MParameterPanel lomena        = new MParameterPanel ( 5  , panelSize , true  );
  
  private final MParameterPanel tangents      = new MParameterPanel ( 1  , panelSize , false );
  private final MParameterPanel other         = new MParameterPanel ( 2  , panelSize , false );
  
  private MParameterPanel currentFunction = linearni;
  private MParameterPanel currentMethod = other;
  
  private final MFunctionDisplay functionDisplay = new MFunctionDisplay ( (double x)->0 );/*(double x)->(x+Math.pow(9,x)) );*/
  
  private final Choice types   = new Choice ();
  private final Choice methods = new Choice ();
  
  private final MParameterPanel geniometricke = new MParameterPanel ( 0  , panelSize , true );
  
  private final Button searchZeroPoints = new Button ( "Najit nulovy bod" );
  
  private final Dialog dialog = new Dialog(this,"Odpoved");
  
  private final MLabel answer = new MLabel("");
  
  static Window instance;
  
  
  private Window ()
  {
    super ( "Hledani nulovych bodu funkci" );
    instance = this;
    setSize ( size );
    setMinimumSize ( size );
    setLocationRelativeTo(null);
    Container base = new Container ();
    base.setSize(((Container)this).getSize());
    dialog.add(answer);
    dialog.setSize(150,60);
    dialog.setLocationRelativeTo(this);
    
    base.setLayout(new MResizeLayout(((Container)this).getSize()));
    
    addWindowListener(new WindowAdapter ()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    
    dialog . addWindowListener(new WindowAdapter ()
    {
      public void windowClosing(WindowEvent e)
      {
        dialog.setVisible(false);
      }
    });
    
    
    types.add("Linearni");
    types.add("Mocninna");
    types.add("Exponencialni");
    types.add("Logaritmicka");
    types.add("Linearni lomena");
    types.add("Geniometricke funkce - (soon)");
    
    
    methods . add ( "Puleni intervalu" );
    methods . add ( "Regula falsi" );
    methods . add ( "Metoda tecen" );
    methods . add ( "Metoda secen" );
    
    methods.addItemListener(new ItemListener()
    {
      public void itemStateChanged ( ItemEvent e )
      {
        removeLayoutComponents (base);
        switch ( methods.getSelectedItem() )
        {
          case "Puleni intervalu":
          case "Regula falsi":
          case "Metoda secen":
            currentMethod = other;
            break;          
          case "Metoda tecen":
            currentMethod = tangents;
            break;
        }
        addLayoutComponents (base);
        pack();
      }
    });
    
    types.addItemListener(new ItemListener()
    {
      public void itemStateChanged ( ItemEvent e )
      {
        removeLayoutComponents (base);
        switch ( types.getSelectedItem() )
        {
          case "Linearni":
            currentFunction = linearni;
            functionDisplay . func = MathematicalFunction.linearni(currentFunction.getParameters());            
            break;
          case "Mocninna":
            currentFunction = mocninna;
            functionDisplay . func = MathematicalFunction.mocninna(currentFunction.getParameters());
            break;
          case "Exponencialni":
            currentFunction = exponencialni;
            functionDisplay . func = MathematicalFunction.exponencialni(currentFunction.getParameters());
            break;
          case "Logaritmicka":
            currentFunction = logaritmicka;
            functionDisplay . func = MathematicalFunction.logaritmicka(currentFunction.getParameters());
            break;
          case "Linearni lomena":
            currentFunction = lomena;
            functionDisplay . func = MathematicalFunction.linearniLomena(currentFunction.getParameters());
            break;
          case "Geniometricke funkce - (soon)":
            currentFunction = geniometricke; 
            break;
        }
        
      
        addLayoutComponents (base);
        types.requestFocus();
      }
    });
    
    searchZeroPoints.addActionListener(this::search);
    base . add ( types   , new MResizeLayout.Constraint ( 1   , 1   , 799 , 20 ) );
    methods.setSize(150,20);
    addLayoutComponents (base);
    add ( base );
  }
  
  public void updateFunction ()
  {
    if ( currentFunction.getParameters() != null )
    {
      switch ( types.getSelectedItem() )
      {
        case "Linearni":
          functionDisplay . func = MathematicalFunction.linearni(currentFunction.getParameters());
          break;
        case "Mocninna":
          functionDisplay . func = MathematicalFunction.mocninna(currentFunction.getParameters());
          break;
        case "Exponencialni":
          functionDisplay . func = MathematicalFunction.exponencialni(currentFunction.getParameters());
          break;
        case "Logaritmicka":
          functionDisplay . func = MathematicalFunction.logaritmicka(currentFunction.getParameters());
          break;
        case "Linearni lomena":
          functionDisplay . func = MathematicalFunction.linearniLomena(currentFunction.getParameters());
          break;
        case "Geniometricke funkce - (soon)":
          break;
      }
      
    }
    else 
      functionDisplay . func . correct = false;
    
    functionDisplay.repaint();
    
  }
  
  private void addLayoutComponents ( Container base )
  {
    base . add ( currentFunction  , new MResizeLayout.Constraint ( 1   , 30  , 799 , panelSize.height * currentFunction.parameters      ) );
    base . add ( methods          , new MResizeLayout.Constraint ( 325 , panelSize.height * currentFunction.parameters + 40  , 150 , 20 ) );
    base . add ( currentMethod    , new MResizeLayout.Constraint ( 1   , panelSize.height * currentFunction.parameters + 40 + methods.getSize().height + 10  , 799 , panelSize.height * currentMethod.parameters ) );
    base . add ( searchZeroPoints , new MResizeLayout.Constraint ( 325 , panelSize.height * currentFunction.parameters + 40 + methods.getSize().height + 10 + panelSize.height * currentMethod.parameters + 10 , 150 , 30 ) );
    base . add ( functionDisplay  , new MResizeLayout.Constraint ( 1 ,
                                                                  panelSize.height * currentFunction.parameters + 40 + methods.getSize().height + 10 + panelSize.height * currentMethod.parameters + 10 + 30 + 10, 
                                                                  799 , 
                                                                  base.getSize().height - (panelSize.height * currentFunction.parameters + 40 + methods.getSize().height + 10 + panelSize.height * currentMethod.parameters + 10 + 30 + 10)));
    currentFunction.setVisible(true);
    currentMethod.setVisible(true);
    functionDisplay.repaint();
    pack();
  }
  
  private void removeLayoutComponents ( Container base )
  {
    currentFunction.setVisible(false);
    currentMethod.setVisible(false);
    base . remove ( currentFunction );
    base . remove ( methods );
    base . remove ( currentMethod );
    base . remove ( searchZeroPoints );
    base . remove ( functionDisplay );
  }
  
  private void search ( ActionEvent e )
  {
    double result = 0;
    double[] args = other.getParameters();
    switch ( methods.getSelectedItem() )
    {
      case "Puleni intervalu":        
        result = functionDisplay . func . bisection ( args[0] , args[1] );
        break;
      case "Regula falsi":
        result = functionDisplay . func . chords ( args[0] , args[1] );
        break;
      case "Metoda secen":
        result = functionDisplay . func . secants ( args[0] , args[1] );
        break;          
      case "Metoda tecen":
        result = functionDisplay . func . tangents ( tangents.getParameters()[0] );
        break;
    }
    
    answer.setText(""+result);
    dialog.setVisible(true);
    System.out.println(result);
  }
  
  public static void main (String[] args )
  {
    System . setProperty ( "awt.useSystemAAFontSettings" , "on" );
    new Window().setVisible(true);
  }
}