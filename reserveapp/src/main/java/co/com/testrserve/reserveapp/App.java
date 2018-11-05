package co.com.testrserve.reserveapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPDouble;
import org.rosuda.REngine.REXPExpressionVector;
import org.rosuda.REngine.REXPGenericVector;
import org.rosuda.REngine.REXPInteger;
import org.rosuda.REngine.REXPList;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REXPString;
import org.rosuda.REngine.REXPVector;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
    		
    		String[] ciudades= {"Alabama",	"Alaska",	"Arizona",	"Arkansas",	"California",	"Colorado",	"Connecticut",	"Delaware",	"Florida",	"Georgia",	"Hawaii",	"Idaho",	"Illinois",	"Indiana",	"Iowa",	"Kansas",	
    				"Kentucky",	"Louisiana",	"Maine",	"Maryland",	"Massachusetts",	"Michigan",	
    				"Minnesota",	"Mississippi",	"Missouri",	"Montana",	"Nebraska",	"Nevada",	
    				"New Hampshire",	"New Jersey",	"New Mexico",	"New York",	"North Carolina",	
    				"North Dakota",	"Ohio",	"Oklahoma",	"Oregon",	"Pennsylvania",	"Rhode Island",	
    				"South Carolina",	"South Dakota",	"Tennessee",	"Texas",	"Utah",	"Vermont",	
    				"Virginia",	"Washington",	"West Virginia",	"Wisconsin",	"Wyoming"};
    		
    		double[] murder = {13.2, 10, 8.1, 8.8, 9, 7.9, 3.3,	5.9,15.4, 17.4, 5.3, 2.6, 10.4,	7.2, 2.2, 6, 9.7,
    				15.4, 2.1, 11.3, 4.4, 12.1, 2.7, 16.1, 9, 6, 4.3, 12.2, 2.1, 7.4, 11.4,	11.1, 13, 0.8, 7.3,
    				6.6, 4.9, 6.3, 3.4, 14.4, 3.8, 13.2, 12.7, 3.2, 2.2, 8.5, 4, 5.7, 2.6, 6.8};
    		
    		int[] assault={236,	263,	294,	190,	276,	204,	110,	238,
    				335,	211,	46,	120,	249,	113,	56,	115,	109,	
    				249,	83,	300,	149,	255,	72,	259,	178,	109,	
    				102,	252,	57,	159,	285,	254,	337,	45,	120,	
    				151,	159,	106,	174,	279,	86,	188,	201,	120,	
    				48,	156,	145,	81,	53,	161};
    		int[] urbanPop= {58,	48,	80,	50,	91,	78,	77,	72,	80,	60,	83,	54,	83,	65,	57,	
    				66,	52,	66,	51,	67,	85,	74,	66,	44,	70,	53,	62,	81,	56,	89,	70,	
    				86,	45,	44,	75,	68,	67,	72,	87,	48,	45,	59,	80,	80,	32,	63,	73,	
    				39,	66,	60};
    		double[] rape= {21.2,	44.5,	31,	19.5,	40.6,	38.7,	11.1,	15.8,	
    				31.9,	25.8,	20.2,	14.2,	24,	21,	11.3,	18,	16.3,	22.2,	
    				7.8,	27.8,	16.3,	35.1,	14.9,	17.1,	28.2,	16.4,	
    				16.5,	46,	9.5,	18.8,	32.1,	26.1,	16.1,	7.3,	21.4,	
    				20,	29.3,	14.9,	8.3,	22.5,	12.8,	26.9,	25.5,	22.9,	
    				11.2,	20.7,	26.2,	9.3,	10.8,	15.6};
    		
    		String[] names= {"Murder","Assault","UrbanPop","Rape"};
    		List<REXP> registers = new ArrayList<REXP>();
    		registers.add(new REXPDouble(murder));
    		registers.add(new REXPInteger(assault));
    		registers.add(new REXPInteger(urbanPop));
    		registers.add(new REXPDouble(rape));
    		RList list = new RList(registers,names);
    		REXP datframe_a= REXP.createDataFrame(list);
    		REXP dataFrame = createDataFrame(list, ciudades);
    		REXP res;
    		String distanceMeasure = "'euclidean'";
    		int centers = 4;
    		int iter_max =25;
    		int n_start =1;
			RConnection con = new RConnection("localhost", 6311);
			con.eval("source('C:\\\\dev\\\\R\\\\workspace\\\\ClusterServiceScripts\\\\KMeansScript.R')");
			
			con.assign("dfa", dataFrame);
			REXP rt=con.eval("try (scale(dfu), silent = TRUE)");
			con.eval("try (asignarDataFrame(), silent = TRUE)");
			con.eval("try ( asignarVariables("+centers+","+iter_max+","+n_start+","+distanceMeasure+"), silent = TRUE)");
			res=con.eval("try (ejecutarKmeans(), silent = TRUE)");
			System.out.println(res.asString());
			System.out.println(res.length());
		} catch (RserveException e) {
			System.out.println("no conection in this program");
			e.printStackTrace();
		} catch (REXPMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println( "Hello World!" );
    }
    
    public static REXP createDataFrame(RList l, String[] rownames) {
    	return  new REXPGenericVector(l,
				  new REXPList(
						new RList(
							   new REXP[] {
								   new REXPString("data.frame"),
								   new REXPString(l.keys()),
								   new REXPString(rownames)
							   },
							   new String[] {
								   "class",
								   "names",
								   "row.names"
							   })));
    }
}
