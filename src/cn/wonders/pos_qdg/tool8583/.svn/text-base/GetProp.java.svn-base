package cn.wonders.pos_qdg.tool8583;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class GetProp{
	public static Properties getProp(){
		Properties p = new Properties();
		InputStream is = GetProp.class.getResourceAsStream("/conf/config.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}
}