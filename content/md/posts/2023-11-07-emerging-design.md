{:title "Emerging Design"
 :layout :post
 :tags ["design"]}

I'm a big fan of emerging design.  Instead of doing a "big design up front"
(or _BDUP_ in short), we design the application as we go along.  So actually,
no design?  No, not really.  There is some global vision as to where we want
to go, of course.  But we don't go into the details all that much.  Why?
Because I think that the BDUP always ends up with a _wrong_ design.  Sometimes
due to changing requirements, but more because it's just impossible to know
everything beforehand, even if you think about it really hard (and long).

So how does this work in practice?  I generally set some priorities, and
make some obvious decisions, like what platform, or should it be a web app?
But even these decisions are not carved in stone.  I try to keep my options
open as much as possible, and I usually take the path of least resistance.
_TDD_ helps a lot here: because I'm constantly writing tests and then the
minimal amount of code to make them pass, I tend to end up with very basic
implementations.  Like using files instead of a relational database.  Or
some local process instead of a network call.  This can all be changed
later.  That's why the design is _emerging_: every once in a while I take
a step back and look at the overall architecture.  And since we're constantly
becoming smarter, that architecture will most likely be better than anything
I would have thought about up front.

The drawback?  That sometimes you have to throw away a lot of your code,
if you come to the conclusion that you don't need it anymore.  But hey, that
can also happen with _BDUF_, although in that case, it could be that you
have to throw it _all_ away!