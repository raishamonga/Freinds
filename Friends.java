package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the mostShort chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which mostShort chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The mostShort chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		ArrayList <String> mostShort = new ArrayList <>();
		String p1a = new String (p1.toLowerCase()) ;  
		String p2a = new String (p2.toLowerCase()) ; 
		
		if ( p1a.equals(p2a)) {
			return mostShort; 
		}
		if ( g == null || p1a == null || p2a == null ) {
			return mostShort; 
		}
		 

		boolean haveVisited [] = new boolean [g.members.length]; 
		for ( int k = 0 ; k < haveVisited.length ; k ++ )  {
			haveVisited [k] = false; 
		}
		
		int previous [] = new int [g.members.length] ; 
		for ( int l = 0 ; l < previous.length ; l ++ )  {
			previous [l] = -1 ; 
		}
		
		Queue <Integer> queue = new Queue <>() ; 

		for ( int i = 0 ; i < g.members.length; i ++ ) { 			
			if ( !haveVisited [i] && g.members[i].name.toLowerCase().equals(p1a)) {
				
				if ( previous [i] == -1 ) {
					previous [i] = -1 ; 
				}
				
				haveVisited [ i ] = true;  
				queue.enqueue ( i ) ; 

				while ( !queue.isEmpty() ) { 
					
					int a = queue.dequeue () ; 
					
					for ( Friend j = g.members[a].first ; j != null ; j = j.next ) {
						
						int num = j.fnum; 
						if ( !haveVisited [num] )  {
							haveVisited [num] = true; 
							queue.enqueue(num);
							previous [num] = a; 
							
						}
						if ( g.members[num].name.toLowerCase().equals (p2a) ) {
							mostShort = add ( previous , g , p1a , p2a ) ;
							return mostShort; 
						}
						
					}//inner for loop 

				}//while loop
			}//inner if loop 
		}//outer for loop 
				
		return mostShort; 
		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		//return null;
	}
	
	private static ArrayList <String> add ( int array[], Graph g, String p1, String p2) {
		ArrayList <String>mostShort = new ArrayList <> (); 
		
		
		int start = 0 ; 
		int end = 0; 
		for ( int i = 0 ; i < g.members.length ; i ++ ) {
			if (g.members[i].name.toLowerCase().equals(p1) ) {
				start = i ; 
			}
			if (g.members[i].name.toLowerCase().equals(p2) ) {
				end = i ; 
			}
		}
		Stack <Integer> stack = new Stack () ; 
		stack.push (end);

		int a = end ; 
		a = array [a] ;
		
		while ( a != -1 ) {
			stack.push( a );
			a = array [a] ; 
		}
		int b = 0 ; 
		while (!stack.isEmpty()) {
			b = stack.pop() ; 
			mostShort.add(g.members[b].name) ; 
		}
			
		return mostShort; 
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		ArrayList <ArrayList <String>> answer = new ArrayList <>();
		
		if (school == null||g == null) {
			return answer; 
		}
		String sc1 = new String (school) ; 
		
		sc1 = sc1.toLowerCase(); 
		
		boolean haveVisited [] = new boolean [g.members.length]; 
		for ( int k = 0 ; k < haveVisited.length ; k ++ )  {
			haveVisited [k] = false; 
		}
		

		for ( int i = 0 ; i < g.members.length ; i ++ ) { 			
			if ( !haveVisited [i] && g.members[i].school!=null && g.members[i].school.toLowerCase().equals (sc1) ) {
				
				Queue <Integer> queue = new Queue <>() ; 
				ArrayList <String> clique = new ArrayList <>();
				
				haveVisited [ i ] = true;  
				queue.enqueue ( i ) ; 
				clique.add(g.members[i].name) ;
				System.out.println("First add :" +g.members[i].name);
				while ( !queue.isEmpty() ) { 
					
					int a = queue.dequeue () ; 

					for ( Friend j = g.members[a].first ; j != null ; j = j.next ) {
						
						int num = j.fnum; 
						if ( !haveVisited [num] && g.members[num].school!=null && g.members[num].school.toLowerCase().equals (sc1))  {
							haveVisited [num] = true; 
							queue.enqueue(num);
							clique.add(g.members[num].name) ; 
							System.out.println("Second add :" +g.members[num].name);
						}
					}//inner for loop 
					
					
				}//while loop
				
				answer.add(clique) ; 
				
			}//inner if loop 
		}//outer for loop 
				
		return answer; 
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		//return null;
		
	}
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		ArrayList<String> answer = new ArrayList<> () ; 
		if (g == null) {
			return answer; 
		}
		int [] dfsnum = new int [ g.members.length ] ; 
		for ( int i = 0 ; i < dfsnum.length ; i ++ ) {
			dfsnum [i] = 0 ; 
		}
		int [] back = new int [ g.members.length ] ; 
		for ( int i = 0 ; i < back.length ; i ++ ) {
			back [i] = 0 ; 
		}
		boolean [] haveVisited = new boolean [g.members.length];
		for ( int i = 0 ; i < haveVisited.length ; i ++ ) {
			haveVisited [i] = false ; 
		}
		int count1 = 0 ; int count2 = 0 ; 
		for ( int i = 0 ; i < haveVisited.length ; i ++ ) {
			if ( !haveVisited [i] ) {
				haveVisited [i] = true ;					
				answer = dfs ( i , count1, count2, dfsnum, back, haveVisited, g , answer ) ;
					
			}
			
		}
		return answer; 
		

		
		/** COMPLETE THIS METHOD **/
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
//		 return null;
		
	}
	private static int visitednumber ( boolean haveVisited [] ) { 
		int num = 0 ; 
		for ( int i = 0 ; i < haveVisited.length; i ++ ) {
			if (haveVisited[i]) num ++ ; 
		}
		return num; 
	}
	private static ArrayList<String> dfs ( int v, int count1 , int count2, int []dfsnum , int []back  ,boolean [] haveVisited, Graph g , ArrayList<String> answer ) {
		String a = new String () ;
//		ArrayList <String> b = new ArrayList<>() ;
		count1 = visitednumber ( haveVisited ); 
		count2  = visitednumber ( haveVisited  ); 
		dfsnum[v] = count1  ; 
		back [v] = count2; 
		System.out.println( g.members[v].name + " " + dfsnum[v] + " " + back[v] ) ; 
		haveVisited [v] = true ;
		 
		for ( Friend j = g.members[v].first;  j !=null ; j = j.next ) {
			if ( !haveVisited [j.fnum] ) {
				haveVisited [j.fnum] = true; 
				System.out.println( count1 + " " + count2 );
				
				answer = dfs ( j.fnum , count1, count2, dfsnum, back, haveVisited, g, answer ) ; 
				System.out.println( "Current " + g.members[v].name + ": " + " dfsnum: " + dfsnum[v] + " back: " + back[v] );
				System.out.println( "Current " + g.members[j.fnum].name + ": " + " dfsnum: " + dfsnum[j.fnum] + " back: " + back[j.fnum] );

				if (dfsnum[v] > back[j.fnum] ) {
					back [v] = Math.min( back[v] , back [j.fnum] ) ; 
					System.out.println( "condition1: " + g.members[v].name + ": " + " dfsnum: " + dfsnum[v] + " back: " + back[v] );
				}
				
				if (dfsnum [v] <=back [j.fnum] && (onlyNbr (j.fnum, v, g )) ) {
					if (dfsnum [v] <=back [j.fnum] && (onlyNbr (j.fnum, v, g )) ) {
						System.out.println( "added name: " + g.members[v].name ) ; 
					}
					if (!checkdup ( answer , g.members[v].name )) {
						answer.add(g.members[v].name) ; 
					}					
				} else if  (dfsnum [v] <=back [j.fnum] && (onlyNbr ( v, j.fnum, g )) ) {
					if (dfsnum [v] <=back [j.fnum] && (onlyNbr ( v, j.fnum, g )) ) {
						System.out.println( "added name: " + g.members[j.fnum].name ) ; 
					}
					if (!checkdup ( answer , g.members[j.fnum].name )) {
						answer.add(g.members[j.fnum].name) ; 
					}
				}
				
				
				

			} else {
				back[v] = Math.min( back[v] , dfsnum [j.fnum] ) ;
				System.out.println( "condition2: " + g.members[v].name + ": " + " dfsnum: " + dfsnum[v] + " back: " + back[v] + " || " + "Current v: " + g.members[v].name + " Current j.fum: " + g.members[j.fnum].name );
				if (dfsnum [v] <=back [j.fnum] && (onlyNbr (j.fnum, v, g )) ) {
					if (dfsnum [v] <=back [j.fnum] && (onlyNbr (j.fnum, v, g )) ) {
						System.out.println( "added name: " + g.members[v].name ) ; 
					}
					if (!checkdup ( answer , g.members[v].name )) {
					answer.add(g.members[v].name) ; 
					}
				} else if  (dfsnum [v] <=back [j.fnum] && (onlyNbr ( v, j.fnum, g )) ) {
					if (dfsnum [v] <=back [j.fnum] && (onlyNbr ( v, j.fnum, g )) ) {
						System.out.println( "added name: " + g.members[j.fnum].name ) ; 
					}
					if (!checkdup ( answer , g.members[j.fnum].name )) {
						answer.add(g.members[j.fnum].name) ; 
					}
				}
			}
//			if (dfsnum [v] <=back [j.fnum] && onlyNbr (j.fnum, v, g ) ) {
//				if (dfsnum [v] <=back [j.fnum] && onlyNbr (j.fnum, v, g )) {
//					System.out.println( "added name: " + g.members[v].name ) ; 
//				}
//				answer.add(g.members[v].name) ; 
//			}
			
		}
		
		return answer ; 
	}
	private static boolean onlyNbr ( int v, int fnum, Graph g ) {
		
		int count = 0 ; 
		for ( Friend i = g.members[v].first ; i!=null ; i = i.next ) {
			count ++ ;
		}
		if (count == 1 && g.members[v].first.fnum == fnum ) {
			return true; 
		}
		
		return false; 
	}
	private static boolean checkdup ( ArrayList<String> a , String name ) {
		for ( int i = 0 ; i < a.size() ; i ++ ) {
			if ( a.get(i).toLowerCase().equals(name.toLowerCase()) ) { 
				return true ; 
			}
		}
		return false ; 
	}
}

