package Model;

public enum Filter 
{
    RED("RED"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    GRAYSCALE("GRAYSCALE"),
    NEGATIVE("NEGATIVE");
	
    Filter(String label) 
    {
	this.label = label;
    }
    private final String label;
    public String getLabel() 
    {
	return label;
    }
}
