
PROJECT TITLE: Traveling Salesperson Problem
PURPOSE OF PROJECT: Implement two greedy heuristics in the attempt to find
an optimal solution to the Traveling Salesperson Problem
DATE:11/13/12
AUTHOR: Wyatt Dahlenburg
ALGORITHM DESCRIPTIONS: 
    insertNearest()
        This method uses O(N2) notation. The insertNearest heuristic is used to 
        insert the points based on the nearest point. This works by checking to
        see if there is a first point to compare to. If there is not a point
        add one. Then go through all of the points and compare the distance 
        between each point and the point to be added. Continue to do this for
        as many points as there are.
    insertSmallest()
        The heuristic insertSmallest uses O(N2) notation. The method compares
        the points and compares which insertion would make the smallest distance.
        This process was done by ensuring there were at least three points added.
        If not, then the points were added. Next the points were looped through
        and the distance between the new point was compared to the current setup.
        This can be shown through
        
                double d1 = p.distance(node.data);
                double d2 = p.distance(node.next.data);
                double d3 = node.data.distance(node.next.data);

                double tempMin = d1 + d2 - d3;
               
        This takes the distance between the current node and the point plus the
        distance between the next node and the point minus the original distance
        between the points. Next the tempMin distance was compared to a variable
        that was the minimum distance. This allowed for the best position to be
        set. Once the current node was at the last node, the tempMin was again 
        compared to the minimum distance. If it was smaller the point would be
        added to the end of the list. If it was larger, the point would be 
        inserted at the best insertion. My code looked like this:
        
            public void insertSmallest(Point p) { 
                   double minDist = Double.POSITIVE_INFINITY;
                   int    minNode = -1;

                   ListNode node = front;
                   int count = 0;

                   if (node == null || node.next == null || node.next.next == null) {
                       add(p);
                   } else {
                      while(node.next != null) {
                          double d1 = p.distance(node.data);
                          double d2 = p.distance(node.next.data);
                          double d3 = node.data.distance(node.next.data);

                          double tempMin = d1 + d2 - d3;

                          if(tempMin <= minDist) {
                              minDist = tempMin;
                              minNode = count;
                            }

                            node = node.next;
                            count++;
                        }

                        add(p, minNode);
                    }
                }
        The problem with this code is that as it itterates through the current list
        but then doesn't check to see if adding the point to the end is less than
        the current best position. This mixed up a few numbers, then the rest of the
        code didn't work correctly. This gave a value of 800 greater than the needed
        distance.
        
    Animation
        To animate the tour I initially looked at the timer object. I was
        testing the timer and ran across a simple line of code.
            Thread.sleep(1000);
        I tried to implement this into my draw method and an error popped up. I
        saw that the compiler needed me to throw an exception. I wrote the code in
        the tour class to throw an exception for the draw method. I tried to
        compile it and again recieved an error. The TSPClientDriver class opened
        up and it said I needed to throw the exception. I used the try-catch
        statement to try to draw the tour. If an error came up I would just have
        an exception thrown. I tested the animation and it took a really long time.
        I began to tweak the Thread.sleep time and found that a time between 20
        and 3 animated the tour in a comfortable time. I found that this way works
        better than if the wait was placed in the code where insertNearest and
        insertSmallest is called. When the animation is done this way, the tour is
        not animated and there is a large amount of lag.
        
    Color
        I added color to the line and the background. I did this just as a fun
        element to the project. I used a switch statement and a random object to
        randomly change the colors each time the main method was called. I did this
        by setting up a random object. Next I had the random object get the next
        integer. I set it to get a number between 1 and 10. Then I just assigned
        the line or the background a color based on where the code was placed. The
        code to change the line was placed in my draw method before each line
        was drawn. The background color was placed in the Drawing Panel class. It was
        placed in the ImagePanel constructor.