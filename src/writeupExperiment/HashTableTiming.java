/**
 * @author Morgan Evans, Joshua Malters
 * @UWNetID mnevans, maltersj
 * @studentID 1124703, 1336144
 * @email mnevans@uw.edu, maltersj@uw.edu
 */ 

package writeupExperiment;

import java.io.IOException;

import providedCode.*;
import shake_n_bacon.*;

public class HashTableTiming{
	private static final int NUM_TESTS = 20;
	private static final int NUM_WARMUP = 5;
	
	public static void main(String[] args) {
		Comparator<String> c = new StringComparator();
		Hasher h = new StringHasher();
		DataCounter sc = new HashTable_SC(c, h);
		DataCounter oa = new HashTable_OA(c, h);
		
		System.out.println("avg time for SC hamlet = " + getAverageRuntime(sc));
		System.out.println("avg time for OA hamlet = " + getAverageRuntime(oa));
	}   

	public static void timeInsert(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();

			while (word != null) {
				counter.incCount(word);
				word = reader.nextWord();
			}
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}  
	}

	private static double getAverageRuntime(DataCounter dataCounter) {
		double totalTime = 0;
		
		for(int i=0; i< NUM_TESTS; i++) {
			long startTime = System.currentTimeMillis();
			timeInsert("the-new-atlantis.txt", dataCounter);
//			timeInsert("hamlet.txt", dataCounter);
			long endTime = System.currentTimeMillis();
			if(NUM_WARMUP <= i) { // Throw away first NUM_WARMUP runs to encounter JVM warmup
				totalTime += (endTime - startTime);
			}
		}
		return totalTime / (NUM_TESTS-NUM_WARMUP); // Return average runtime.
	}

}