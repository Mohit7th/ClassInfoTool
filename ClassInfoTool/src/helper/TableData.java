package helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableData {
private StringProperty tableColValue;
	
	public TableData(String tableColVal){
		this.tableColValue = new SimpleStringProperty (tableColVal);
			
	}
	
	//userId property
	public void setTableColValue(String uid){
		tableColValue.set(uid);
	}
	public String getTableColValue(){
		return tableColValue.get();
	}
	
}
