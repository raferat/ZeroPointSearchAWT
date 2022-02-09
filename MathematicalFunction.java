@FunctionalInterface
public interface MathematicalFunction
{
  public double limitZero = 0.0000001d;
  public double epsilon = 0.0001d;
  
  double function ( double x );
  
  public default double f ( double x )
  {
    return function(x)*60;
  }
  
  public default double derivation ( double x )
  {
    return ( function ( x + limitZero ) - function(x) ) / limitZero;
  }
  
  public default double derivation ( double x , double functionX )
  {
    return ( function ( x + limitZero ) - functionX ) / limitZero;
  }
  
  public default double tangents ( double x )
  {
    
    double y = 0;
    while ( Math.abs( y = function(x)) > epsilon )
    {
      x = x - y / derivation(x , y);
      
    }
    
    return x;
  }
  
  public default double bisection ( double start , double end )
  {
    double middle;
    do
    {
      middle = (end - start)/2 + start;
      
      if ( function(middle) * function(end) < 0 )
        start = middle;
      else if ( function(middle) * function(start) < 0 )
        end = middle;
      
    } while ( Math.abs( function(middle) ) > epsilon );
    
    
    return middle;
  }
  
  public default double chords ( double start , double end )
  {
    double middle;
    do
    {
      middle = start-(function(start)*(end-start))/(function(end)-function(start));
      
      if ( function(middle) * function(end) < 0 )
        start = middle;
      else if ( function(middle) * function(start) < 0 )
        end = middle;
      
    } while ( Math.abs( function(middle) ) > epsilon );
    
    
    return middle;
  }
  
  public default double secants ( double first , double second )
  {
    double third;
    
    
    do
    {
      third = second - ( second - first )/(function(second)-function(first))*function(second);
      
      first = second;
      second = third;
      
    } while ( Math.abs( function(third) ) > epsilon );
    
    
    return third;
  }
  
  public static MathematicalFunction linearni ( double... args )
  {
    return (double x)-> args[0]*x+args[1];
  }
  
  public static MathematicalFunction mocninna ( double... args )
  {
    return (double x) ->
    { 
      double result = 0;
      for ( int i = 0 ; i < args.length ; i ++ )
      {
        result += args[i] * Math.pow ( x , i );
      }
      
      return result;
    };
  }
  
  public static MathematicalFunction exponencialni ( double... args )
  {
    return (double x) ->
    { 
      return Math.pow ( args[0] , x ) + args[1];
    };
  }
  
  private static double log ( double base , double number )
  {
    return Math.log(number)/Math.log(base);
  }
  
  public static MathematicalFunction logaritmicka ( double... a )
  {
    return (double x) -> a[0]*log(a[1],a[2]*x)+a[3];    
  }
  
  public static MathematicalFunction linearniLomena ( double... a )
  {
    return (double x) -> (a[0]*x+a[1])/(a[2]*x+a[3])+a[4];    
  }
    
}