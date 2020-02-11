package com.Training.String_Class;

public class HashCodeCompare {
	int hash=0;
	 char value[] ;
	public static void main(String [] args)
	{	HashCodeCompare c = new HashCodeCompare();
		String x1 = new String("Krishna");
		String x2 = new String("2015-05-20 13:07:47.0");
		int x=0;
		x= ++x;
		x=0;
		x = x++;
		c.value = x1.toCharArray();
		System.out.println("X1: "+c.hashCode()+" X2 : "+x2.hashCode());
	}

	// String method hashCode method exact implementation done here
    public int hashCode() {
    	this.value = value;
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;
            
            for (int i = 0; i < value.length; ++i) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
}
