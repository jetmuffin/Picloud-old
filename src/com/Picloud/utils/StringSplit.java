package com.Picloud.utils;

public class StringSplit {
	
	private static String div = "`";
	
	public static String descSplit(String str, int n){
		String[] strs = str.split(div);
		return strs[n];
	}
	public static void main(String[] args) {
		System.out.println(descSplit("`gdgdgdfg`gdgdgdfg`gdgdgdfg", 3));
	}
	
}
