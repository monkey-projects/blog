{:title "Real Functional Development"
 :layout :post
 :tags ["functional programming"]}

Functional programming is great, we *all* know that, don't we?  But we also want
to build applications for the real world.  That means they *actually have to do
something*.  Those textbooks about functional development mostly focus on theoretical
things and come up with convoluted examples about bank accounts that somehow don't
need any storage or anything.  Perhaps the writers see that as some kind of
implementation detail?  But of course it's not.  Actually, as it happens so often,
the devil is again in the details.  Because how *do* you write software that is
both functional and has side effects?  Writing to files, reading from network
connections, talking to database servers, etc.  All those things are what makes
your application tick, but they are also not purely functional.  It's not possible
to call functions doing something like that and just inspecting the return values,
and then calling them again as if the previous call never happened.

This also makes *TDD* more complicated.  The problem is the same with imperative
programming, of course.  There they solve it using interfaces, facades and other
OOP [design patterns](https://en.wikipedia.org/wiki/Design_pattern).  Functional
programming doesn't really have design patterns, because they are (arguably) not needed.
Since functions are first-class citizens, you can just create a higher-order function and
pass in the "implementation details" as an argument.  For example:

```clojure
(defn create-customer-transient [name address]
  {:type :customer
   :name name
   :address address})
```
This creates a new customer structure (or dare I say "object"?) but since it doesn't save
it anywhere, it's not that useful.  We need someplace to store it.  Higher-order
functions to the rescue!

```clojure
(defn create-customer-persistent [name address storage]
  (storage (create-customer-transient name address)))
```

This will invoke our previous transient customer creation function, and pass the
result to some kind of storage thing, which is just a function.  It's up to the caller
to determine how this needs to be stored.  For testing purposes, you could just pass
in the `identity` function.  In the real world, this would perhaps insert a record in
a database table.

This is an admittedly naive example of how we would handle side effects in functional
programming.  And of course the world is never *this* simple, so we may need more
than this.  Clojure offers more ways of dealing with things like this, but I'll go
into more details in a future post.