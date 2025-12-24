{:title "Old and New"
 :tags ["monkeyci" "infra"]}

2025 is nearing it's end and as every year, I'm looking back and looking forward.
What have I accomplished this year?  What do I hope to do next year?  What have I
learned?  And which mistakes have I made?  The last two are equivalent: after all,
making mistakes is the best way to learn.  Some things are not mistakes at all,
but with the benefit of hindsight we now know that it was perhaps a bad choice,
based on information that was incomplete (or maybe even wrong).

What have I been working on the past year?  [MonkeyCI](https://monkeyci.com) of
course.  The intention was to go live in the beginning of 2025.  But international
politics got in the way.  Once reliable suppliers and partners suddenly turned
out not to be reliable after all.  Or they didn't share my values anymore.  This
meant I had to do make new choices, which often led to technical modifications.
And these were not always trivial.  For example, I decided to move away from
Oracle Cloud (OCI) to a purely European cloud provider.  I selected
[Scaleway](https://scaleway.com), to which I later added [Hetzner](https://hetzner.com).
This was not purely for political reasons, but also because they offered
solutions that OCI did not.  Or it turned out to be financially more attractive.

But it also allowed me to simplify my infrastructure.  Scaleway offers
[NATS](https://nats.io), an eventing platform that aims to incorporate the
best of [Kafka](https://kafka.apache.org/) and [JMS](https://en.wikipedia.org/wiki/Jakarta_Messaging).  OCI does not have something similar, or it's outrageously expensive.
I was using [Artemis](https://artemis.apache.org/components/artemis/) on a
[Kubernetes](https://kubernetes.io) cluster before the move.  But it turned out
to be overkill to have to run a full cluster with two nodes, each having 8GB
of memory just to run a few small containers.  Scaleway offered me a better
(and cheaper) way to run those containers.  And I could eliminate Artemis.

Similarly, I switched the cloud containers in MonkeyCI's builds for a more
traditional agent-based approach.  This is faster, albeit somewhat less
efficient in terms of resource usage: I now have to run agent VM's around
the clock.  That is where Hetzner comes in: they offer very cheap VM's,
hosted in Germany.  So MonkeyCI is now fully Europe-based, and
I have even lower costs than before!

The downside here was that I spent a large part of my development time to
get to run MonkeyCI on cloud containers, only to throw part of that effort
away when replacing it with agents.  A mistake?  I did learn a lot, and
some of the effort had to be done anyway.  And who knows, maybe it will
be needed again in the future?

But eventually it was up and running and [MonkeyCI is now live](https://app.monkeyci.com)!
A momentous event (for me, at least).  So what else does the future bring?
Next in the pipeline is finishing up the commercial part.  Currently MonkeyCI
is only usable for free for non-commercial purposes.  Great for personal
projects, but that does not pay my bills.  So finding paying customers is a
necessity.  And that requires some more additions to the application.  I also
have some more ideas I want to add.  Maybe some will even turn out to be
killer features?  We'll see...

One more thing: what about AI?  I was everywhere the past year.  It seems
as if every company now wants to incorporate it into it's tools, whether it
adds value or not.  I won't be doing that any time soon.  Why not?  Well,
first of all I don't think we need it.  Current AI implementations are just
not that useful for development purposes.  There have been several studies
regarding the subject and they turn out to be inconclusive.  So to me, that
does not justify putting up with the drawbacks of AI.  And those are huge.
The enormous energy consumption not only drives up prices for everyone else, but
considering the climate distortion we need to be extra conscious about our energy
efficiency.  To me, a human (that can easily operate on a few sandwiches and
some cups of coffee a day) can do the work of an AI more efficiently.  And
there is no shortage of humans on earth!  Also the proprietary nature of it
is a drawback.  Something that is so important to the future of human
development should be owned by the whole of mankind, I think.

Does that mean that AI is worthless?  Of course not.  Especially for
research purposes it can be very useful, and I predict that we will see
several breakthroughs in the near future that may not have happened
without AI.  Especially in the medical domain.  But I won't be using it
to write my code any time soon.

So with that food for thought I wish you all happy holidays, and a great
new year!

(Be sure to check out [MonkeyCI](https://monkeyci.com) and enjoy an
additional 2.000 credits per month for a full year!)
