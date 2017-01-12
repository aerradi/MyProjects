package adile.erradi.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class Algorithms {

	public static List<Integer> solution (ArrayList<Integer> input, ArrayList<Integer> query){

		LinkedList<ArrayList<Integer>> leftToRight = new LinkedList<>();
		LinkedList<ArrayList<Integer>> rightToLeft = new LinkedList<>();
		Set<Integer> ignoredLists1 = new TreeSet<>();
		Set<Integer> ignoredLists2 = new TreeSet<>();

		int min = 0, max = Integer.MAX_VALUE;

		for (int i = 0 ; i < input.size(); i++){
			for (int l = 0; l < leftToRight.size(); l++){
				ArrayList<Integer> list = leftToRight.get(l);
				if(max - min <= i - list.get(0)){
					ignoredLists1.add(l);
				} else {
					if(list.size() < query.size() && input.get(i).intValue() == query.get(list.size())){
						list.add(i);
						if(list.size() == query.size()){
							if(max - min > list.get(list.size() - 1) - list.get(0)){
								min = list.get(0);
								max = list.get(list.size() - 1);
							}
							ignoredLists1.add(l);
						}
					}
				}
			}
			
			for (int l = 0; l < rightToLeft.size(); l++){
				ArrayList<Integer> list = rightToLeft.get(l);
				if(max - min <= list.get(0) - input.size() - i -1){
					ignoredLists2.add(l);
				} else {
					if(list.size() < query.size() && input.get(input.size() - i -1).intValue() == query.get(query.size() -1 -list.size())){
						list.add(input.size() - i -1);
						if(list.size() == query.size()){
							if(max - min >  list.get(0) - list.get(list.size() - 1)){
								max = list.get(0);
								min = list.get(list.size() - 1);
							}
							ignoredLists2.add(l);
						}
					}
				}
			}
			
			if(input.get(i).intValue() == query.get(0).intValue()){
				for (int l = 0; l < leftToRight.size(); l++){
					if(leftToRight.get(l).size() == 1){
						ignoredLists1.add(l);
					}
				}
				leftToRight.add(new ArrayList<>(Arrays.asList(i)));
			}
			
			if(input.get(input.size() - i -1).intValue() == query.get(query.size()-1).intValue()){
				for (int l = 0; l < rightToLeft.size(); l++){
					if(rightToLeft.get(l).size() == 1){
						ignoredLists2.add(l);
					}
				}
				rightToLeft.add(new ArrayList<>(Arrays.asList(input.size() - i -1)));
			}

			int totalRemoved = 0;
			for (int index : ignoredLists1){
				leftToRight.remove(index - totalRemoved);
				totalRemoved++;
			}
			ignoredLists1.clear();
			
			totalRemoved = 0;
			for (int index : ignoredLists2){
				rightToLeft.remove(index - totalRemoved);
				totalRemoved++;
			}
			ignoredLists2.clear();
		}
		System.out.println("ignored size "+ignoredLists1.size());
		System.out.println("founds size "+leftToRight);
		System.out.println("ignored size "+ignoredLists2.size());
		System.out.println("founds size "+rightToLeft);
		if(max == Integer.MAX_VALUE){
			return null;
		}
		return input.subList(min, max + 1); 
	}

	public static void main(String[] args) {

		ArrayList<Integer> input = new ArrayList<>(Arrays.asList(22,1,1,2,3,1,5,5,5,3,4,1,5));

		for (int i = 0; i < 1_00; i++){
			input.add(ThreadLocalRandom.current().nextInt(0, 10));
		}
		input.add(22);
		input.add(23);
		
		ArrayList<Integer> query = new ArrayList<>(Arrays.asList(1,4,5,5,5,100,4,8,9,6));
		long start = System.currentTimeMillis();
		System.out.println(solution(input, query));
		System.out.println((System.currentTimeMillis() - start)+" ms");


	}
}
