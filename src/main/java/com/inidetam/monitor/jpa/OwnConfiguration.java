package com.inidetam.monitor.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="configunidadpropia")
public class OwnConfiguration {
	@Id
    private Long iLocalStationId;
	private String szOwnShipName;
    private String szOwnShipNumeral;
    private String szOwnShipCountryName;
    private String szOwnShipCountryCode;
    private Long iLastAssignedSerialNumber;
    
	public Long getiLocalStationId() {
		return iLocalStationId;
	}
	public void setiLocalStationId(Long iLocalStationId) {
		this.iLocalStationId = iLocalStationId;
	}
	public String getSzOwnShipName() {
		return szOwnShipName;
	}
	public void setSzOwnShipName(String szOwnShipName) {
		this.szOwnShipName = szOwnShipName;
	}
	public String getSzOwnShipNumeral() {
		return szOwnShipNumeral;
	}
	public void setSzOwnShipNumeral(String szOwnShipNumeral) {
		this.szOwnShipNumeral = szOwnShipNumeral;
	}
	public String getSzOwnShipCountryName() {
		return szOwnShipCountryName;
	}
	public void setSzOwnShipCountryName(String szOwnShipCountryName) {
		this.szOwnShipCountryName = szOwnShipCountryName;
	}
	public String getSzOwnShipCountryCode() {
		return szOwnShipCountryCode;
	}
	public void setSzOwnShipCountryCode(String szOwnShipCountryCode) {
		this.szOwnShipCountryCode = szOwnShipCountryCode;
	}
	public Long getiLastAssignedSerialNumber() {
		return iLastAssignedSerialNumber;
	}
	public void setiLastAssignedSerialNumber(Long iLastAssignedSerialNumber) {
		this.iLastAssignedSerialNumber = iLastAssignedSerialNumber;
	}
}
