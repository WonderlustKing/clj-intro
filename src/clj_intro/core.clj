(ns clj-intro.core
  (:require [clojure.spec.alpha :as spec]
            [clojure.set :as set])
  (:import java.util.UUID)
  (:gen-class))

;; ----------- Data - Number literals ------------------

;; --- Strings ----
;; - Enclosed in double quotes " ", e.g
(def my-name "Christos")

;; Numbers represented as per Java. We have:
;; -Integers (if number is big will be read as Long)
;; -Floating point (read as Double)
;; -Octal (with a 0 prefix)
;; -hex (with a 0x prefix)
;; -ratios (31/2)
;;
;; see below examples

(def one-integer 291)

(def one-float 2.12)

(def one-octal 021)
;;evaluates to 17

(def one-hex 0xfffaff)
;;evaluates to 16775935

(def one-ratio 31/2)
;;evaluates to 31/2




;; ---- Characters ----
;; All characters are preceded by a backslash
;; Unicode characters are presented with \uNNNN as in Java
;; Octals are presented with \oNNN
;; The following characters correspond to the related characters
;;  \newline
;;  \space
;;  \tab
;;  \backspace
;;  \return
;;
;; check also the following examples

(def my-char \c)
;; evaluates to /c

;; evaluates to \tab
(def tab \tab)

;;We can try to print some dummy text to see how
;;the above \tab character is applying
(println tab "I'm printed with a tab space")


;; ---- Boolean ----
;; Pretty simple here, true and false values are supported. e.g

(def true-value true)
;;evaluates to true

(def false-value false)
;;evaluates to false


;; ---- Keywords ----
;; - All keywords must begin with a colon. e.g :name
;; - They cannot contain '.' values.
;; See the below example

(def my-keyword :favorite-team)
;;evaluates to :favorite-team


;; ---- Lists ----
;; Lists are consisted from zero or more forms enclosed in parentheses (a b c)
;; May contain any type of data.
;; We can imagine them like the Java's LinkedList. You can easy modify the head/tail
;; but provide linear access to elements.
;; Let's see some examples

(def my-num-list `(1 2 3 89 0 -2 1022 2))
;; pay attention to the way that we define a list.
;; See the ` character before the parentheses
;; Also check that we don't need a comma to separate the entries (a white space is more than enough)

(def my-string-list `("Chris" "Marie" "Fred" "Anna" "Fred"))

(def my-random-list `("Football" 12 :basketball 8 19 \c))

;; We can also use the 'list' function in order to create a new list
(list 1 2 "three" "four")
;; evaluates to (1 2 "three" "four")

;; We can retrieve an element from a list with the 'nth' function
(nth `(10 20 30 40) 2)
;; evaluates to 30

;; We can use the 'conj' function if we want to add an element into a list (the element will be added in the beginning)
(conj `(1 2 3 4) 5)
;; evaluates to (5 1 2 3 4)


;; ---- Vectors ----
;; Vectors are consisted from zero or more forms enclosed in square brackets [a b c]
;; May contain any type of data.
;; We can imagine them like Java's ArrayList. They provide a quick ( O(1) ) indexed random access
;; to it's elements. On the other hand they are generally more expensive to modify them.
;; Let's see some examples

(def my-num-vector [1 2 3 92 -90 - 8 0 223 2])

(def my-string-vector ["Chris" "Marie" "Ann" "Jane" "Chris"])

(def my-random-vector ["Hockey" 20 :football 18 "Chris" 10 12 13 \f \r \e \e])

(def my-vector-with-nested-vectors [ ["Chris" "Anna" "Mary"] [1 92 22 10 0] [:name 2 \c] ])

;; We can also use the 'vector' function in order to create a new vector
(vector "one" "two" "three")
;; evaluates to ["one" "two" "three"]

;; We can retrieve the element of a vector with the 'get' function
(get [10 15 20] 0)
;; evaluates to 10

;; We can use the 'conj' function in order to add an element (to the end) in an existing vector
(conj [1 2 3] 4)
;; evaluates to [1 2 3 4]


;; ---- seqs access/modification functions ----
;; Let's see some more functions which access/modify collections

;; 'first' returns the first element of a collection
(first [10 20 30])
;; evaluates to 10

;; 'second' returns the second element of a collection
(second [10 20 30])
;; evaluates to 20

;; 'last' returns the last element of a collection
(last [10 20 30])
;; evaluates to 30

;; 'rest' returns the elements after the first. If there no more items return empty seq
(rest [10 20 30])
;; evaluates to (20 30)

;; 'next' returns the elements after the first. If there no more items returns nil
(next [10 20 30])
;; evaluates to (20 30)

;; 'nth' returns tej value at the index
(nth [10 20 30] 1)
;; evaluates to 20

;; 'nth' works also with strings
(nth "Hello there" 1)
;; evaluates to \e

;; 'nth' take a 3rd argument also for the index-not-found case
(nth [10 20 30] 3 "cannot find")
;; evaluates to "cannot find"

;; 'take' returns a lazy sequence of the first n elements in the collection
(take 3 [10 20 30 40 50])
;; evaluates to (10 20 30)

;; 'take-nth' returns a lazy sequence of every nth item in collection
(take-nth 2 [10 20 30 40 50])
;; evaluates to (10 30 50)


;; 'butlast' returns a seq with all elements except the last one (in linear time)
(butlast [10 20 30 40])
;; evaluates (10 20 30)

;; 'drop-last' returns a lazy sequence with all the elements except the last n
(drop-last 2 [10 20 30 40])

;; 'cons' returns a new seq with the given element in the first position
(cons 1 [10 20 30])
;; evaluates to (1 10 20 30)

;; 'distinct' returns a lazy sequence by removing the duplicates elements of the given collection
(distinct [1 1 2 3 4 3 3 5 1])
;; evaluates to (1 2 3 4 5)

;; 'dedupe' returns a lazy sequence by removing consecutive duplicates in the given collections
(dedupe [1 1 2 2 2 3 4 3 4])
;; evaluates to (1 2 3 4 3 4)



;; ---- Maps ----
;; Maps are consisted from zero or more key/values pairs enclosed in braces {:a 1 :b 2 :c 3}
;; Same as Lists and Vectors the comma character is not required in order to separate the values.
;; But of course you can use it if you need.
;; Finally key and values can be any forms.
;; Examples:

(def my-map {:name "Christos"
             :age 27
             :favorite-language "Clojure"
             :hobbies ["Football" "Hiking" "Reading"]
             "quote" "Abrakatabra"})


(def my-nested-map {:name "Mary"
                    :hobbies {
                              :name "Swimming"
                              :how-often "5 times per week"
                              }
                    :age 29
                    })

;; We can create a map by using the 'hash-map' function
(hash-map :name "Christos" :age 12)
;;evaluates {:age 12, :name "Christos"}

;; We can look up values in maps with the 'get' function
(get {:name "Christos" :job "IT"} :name)
;;evaluates to "Christos"

;; In case a value is not existed into the map, 'get' function will return nil
(get {:name "Christos" :job "IT"} :age)
;; evaluates to nil

;; We can provide a default value for the 'get' function to return (instead of nil)
;; when a value is not existed
(get {:name "Christos" :job "IT"} :age "Cannot find")
;; evaluates to "Cannot find"

;; We can look up in nested maps with the 'get-in' function
(get-in {:name "Jane"
         :details {:id 12
                   :age 40
                   :eyes "Blue"}}
        [:details :eyes])
;;evaluates to "Blue"


;; We can check if a map contains a key with the 'contains?' function
(contains? #{:a 1 :b 2} :a)
;; evaluates to true


;; We can use 'val' to get a value from a map.
(val (first {:a 1 :b 2}))
;; evaluates to: 1

;; If we want to get all the values from a map we can use 'vals'
(vals {:a 1 :b 2 :c 3})
;; evaluates to: (1 2 3)

;; If we want to get the key from a map, we can use the 'key' function
(key (first {:a 1 :b 2}))
;; evaluates to: :a

;; And likes val-vals, there is also the 'keys' which returns all the keys from a map
(keys {:a 1 :b 2 :c 3})
;; evaluates to: (:a :b :c)

;; If we want to update the value of a key in a map, we can use the 'assoc' function
(assoc {:a 1 :b 2} :a 4)
;; evaluates to: {:a 4, :b 2}

;; We can use also 'assoc' to add new keys and values in an existing map
(assoc {:a 1 :b 2} :c 3 :d 4)
;; evaluates to: {:a 1, :b 2, :c 3, :d 4}

;; If we want to associate a value in a nested map we can use 'assoc-in' function
(assoc-in {:a 1 :b {:c 3}} [:b :c] 4)
;; evaluates to: {:a 1, :b {:c 4}}

;; Of course if any levels do not exist, a new entry will be created
(assoc-in {:a 1 :b {:c 3}} [:b :d] 5)
;; evaluates to: {:a 1, :b {:c 3, :d 5}}

;; If we want to merge 2 or more maps, we can use the 'merge' function.
;; If a key occurs in more than one map, the mapping from the latter (righter) wins
(merge {:a 1 :b 2} {:c 3 :d 4} {:e 5 :f 6})
;; evaluates to: {:a 1, :b 2, :c 3, :d 4, :e 5, :f 6}{:a 1, :b 2, :c 3, :d 4}


(merge {:a 1 :b 2} {:a 4 :b 5})
;; evaluates to: {:a 4, :b 5}


;; If we want to merge 2 or more maps, but in case of a same key we want to do
;; something else (apply a function) than the default behavior of merge (which keeps
;; the latest value) we can use 'merge-with' function.

(merge-with + {:a 1 :b 2} {:a 10 :b 20 :c 30})
;; evaluates to: {:a 11, :b 22, :c 30}

(merge-with into {:a [1 2 3] :b [70]} {:a [10 20] :b [100 200]})
;; evaluates to: {:a [1 2 3 10 20], :b [70 100 200]}


;; ---- Sets ----
;; Sets are consisted from zero or more unique forms enclosed in braces preceded by #. #{:a :b :c}
;; Same as previous comma character is not required in order to separate the values.
;; Examples:

(def my-set #{"Christos" "Anna" :a 23 })

;;(def my-set-with-duplicate #{1 2 3 1 2 3})
;; this will not evaluate, a syntax error (Duplicate key: 1) will occur


;;We can also use the 'hash-set' function in order to create a set
(hash-set 1 1 2 2)
;;evaluates to #{1 2}

;;We can create a set from an existing vector or list by using the 'set' function
(set [1 2 2 2 3 4 4 4])
;; evaluates to #{1 2 3 4}

;; In the same way as maps we can use the 'contains?' function to check if our set contains a specific value
(contains? #{1 2 3} 1)
;; evaluates to true

;; We can add a value to a set with the 'conj' function
(conj #{:a :b} :c)
;; evaluates to #{:c :b :a} (the order of the column is not standard)

;; And we can remove a value from a set with the 'disj' function
(disj #{:a :b} :b)
;;evaluates to: #{:a}

;; If we want to get a union of 2 or more sets we can use the 'clojure.set/union' function
(clojure.set/union #{1 5} #{1 2 3 4})
;; evaluates to: #{1 4 3 2 5}


;; If we want a set without the elements of other sets we can use the 'clojure.set/difference'
(clojure.set/difference #{1 3 5 7} #{1 2 3 4} #{1 7})
;; evluates to: #{5}


;; If we want a set that is the intersection of the input sets, then we can use
;; 'clojure.set/intersection'
(clojure.set/intersection #{1 2 3} #{2 3 4} #{2 3 59})
;; evaluates to: #{3 2}

(clojure.set/intersection #{1 3 4} #{5 6 7})
;;evaluates to: #{}


;; If we want to get a set with the elements that a pred function is true
;; we can use 'clojure.set/select'
(clojure.set/select number? #{1 2 "Hello" nil 10 -2})
;;evaluates to: #{1 -2 2 10}





;; ----------- Conditionals ------------------

;; ---- If ----
;; if consists of a condition, a 'then' and an 'else' (optional) parts
;; Example:

;;below code checks if the number 2 is even (by using the built-in even? function)
;;if true prints "even" otherwise prints "odd". In our case here it will evaluates to "even"
(if (even? 2) "even" "odd")

(if (even? 3) "even" "odd")
;;evaluates to "odd"

(if (even? 3) "even")
;;evaluates to nill since we didn't provide an else part

;; One note here. All values in Clojure are logically true or false.
;; The only false values are the boolean false and the nil (the same with Java's null).
;; All other values are logically true. For example

(if "Clojure" "I'm true!!" "I'm false!!")
;;evaluates to "I'm true"

(if 10 "I'm true!!" "I'm false!!")
;;evaluates to "I'm true"

(if false "I'm true!!" "I'm false!!")
;;evaluate to "I'm false!!"

(if nil "I'm true!!" "I'm false!!")
;;evaluate to "I'm false!!"


;; ---- If and Do ----
;; One problem that may occur with the 'if' is that it only takes a single expression for
;; the 'then' and 'else' blocks. Our savior here is the 'do'. 'do' allow us to create
;; larger blocks. Let's see an example:

(if true
  (do (println "Success")
      true)
  (do (println "Failure")
      false))
;;above will print 'Success' and will return true for the if expression


;; ---- Boolean operators (or, and) ----
;;
;; --- or ---
;; 'or' operator returns either the first truthy value or the last value.
;; For example:

(or false :name nil)
;; evaluates to :name

(or false false nil)
;; evaluate to nil

;; --- and ---
;; 'and' operator returns the first falsey value or (if no values are falsey) the last truthy value.
;; For example:

(and "hello" :name)
;;evaluates to :name

(and "Hello" nil false)
;;evaluates to nil


;; ---- when ----
;; 'when' operator is like a combination of the 'if' and 'do' but without the else block
;; We can use it when we want to do multiple things when some condition is true.
;; Let's see an example


(when true
  (println "Success")
  "All good")
;; will print 'Success' and will return the "All good"

(when false
  (println "Success")
  "All good")
;; will evaluate to nil


;; ---- cond ----
;; 'cond' is a series of tests and expressions. Each test is evaluated in order
;; and the expression is evaluated and returned for the first true test.
;; For example:

(let [x 5]
  (cond
    (< x 2) "X is less than 2"
    (< x 7) "X is less than 7"))
;; evaluates to "X is less than 7"


;; ---- cond with else ----
;; If none test is satisfied, cond returns nil. In case we want to return something
;; as default or fallback value, we can use a final test of :else keyword. Keywords evaluates
;; to true, so this will be always selected as a final default value.
;; An example:


(let [x 15]
  (cond
    (< x 2) "X is less than 2"
    (< x 7) "X is less than 7"
    :else "X is >= to 7"))
;;evaluates to "X is >= to 7"


;; ---- condp ----
;; 'condp' takes a binary predicate, an expression and a set of clauses.
;; Each clause can take the following form:
;; value-to-check   to-do-if-predicate-evaluates-to-true
;; For each clause the predicate is evaluated, if it's true it returns the right part of the clause form.
;; In case the predicate is not match with any clause an IllegalArgumentException will be thrown
;; For example:

;;Below code keep in a temp variable (name) the "Clojure" string.
;;Then we pass in the 'condp' the '=' function, the 'name' variable and a set of clauses with the form:
;; value-to-check-for-equality  |  what-to-do-if-equality-matched
;;Since the value of the 'name' variable match the second clause, the 'I love Clojure' will return
(let [name "Clojure"]
  (condp = name
    "Java" "I love java"
    "Clojure" "I love Clojure"
    "C++" "I love C++"))


;; ---- case ----
;; 'case' compares an argument to a series of values to find a match. If no value matches the given argument
;; an IllegalArgument exception will be thrown.
;; For example:

(let [x 5]
  (case x
    5 "X is 5"
    7 "X is 7"))
;; evaluates to "X is 5"

;; 'case' supports a final expression that will be evaluated if none of the values matches the given arguments.
;; For example:

(let [x 15]
  (case x
    5 "X is 5"
    7 "X is 7"
    "X is not 5 nor 7"))
;;evaluates to "X is not 5 nor 7"



;; ----------- Defining Functions ------------------
;; A function definition is composed of seven main parts
;; 1) An open parenthesis (
;; 2) The 'defn' word
;; 3) The function name
;; 4) A docstring which describes what the function do (optional)
;; 5) Parameters listed in brackets
;; 6) Function body
;; 7) A close parenthesis )
;; Let's see an example

(defn present-my-name
  "Return the given name following by some adjectives"
  [name]
  (str "Ladies and gentleman here is the one... the fantastic... the unique... " name "!!"))


;; Functions also support overloading. This means that we can define a function
;; so a different function body will run depending on the given parameters (arguments).
;; Let's see an example

(defn add-numbers
  ([num1 num2 num3]
   (+ num1 num2 num3))
  ([num1 num2]
   (+ num1 num2))
  ([num1]
   num1))

;; We can use above function with the following way

(add-numbers 2 3 4)
;; evaluates to 9

(add-numbers 2 8)
;; evaluates to 10

(add-numbers 1)
;; evaluates to 1


;; Clojure allows us to define functions with a rest parameter, like saying:
;; 'put all these arguments in a list'. The rest parameter is indicated by an '&' character.
;; Let's see an example:

(defn my-hobbies
  [name & hobbies]
  (str "Hi, my name is " name " and my hobbies are: "
       (clojure.string/join ", " hobbies)))

(my-hobbies "Mike" "Football" "Music" "Theater")
;; evaluates to "Hi, my name is Mike and my hobbies are: Football, Music, Theater


;; ---- Anonymous Functions ----
;; In Clojure functions are able to not have any names at all. These functions are called
;; anonymous functions. We can create anonymous functions in 2 ways. The first is to use the 'fn'.
;; Let's see an example

;; Here we create an anonymous function which take one argument and multiple this arg with 3
((fn [x] (* 3 x)) 6)
;; evaluates to 18

;; Anonymous functions are almost identically to the 'defn' way. The parameter lists and function
;; bodies work exactly the same. We can also associate an anonymous function with a name.
;; For example:

(def multiply-with-3
  (fn [x] (* 3 x)))

(multiply-with-3 10)
;; evaluates to 30


;; The other way to define an anonymous function is the following:
#(* 3 %)

;; We can try to call the above
(#(* 3 %) 10)
;; will evaluate to 30

;; Let's try to understand better the above. This hash-tag along side with the opening parenthesis
;; is the way to start defining an anonymous function. Like the (fn in the previous case.
;; then we can see that the (* 3 %) is the body of the function. The interesting part is the % character,
;; which indicates the argument passed to the function. If our anonymous function
;; takes more than one arguments  we can distinguish them like the following %1, %2, %3, etc..
;; % is equivalent to %1.
;; Let's see another example

(#(str "Hello " %1 " and " %2 " how are you today?") "Nick" "Davies")
;; will evaluate to "Hello Nick and Davies how are you today?


;; ----------- Maps as functions ------------------
;; We can use a map (or a set) as function, for example:

({:a 1 :b 10 :c 20} :b)
;; evaluates to 10
;; It is like we use the get function to retrieve the value of key in a map

;; We can also use maps as predicates. Let's see another example.
;; Let's suppose that we have a map with all users of our app (it just a simple map<id name>
;; and we also have a map with the banned users. Now we want to find the not banned users.

;; here is the map with the 2 banned users
(def banned-users {100 "Chris" 211 "John"})

;; here is the map with all users
(def all-users {10 "Bill" 100 "Chris" 122 "Joan" 211 "John" 334 "Fariel" 433 "John"})

;; here is the function which checks if a user is not banned by
;; searching the given id(key) in the banned-users map.
;; In case the result is nil (which means the key is not found into banned-users map)
;; the user is not banned

(defn allowed-user?
  [id]
  (nil? (banned-users id)))

;; here is how we call the above method. We want to do a filter in the keys of all-users map
;; and the result (which will be a list) to receive it back in a map.
(into {} (filter #(allowed-user? (key %)) all-users))

;; a better definition of the above method by using the 'comp' function
(into {} (filter (comp allowed-user? key) all-users))


;; We can also use sets as predicates. For example

;; below method removes the set's elements from the list. Here the set act as the predicate function
(remove #{1 2 3} `(1 2 3 4 5 6))
;; evaluates to (4 5 6)



;; ----------- Function constructors ------------------

;; ---- partial ----
;; Takes a function and any number of arguments and returns a new function.
;; When we call the returned function, it calls the initial function with the initial arguments
;; along side with the new arguments. For example

((partial + 5) 10)
;; evaluates to 15

;; we can assign the partial in a variable and then use it around
(def add-10 (partial + 10))

(add-10 10)
;; evaluates to 20

(add-10 20)
;; evaluates to 30

;; and another example
(def add-famous-names
  (partial conj ["Rich Hickey" "James Gosling" "Robert C. Martin" "Martin Fowler"]))

(add-famous-names "John Doe" "Peter P.")
;; evaluates to ["Rich Hickey" "James Gosling" "Robert C. Martin" "Martin Fowler" "John Doe" "Peter P."]


;; ---- complement ----
;; Often we want to use the negation of an already existing function.
;; Let's take for example the previous 'allowed-user?' function from 'Maps as functions' section
;; What if we want a predicate function which check if a user is banned?
;; Instead of re-write almost the same code as in 'allowed-user?' we can easily do the following:

(def banned-user? (complement allowed-user?))

(banned-user? 100)
;; evaluates to true



;; ---- comp ----
;; We can composite functions by using the 'comp' function. Let's see an example

((comp inc *) 5 5)
;; evaluates to 26

;; Above we create an anonymous function by composing 'inc' and '*' functions.
;; Then we applied the anonymous function to the arguments 5 5. The function multiplies (the '*' function)
;; the numbers 5 and 5 and then increase ('inc' function) the result by one.
;; One detail here is that the rightest function into the comp definition can take any number of args
;; whereas the remaining functions must be able to take only one (the result from the previous)

;; Let's see another example
(def person {:name "John" :attributes {:hair "curly"
                                       :eyes "brown"
                                       :height 1.82}})

(def person-height (comp :height :attributes))
(def person-eyes (comp :eyes :attributes))
(def person-hair (comp :hair :attributes))

(person-height person)
;;evaluates to 1.82

(person-eyes person)
;;evaluates to "brown"

(person-hair person)
;;evaluates to "curly"



;; ---- juxt ----
;; Takes a set of functions and return a function which when received any
;; arguments, applies in each function (from the set) the passed arguments
;; and returns a vector with the results. Let's see an example

((juxt + * max min) 5 10 15)
;; evaluate to [30 750 15 5]
;;
;; What is happening above is that we create a function by passing a set of functions (+ - max min)
;; to the 'juxt' function. After that we pass 3 arguments to this new function which
;; apply them to each function (from the 'juxt' definition). The first thing that is executed is (+ 5 10 15), the second one (* 5 10 15) etc..
;; All results are added in a vector


;; Let's see another example where we want to define a function which will return the
;; :name and the height-attribute from the previous person map
(def name-and-height (juxt :name (comp :height :attributes)))

(name-and-height person)
;; evaluates to ["John" 1.82]


;; ---- repeatedly ----
;; Takes a function of no args and an optional number N. If the number is not provided
;; makes infinite calls to the given function. If the number is provided makes N calls to the given function.
;; For example:

;; below we do a forever call to the anonymous function #(rand-int 10) and we take the first 3 random numbers
;; repeatedly returns a lazy sequence, it will never realized if we dont use any function to take any result from it
;; Here we use take which gets 3 values from this 'infinity' call
(take 3 (repeatedly #(rand-int 10)))
;; evaluates to a list with 3 random numbers (with a value 0-9)


;;we can re-write the above like this:
(repeatedly 3 #(rand-int 10))





;; ----------- let & destructuring ------------------
;;
;; ---- destructuring ----
;; Destructuring lets us bind names to values within a collection.
;; For example:

(defn get-first
  [[first-element]]
  first-element)

(get-first ["sun" "moon" "earth"])
;; evaluates "sun"

;; We can bind more than one value of course
(defn get-first-and-second
  [[first-element second-element]]
  [first-element second-element])

(get-first-and-second ["sun" "moon" "earth" "water"])
;; evaluates to ["sun" "moon"]

(defn categorize-elements
  [[important1 important2 & others]]
  {:important   [important1 important2]
   :unimportant (apply vector others)})

(categorize-elements ["sun" "water" "newsletters" "football"])
;; evaluates to {:important ["sun" "water"], :unimportant ["newsletters" "football"]}


;; ---- let ----
;; We can use 'let' to introduce a new scope where we can bind some variables.
;; Keep in mind that any binding variable its only leave in the specific scope that let introduced.
;; For example


(def global-scope-name "Alex")

(let [global-scope-name "Acantha"]
  global-scope-name)
;; evaluates to "Acantha"

global-scope-name
;; evaluates to "Alex"

;; And some more examples

(let [num1 2 num2 4]
  (+ num1 num2))
;; evaluates to 6

(defn add-first-two
  [nums]
  (let [[x y] nums]
    (+ x y)))

(add-first-two [2 4 5 7])
;; evaluates to 6

(let [x (+ 3 4)
      [y z] [4 1]]
  (+ x y z))
;; evaluates to 12

;; we can use :as to bind the entire vector
(let [[first-item :as entire] [10 20 30 40 50]]
  (str "first item is " first-item " and the entire list is " entire))

;; we can use also the '&' character to get the remaining parts (except first) from a collection
(let [[first-item & remaining] [10 20 30 40 50]]
  (str "first item is " first-item " and the remaining " remaining))

;; we can also destructure a map
(let [{a-value :a} {:a 10 :b 20 :c 30}]
  a-value)
;; evaluates to 10

;;we can use :or to get a value (as default) from a map if the specific key is not existed in the first map
(let [{d-value :d :or {d-value 0}} {:a 10 :b 20 :c 30}]
  d-value)
;; evaluates to 0

;; we can use the :keys to get the value from many keywords
(let [{:keys [a b c]} {:a 10 :b 20}]
  [a b c])
;; evaluates to [10 20 nil]

;; of course we can combine :keys and :or
(let [{:keys [a b c] :or {c 30}} {:a 10 :b 20}]
  [a b c])
;; evaluates to [10 20 30]

;; :keys is for associative values with keyword keys, but there are also :strs for strings
;; and :syms for symbols keys. For example:

(let [{:strs [a b c]} {"a" 10 "b" 20 "c" 30}]
  [a b c])
;; evaluates to [10 20 30]

(let [{:syms [a b c]} {'a 10 'b 20 'c 30}]
  [a b c])
;; evaluates to [10 20 30]


;; we can use :keys with a lists or sequences of key-value pairs
(defn configure
  [val & {:keys [debug]}]
  [val debug])

(configure 10 :debug true)
;; evaluates to [10 true]


;; ---- if-let ----
;; 'if-let' binds a value if an expression evaluates to true,
;; otherwise if an 'else' clause has been provided, perform the else clause. For example:

(if-let [x true]
  true
  false)
;; evaluates to true

(if-let [x false]
  true
  false)
;; evaluates to false

(if-let [x false]
  true)
;; evaluates to nil

(defn sum-numbers
  [random-coll]
  (if-let [nums (not-empty (filter number? random-coll))]
    (apply + nums)
    "None number is found"))

(sum-numbers ["hello" 2 5 3 "world"])
;; evaluates to 10

(sum-numbers ["hello" "world"])
;; evaluates to "None number is found"


;; ---- when-let ----
;; 'when-let' behaves basically the same with 'if-let' except there is no 'else' block
;; and we can pass as many forms to the 'then' block as we would like
;; For example:

(when-let [x true]
  (println x)
  (str "Result is " x))
;; will print true to the output and will return "Result is true"

(when-let [x false]
  (println x)
  (str "Result is " x))
;; evaluates to nil

;;we can try to re-write the previous 'if-let' example by using 'when-let'
(defn sum-numbers-with-when-let
  [random-coll]
  (when-let [nums (not-empty (filter number? random-coll))]
    (apply + nums)))

(sum-numbers-with-when-let ["hello" 2 5 3 "world"])
;; evaluates to 10

(sum-numbers-with-when-let ["hello" "world"])
;; evaluates to nil



;; ----------- loop & recur ------------------

;; ---- loop ----
;; loop provides a way to do recursion in Clojure.
;; For example

(loop [iteration 0
       sum 0]
  (println (str "Iteration: " iteration))
  (if (> iteration 5)
    (println "Sum: " sum "\nBye bye!")
    (recur (inc iteration) (+ sum iteration))))
;;prints
;; Iteration: 0
;;Iteration: 1
;;Iteration: 2
;;Iteration: 3
;;Iteration: 4
;;Iteration: 5
;;Iteration: 6
;;Sum:  15
;;Bye bye!

;; ---- recur ----
;; We can re-write above method by using-recur a function. For example

(defn recur-sum-printer
  ([]
   (recur-sum-printer 0 0))
  ([iteration sum]
   (println (str "Iteration: " iteration))
   (if (> iteration 5)
     (println "Sum: " sum "\nBye bye")
     (recur-sum-printer (inc iteration) (+ sum iteration)))))

;; we can replace the final call to the method name by using recur instead
;; this way we gain better performance (tail recursion)

(defn recur-sum-printer-better
  ([]
   (recur-sum-printer-better 0 0))
  ([iteration sum]
   (println (str "Iteration: " iteration))
   (if (> iteration 5)
     (println "Sum: " sum "\nBye bye")
     (recur (inc iteration) (+ sum iteration)))))



;; ----------- reduce ------------------
;; reduce is like we saying 'process each element in a sequence and build a result'. For example:

(reduce + [1 2 3 4])
;; evaluates to 10

;;It is like telling Clojure to do this:
(+ (+ (+ 1 2) 3) 4)

;; reduce also takes an optional initial value. For example:
(reduce + 10 [1 2 3 4])
;; evaluates to 20

;; Here is another example where we convert a list of map to a single map
(reduce into {} [{:a 10 :b 20} {:c 30 :d 40}])
;; evaluates to {:a 10, :b 20, :c 30, :d 40}

;; or we can use reduce change the values of a map
;; check that we can destructure map's key/value
(reduce (fn [result [k v]]
          (assoc result k (* 10 v)))
        {}
        {:a 1 :b 2 :c 3})
;; evaluates to {:a 10, :b 20, :c 30}

;; or the values of a vector
(reduce #(conj %1 (* 10 %2)) [] [1 2 3 4])
;; evaluates to [10 20 30 40]



;; ---- reduced ----
;; If we want to terminate a reduce process we can use the 'reduced'.
;; For example lets suppose that we want a create a function which calculate the sum
;; of the given number until the 100 values. After 100 it will terminate by returning a "To long sum" string

(reduce (fn [result x]
          (if (> result 100)
            (reduced "To long sum")
            (+ result x)))
        (range 10))
;; evaluates to 45


(reduce (fn [result x]
          (if (> result 100)
            (reduced "To long sum")
            (+ result x)))
        (range 20))
;; evaluates to "To long sum"


;; ---- reductions ----
;; We can get a sequence of the reduce intermediate values by using the 'reductions'.
;; For example

(reductions + [1 2 3 4])
;; evaluates to (1 3 6 10)
;; it starts with the first column 1 and add it in the result. Then it adds the 1 + 2 --> 3 value in the result
;; then the 3 + 3 ---> 6 and finally the 6 + 4 ---> 10


(reductions + 2 [1 2 3 4])
;; evaluates to (2 3 5 8 12)
;; same as above but this time we start with the initial value 2


;; lets see what we will get if use the reduction for one of the previous reduce examples
(reductions #(conj %1 (* 10 %2)) [] [1 2 3 4])
;; evaluates to ([] [10] [10 20] [10 20 30] [10 20 30 40])






;; ----------- filter/remove & predicates ------------------
;; Predicates are functions that evaluate a condition and provide a value of either true or false.
;; Clojure have pre-defined predicates method. Usually these methods have a ? in the end of their name.
;; For example: even? odd? neg? empty? number? some? every? etc.

(even? 2)
;; evaluate to true

(even? 3)
;; evaluate to false

(empty? [])
;; evaluates to true

(empty? `())
;; evaluates to true

(empty? [1 2 3])
;; evaluates to false

(empty? nil)
;; evaluates to true

;; some? will always evaluate to true except if the given argument is nil. It is like a not-nil implementation
(some? nil)
;; evaluates to false

(some? "test")
(some? [1 "s"])
(some? {:a 10})
;; all above will evaluate to true

;; every? take a predicate and a collection items.
;; Returns true if the given predicate is true for every item in the collection. For example
(every? number? [1 2 3 10 200])
;; evaluates to true

(every? number? [2 29 "hello" 111])
;; evaluates to false since the "hello" is not a number

;; of course we can also use a custom predicate.
;; Let's say we want to check if all the elements of a collection is < than 5
(every? #(< % 5) [1 2 3 4 5 6 7 8 9])
;; evaluates to false

(every? #(< % 5) [1 2 3 4 0 -2 -6])
;; evaluates to true

;; some will return the first logical true value of the prediction in the collections or else nil
(some even? [1 2 3 4])
;; evaluates to true since 2 is even

(some even? [1 3 5])
;; evaluates to nil since no number into collection is even

;; ---- filter/remove ----
;; 'filter' and 'remove' are functions which take a predicate and a collection and return a sequence (lazy)
;; of the items in collection for which the predicate function return a logical true (filter) or false (remove).
;; For example, we can use some of the above predicates with the filter function and a collection:

(filter even? [1 2 3 4])
;; evaluates to (2 4) --> keeps only the even numbers

(remove even? [1 2 3 4])
;; evaluates to (1 3) ---> removes any even number

;; we can use some? to keep the not nil elements
(filter some? [1 2 "hello" \c nil {:a 2 :b 3} nil 10 :x])
;; evaluates to (1 2 "hello" \c {:a 2, :b 3} 10 :x)

;; we can use remove with 'empty?' to remove any empty vectors from a root vector
(remove empty? [[1 2 3] [4 5 6] [] [7 8 9] [] [10]])
;; evaluates to ([1 2 3] [4 5 6] [7 8 9] [10])


;; we can use of course custom predicates with both filter and remove. For example:
(filter #(> % 5) [-2 0 1 2 4 5 8 9 10])
;; evaluates to (8 9 10) ---> keeps only the numbers which are > 5

(remove #(= (count %) 1) ["h" "e" "hello" "w" "word" [1] [1 2 3]])
;; evaluates to ("hello" "word" [1 2 3]) ---> removes the elements in the collection with size = 1


(remove #(or (= "Chris" %) (= "Marie" %)) ["Daniel" "Brock" "Ariel" "Chris" "John" "Marie"])
;; evaluates to ("Daniel" "Brock" "Ariel" "John") ---> removes the "Chris" and "Marie" values from the collection

;;same with the above but by using the 'some-fn'
(remove (some-fn #(= "Chris" %) #(= "Marie" %)) ["Daniel" "Brock" "Ariel" "Chris" "John" "Marie"])
;; evaluates to ("Daniel" "Brock" "Ariel" "John")


;; in case we want to filter for even number in a vector of vectors we can do the following
(map #(filter even? %) [[1 2 3] [4 5 6]])
;; evaluates to ((2) (4 6))

;; and in case we want to get the above result in a flat list
(mapcat #(filter even? %) [[1 2 3] [4 5 6]])
;; evaluates to (2 4 6)






;; ----------- Map ------------------
;; Takes a function and any collections and
;; returns a sequence (lazy) consisting of the result of applying the given function
;; to the set of first items of each collection, followed by applying the given function
;; to the set of the second items in each collections... etc... until any of the collections
;; is exhausted. Let's see some examples:


;;We have already seen an example of map in the previous 'filter/remove' section:

(map #(filter even? %) [[1 2 3] [4 5 6]])
;; This is like we say: OK Clojure, for each element (vector) of the given vector
;; apply the given anonymous function and keep the result in a single list (sequence).
;; which evaluate to ( (2) (4 6) )


;; The only difference with the mapcat case:
(mapcat #(filter even? %) [[1 2 3] [4 5 6]])

;; is that the mapcat will apply a concatenation between the result of map and thus
;; we will have a single flat result
;; (2 4 6)


;; Let's see some other examples:

(map inc [1 2 3 4 5])
;; evaluates to (2 3 4 5 6) ---> apply the inc function for each element of the given vector

;; we can pass more than one collection
(map + [1 2 3] [4 5 6])
;; evaluates to (5 7 9) ----> apply the + function for the first element of both collection, then for the seconds, etc


;; of course we can pass custom functions
(map #(str "Hello, " % "!") ["Chris" "John" "Anna"])
;; evaluates to ("Hello, Chris!" "Hello, John!" "Hello, Anna!")


;; we can pass maps as collections. For example, here is how we get the values of a map
(map second {:a 2 :b 3})
;; evaluates to (2 3)


;; or we can get the values of a map and do something with them
(map (comp dec second) {:a 2 :b 3})
;; evaluates to (1 2)


;; or here is how we can get the value of a specific key from many maps in a collection
(map :a [{:a 2 :b 3} {:a 1 :b 3}])
;; evaluates to (2 1)


;; finally it is possible for a map to take a collection of functions as argument

(def my-sum #(reduce + %))
(def my-avg #(/ (my-sum %) (count %)))

(map #(% [10 20 30]) [my-sum my-avg count])
;; evaluates to (60 20 3) ----> 60 is the sum, 20 is the avg, 3 is the count








;; ----------- Laziness ------------------
;; As we saw earlier, 'map' and 'filter/remove' return a lazy seq. A lazy seq is a seq whose members
;; aren't computed until we try to access them. Computing a lazy seq's members is called realization.
;; Lazy seqs allow us to make our programs more efficient and to construct infinite sequences.
;; When it is necessary to fully realize a lazy sequence, Clojure provides a way to force evaluation.
;; Let's see an example to understand laziness.

(def programmers-db
  {1 {:name "Chris"     :fav-language "Clojure"     :work-language "Java"}
   2 {:name "Stefanie"  :fav-language "Javascript"  :work-language "Javascript"}
   3 {:name "Alex"      :fav-language "Lisp"        :work-language "Clojure"}
   4 {:name "Juan"      :fav-language "Clojure"     :work-language "Clojure"}
   5 {:name "Mara"      :fav-language "Java"        :work-language "Java"}})

(defn programmer-details
  [id]
  (Thread/sleep 1000)
  (get programmers-db id))

(defn clojure-everywhere?
  [record]
  (and (= "Clojure" (:fav-language record))
       (= "Clojure" (:work-language record))
       record))

(defn identify-clojurian
  [ids]
  (first (filter clojure-everywhere? (map programmer-details ids))))

;; to get the programmers-details for one id, would take 1 sec
(time (programmer-details 1))
;; evaluates to: Elapsed time: 1000.204082 msecs"
;; {:name "Chris", :fav-language "Clojure", :work-language "Java"}


;; Let's define now a variable which will apply the programmer-details in a range of 1M ids.
(time (def mapped-details (map programmer-details (range 1 1000001))))
;; evaluates to: "Elapsed time: 0.143193 msecs"
;;=> #'my-playground.core/mapped-details-1

;; Because 'range' and 'map' are lazy above definition evaluates instantly.
;; Actually, map didn't apply programmers-details to any of the element's returned by range.
;; If 'map' was nonlazy this operation would take one million seconds!!
;; 'map' will realize any elements once we try to get one. For example:

;;(time (first mapped-details))
;;"Elapsed time: 32004.656552 msecs"
;; => {:name "Chris", :fav-language "Clojure", :work-language "Java"}

;;The reason it took 32 seconds is that Clojure chunks its lazy sequences,
;; which just means that whenever Clojure has to realize an element,
;; it preemptively realizes some of the next elements as well

;; Thankfully, lazy seq elements need to be realized only once,
;; if we try to get again the first element of mapped-details we will get this:

;;(time (first mapped-details))
;;"Elapsed time: 0.074106 msecs"
;;=> {:name "Chris", :fav-language "Clojure", :work-language "Java"}

;; so, if we try now to run the identify-clojurian function by passing one million ids we will get:
;;(time (identify-clojurian (range 1 1000001)))
;;"Elapsed time: 32005.110499 msecs"
;;=> {:name "Juan", :fav-language "Clojure", :work-language "Clojure"}

;; one note here, if our clojurian was in the last position of the 1M ids, this would mean that
;; this operation would take 12M seconds!


;;As we said, we can create infinite sequences with the lazy seq. For example:
;; if we want a function which produces a lazy sequence of random UUIDs we could use
;; the 'lazy-seq'

(defn uuid-seq
  []
  (lazy-seq
    (cons (str (UUID/randomUUID)) (uuid-seq))))

(take 2 (uuid-seq))
;; evaluates to: ("e6f7efa4-b181-4255-b5f5-45a120becf04" "41342545-8b3a-4b2c-9f90-4f6b4932f94a")



;; Lazy sequences can be forcefully realized with 'dorun' and 'doall'
;; The difference between the two is that dorun throws away all results
;; while doall returns computed values:


(dorun (map inc [1 2 3]))
;; evaluates to nil

(doall (map inc [1 2 3]))
;; evaluates to (2 3 4)


;; let's see some other examples
(take 5 (map prn (range 10)))
;; someone will expect to see as output: 0 1 2 3 4
;; but above will evaluate to: 0 1 2 3 4 5 6 7 8 9
;; The above happened due to the chunks way that clojure uses for the lazy sequences.

; or let's see also this:
(do (map prn [0 1 2 3 4 5])
    (println "Hello world"))

;; this will evaluate to: "Hello world"
;; the map prn will never shown, since we never realize the map lazy-sequence.


;; for the second case we can use the 'dorun'/'doall'
(do (doall (map prn [0 1 2 3 4]))
    (println "Hello world"))
;; evaluates to :
;; 0
;; 1
;; 2
;; 3
;; 4
;; Hello world

;; or we can use the 'doseq' which is a good choice especially if we work with side effects

(doseq [x (take 5 (range 10))]
  (prn x))
;; evaluates to: 0 1 2 3 4

(do (doseq [x [0 1 2 3 4]]
      (prn x))
    (println "Hello world"))


;; doseq support more than one binding which iterating in a nested way. For example:
(doseq [x [1 2 3]
        y [1 2 3]]
  (prn [x y]))
;; evaluates to:
;; [1 1]
;; [1 2]
;; [1 3]
;; [2 1]
;; [2 2]
;; [2 3]
;; [3 1]
;; [3 2]
;; [3 3]



;; doseq support also some keywords: :let :when :while
;; For example

(doseq [x (range 10)
        :when (even? x)
        :let [y (* x x)]]
  (println y))





;; -------------- Combine Seqs ---------------------


;; We can use 'concat' which returns a lazy seq representing the concatenation of the elements in the given collection. For example:


(concat [1 2 3] [4 5 6 7])
;; evaluates to: (1 2 3 4 5 6 7)

(concat [1 2] [3 1 2] [2] [4 51])
;; evaluates to: (1 2 3 1 2 2 4 51)

;; any nil values will not be included in the final sequency
(concat [10 20] nil [1 2 3])
;; evaluates to: (10 20 1 2 3)

;; but any nil values into a given collection will be included
(concat [10 20 nil] [1 2 3])
;; evaluates to: (10 20 nil 1 2 3)


;; we can concat string values which will retrn a sequency of the string's characters
(concat "hello" "world")
;; evaluates to: (\h \e \l \l \o \w \o \r \l \d)


;; finally we can concat map values
(concat {:a "A" :b "B" :c "C" } {:d "D" :e "E"})
;; evaluates to: ([:a "A"] [:b "B"] [:c "C"] [:d "D"] [:e "E"])




;; 'interleave' can be used when we want to create a lazy sequency from given collections
;; in the following  order: first item in each collection, then the second etc..
;; For example:

(interleave [1 2 3] [10 20 30] [100 200 300])
;; evaluates to: (1 10 100 2 20 200 3 30 300)

(interleave [:a :b :c] [1 2 3])
;;evaluates to: (:a 1 :b 2 :c 3)


;; shortest input stops interleave
(interleave [:a :b :c] [1 2 3 4 5])
;;evaluates to: (:a 1 :b 2 :c 3)




;; 'interpose' can be used when want to create a lazy sequency from a given collection
;; with the elements seperated by a given seperator. For example:

(interpose "|" ["Chris" "Nick" "Julia"])
;; evaluates to: ("Chris" "|" "Nick" "|" "Julia")


(interpose "and" ["James" "Xavier" "Eva"])
;; evaluates to: ("James" "and" "Xavier" "and" "Eva")




;; 'mapcat' returns the result of applying 'concat' to the result
;; of applying map to a given function and collections. For example:

(mapcat list [:a :b :c] [1 2 3])
;; evaluates to: (:a 1 :b 2 :c 3)


(mapcat #(repeat 4 %) [1 2 3 4])
;;evaluates to: (1 1 1 1 2 2 2 2 3 3 3 3 4 4 4 4)


(mapcat #(remove even? %) [[1 2 3] [4 5 6] [7 8 9]])
;; evaluates to: (1 3 5 7 9)



;; 'zipmap' returns a map with the given collection keys corresponding to the given
;; collection values. For example:

(zipmap [:a :b :c] [1 2 3])
;; evaluates to: {:a 1, :b 2, :c 3}


;; extra values in any of both collections will not included in the result
(zipmap [:a :b :c :d] [10 20 30])
;; evaluates to: {:a 10, :b 20, :c 30}

(zipmap [:a :b :c] [10 20 30 40])
;; evaluates to: {:a 10, :b 20, :c 30}


(zipmap [:a :b :c] (repeat 0))
;; evaluates to: {:a 0, :b 0, :c 0}







;; -------------- reduce-kv  ---------------------
;;
;; 'reduce-kv' is like 'reduce' but the given function should accept 3 arguments.
;; It is used mainly for maps (key-value) but it can be used for vectors as well
;; where the keys will be the ordinals. Let's see some examples:

;; If we have a map and we want to increase the value of each entry, we can do the
;; following:

(reduce-kv (fn [m k v]
             (assoc m k (inc v)))
           {}
           {:a 1 :b 2 :c 3 :d 4})

;; evaluates to: {:a 2, :b 3, :c 4, :d 5}



;; If we want to give a default value to any nil key-value then we can do the following:

(reduce-kv (fn [m k v]
             (assoc m k (if (nil? v) "Foo" v)))
           {}
           {:name "John" :surname "Bar" :middlename nil})

;; evaluates to: {:name "John", :surname "Bar", :middlename "Foo"}


;; In case of a vector, if we want to increase only the elements in an even position (index)
;; we can do the following:

(reduce-kv (fn [m k v]
             (assoc m k (if (even? k) (+ 10 v) v)))
           []
           [1 2 3 4 5 6])
;; evaluates to: [11 2 13 4 15 6]



;; We can define a map-kv with 'reduce-kv' in order to do something to every value in a map.
(defn map-kv [f coll]
  (reduce-kv (fn [m k v]
               (assoc m k (f v)))
             (empty coll)
             coll))

(map-kv inc {:a 1 :b 2})
;; evaluates to: {:a 2, :b 3}

;; which can be run in a vector too
(map-kv inc [1 2 3 4])
;; evaluates to: [2 3 4 5]







;; ----------- take-while/drop-while ------------------
;;
;; ---- take-while ----
;; 'take-while' takes a predicate and a collection and returns a lazy sequence with the elements
;; from the given collections which continuous evaluate to true for the given predicate. For example:

(take-while #(> 300 (* % %)) (range 0 1000))
;; evaluates to: (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17)


;; check that take-while stops traversion the collection when the predicate is false
(take-while neg? [-1 -2 10 100 0 -100 -22 -85])
;; evaluates to: (-1 -2)


;; we can use a set as predicate
(take-while #{[1 2] [3 4]} #{[3 4]})
;; evaluates to: ([3 4])

(take-while #{1 2 3 4} #{1 2 9})
;; evaluates to (1 2)

(take-while even? [1 3 5])
;; evaluates to ()



;; ---- drop-while ----
;; 'drop-while' works the same as 'take-while' with the only difference that drops the
;; elements from the given collection that continous evaluate to true for the given predicate.
;; For example:

(drop-while neg? [-2 -10 -22 0 10 22 -21 111])
;; evaluates to: (0 10 22 -21 111)


;; We can combine both functions if we want to take any data that are between min and max values.
;; For example:


(def month-values [{:month 1 :value 10}
                   {:month 2 :value 20}
                   {:month 3 :value 30}
                   {:month 4 :value 40}
                   {:month 5 :value 50}
                   {:month 6 :value 60}])

;; let's assume that we want to take the values from month 3-5.

(take-while #(< (:month %) 6)
            (drop-while #(< (:month %) 3) month-values))








;; ----------- iterate ------------------
;; 'iterate' takes 2 arguments (one method and one value) and returns a lazy sequence
;; by applying the given function to the given value and the result passes as argument
;; to the given function and the new result passes as argument to the given function etc...
;; We can create infinity sequences with 'iterate'.


(take 4 (iterate inc 0))
;; evaluates to (0 1 2 3)


(take 5 (iterate (partial + 2) 0 ))
;; evaluates to: (0 2 4 6 8)






;; ----------- partition/partition-by ------------------

;; ---- partition ----
;; 'partition' takes 4 arguments:
;; n---> a number which defines the size of each nested sequence in the result (required)
;; step ----> a number which defines the step that the elements will divided in each partition list (optional, defaults to n)
;; pad ----> a collection which if supplied use its elements as necessarty to complete last partition
;; in case there are not enought padding elements (optional)
;;
;; and returns a lazy sequence of lists of n items each, at offsets step apart. Let's see some examples



(partition 4 (range 20))
;; evaluates to: ((0 1 2 3) (4 5 6 7) (8 9 10 11) (12 13 14 15) (16 17 18 19))

;; since we dont provide a 'pad' collection the elements 12, 13, 14 will be omitted from the result
(partition 4 (range 15))
;; evaluates to: ((0 1 2 3) (4 5 6 7) (8 9 10 11))

;; in case the step is greater than n then some elements will not appear at all in the final result
(partition 4 5 (range 15))
;; evaluates to: ((0 1 2 3) (5 6 7 8) (10 11 12 13))

;; and in case the step is lower than n then some elements will be reused
(partition 4 3 (range 15))
;; evaluates to: ((0 1 2 3) (3 4 5 6) (6 7 8 9) (9 10 11 12))

;; if the pad is supplied will be used in order to fill the last partition
(partition  4 4 [10] (range 15))
;; evaluates to:((0 1 2 3) (4 5 6 7) (8 9 10 11) (12 13 14 10))



;; ---- partition-by ----
;; 'partition-by' takes one function and a collection. Returns a lazy sequence
;; by applying the given function to each value in collection, splitting it each time
;; the result is a new value. For example:


;; if we want to partition the same sequential values in a collection
(partition-by identity [1 1 2 3 3 3 1 1 2 2])
;; evaluates to: ((1 1) (2) (3 3 3) (1 1) (2 2))


(partition-by odd? [1 1 3 5 2 2 4 6 9])
;; evaluates to: ((1 1 3 5) (2 2 4 6) (9))


(partition-by #(= 10 %) [1 2 3 4 10 6 7 8])
;; evaluates to: ((1 2 3 4) (10) (6 7 8))








;; ----------- future/promises/delays ------------------
;; Future, delays and promises allow us to seperate task definition, task execution and requiring
;; the result.

;; ---- future ----
;; We can use futures to define a task and place it on another thread without requiring the
;; result immediately. We can ask to get the result from a future by using deref/@. If the computation
;; has not yet finished the call to deref/@ will block the thread. The result is cached and will
;; return immediately in any next deref/@.

(future (Thread/sleep 2000)
        (println "Im done after 2 sec"))

(println "I arrived before future!!")

;; "I arrived before future!!" will be print before "Im done after 2 sec"


;; A future's body executes only once.
(let [result (future (println "this prints once")
                     (* 2 2))]
  (println "deref: " (deref result))
  (println "@: " @result))
;; evaluates to "this prints once"
;; deref: 4
;; @: 4


;; Dereferencing a future will block if the future hasn't finished yet.
(let [result (future (Thread/sleep 3000)
                     (+ 10 10))]
  (println "Future result is: " @result)
  (println "It will be at least 3 secs before I print"))
;; evaluates to: Future result is:  20
;; It will be at least 3 secs before I print



;; We can also define a limit time on how long to wait for a future response.
;; We can pass in 'deref' a milliseconds time to wait along with the value to return if the
;; response time > limit time.

(deref (future (Thread/sleep 3000) 20) 100 5)
;; evaluates to: 5


;; Finally we can use 'realized?' to check if a future is done running
(realized? (future (Thread/sleep 1000)))
;; evaluates to: false

(let [result (future 10)]
  @result
  (realized? result))
;; evaluates to: true




;; ---- delay ----
;; Delays allow us to define a task without having to execute it or require the result immediately.
;; We can create a delay using 'delay'. For example:

(def delay-message
  (delay (let [msg "Hi from delay"]
           (println "First call: " msg)
           msg)))


;; We can evaluate the delay and get the result if we use 'force' or @
(force delay-message)
;; evaluates to: "First call: Hi from delay"
;; "Hi from delay"


;; Like future, delay is run only once and its result is cached. If we try to force it again
@delay-message
;; evaluates to: "Hi from delay"




;; ---- promises ----
;; Promises allow us to express that we expect a result without having to define the task that should
;; produce it or when the task should run. We can create a promise using 'promise' and deliver a result
;; using 'deliver'. We can get the result of a promise by using 'deref' or @.
;; If we try to derefernce a promise without first delivering a value, the program would block
;; like futures and delays. Finally we can deliver a result to a promise only once. For example:

(def my-promise (promise))

(deliver my-promise (+ 10 10))

@my-promise
;; evaluates to 20

(deliver my-promise 1)

;; if we try to deref the 'my-promise' we will still get 20
@my-promise
;; evaluates to 20


(def anotha-promise (promise))

(future
  (Thread/sleep 5000)
  (deliver anotha-promise "Hello"))

@anotha-promise
;; after 5 secs we will get the "Hello" value, delivered from the previous future



;; Combining futures and promises we can achieve a callback functionality.

(def callback-promise (promise))

(future (println "I'll wait for promise value...")
        (println "Done, value is: " @callback-promise))
;; prints: "I'll wait for promise value..."

(deliver callback-promise 101)
;; continue the printing of the previous future: Done, value is 101






;; ----------- Atoms ------------------
;; Clojure provide some Reference types which let us manage identities by naming
;; an identity and retrieve its state. The simplest Reference type is Atom.
;; Atoms provide a way to manage shared, synchronous, independent state. We can create one this way:

(def my-atom (atom 0))

;; And we can get the value by dereferencing
@my-atom
;; evaluates to 0


;; We can update the atom to refers to a new state, we can use 'swap!'
;; 'swap!' receives an atom and a function as arguments. It applies the function to the atom's
;; current state to produce a new value and then updates the atom to refer to this new value.
;; Changes to atoms are always free of race conditions.

(swap! my-atom inc)
;; evaluates to 1

;; now if we try to take the atom's value
@my-atom
;; evaluates to 1


;; Of course we can change the atom's value as many times as we want
(swap! my-atom (fn [state] (+ state 10)))
;; evaluates to 11

@my-atom
;; evaluates to 11

;; Let's see how 'swap' works
;; 1. It reads the current state of the atom.
;; 2. It then applies the update function to that state.
;; 3. Next, it checks whether the value it read in step 1 is identical to the atom’s current value.
;; 4. If it is, then swap! updates the atom to refer to the result of step 2.
;; 5. If it isn’t, then swap! retries, going through the process again with step 1.


;; If we want to update an atom's value without checking its current value,
;; then we can use 'reset!'

(reset! my-atom 0)
;; evaluates to 0

@my-atom
;; evaluates to 0



;; ---- Watchers ----
;; A watch is a function that takes 4 arguments: a key (which can be used for reporting), the reference (atom) being watched, its previous state and its new state. Let's define one

(defn negative-alert
  [key-watch watched old-state new-state]
  (if (neg? new-state)
    (println "Oh no, negative alert for" key-watch "...Run!!!" )
    (println "OK, all good with" key-watch)))


;; We can attach the above function to our my-atom with the 'add-watch' function.
;; This function takes 3 arguments. A reference (atom), a key and a watch-function.

(add-watch my-atom ":critical-positive-value" negative-alert)

;; And now let's test it

(swap! my-atom (partial + 10));;
;; evaluates to: "OK, all good with :critical-positive-value"

@my-atom
;; evaluates to 10

(swap! my-atom (partial - 9))
;; evaluates to: "Oh no, negative alert for :critical-positive-value ...Run!!!"

@my-atom
;; evaluates to: -1




;; ---- Validators ----
;; Validators let us specify what states are allowable for a reference (atom).
;; A validator is a function which takes only one argument. If we add a validator
;; in a reference, whenever we update its value, the validator will be called with
;; the updated value as argument. If the validator return false or throw an exception
;; the reference won't change to the new value. We can attach a validator during
;; atom creation

;; the validator
(defn percent-validator
  [{:keys [percent-battery]}]
  (if (or (< percent-battery 0) (> percent-battery 100))
    (throw (IllegalArgumentException. "Value must be between 0-100"))
    percent-battery))

(def my-electric-car
  (atom
    {:name "Humble Car" :percent-battery 100}
    :validator percent-validator))

(swap! my-electric-car update-in [:percent-battery] - 20)
;; evaluates to: {:name "Humble Car" :percent-battery 80}

@my-electric-car
;; the same as above

(comment
  (swap! my-electric-car update-in [:percent-battery] + 40))
;; throws illegal argument exception

;; and nothing changes
@my-electric-car
;; evaluates to: {:name "Humble Car", :percent-battery 80}







;; ----------- Java Interop ------------------
;; Clojure is designed to make it easy for us to interact with Java
;; classes and objects. We can call methods on a Java object using
;; (. methodName object opt-args). For example:

(.toUpperCase "chris")
;; evaluates to: "CHRIS"

(.indexOf "chris" "r")
;; evaluates to: 2


;; We can also call static methods/fields. We already did this in
;; a previous section where we used UUID.

(UUID/randomUUID)
;; evaluates to: #uuid "7d66ac30-34d4-4b53-8a33-46c5b794760b"


(java.lang.Math/abs -2)
;; evaluates to 2


java.lang.Math/PI
;; evaluates to: 3.141592653589793



;; So far we have seen how to call methods from already existed
;; objects. Let's check now how we can create new objects and how to
;; interact with them. There are two ways, (new className opt-args),
;; (ClassName. opt-args). For example:

(new String "Hello")
;; evaluates to "Hello"


(String. "Hello again")
;; evaluates to: "Hello again"

;; In order to execute multiple methods on a same object we can use
;; the 'doto'. Here is how we can define an ArrayList and add some
;; elements.

(doto (java.util.ArrayList.)
  (.add "Marie")
  (.add "Chris"))
;; evaluates to: ["Marie" "Chris"]

;; Which is equivalent with:
(def my-arraylist (java.util.ArrayList.))
(.add my-arraylist "Marie")
(.add my-arraylist "Chris")

my-arraylist
;; evaluates to ["Marie" "Chris"]



;; At some point, during you Clojure adventure, while interacting with a Java library it may be needed to
;; extend/implement a Java Class/Interface.
;; Clojure provide 3 tools for this job, 'gen-class', 'proxy', 'reify'. If we just need to implement an interface (or multiple)
;; in an anonymous instance, reify is the best choice. If we need to create an anonymous instance that subclasses
;; another class, reify can’t help you, but proxy can. Let's see some examples:


;; Here we just implement a new java.util.List (for demonstrasion purposes we implement only 3 methods (add, size, get).
(defn my-proxy [xs]
  (let [items (atom xs)]
    (proxy [java.util.List] []
      (add [item]
        (boolean (swap! items conj item)))
      (size [] (count @items))
      (get [index] (nth @items index))
      (iterator [] (.iterator @items)))))

;; and we can use it this way:
(def my-new-list-impl (my-proxy [1 2 3]))

(.add my-new-list-impl 10)
;; evaluates to: true

(.size my-new-list-impl)
;; evaluates to: 4

(.get my-new-list-impl 0)
;; evaluatest to: 1

(.get my-new-list-impl 3)
;; evaluates to: 10

my-new-list-impl
;; evaluates to: (1 2 3 10)

;; And here is an example where we extends the java.util.ArrayList class by defining a new subclass which
;; retuns for size... a random number!!
(defn my-fun-proxy []
  (proxy [java.util.ArrayList] []
    (size [] (rand-int 100)))) ;; a fun modification DO NOT do this in real code!!

;; Let's try it
(def my-extended-array-list (my-fun-proxy))

(.addAll my-extended-array-list [1 2 3 4 5])
;; evaluates to: true

(.size my-extended-array-list)
;; evaluates to a random number between 0-99


;; And here is another example on how we can use a method from the superclass (by using 'proxy-super')
;; We are extending again the java.util.ArrayList class but this time for the size method we call the super class size
;; and we increase it by one (pretty evil).
(defn my-anotha-fun-proxy []
  (proxy [java.util.ArrayList] []
    (size [] (inc (proxy-super size))))) ;; an EVIL modification

(def my-evil-array-list (my-anotha-fun-proxy))

(.addAll my-evil-array-list [1 2 3])

my-evil-array-list
;; evaluates to: [1 2 3]

(.size my-evil-array-list)
;; evaluates to: 4


;; As we said in the beggining of this section, if we want to implement an Interface, best choice is: 'reify'.
;; Here is the equivalent reify way of the first example (where we implemented a new java.util.List by using 'proxy').
(defn my-reify [xs]
  (let [items (atom xs)]
    (reify java.util.List
      (^boolean add [_ item]
        (boolean (swap! items conj item)))
      (size [_] (count @items))
      (get [_ index] (nth @items index))
      (iterator [_] (.iterator @items)))))

;; The difference with the 'proxy' solution is that here for each implemented method we have to pass another one
;; argument which goes to 'this' java Keyword. Also for each method we can define what kind of type will return.
;; For example, for the 'add' method we added the meta: ^boolean which defines that add method returns a boolean type.

(def my-reify-list-impl (my-reify [1 2 3 4]))

(.add my-reify-list-impl 9)
;; evaluates to: true

(.add my-reify-list-impl 21)
;; evaluates to: true

(.size my-reify-list-impl)
;; evaluates to 6

(.get my-reify-list-impl 5)
;; evaluates to: 21

my-reify-list-impl
;; evaluates to: (1 2 3 4 9 21)





;; ----------- Memoize ------------------
;; Memoization let us take advantage of referential transparencty by storing the arguments passed to a
;; function and the return value of the function. That way, subsequent calls to the function with the
;; same args will return the result immediately. This is especially useful for functions that take a lot
;; of time to run. Let's see some examples


(defn identify-me [x]
  (Thread/sleep 1000)
  (println "I arrived after 1 sec")
  x)

;; if we call
(identify-me 10)
;; evaluates to: "I arrived after 1 sec" 10

;; and if we call it again, it will do the same, after 1 sec we will see the same result
(identify-me 10)
;; evaluates to: "I arrived after 1 sec" 10


;; But if we use 'memoize' only the first call will wait for 1 sec and will print the 'I arrived after 1 sec'
;; every subsequent call returns immediately.
(def memo-identify (memoize identify-me))

(memo-identify "Hello")
;; waits for 1 sec and then evaluates to: "I arrived after 1 sec" "Hello"

;; if we call it again
(memo-identify "Hello")
;; evaluate immediately to: "Hello"


;; But if we call the above with another argument, it will take again 1 sec to finish
(memo-identify 100)
;; waits for 1 sec and then evaluates to: "I arrived after 1 sec" 100

;; And of course if we call it again we will get the result immediately
(memo-identify 100)
;; evaluates immediately to: 100

;; The same for the "Hello" argument
(memo-identify "Hello")
;; evaluates immediately to: "Hello"


;; We can define two fibonnaci functions, one without memoize and one with.

;; simple version
(defn fib [n]
  (condp = n
    0 1
    1 1
    (+ (fib (dec n)) (fib (- n 2)))))

;;memoize-version
(def m-fib
  (memoize (fn [n]
             (condp = n
               0 1
               1 1
               (+ (m-fib (dec n)) (m-fib (- n 2)))))))



;; let's use 'time' to see how long it takes to find the fibonnaci of 20 in each of them.

(time (fib 20))
;; evaluates to: "Elapsed time: 6.792098 msecs" 10946

(time (m-fib 20))
;; evaluates to: "Elapsed time: 0.263373 msecs" 10946


(defn anotha-test-here [x y]
  (println "args:" x y)
  (* 2 (+ x y)))


(defn anothat-tets-2 [x y z]
  (+ z (anotha-test-here x y)))



;; ----------- Multimethods & Protocols ------------------


;; ---- Multimethods ----
;; From clojure.org/reference/multimethods
;;
;; A Clojure multimethod is a combination of a dispatching function, and one or more methods.
;; When a multimethod is defined, using 'defmulti', a dispatching function must be supplied.
;; This function will be applied to the arguments to the multimethod in order to produce a dispatching value.
;; The multimethod will then try to find the method associated with the dispatching value or a value
;; from which the dispatching value is derived. If one has been defined (via 'defmethod'),
;; it will then be called with the arguments and that will be the value of the multimethod call.
;; If no method is associated with the dispatching value,
;; the multimethod will look for a method associated with the default dispatching value (which defaults to :default),
;; and will use that if present. Otherwise the call is an error.
;; Let's see some example:

(defmulti gate-keeper (fn [x]
                        (if (>= (:age x 0) 18) :adult :kid)))

(defmethod gate-keeper :kid [_] (str "Sorry, you are a kid!!"))
(defmethod gate-keeper :adult [_] (str "You entered!"))



(defmulti remove-nils #(cond
                         (map? %) :map
                         (coll? %) :coll
                         :else :default))

(defmethod remove-nils :map [a-map]
  (into {} (remove (comp nil? val) a-map)))

(defmethod remove-nils :coll [coll]
  (remove nil? coll))

(defmethod remove-nils :default [_] "Not a map or a collection")



;; ---- Protocols ----

;; Unlike multimethods, which perform dispatch on arbitrary values returned by a dispatching function,
;; protocol methods are dispatched based on the type of the first argument. By defining a protocol, we are
;; defining an abstraction, but we haven't yet defined how the abstraction is implemented.

(defprotocol my-protocol
  (welcome [x] "A welcome method")
  (goodbye [x] [x y] "A goodbye method which accept 1 and 2 arguments"))



(extend-protocol my-protocol
  java.lang.String
  (welcome [x] "Hello there!")
  (goodbye
    ([x] "Bye bye!")
    ([x y] (str "Bye bye " y)))

  java.lang.Object
  (welcome [x] "A more global welcome")
  (goodbye
    ([x] "A more global bye bye!")
    ([x y] (str "A more global goodbye for " y))))


(defprotocol date-utils
  (current-time [this])
  (yesterday [this])
  (tomorrow [this]))


(extend-protocol date-utils
  java.util.Date
  (current-time [this] (this))
  (yesterday [this] (doto (java.util.Calendar/getInstance)
                      (.add java.util.Calendar/DATE -1)
                      (.getTime)))
  (tomorrow [this] (doto (java.util.Calendar/getInstance)
                     (.add java.util.Calendar/DATE +1)
                     (.getTime)))

  java.time.LocalDate
  (current-time [this] (java.time.LocalDate/now))
  (yesterday [this] (.plus (java.time.LocalDate/now) -1 java.time.temporal.ChronoUnit/DAYS))
  (tomorrow [this] (.plus (java.time.LocalDate/now) +1 java.time.temporal.ChronoUnit/DAYS)))



(defprotocol full-moon
  (behavior [this])
  (words [this]))

(defrecord Wolfman [name quote]
  full-moon
  (behavior [this] (str (:name this) " the Wolfman, will attack humans!"))
  (words [this] (str "Shouting out: " (:quote this))))


(defrecord Human [name]
  full-moon
  (behavior [this] (str (:name this) " the human, will stay at home.."))
  (words [this] "Sun, help us!"))


(spec/conform even? 2)

(spec/def :deck/suit #{:club :diamond :heart :spade})

(spec/valid? :deck/suit :diamond)


(def tree [{:name "Chris"
            :id 1
            :father-id -1
            :mother-id -1
            :children-ids [20 30 40]}
           {:name "Mary"
            :id 2
            :father-id -1
            :mother-id -1
            :children-ids [20 30 40]}
           {:name "Adler"
            :id 3
            :father-id -1
            :mother-id -1
            :children-ids [50 60]}
           {:name "Abbie"
            :id 4
            :father-id -1
            :mother-id -1
            :children-ids [50 60]}
           {:name "John"
            :id 20
            :father-id 1
            :mother-id 2
            :children-ids [21 22]}
           {:name "Anna"
            :id 30
            :father-id 1
            :mother-id 2
            :children-ids [31 32]}
           {:name "Kadel"
            :id 40
            :father-id 1
            :mother-id 2
            :children-ids []}
           {:name "Jack"
            :id 50
            :father-id 3
            :mother-id 4
            :children-ids [51]}
           {:name "Fraya"
            :id 60
            :father-id 3
            :mother-id 4
            :children-ids [61 62]}
           {:name "Kathrine"
            :id 51
            :father-id 50
            :mother-id 49
            :children-ids []}
           {:name "Foo"
            :id 21
            :father-id 20
            :mother-id -1
            :children-ids []}
           {:name "Bar"
            :id 22
            :father-id 20
            :mother-id -1
            :children-ids []}
           {:name "Foo2"
            :id 31
            :father-id 30
            :mother-id -1
            :children-ids []}
           {:name "Bar2"
            :id 32
            :father-id 30
            :mother-id -1
            :children-ids []}
           ])

(defn get-human-by-id
  [id]
  (-> (filter #(= id (:id %)) tree)
      first))

(defn num-of-progeny
  [human num]
  (if (empty? (:children-ids human))
    num
    (let [children (filter #(.contains (:children-ids human) (:id %))
                           tree)]
      (reduce +
              (-> (map #(num-of-progeny % (count children)) children)
                  flatten
                  dedupe)))))

