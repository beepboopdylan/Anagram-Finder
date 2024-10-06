Dylan Tran
Date: 12/16/2023

The following are the average runtimes of each data structure when the bash command "time java AnagramFinder "least" words.txt <bst|avl|hash>" is run:

BST: Average Runtime = 3.364s

AVL: Average Runtime = 1.216s

Hash: Average Runtime = 0.552s

Initially, my hypothesis was that BST would be the slowest of the data structures, followed by AVL and then Hash. I expected hashmap to have the best runtime because given the nature of this project, separate chaining hashing would make the most sense. This is because the runtime for fetching the value of a key in a hashmap is theta(1), which is needed when calling get() to retrieve the MyList<String> values associated with the inputed word. This is comparatively faster than both BST and AVL. AVL follows next because its self-balancing properties allow for a worst case of O(lgN) runtime to look up the value of a key, while BST, which may not be balanced, has a worst case of O(N) runtime. My hypothesis was confirmed when running the algorithm, with hash being the fastest, followed by avl and then bst.