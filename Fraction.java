
import java.util.Objects;

/**
 *
 * @author amin
 */
public class Fraction extends Number implements Comparable<Fraction> {

    private final Integer numerator;
    private final Integer denumerator;
    final int signum;

    public Fraction(Integer numerator, Integer denumerator) {
       
        if (denumerator == 0)
            throw new ArithmeticException("division by zero, the denominator is zero");        
        
        if (denumerator<0)
            numerator=numerator * -1;
        
        if (numerator<0)
            signum = -1;
        else if(numerator>0)
            signum=1;
        else
            signum=0;                   
        
        int lcm = lcm(numerator, denumerator);
        
        numerator = numerator / lcm;
        denumerator = denumerator / lcm;
        
        this.numerator = numerator;
        this.denumerator = denumerator;
        
    }

    public Fraction(String frac) {
        if (frac == null) {
            throw new IllegalArgumentException("Null argument is not allowed");
        }
        int slash_pos = frac.indexOf(frac);
        
        
        Integer num;
        num = new Integer(frac.substring(0, slash_pos - 1));
        Integer den;
        den = new Integer(frac.substring(slash_pos + 1, frac.length()-1));
        Fraction fr = new Fraction(num, den);
        this.numerator = fr.numerator;
        this.denumerator = fr.denumerator;
        this.signum = fr.signum;        
    }
    
    
    public Fraction(Integer num)
    {
        Fraction fr = new Fraction(num, 1);
        this.numerator = fr.numerator;
        this.denumerator = fr.denumerator;
        this.signum = fr.signum;  
    }
    
    
    public Fraction add (Fraction val)
    {
        int lcm;
        
        lcm = lcm(this.denumerator, val.denumerator);
        
        Fraction frac = new Fraction(this.numerator * lcm / this.denumerator + 
                val.numerator * lcm / val.denumerator, lcm);
        return frac;
    }
    
    public Fraction subtract (Fraction val)
    {
        int lcm;
        
        lcm = lcm(this.denumerator, val.denumerator);
        
        Fraction frac = new Fraction(this.numerator * lcm / this.denumerator - 
                val.numerator * lcm / val.denumerator, lcm);
        return frac;
    }
    
    public Fraction multiply (Fraction val)
    {
        
        Fraction frac = new Fraction(this.numerator * val.numerator,
                this.denumerator * val.denumerator);
        return frac;
    }
    
    public Fraction divide (Fraction val)
    {
        Fraction frac = new Fraction(this.numerator * val.denumerator,
                this.denumerator * val.numerator);
        return frac;
    }
    
    private Integer gcd(Integer a, Integer b) {
        if (a == 0) {
            return b;
        } else if (b == 0) {
            return a;
        } else if (Objects.equals(a, b)) {
            return a;
        } else if (a == 1 || b == 1) {
            return 1;
        } else if (a % 2 == 0 && b % 2 == 0) {
            return 2 * gcd(a / 2, b / 2);
        } else if (a % 2 == 0 && b % 2 != 0) {
            return gcd(a / 2, b);
        } else if (a % 2 != 0 && b % 2 == 0) {
            return gcd(a, b / 2);
        } else if (a % 2 != 0 && b % 2 != 0 && a > b) {
            return gcd((a - b) / 2, b);
        } else if (a % 2 != 0 && b % 2 != 0 && a < b) {
            return gcd((b - a) / 2, a);
        }
        return 1;
    }
    
    private Integer lcm(Integer a, Integer b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    private Double toDouble() {
        return new Double(numerator) / new Double(denumerator);
    }

    @Override
    public int compareTo(Fraction o) {
        if(signum != o.signum)
            return signum - o.signum;
        
        if (Objects.equals(this.denumerator, o.denumerator))
            return this.numerator.compareTo(o.numerator);
        
        Integer lcm;
        lcm = lcm(this.denumerator, o.denumerator);
        Integer num;
        num = this.numerator*lcm/this.denumerator;
        Integer comp;
        comp = o.numerator*lcm/o.denumerator;
        return (num.compareTo(comp));
    }

    @Override
    public int intValue() {
        return toDouble().intValue();
    }

    @Override
    public long longValue() {
        return toDouble().longValue();
    }

    @Override
    public float floatValue() {
        return toDouble().floatValue();
    }

    @Override
    public double doubleValue() {
        return toDouble();
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denumerator;
    }
    
    
}
