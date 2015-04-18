package com.Picloud.jxm;

import java.text.DecimalFormat;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.Picloud.config.SystemConfig;
import com.Picloud.web.model.SystemStateInfo;

public class SystemState {


	private static DecimalFormat df = new DecimalFormat("#.00");

	public static SystemStateInfo getSystemState() {
		String nameNodeInfo = SystemConfig.getNameNodeInfo();
		String nameNodeStatus = SystemConfig.getNameNodeStatus();
		String FSNamesystemState = SystemConfig.getFSNamesystemState();
		String memory = SystemConfig.getMemory();
		
		SystemStateInfo info = new SystemStateInfo();

		JSONObject jsonNameNodeInfo = new JSONObject().fromObject(SendGet
				.sendGet(nameNodeInfo));
		JSONArray jsonBeans = jsonNameNodeInfo.getJSONArray("beans");
		JSONObject jsonInfo = jsonBeans.getJSONObject(0);

		double used = Double.valueOf(jsonInfo.getString("Used"))
				/ (1024 * 1024);
		info.setDfsUsed(df.format(used) + "MB");

		double blockPoolUsed = Double.valueOf(jsonInfo
				.getString("BlockPoolUsedSpace")) / (1024 * 1024);
		info.setBlockPoolUsed(df.format(blockPoolUsed) + "MB");

		double nonUsed = Double.valueOf(jsonInfo.getString("NonDfsUsedSpace"))
				/ (1024 * 1024 * 1024);
		info.setNonDFSUsed(df.format(nonUsed) + "GB");

		double dfsRemaining = Double.valueOf(jsonInfo.getString("Free"))
				/ (1024 * 1024 * 1024);
		info.setDfsRemaining(df.format(dfsRemaining) + "GB");

		double percentUsed = Double.valueOf(jsonInfo.getString("PercentUsed"));
		if (percentUsed < 1.0)
			info.setDfsUsedPercent("0" + df.format(percentUsed) + "%");
		else
			info.setDfsUsedPercent(df.format(percentUsed) + "%");

		double percentRemaining = Double.valueOf(jsonInfo
				.getString("PercentRemaining"));
		if (percentRemaining < 1.0)
			info.setDfsRemainingPercent("0" + df.format(percentRemaining) + "%");
		else
			info.setDfsRemainingPercent(df.format(percentRemaining) + "%");

		double blockPoolUsedPercent = Double.valueOf(jsonInfo
				.getString("PercentBlockPoolUsed"));
		if (blockPoolUsedPercent < 1.0)
			info.setBlockPoolUsedPercent("0" + df.format(blockPoolUsedPercent)
					+ "%");
		else
			info.setBlockPoolUsedPercent(df.format(blockPoolUsedPercent) + "%");

		JSONObject jsonNode = jsonInfo.getJSONObject("NodeUsage")
				.getJSONObject("nodeUsage");
		String nodeUsageMin = jsonNode.getString("min");
		info.setNodeUsageMin(nodeUsageMin);

		String nodeUsageMedian = jsonNode.getString("median");
		info.setNodeUsageMedian(nodeUsageMedian);

		String nodeUsageMax = jsonNode.getString("max");
		info.setNodeUsageMax(nodeUsageMax);

		String nodeUsageStdDev = jsonNode.getString("stdDev");
		info.setNodeUsageStdDev(nodeUsageStdDev);

		System.out.println(info);
		return info;
	}
//
//	public static void main(String[] args) {
//		getSystemState();
//	}

}
