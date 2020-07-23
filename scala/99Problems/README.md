# Ninty-Nine Scala Problems

<br>

## Working with lists
In Scala, lists are objects of type List[A], where A can be any type. Lists are effective for many recursive algorithms, because it's easy to add elements to the head of a list, and to get the tail of the list, which is everything but the first element.
- [[P01](./P01.md)] Find the last element of a list.
- [[P02](./P02.md)] Fint the last but one element of a list.
- [[P03](./P03.md)] Find the *K*th element of a list.
- [[P04](./P04.md)] Find the number of elements of a list.
- [[P05](./P05.md)] Reverse a list.
- [[P06](./P06.md)] Find out whether a list is a palindrome.
- [[P07](./P07.md)] Flatten a nested list structure.
- [[P08](./P08.md)] Eliminate consecutive duplicates of list elements.
- [[P09](./P09.md)] Pack consecutive duplicates of list elements into sublists.
- [[P10](./P10.md)] Run-length encoding of a list.
- [[P11](./P11.md)] Modified run-length encoding.
- [[P12](./P12.md)] Decode a run-length encoded list.
- [[P13](./P13.md)] Run-length encoding of a list (direct solution).
- [[P14](./P14.md)] Duplicate the elements of a list.
- [[P15](./P15.md)] Duplicate the elements of a list a given number of times.
- [[P16](./P16.md)] Drop eveery *N*th element from a list.
- [[P17](./P17.md)] Split a list into two parts.
- [[P18](./P18.md)] Extract a slice from a list.
- [[P19](./P19.md)] Rotate a list *N* places to the left.
- [[P20](./P20.md)] Remove the *K*th element from a list.
- [[P21](./P21.md)] Insert an element at a given position into a list.
- [[P22](./P22.md)] Create a list containing all integers within a given range.
- [[P23](./P23.md)] Extract a given number of randomly selected elements from a list.
- [[P24](./P24.md)] Lotto: Draw *N* different random number from the set 1.*M*.
- [[P25](./P25.md)] Generate a random permutation of the elements of a list.
- [[P26](./P26.md)] Generate the combinations of *K* distinct objects chosen from the *N* elements of a list.
- [[P27](./P27.md)] Group the elements of a set into disjoint subsets.
- [[P28](./P28.md)] Sorting a list of lists according to length of sublists.

<br>

## Arithmetic
For the next section, we're going to take a different tack with the solutions. We'll declare a new class, S99Int, and an implicit conversion from regular Ints. The [arithmetic1](./src/arithmetic1.scala) file contains the starting definitions for this section. Each individual solution will show the relevant additions to the S99Int class
- [[P31](./P31.md)] Determine whether a given integer number is prime.