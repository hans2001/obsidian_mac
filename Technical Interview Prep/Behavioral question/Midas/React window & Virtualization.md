## **React Window:**  

A lightweight library for efficiently rendering long lists, grids, or tables. It renders only the visible items (and a little extra buffer) instead of the entire list.

## **Virtualization:**  

Only the portion of data that’s visible to the user is rendered.

	->reduces the memory footprint 
	->improves performance
	
especially when dealing with millions of records. With virtualization, as you scroll, new elements are rendered dynamically while off-screen elements are removed from the DOM.