package test;

import java.util.Random;

import utils.DictionaryBasedValueGenerator;

public class App 
{
	public static void main(String[] args) {
        
        Random random = new Random(1);
        Integer seed = random.nextInt();
        System.out.println(seed);
        DictionaryBasedValueGenerator dictionary = new DictionaryBasedValueGenerator(1, seed);
        System.out.println(dictionary.generate());
        

	}
}
