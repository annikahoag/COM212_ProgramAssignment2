/** 
 * A class that allows access and manipulation of an array of numbers, providing 
 * a high-level interface for common array operations.
 * Filename: HighArray.java (adapted from Robert Lafore's Data Structures text)
 * @author Annika Hoag in specified parts
 */
public class HighArray {

	private long[] a;        // private instance variable for array a
	private int nElems;      // private instance variable for number of data items

	/**
	 * Construct a new array of longs along with 
	 * an integer variable to track the number of elements in use
	 * @param max
	 *		specifies the size of the array
	 */
	public HighArray(int max) {  // constructor
		a = new long[max];        	// create the array
		nElems = 0;               	// no items yet
	}


	/**
	 * Finds the given value in the array
	 * @param searchKey
	 * 		the value to be searched for
	 * @return
	 *		true if searchKey is found, false otherwise
	 */
	public boolean find(long searchKey) {
		int j;
		for(j=0; j<nElems; j++) {           // for each element,
			if(a[j] == searchKey)           	// found item?
				return true;                       // exit loop before end
		}
		// gone to end of for loop
		return false;                   // yes, can't find it
	}  // end find()


	/**
	 * Inserts a new value into array a
	 * @param value
	 *		the value to be inserted
	 */
	public void insert(long value) {   // put element into array
		a[nElems] = value;             // add element to the end of the array
		nElems++;                      // increment size
	}


		/**
		 * Deletes the specified value
		 * @param value 
		 * 		The value to be deleted
		 * @return 
		 * 		true if search and deletion was successful, false otherwise
		 */
	public boolean delete(long value) { 
		int j;
		for(j=0; j<nElems; j++) {       // look for it
			if( value == a[j] ) {
				for(int k=j; k<nElems; k++) { // move higher ones down
					a[k] = a[k+1];
				}
				nElems--;                   // decrement size
				return true;				//exit entire function
			} //end if
		} //end for j
		return false; // can't find it if we made it this far
	} //end delete()


	/**
	 * Displays array contents
	 */
	public void display() {
		for(int j=0; j<nElems; j++) {      // for each element,
			System.out.print(a[j] + " ");  // display it
		}
		System.out.println("");
	}


	/**
	 * -----Following is written by Annika Hoag-----
	 */


	/**
	 * Part A - Finds and returns the maximum value in private array a
	 * @return -1 -> if a[] is empty, else currentMax -> the max value in a[]
	 * @author Annika Hoag
	 * @since 9/19/2022
	 */
	public long getMax(){
		if (nElems == 0){					//tests if a[] is empty	
			return -1;
		}

		long currentMax = a[0];				//sets currentMax to first value in a[] to start

		for (int i=1; i<nElems; i++){		//loop through all values in a[]
			if (a[i] > currentMax){			//if greater value is found, set currentMax to that value
				currentMax = a[i];
			}
		}//close for loop

		return currentMax;
	}

	
	/**
	 * Part B - Finds, removes, and returns the maximum value in private array a  
	 * @return -1 -> if a[] is empty, else currentMax -> the max value in a[]
	 * @author Annika Hoag 
	 * @since 9/19/2022
	 */ 
	public long removeMax(){
		if (nElems == 0){					//tests if a[] is empty
			return -1;
		}

		long currentMax = a[0];				//sets currentMax to first value in a[] to start
		int currentMaxIndex = 0;			//sets the index of currentMax to 0 to start

		for (int i=1; i<nElems; i++){		//loop through all values in a[]
			if (a[i] > currentMax){			//if greater value is found, set currentMax to that value
				currentMax = a[i];
				currentMaxIndex = i;		//reset currentMaxIndex to index where new max is found
			}
		}//close for loop

		/** 
		 * Note: I chose to have for loop go until i < nElems not i <= nElems because the latter
		 * could cause an IndexOutOfBounds exception if all indices of a[] were filled.
		*/
		for (int i=currentMaxIndex; i<nElems; i++){		//loop from index where max was found to end of a[]
			a[i] = a[i+1];								//move each value one space to the left in order to replace max value
		}
		nElems--;							//decrease nElems since we lost a value

		return currentMax;
	}


	/**
	 * Part C - Reverses the private array a by swapping the farthest values to the right and left
	 * @author Annika Hoag
	 * @since 9/21/2022
	 */ 
	public void reverse(){
		int lowIndex=0;					//variable to indicate index of value farthest left that hasn't been swapped yet				
		int highIndex=nElems-1; 		//variable to indicate index of value farthest right that hasnt been swapped yet
		long temp;						//variable to store a value as it gets replaced 

		while(lowIndex < highIndex){			//loop until lowIndex is no longer less than highIndex (center has been found)
			temp = a[lowIndex];					//store lowest unswapped value as temp
			a[lowIndex] = a[highIndex];			//set new lowest as the current highest, now the lowest and highest are equal
			a[highIndex] = temp;				//set highest as the old lowest

			lowIndex++;					//increase lowIndex to move right
			highIndex--;				//decrease highIndex to move left

		}//close while loop

	}

	/**
	 * Part D - Tests if there are any repeated values in private array a
	 * @return false -> if there is at least one repeated value in a[], else true -> if all values are distinct in a[]
	 * @author Annika Hoag
	 * @since 9/21/2022
	 */ 
	public boolean allDistinct(){
		/** Loop through each value in a[], 
		 * i is the index of the value we're testing against the rest
		 */ 
		for(int i=0; i<nElems; i++){			

			for(int j=i+1; j<nElems; j++){			//start looping from one value to the right of where we were to end of a[]
				if (a[j]==a[i]){					//if we find a value that equals the value at i ... 
					return false;					//that means a value is repeated 
				}

			}//close inner for loop
		}//close outer for loop

		return true;					//if we've made it through the for loop all values are distinct 
	}

}  // end class HighArray


/**
 * This HighArrayTest class has only a main function.
 * It's purpose is only to test the HighArray class.
 * @author Annika Hoag in specified parts
 */
class HighArrayTest {

	public static void main(String[] args) {

		int maxSize = 100;            // array size
		HighArray arr;                // declare a variable of type HighArray
		arr = new HighArray(maxSize); // create the new HighArray object and assign it to arr

		arr.insert(77);               // insert 10 items
		arr.insert(35);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(88);
		arr.insert(11);
		arr.insert(00);
		arr.insert(66);
		arr.insert(33);

		arr.display();          	// display items

		int searchKey = 35;         // value to search for
		if( arr.find(searchKey) )	//search for item
			System.out.println("Found " + searchKey);
		else
			System.out.println("Can't find " + searchKey);

		arr.delete(00);               // delete 3 items
		arr.delete(55);
		arr.delete(99);

		arr.display();                // display items again


		/**
		 * -----Following is written by Annika Hoag-----
		 */


		/**
		 * Declares new variable(s) to be used in testing
		 */ 
		long max;
		boolean distinct;

		/**
		 * Sets long variable max to return value of getMax()
		 * Prints out variable to see if the value is what we expect
		 */ 
		max = arr.getMax();
		System.out.println("\nThe maximum value is " + max + ".");


		/**
		 * Sets long variable max to return value of removeMax()
		 * Prints out variable to see if the value is what we expect
		 * Calls display() method to see if max value was removed properly
		 */ 
		max = arr.removeMax();
		System.out.println("\nHere is what the array looks like after removeMax(): ");
		arr.display();


		/**
		 * Calls reverse method
		 * Prints array to test if reverse() method worked properly
		 */ 
		arr.reverse();
		System.out.println("\nThe array has been reversed: ");
		arr.display();

		/**
		 * Adds value to make sure reverse() works with even number of values
		 * Calls reverse method again
		 * Prints array to test if reverse() method worked properly
		 */ 
		arr.insert(26);
		System.out.println("26 was added to the end of the array.");
		arr.reverse();
		System.out.println("The array has been reversed: ");
		arr.display();


		/**
		 * Sets boolean variable distinct to return value of allDistinct()
		 * Tests if distinct is true or false, prints corresponding statements
		 */ 
		distinct = arr.allDistinct();
		if(distinct){
			System.out.println("\nNo values in array repeat.");
		}else{
			System.out.println("\nAt least one value in array is repeated.");
		}

		/**
		 * Adds a value that is already in array to end of array
		 * Prints what array currently looks like
		 * Sets boolean variable distinct to return value of allDistinct()
		 * Tests if distinct is true or false, prints corresponding statements
		 */ 
		arr.insert(44);
		System.out.println("44 was added to the end of the array. This is what the array looks like: ");
		arr.display();
		distinct = arr.allDistinct();
		if(distinct){
			System.out.println("No values in array repeat.");
		}else{
			System.out.println("At least one value in array is repeated.");
		}

	}  // end main()
	
}  // end class HighArrayTest