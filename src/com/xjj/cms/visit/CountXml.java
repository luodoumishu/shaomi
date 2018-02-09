package com.xjj.cms.visit;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.exolab.castor.xml.Marshaller; 
import org.exolab.castor.xml.Unmarshaller;

public class CountXml {
	private String path = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
	private String secendPath = path.substring(0, path.lastIndexOf("/")); 
	private String fileName = secendPath.substring(0,secendPath.lastIndexOf("/")) + "/xmlcount.xml";
    private static CountObjectInf obj=null; 
    private static CountXml instance=null; 
    
     public static CountXml getInstance(){ 
         if(instance==null){ 
                 instance=new CountXml(); 
          } 
          return instance; 
      } 

private CountXml() { 
     try{ 
         obj = read(fileName); 
     }catch(Exception e){ 
          System.out.println(e); 
     } 
      

} 

 public int getTotalCount(){ 
     return obj.getTotalCount(); 
 } 

 public int getDayCount() { 
return obj.getDayCount(); 
} 


public int getMonthCount() { 
return obj.getMonthCount(); 
} 


public int getWeekCount() { 
return obj.getWeekCount(); 
} 


public int getYearCount() { 
return obj.getYearCount(); 
} 

//public static void main(String[] args)throws Exception  {
//	File file  = new File("c:/xmlcount.xml");        
//	SAXBuilder builder = new SAXBuilder();        
//	Document doc = builder.build(file);        
//	Element root =doc.getRootElement();        
//	System.out.println("month-count:"+root.getAttributeValue("month-count"));        
//	System.out.println("total-count:"+root.getAttributeValue("total-count"));        
//	System.out.println("temp-count:"+root.getAttributeValue("temp-count"));        
//	System.out.println("week-count:"+root.getAttributeValue("week-count"));        
//	System.out.println("day-count:"+root.getAttributeValue("day-count"));        
//	System.out.println("year-count:"+root.getAttributeValue("year-count"));    
//}


public synchronized void addcount(Date da){//比较日期增加计数 

		
         if (new SimpleDateFormat("yyyy-MM-dd").format(this.obj.date) 
                       .equals(new SimpleDateFormat("yyyy-MM-dd").format(da))) 
            this.obj.setDayCount(this.obj.getDayCount() + 1); 
        else 
            this.obj.setDayCount(1); 

        if (new SimpleDateFormat("yyyy-MM").format(this.obj.date) 
                        .equals(new SimpleDateFormat("yyyy-MM").format(da))) 
            this.obj.setMonthCount(this.obj.getMonthCount() + 1); 
        else 
            obj.setMonthCount(1); 

        Calendar ca = Calendar.getInstance(); 
        ca.setTime(da); 
        ca.setFirstDayOfWeek(Calendar.MONDAY); 

        if (ca.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && !new SimpleDateFormat("yyyy-MM-dd").format

(this.obj.date).equals(new SimpleDateFormat("yyyy-MM-dd").format(da))) 
             obj.setWeekCount(1); 
        else 
             obj.setWeekCount(obj.getWeekCount() + 1); 

        if (new SimpleDateFormat("yyyy").format(this.obj.date) 
                     .equals(new SimpleDateFormat("yyyy").format(da))) 
            this.obj.setYearCount(this.obj.getYearCount() + 1); 
       else 
            obj.setYearCount(1); 
       obj.setDate(da); 

obj.setTotalCount(obj.getTotalCount()+1);  
       obj.setTempCount(obj.getTempCount()+1); 
       if(obj.getTempCount()>=2){//只有当临时访问量大于等于20时才保存一次 
                 obj.setTempCount(0);//临时计数器置0 
                  write(fileName); 
                   
       } 
    } 

private static void write(String fileName) { 
            try { 
                    FileWriter writer = new FileWriter(fileName); 
                    System.out.println("start writer ..."+writer.toString());
                    
                    Marshaller.marshal(obj, writer);
                    
                    System.out.println("end writer ..."+writer.toString());
                    writer.close(); 
            } catch (Exception e) { 
                    System.out.println(e); 

            } 
    } 

    private CountObjectInf read(String fileName) throws Exception { 
            FileReader reader = new FileReader(fileName); 
            CountObjectInf result = (CountObjectInf)  

            Unmarshaller.unmarshal(CountObjectInf.class, reader); 
            
            reader.close(); 
            return result; 
     } 
}
