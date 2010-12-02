package com.niothiel.simplesms;

import java.util.ArrayList;

import android.util.Log;

public class Benchmarker {
	private static ArrayList<Benchmark> mBenchmarks = new ArrayList<Benchmark>(5);
	
	public static void start(String label) {
		//Log.d("Benchmark", "Starting benchmark for: " + label);
		Benchmark b = new Benchmark();
		b.label = label;
		b.startTime = System.currentTimeMillis();
		mBenchmarks.add(b);
	}
	
	public static void stop(String label) {
		long endTime = System.currentTimeMillis();
		long startTime = -1;
		for(Benchmark b : mBenchmarks) {
			if(label.equals(b.label)) {
				startTime = b.startTime;
				mBenchmarks.remove(b);
				break;
			}
		}
		long elapsed = endTime - startTime;
		Log.d("Benchmark", label + " finished in " + elapsed + " ms.");
	}
	
	private static class Benchmark {
		long startTime;
		String label;
	}
}
