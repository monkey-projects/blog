{:title "The power of multimethods"
 :layout :post
 :tags ["functional programming"]}

In a [previous post](/posts/2023-12-21-real-functional-development/) about "real" functional
development, I hinted at the additional tools that Clojure provides to handle actual, non-trivial
programming situations.  Pushing out the side effects by creating a [higher-order function](https://en.wikipedia.org/wiki/Higher-order_function)
is nice, but it may become tedious to have to pass it along all the time, throughout multiple
call levels.  You also don't always have control over the entire call stack.  What if you're
invoking a library that deep down calls another one that performs some side effect?  For
situations like these you can use [multimethods](https://clojure.org/reference/multimethods).
This is actually something that was already present in Lisp and is a kind of syntactic sugar
that behaves like the combination of higher-order functions and state.  It's a function without
an implementation, but with a "decider" function that receives the same arguments, and whose
return value is used to select the correct implementation.  An example may make it clearer:

```clojure
;; Uses the :driver property of the argument to decide implementation
(defmulti save-entity :driver)

;; Dummy implementation
(defmethod save-entity :dummy [e]
  (println "I'm not doing anything!  Use another driver to save" e))

(defmethod save-entity :sql [e]
  ;; Connect to an sql database and perform an insert or update
  ...)

;; Call it
(save-entity {:driver :dummy
              :name "Hi, I'm an entity!"})
```
This multimethod `save-entity` has two implementations, depending on the `:driver` property
of the entity argument.  It's also possible to pass multiple arguments to the multimethod,
and more importantly, you can also define more implementations outside of the namespace and
even outside of the original library.  This makes this a fairly powerful and flexible method
to push out the side effects of your application.  The drawback is that it's a bit "magic-y"
and it may result in behavior that's difficult to explain.  But that's usually the dichotomy:
either you have full control and clear overview, but you also have more work managing it, or
it's the other way around.

What if you have more than one function that should be abstracted?  That's where [protocols](https://clojure.org/reference/protocols) come in!  But I'll show those in another post.