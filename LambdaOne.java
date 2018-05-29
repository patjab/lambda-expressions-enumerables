import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;

/* Note about Generics:
 * Map and Reduce could accept any type of data in their block so this was accounted
 * for in their results variables during implementation of their loops by using an 
 * Object cast rather than the T cast.
 **/

public class LambdaOne<T> {

	private ArrayList<T> listStore;

	interface Block {
		Object blockFunction(Object subject);
	}

	interface BlockTwo {
		Object blockFunction(Object subject1, Object subject2);
	}

	public LambdaOne(ArrayList<T> listStore) {
		this.listStore = listStore;
	}

	public ArrayList<T> getListStore() {
		return listStore;
	}

	public ArrayList<T> select(Block lExpression) {
		ArrayList<T> al = new ArrayList<T>();
		Iterator<T> it = listStore.iterator();
		while ( it.hasNext() ) {
			T current = it.next();
			if ( (boolean)(lExpression.blockFunction(current)) )
				al.add(current);
		}
		return al;
	}

	public ArrayList<Object> map(Block lExpression) {
		ArrayList<Object> al = new ArrayList<Object>(); // Casted to Object as other things may be added on
		Iterator<T> it = listStore.iterator();
		while ( it.hasNext() ) {
			al.add((Object)(lExpression.blockFunction(it.next())));
		}
		return al;
	}

	public T find(Block lExpression) {
		Iterator<T> it = listStore.iterator();
		while ( it.hasNext() ) {
			T current = it.next();
			if ( (boolean)(lExpression.blockFunction(current)) )
				return current;
		}
		return null;
	}

	public Object reduce(BlockTwo lExpression) {
		Iterator<T> it = listStore.iterator();
		Object obj = it.next(); // Casted to Object as other things may be added on

		while ( it.hasNext() ) {
			Object current = it.next();
			obj = lExpression.blockFunction(obj, current);
		}
		return obj;
	}


	public static void main(String[] args) {
		System.out.println("INTEGERS TEST");
		// Creation of LambdaOne Object
		Integer[] arr = new Integer[]{234, 346, 25, 742, 53, 56, 1, 5};
		ArrayList<Integer> arrl = new ArrayList<Integer>(Arrays.asList(arr));
		LambdaOne<Integer> lambOne = new LambdaOne<Integer>(arrl);

		// SELECT Enumerator
		Block lExpSelect = (a)->{ return ((int)(a) % 2 == 0 ? true : false); };
		ArrayList<Integer> selectResult = lambOne.select(lExpSelect);
		System.out.println(Arrays.toString(selectResult.toArray()));

		// MAP Enumerator
		Block lExpMap = (a) -> { return (int)(a)+10; };
		ArrayList<Object> mapResult = lambOne.map(lExpMap);
		System.out.println(Arrays.toString(mapResult.toArray()));
		
		// FIND Enumerator
		Block lExpFind = (a) -> { return ((int)(a) % 10 == 6 ? true : false); };
		int findResult = (int)(lambOne.find(lExpFind));
		System.out.println(findResult);

		// REDUCE Enumerator
		BlockTwo lExpReduce = (a, b) -> { return (int)(a)+(int)(b); };
		int reduceResult = (int)(lambOne.reduce(lExpReduce));
		System.out.println(reduceResult);


		System.out.println("\nSTRINGS TEST");
		// Creation of LambdaOne Object
		String[] arr2 = new String[]{"hello, ", "how ", "are ", "you?"};
		ArrayList<String> arrl2 = new ArrayList<String>(Arrays.asList(arr2));
		LambdaOne<String> lambTwo = new LambdaOne<String>(arrl2);

		// SELECT Enumerator
		Block lExpSelect2 = (a)->{ return (((String)(a)).charAt(0) != 'h' ? true : false); };
		ArrayList<String> selectResult2 = lambTwo.select(lExpSelect2);
		System.out.println(Arrays.toString(selectResult2.toArray()));

		// MAP Enumerator
		Block lExpMap2 = (a) -> { return ((String)(a)).length(); };
		ArrayList<Object> mapResult2 = lambTwo.map(lExpMap2);
		System.out.println(Arrays.toString(mapResult2.toArray()));

		// FIND Enumerator
		Block lExpFind2 = (a) -> { return (((String)(a)).length() == 4 ? true : false); };
		String findResult2 = (String)(lambTwo.find(lExpFind2));
		System.out.println(findResult2);

		// REDUCE Enumerator
		BlockTwo lExpReduce2 = (a, b) -> { return (String)(a)+(String)(b); };
		String reduceResult2 = (String)(lambTwo.reduce(lExpReduce2));
		System.out.println(reduceResult2);

		
	}

}

