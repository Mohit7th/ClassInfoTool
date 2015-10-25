package helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableData {
	private StringProperty colValue;

	public TableData(String tableColVal) {
		this.colValue = new SimpleStringProperty(tableColVal);

	}

	// colValue property
	public void setTableColValue(String uid) {
		colValue.set(uid);
	}

	public String getTableColValue() {
		return colValue.get();
	}

	
	//newly added 25/10/2015
	private StringProperty propCol, propColValue;

	public TableData(String propCol, String propColValue) {
		System.out.println("yes"+ propCol+" "+propColValue );
		this.propCol = new SimpleStringProperty(propCol);
		this.propColValue = new SimpleStringProperty(propColValue);
	}

	// propCol property
	public void setPropCol(String propVal) {
		propCol.set(propVal);
	}

	public String getPropCol() {
		return propCol.get();
	}

	// propColVal property
	public void setPropColValue(String propColVal) {
		propColValue.set(propColVal);
	}

	public String getPropColValue() {
		return propColValue.get();
	}
}
