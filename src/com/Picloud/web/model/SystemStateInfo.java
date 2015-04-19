package com.Picloud.web.model;

import java.util.ArrayList;
import java.util.List;

public class SystemStateInfo {

	private String dfsUsed;
	private String nonDFSUsed;
	private String dfsRemaining;
	private String dfsUsedPercent;
	private String dfsRemainingPercent;
	private String blockPoolUsed;
	private String blockPoolUsedPercent;
	private String nodeUsageMin;
	private String nodeUsageMedian;
	private String nodeUsageMax;
	private String nodeUsageStdDev;
	private List<DataNodeState> datanodes = new ArrayList<DataNodeState>();
	
	
	public String getNodeUsageMin() {
		return nodeUsageMin;
	}
	public void setNodeUsageMin(String nodeUsageMin) {
		this.nodeUsageMin = nodeUsageMin;
	}
	public String getNodeUsageMedian() {
		return nodeUsageMedian;
	}
	public void setNodeUsageMedian(String nodeUsageMedian) {
		this.nodeUsageMedian = nodeUsageMedian;
	}
	public String getNodeUsageMax() {
		return nodeUsageMax;
	}
	public void setNodeUsageMax(String nodeUsageMax) {
		this.nodeUsageMax = nodeUsageMax;
	}
	public String getNodeUsageStdDev() {
		return nodeUsageStdDev;
	}
	public void setNodeUsageStdDev(String nodeUsageStdDev) {
		this.nodeUsageStdDev = nodeUsageStdDev;
	}
	public String getDfsUsed() {
		return dfsUsed;
	}
	public void setDfsUsed(String dfsUsed) {
		this.dfsUsed = dfsUsed;
	}
	public String getNonDFSUsed() {
		return nonDFSUsed;
	}
	public void setNonDFSUsed(String nonDFSUsed) {
		this.nonDFSUsed = nonDFSUsed;
	}
	public String getDfsRemaining() {
		return dfsRemaining;
	}
	public void setDfsRemaining(String dfsRemaining) {
		this.dfsRemaining = dfsRemaining;
	}
	public String getDfsUsedPercent() {
		return dfsUsedPercent;
	}
	public void setDfsUsedPercent(String dfsUsedPercent) {
		this.dfsUsedPercent = dfsUsedPercent;
	}
	public String getDfsRemainingPercent() {
		return dfsRemainingPercent;
	}
	public void setDfsRemainingPercent(String dfsRemainingPercent) {
		this.dfsRemainingPercent = dfsRemainingPercent;
	}
	public String getBlockPoolUsed() {
		return blockPoolUsed;
	}
	public void setBlockPoolUsed(String blockPoolUsed) {
		this.blockPoolUsed = blockPoolUsed;
	}
	public String getBlockPoolUsedPercent() {
		return blockPoolUsedPercent;
	}
	public void setBlockPoolUsedPercent(String blockPoolUsedPercent) {
		this.blockPoolUsedPercent = blockPoolUsedPercent;
	}
	public List<DataNodeState> getDatanodes() {
		return datanodes;
	}
	public void setDatanodes(List<DataNodeState> datanodes) {
		this.datanodes = datanodes;
	}
	@Override
	public String toString() {
		return "SystemStateInfo [dfsUsed=" + dfsUsed + ", nonDFSUsed="
				+ nonDFSUsed + ", dfsRemaining=" + dfsRemaining
				+ ", dfsUsedPercent=" + dfsUsedPercent
				+ ", dfsRemainingPercent=" + dfsRemainingPercent
				+ ", blockPoolUsed=" + blockPoolUsed
				+ ", blockPoolUsedPercent=" + blockPoolUsedPercent
				+ ", nodeUsageMin=" + nodeUsageMin + ", nodeUsageMedian="
				+ nodeUsageMedian + ", nodeUsageMax=" + nodeUsageMax
				+ ", nodeUsageStdDev=" + nodeUsageStdDev + ", datanodes="
				+ datanodes + "]";
	}
	
}
