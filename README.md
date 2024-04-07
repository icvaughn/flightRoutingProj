display all airports (given to method) -- as many as you give it, should be able to scroll through
gui for adding airport
  -add button to main screen
      new panel screen 
          data validation must occur on input in the addition screen
  -then call the addAirport(Airport) method
Gui for adding airplane

search functionality (maybe display autocomplete -- character bst)
search (in same field) : icao or name
search location (exact location only? -- search over an area to find an airport or airports?)
search then -> select -> then you can call removeAirport
select plane (assuming not many planes will be added) --> then you can call removeAirplane

search airport -> select airport -> set as start
search airport -> select airport -> set as end (cannot be same as start)
calculate path
display: optimal path (if not good, ask for next best? -- in priorque this would be mega easy)
display next best?
show graph of airports and optimal path.

Dylan todo:
Implement db methods -- done


implement point class
implement pathfinding class
implement algorithm in pathfinding class
