package trace;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class trace {

	public static void main(String[] args) {
		String data = null;
		try {		
			File f;
			boolean reverse_mode = false;
			if(args[0].compareTo("-r") == 0)
			{
				f = new File(args[1]);	
				reverse_mode = true;
			} else {
				f = new File(args[0]);
			}
			BufferedReader br = new BufferedReader(new FileReader(f));
			String s;
			StringBuilder sb = new StringBuilder();
			while((s=br.readLine())!=null)
				sb.append(s+"\n");
			br.close();
			data = sb.toString();
			{
				String regx;
				regx ="TRACE";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(data);	
		        if (m.find()) {  
		        	System.out.println("File is already processed");
		        	return;
		        }  
			}		
			{
				String regx;
				regx ="(?m)\\)\\s+^\\{";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(data);	
		        if (m.find()) {  
		        	data = m.replaceAll(")\n{TRACE");  
		        }  
			}
			{
				String regx;
				regx = "(?m)^}(?m)$";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(data);	
		        if (m.find()) {  
		        	data = m.replaceAll("TRACE}");    
		        }  
			}
			{
				String regx;
				regx = "return";
				Pattern p = Pattern.compile(regx);
				Matcher m = p.matcher(data);	
		        if (m.find()) {  
		        	data = m.replaceAll("TRACE return");    
		        }  
			}
			
			data = "#define TRACE {buginf(\"\\n%s %s %d %d\",__FILE__,__FUNCTION__,__LINE__, clock_get_microsecs());}\n"
			+ data;
			System.out.println(data);
			FileWriter outfile=new FileWriter(f);
			outfile.write(data);
			outfile.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

}
