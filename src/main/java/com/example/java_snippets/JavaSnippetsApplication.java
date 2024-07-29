package com.example.java_snippets;

import bitwise.KeyPair;
import enums.Day;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tasks.search.Maze;
import tasks.search.MazeStarter;

import static bitwise.UnbreakableEncryption.*;

@SpringBootApplication
public class JavaSnippetsApplication {

	public static void main(String[] args) {
//		System.out.println(FibonacciStarter.fibonacciRecursive(10));
//		 System.out.println(FibonacciStarter.fibonacciLinear(10));
		// System.out.println(FibonacciStarter.fibonacciStream(10));
//		CompressedGene compressed = new CompressedGene(CompressedGene.original);
//		final String decompressed = compressed.decompress();
//		System.out.println(compressed);
//		System.out.println(decompressed);
//		System.out.println(Day.MONDAY);
//		KeyPair kp = encrypt("One Time Pad!");
//		String result = decrypt(kp);
//		System.out.println(result);
		MazeStarter.run();
		SpringApplication.run(JavaSnippetsApplication.class, args);
	}

}
