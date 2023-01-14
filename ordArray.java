/** 
* A class that allows access and manipulation of an ordered array of numbers, 
* providing a high-level interface for common array operations.
* Filename: OrdArray.java (adapted from Robert Lafore's Data Structures text)
* @author Annika Hoag in specified parts
*/
public class OrdArray {

	private long[] a;		// private instance variable for array a
	private int nElems;		// private instance variable for number of data items
	
	/**
	 * Construct a new array of longs along with 
	 * an integer variable to track the number of elements in use
	 * @param max
	 *		specifies the size of the array
	 */
	public OrdArray(int max) {          // constructor
		a = new long[max];             // create array
		nElems = 0;
	}

	/**
	 * Accessor method for nElems
	 * @return 
	 *		returns the number of array elements in use
	 */ 
	public int size() {
		return nElems; 
	}

	/**
	 * Finds the given value in the array
	 * Part E - Modification made to use binary search instead of linear
	 * @param searchKey
	 * 		the value to be searched for
	 * @return
	 *		the index where the searchKey is found, 
	 *		or the value of nElems if not found
	 * @author Annika Hoag on the modifications
	 * @since 9/23/2022
	 */
	public int find(long searchKey) {
		//represents index farthest left of the area we're currently searching
		int leftIndex = 0;

		//represents index farthest right of the area we're currently searching				
		int rightIndex = nElems-1;					
		int midIndex;

		while(leftIndex <= rightIndex) {					//loops until leftIndex>rightIndex or they're equal
			midIndex = (rightIndex + leftIndex) / 2;	//midIndex -> value in the middle of the area we're currently searching

			if (a[midIndex] == searchKey){			//searchKey has been found at midIndex
				return midIndex;		

			}else if (a[midIndex] > searchKey){		//if searchKey is less that means it is on the left half of where we're searching
				rightIndex = midIndex - 1;			//edit rightIndex to modify the area we're searching to only include the left half
			
			}else{									//else searchKey must be greater, must be on right half 
				leftIndex = midIndex + 1;			//edit leftIndex to modify the area we're searching to only include the right half
			}

		}//close while loop

		/**
		 * This conditional occurs when leftIndex and rightIndex are equal and there's only one value left
		 */ 
		// if (a[leftIndex] == searchKey){		//test if last value is equal to searchKey
		// 	return leftIndex;				
		// }else{								//if not return nElems
		// 	return nElems;
		// }
		return nElems;

		/** Original code with linear search
		 *
		 */  
		// int j;
		// for(j=0; j<nElems; j++) {  // for each element,
		// 	if(a[j] == searchKey)     // found search value?
		// 		return j;                // then exit loop before end (exits entire function)
		// }
		// // reached end of for loop
		// return nElems;             // thus, can't find it

	} // end find()


	/**
	 * Inserts a new value into array a[]
	 * (Assumes the array is long enough to handle all items that will be inserted.)
	 * Part F - Modification made to use binary search to find the position where value should be inserted
	 * @param value
	 *		the value to be inserted
	 * @author Annika Hoag on modifications
	 * @since 9/24/2022
	 */
	public void insert(long value) { 

		/** 
		 * Area we're searching starts off as being the entire array
		 */ 
		int leftIndex=0;					//farthest left index of the area we're currently searching 
		int rightIndex = nElems-1;			//farthest right index of the area we're currently searching
		int mid = 0;						//index of middle value, starts as 0 until computed
		int insertIndex=0;					//represents the index where value should be inserted
		boolean found=false;				//flag when we've found where value belongs so loop will stop

		if(nElems==1){
			if(a[0]>=value){
				insertIndex=0;
			}else{
				insertIndex=1;
			}
			found=true;
		}

		/**
		 * Loop through array until found=true or until leftIndex not <= rightIndex
		 * If rightIndex is less than leftIndex that means that the value goes at the first index of a[]u
		 */ 
		while (!found && leftIndex<=rightIndex){
			mid = (leftIndex + rightIndex) / 2;			//calculate mid by finding average of right and left indices

			/**
			 * First test if value belongs btwn number at mid AND number at mid+1
			 * Then test if it's a duplicate
			 * Then test if the rest of the array is empty
			 * Note for above^: a[mid+1] also could be zero if we're adding another zero,
			 * 					however the second condition would be satisfied first
			 * In any of these circumstances we know that value belongs at pos mid+1
			 */ 
			if ( (a[mid]<value && value<a[mid+1]) || a[mid]==value || a[mid+1]==0){
				//if any of the above are true set insertIndex to mid+1 and flag that pos has been found
				insertIndex=mid+1;
				found=true;

			}else if (a[mid] > value){			//if value is less that means it is on the left half of where we're searching
				rightIndex = mid - 1;			//edit rightIndex to modify the area we're searching to only include the left half
				found=false;					//found is still false

			}else{								//else value must be greater than a[mid], so it must be on right half 
				leftIndex = mid + 1;			//edit leftIndex to modify the area we're searching to only include the right half
				found=false;					//found is still false
			}

		}//close while loop


		/**
		 * For loop to shift all elements up until insertIndex
		 * Taken from original insert() code
		 */ 
		for(int k=nElems; k>insertIndex; k--) {  // shift all bigger elements up
			a[k] = a[k-1];
		}//close for loop


		/**
		 * Insert value and increase nElems
		 * This was also taken from original insert() code
		 */ 
		a[insertIndex]=value;
		nElems++;

		/** Testing sort() method
		 * Print out array each time a value is inserted so we can see if a[] has been properly sorted
		 */ 
		this.sort();
		// System.out.println("\nThis is the array after we insert " + value + ":");
		// this.display();

		/**Original Code:
		 */ 
		// int j = 0;
		// //find position where value should be inserted:
		// //keep looking until we've gone too high or 
		// //we've run out of values to look at
		// while (a[j] <= value && j < nElems) { 	
		// 	j++;  					// linear search
		// }
		// //j now holds the position where the value should be inserted
		// for(int k=nElems; k>j; k--) {  // shift all bigger elements up
		// 	a[k] = a[k-1];
		// }
		// a[j] = value;                  // insert the value
		// nElems++;                      // increment size
	
	}  // end insert()
	

	/**
	 * Deletes the specified value
	 * Part F - Modified to use binary search by calling find()
	 * @param value 
	 * 		The value to be deleted
	 * @return 
	 * 		true if search and deletion was successful, false otherwise
	 * @author Annika Hoag on modifications
	 * @since 9/24/2022
	 */
	public boolean delete(long value) {
		int foundIndex = this.find(value);

		if (foundIndex == nElems){
			return false;

		}else{
			//taken from og code
			for(int k=foundIndex; k<nElems; k++) { // move bigger ones down
				a[k] = a[k+1];
			}//close for loop

			nElems--;                   // decrement size
			return true;
		}

		/**Original Code:
		 */ 
		// int j = 0;
		// //find the value to be deleted:
		// //keep looking until we've gone too high or 
		// //we've run out of values to look at
		// while (a[j] < value && j < nElems){  
		// 	j++;  //linear search for value
		// }
		// if(a[j] == value) { // found it
		// 	for(int k=j; k<nElems; k++) { // move bigger ones down
		// 		a[k] = a[k+1];
		// 	}                
		// 	nElems--;                   // decrement size
		// 	return true;	
		// } //end if
		// else { // value not found in the array
		// 	return false;
		// }

	}  // end delete()

	/**
	 * Displays array contents
	 */
	public void display() {            // displays array contents
		for(int j=0; j<nElems; j++)       // for each element,
		System.out.print(a[j] + " ");  // display it
		System.out.println("");
	}


	/**
	* -----Following is written by Annika Hoag-----
	*/


	/**
	 * Part G - Sorts array in increasing order using insertion sort
	 * @author Annika Hoag
	 * @since 9/25/2022
	 */ 
	public void sort(){
		long sortValue;				//stores value of the number we're trying to find pos of 
		int insertIndex;			//index where sortValue belongs

		//loop through a[] from 1 to last index 
		for(int i=1; i<nElems-1; i++){
			sortValue = a[i];			//value we're trying to find correct pos of is a[i]
			insertIndex = i;			//start the correct index as i
			
			
			/** Loop until insertIndex is less than 0 -> can't have sortValue go at an index lower than 0
			 * Also stop looping if the value an index below is less than sortValue, 
			 * 	this is how we know we're in the right spot
			 * Decrement insertIndex each time 
			 */ 
			while(insertIndex>0 && a[insertIndex-1]>sortValue){
				insertIndex--;
			}//close while loop

		
			//shift all bigger values up 
			for(int k=i; k>insertIndex; k--) {  
				a[k] = a[k-1];
			}//close inner for loop
	

			//store the value we're placing at insertIndex's pos in a[]
			a[insertIndex] = sortValue;

		}//close outer for loop
	
	}//close sort()


	/**
	 * Final Challenge 2nd Part - Removes all values in privata array a[] in the given range,
	 * 	this includes the numbers given if one or both of the values given are in a[]
	 * Also shifts all values after removal and edits nElems accordingly
	 * Run-time is O(n)
	 * THIS PART IS NOT FOR EXTRA CREDIT, THIS IS THE ONE OPTION I WOULD LIKE GRADED
	 * @param low -> lowest value in range to be removed
	 * @param high -> highest value in range to be removed
	 * @return count -> the number of values that were removed from a[], 0 if no values were found in the range
	 * @author Annika Hoag
	 * @since 9/28/2022
	 * 
	 */
	 public int removeRange(long low, long high){
	 	int count=0;					//variable to keep track of how many values have been removed

	 	/** 
	 	 * Figure out where to start removing 
	 	 */ 
	 	int lowIndex=0;					//loop until you've reached a value >= the low, this is the beginning of the range
	 	while(a[lowIndex]<low){					
	 		lowIndex++;
	 	}

	 	/**
	 	 * Figure out where to stop removing 
	 	 * Start with highIndex and lowIndex being equal to test if there are no values in the range
	 	 */ 
	 	int highIndex=lowIndex;							
	 	while(a[highIndex]<=high && highIndex<nElems){	//loop until you're > high (out of range) or at the end of a[]
	 		highIndex++;
	 	}

	 	/**
	 	 * Conditional only true if there are no values in the range
	 	 */ 
	 	if (lowIndex==highIndex){
	 		return count;			//break out of method, no need to shift
	 	}

	 	/**
	 	 * Count how many numbers to remove
	 	 * Note: loop goes until i < highIndex not <= b/c highIndex is one index above where the range ends
	 	 */ 
	 	for(int i=lowIndex; i<highIndex; i++){
	 		count++;					//count increaes for each index in the range
	 	}//close for loop

	 	/**
	 	 * Shift values and edit nElems
	 	 */ 
	 	for(int i=lowIndex; i<nElems; i++){
	 		a[i] = a[i+count];
	 	}
	 	nElems = nElems-count;
	

	 	return count;
	 }//close removeRange() 



	 /**
	  * Final Challenge 1st Part - merges given sorted array into private array a
	  * Run-time is O(n)
	  * THIS PART IS FOR EXTRA CREDIT
	  * @param b -> sorted array of longs to be merged into a[]
	  * @author Annika Hoag
	  * @since 9/29/2022
	  */ 
	 public void merge(long[] b){
	 	int count=b.length-1;			//count to loop through b[] from end to beginning

	 	while (count>=0){				//loop until count equals 0
	 		this.insert(b[count]);		//insert the value of b[] ay count using insert() method
	 		count--;					//decrease count
	 	}//close while loop
	 }//close merge


}  // end class OrdArray



/**
 * This OrderedArrayTest class has only a main function.
 * It's purpose is only to test the OrderderedArray class.
 */
class OrderedArrayTest {

	public static void main(String[] args) {
		int maxSize = 100;             // array size
		OrdArray arr;                  // reference to array
		arr = new OrdArray(maxSize);   // create the array

		arr.insert(77);                // insert 10 items
		arr.insert(99);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(88);
		arr.insert(11);
		arr.insert(00);
		arr.insert(66);
		arr.insert(33);

		int searchKey = 55;            // search for item
		if( arr.find(searchKey) != arr.size() )
			System.out.println("Found " + searchKey);
		else
			System.out.println("Can't find " + searchKey);

		arr.display();                 // display items

		arr.delete(00);                // delete 3 items
		arr.delete(55);
		arr.delete(99);

		arr.display();                 // display items again
	

		/**
		 * -----Following is written by Annika Hoag-----
		 */


		/**
		 * Declares new variable(s) to be used in testing
		 */
		long searchKeyTest;
		int foundIndex;
		boolean isDeleted;


		//-----------Testing find() method----------
		 /**
		  * Sets long variable searchKeyTest to 66, a value we know is in arr[]
		  * Sets int variable foundIndex to return value of find() with searchKeyTest as the parameter
		  * If foundIndex is equal to return value of size(), searchKeyTest wasn't found
		  * Print out foundIndex to see if it's what we expect
		  */ 
		searchKeyTest=66;
		foundIndex = arr.find(searchKeyTest);
		System.out.println("\nSearching for 66...");
		if (foundIndex != arr.size() ){
			System.out.println("Found 66 at index " + foundIndex + ".");
		}else{
			System.out.println("Could not find 66.");
		}

		/**
		  * Sets long variable searchKeyTest to 55, a value we know isn't in arr[] 
		  * Sets int variable foundIndex to return value of find() with searchKeyTest as the parameter
		  * If foundIndex is equal to return value of size(), searchKeyTest wasn't found
		  * Print out foundIndex to see if it's what we expect
		  */ 
		searchKeyTest=55;
		foundIndex = arr.find(searchKeyTest);
		System.out.println("\nSearching for 55...");
		if (foundIndex != arr.size() ){
			System.out.println("Found 55 at index " + foundIndex + ".");
		}else{
			System.out.println("Could not find 55.");
		}

		/**
		 * Adds and deletes some values to test if find() still works after modifications
		 * Prints out what array looks like after modifications
		 */ 
		arr.insert(55);
		arr.insert(99);
		arr.delete(33);
		System.out.println("\nAdded 2 new elements, and removed 1: ");
		arr.display();

		/**
		  * Sets long variable searchKeyTest to 55, a value we know is in arr[] 
		  * Sets int variable foundIndex to return value of find() with searchKeyTest as the parameter
		  * If foundIndex is equal to return value of size(), searchKeyTest wasn't found
		  * Print out foundIndex to see if it's what we expect
		  */ 
		searchKeyTest=55;
		foundIndex = arr.find(searchKeyTest);
		System.out.println("\nSearching for 55...");
		if (foundIndex != arr.size() ){
			System.out.println("Found 55 at index " + foundIndex + ".");
		}else{
			System.out.println("Could not find 55.");
		}

		/**
		  * Sets long variable searchKeyTest to 33, a value we know isn't in arr[] 
		  * Sets int variable foundIndex to return value of find() with searchKeyTest as the parameter
		  * If foundIndex is equal to return value of size(), searchKeyTest wasn't found
		  * Print out foundIndex to see if it's what we expect
		  */ 
		searchKeyTest=33;
		foundIndex = arr.find(searchKeyTest);
		System.out.println("\nSearching for 33...");
		if (foundIndex != arr.size() ){
			System.out.println("Found 33 at index " + foundIndex + ".");
		}else{
			System.out.println("Could not find 33.");
		}


		//----------------Testing delete() method---------
		System.out.println("\nHere is what the array looks like right now: ");
		arr.display();

		/**
		 * Tries to delete 3 values and informs user if each was successful
		 * Sets boolean variable isDeleted to return value of delete() 3 times
		 * Prints message depending on if isDeleted is true or false
		 */ 
		System.out.println("\nGoing to delete 0, 55, and 99. ");
		
		isDeleted = arr.delete(00);
		if (isDeleted){
			System.out.println("Succesfully deleted 0.");
		}else{
			System.out.println("Value not found, could not delete 0.");
		}

		isDeleted = arr.delete(55);
		if (isDeleted){
			System.out.println("Succesfully deleted 55.");
		}else{
			System.out.println("Value not found, could not delete 55.");
		}

		isDeleted = arr.delete(99);
		if (isDeleted){
			System.out.println("Succesfully deleted 99.");
		}else{
			System.out.println("Value not found, could not delete 99.");
		}

		/**
		 * Shows user the array so they can see which values have been deleted
		 */ 
		System.out.println("\nHere is what the array looks like right now: ");
		arr.display();


		//-----------------Testing insert() and sort() methods----------
		
		System.out.println("\nGoing to add 30, 86, 52, 7, 54, 11, 100, and two 0s to the array.");

		//insert 9 items
		arr.insert(30);                
		arr.insert(86);
		arr.insert(52);
		arr.insert(7);
		arr.insert(54);
		arr.insert(11);
		arr.insert(100);
		arr.insert(0);
		arr.insert(0);

		/**
		 * Informs user of which values were added
		 * Displays array to see if it has been properly sorted
		 * Print statement in insert() should also display the array as items are sorted
		 */ 
		// System.out.println("\nJust added 30, 86, 52, 7, 54, 11, 100, and two 0s to the array.");
		System.out.println("This is what the array looks like right now: ");
		arr.display();
		


		//-----------------Testing removeRange() method----------

		/**
		 * Starts by trying to remove values in range where neither low nor high are in array
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */ 
		System.out.println("\nGoing to try to remove values between 20 and 60.");
		int numsRemoved = arr.removeRange(20, 60);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Adds more values to array
		 */ 
		System.out.println("\nAdding 32, 19, 54, 1, 73, and 26 to the array.");
		arr.insert(32);
		arr.insert(19);
		arr.insert(54);
		arr.insert(1);
		arr.insert(73);
		arr.insert(26);
		System.out.println("This is what the array looks like right now: ");
		arr.display();

		/**
		 * Then tries to remove values in range where low is in array but high is not
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */ 
		System.out.println("\nGoing to try to remove values between 11 and 57");
		numsRemoved = arr.removeRange(11, 57);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Attempts to remove values in a range that includes no values in array
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */ 
		System.out.println("\nGoing to try to remove values between 2 and 5");
		numsRemoved = arr.removeRange(2, 5);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Adds more values back to array
		 */ 
		System.out.println("\nAdding 32, 19, 54, 1, 73, and 26 to the array.");
		arr.insert(32);
		arr.insert(19);
		arr.insert(54);
		arr.insert(1);
		arr.insert(73);
		arr.insert(26);
		System.out.println("This is what the array looks like right now: ");
		arr.display();

		/**
		 * Removes values in a range where both low and high are in the array
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */ 
		System.out.println("\nGoing to try to remove values between 86 and 100.");
		numsRemoved = arr.removeRange(86, 100);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Removes values in a range where low is not in the array but high is 
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */  
		System.out.println("\nGoing to try to remove values between 13 and 26.");
		numsRemoved = arr.removeRange(13, 26);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Tries to remove values in a range that is invalid (low>high)
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */  
		System.out.println("\nGoing to try to remove values between 10 and 5.");
		numsRemoved = arr.removeRange(10, 5);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();

		/**
		 * Lastly, removes values in a range that has high being greater than last value
		 * Sets int variable numsRemoved to return value of removeRange()
		 * Prints numsRemoved and shows what array looks like now
		 */  
		System.out.println("\nGoing to try to remove values between 10 and 200.");
		numsRemoved = arr.removeRange(10, 200);
		System.out.println("Removed " + numsRemoved + " numbers.");
		System.out.println("This is what the array looks like now: ");
		arr.display();


		/**
		 * Delete all values in array to start over
		 */ 
		arr.delete(0);
		arr.delete(0);
		arr.delete(1);
		arr.delete(1);
		arr.delete(7);

		/**
		 * Insert 10 items to the array
		 */ 
		arr.insert(77);                
		arr.insert(99);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(14);
		arr.insert(11);
		arr.insert(00);
		arr.insert(66);
		arr.insert(33);
 
 		/**
 		 * Show user what the array looks like right now
 		 */
 		System.out.println("\nThis is what the array looks like now after deleting all values and adding 10 new ones: ");
 		arr.display();

		/**
		 * Testing merge() method
		 * Creates a sorted array of 10 values
		 * Calls merge()
		 * Prints arr[] to see if the arrays merged properly
		 */ 
		long [] arr2 = {10, 20, 26, 30, 40, 50, 60, 70, 80, 90};
		System.out.println("\nThis is array b[]: ");
		for (int i=0; i<arr2.length; i++){
			System.out.print(arr2[i] + " ");
		}

		arr.merge(arr2);
		System.out.println("\nHere is what the array looks like after merging with b[]:");
		arr.display();



	} // end main()
	
}  // end class OrderedApp