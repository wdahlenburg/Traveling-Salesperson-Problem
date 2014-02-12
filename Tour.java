import java.util.*;
import java.awt.Graphics;
import java.awt.*;

/**
 * This class is a specialized Linked List of Points that represents a
 * Tour of locations attempting to solve the Travelling Salesperson Problem
 * 
 * @Wyatt Dahlenburg
 * @11-13-12
 */

public class Tour implements TourInterface
{
    // instance variables
    private ListNode front, back;
    private int size;
    private double distance;

    // constructor
    public Tour()
    {
        //Set the instance variables to the default values
        this.front = null;
        this.back = null;
        this.size = 0;
        this.distance = 0;
    }

    //return the number of points (nodes) in the list   
    public int size()
    {
        return size; //Returns the number of points in the list
    }

    // append Point p to the end of the list
    public void add(Point p)
    {
        ListNode end = new ListNode(p);
        if(front == null) //Checks if there is no points
            front = end; //Sets the front to the new point
        else
            back.next = end; //Otherwise adds the point to the end
        back = end; //Sets the last node to the new node added
        size++; //Updates the size
    } 

    // Add a point to the current location
    public void add(Point p,int index)
    {
        ListNode here = new ListNode(p);//Create a node to be added
        ListNode node = front; //Create a node to loop through
        if(front == null) //Check if the front is null
            front = here; //Set the front node to be the added node
        else{
            int i = 0; //Creates a counter
            while(node != null){ //Checks to see if the node is null
                if(i == index){ //If i is the index then add the point here
                    here.next = node.next; //Set the pointer to the current nodes next node
                    node.next = here; // Set the current nodes next node to the added node
                }
                i++;//Update the counter
                node = node.next;//Update the loop
            }
            node = back;
        }
        size++;//Increase the size
    } 

    // print every node in the list 
    public void print()
    {   
        ListNode node = front; //Create a node to loop through 
        while(node != null) //Go through the list
        {
            System.out.println(node.data); //Print out the location of the point
            node = node.next; //Update the node
        }
    }

    // draw the tour using the given graphics context
    public void draw(Graphics g) throws Exception
    {       
        ListNode node = front; //Create a node to go through the list
        int nextX = 0, nextY=0; //Create some variables
        while(node != null) //Go through the list
        {
            int x = (int)node.data.getX(); //Get the x variable from the point
            int y = (int)node.data.getY(); //Get the y variable from the point
            g.fillOval(x- 2,y-2,5,5); //Fill the point on the graph
            if(node.next!=null){ // Check to see if there is a next point
                nextX = (int)node.next.data.getX(); // Get the x variable from the next point
                nextY = (int)node.next.data.getY(); // Get the y variable from the next point
            }
            else{ //If there isn't a next point
                nextX = (int)front.data.getX(); //Get the x variable from the front point
                nextY = (int)front.data.getY(); //Get the y variable from the front point
            }
            Random rand = new Random(); //Create a random object
            int color = rand.nextInt(7); //Return a random number between 1 and 8
            switch(color){
                case 1 : g.setColor(Color.RED); //Set the color to red
                break;

                case 2 :  g.setColor(Color.BLUE); //Set the color to blue
                break;

                case 3 :  g.setColor(Color.MAGENTA); //Set the color to magenta
                break;

                case 4 : g.setColor(Color.YELLOW); //Set the color to yellow
                break;

                case 5 :  g.setColor(Color.PINK); //Set the color to pink
                break;

                case 6 :  g.setColor(Color.GREEN); //Set the color to green
                break;

                case 7 :  g.setColor(Color.CYAN); //Set the color to cyan
                break;
            }
            g.drawLine(x,y,nextX,nextY); //Draw a line between the points
            Thread.sleep(3); //Delay between drawing the lines
            node = node.next; //Update the node
        }
    }

    //calculate the distance of the Tour, but summing up the distance between adjacent points
    //NOTE p.distance(p2) gives the distance where p and p2 are of type Point
    public double distance()
    {
        ListNode node = front; //Create a node to iterate through the list
        double distance = 0 , nextX = 0, nextY = 0; //Initialize variables
        while(node != null) //Go through the list
        {
            double x = node.data.getX(); //Get the x value from the current point
            double y = node.data.getY(); //Get the y value from the current point
            if(node.next!=null){ //Check to see if there is another point
                nextX = node.next.data.getX(); //Get the x value from the next point
                nextY = node.next.data.getY(); //Get the y value from the next point
            }
            else{ //If there are no more points
                nextX = front.data.getX(); //Get the x value of the front point
                nextY = front.data.getY(); //Get the y value of the front point
            }
            distance += (double)Math.sqrt(Math.pow((nextX - x),2) + Math.pow((nextY - y),2)); //Do the distance formula and update distance
            node = node.next; //Update the list
        }
        return distance; //Return the distance
    }

    // add Point p to the list according to the NearestNeighbor heuristic
    public void insertNearest(Point p)
    {   
        double distance, min=Integer.MAX_VALUE; //Create variables
        ListNode node = front; //Create a node to iterate through the list
        int count = 0; //Create a count
        if(node == null){ //Check to see if there is a point
            add(p); //Add a point
        }
        while(node != null){ //Check to see if the current point is not null
            distance = p.distance(node.data); //Get the distance between the point and the current points
            min = Math.min(min,distance); //Set the minimum distance to the lesser value
            node = node.next; //Update the node
        }
        node = front; //Start at the front of the list
        while(node != null){ //Go through the list again
            distance = p.distance(node.data); //Get the distance between the point and the current points
            if(min == distance){ //Check to see if the distance between the point and the current node is the minimum distance
                add(p,count); //Add the point to the current location
                break;
            }
            count++; //Update the position
            node = node.next; //Update the list
        }
    }

    public void insertSmallest(Point p) { 
        double minDist = -1, fbDistMin = Double.POSITIVE_INFINITY; //Establish variables

        ListNode node = front , point = new ListNode(p) , best = node; //Create nodes to be used

        if (node == null || node.next == null || node.next.next == null) { //Check to see if three points have been added
            add(p); //Add a point
        } else {
            while(node.next != null) { //Go through the list
                double d1 = p.distance(node.data);                  //Get the distance between the point and the node
                double d2 = p.distance(node.next.data);             //Get the distance between the point and the next node
                double d3 = node.data.distance(node.next.data);     //Get the distnace between the node and the next node

                double tempMin = d1 + d2 - d3; //Create a variable to compare to the current distance vs a new distance

                if(tempMin < minDist  || minDist < 0) { //Compare the distances
                    minDist = tempMin; //Set the minimum distance to the temporary distance
                    best = node; //Set the best location to be here
                }

                node = node.next; //Update the list
            }
            double d1 = node.data.distance(front.data);             //Get the distance between the node and the front
            double d2 = node.data.distance(point.data);             //Get the distance between the node and the point
            double d3 = point.data.distance(front.data);            //Get the distance between the front and the point

            double tempMin = d3 + d2 - d1; //Create a variable to compare to the current distance vs a new distance

            if(tempMin < minDist) { //Check to see if adding the node to the end of the list is best
                node.next = point;  //Set the node to the end
            }
            else{ //If the best location is elsewhere
                point.next = best.next; //Set the point to point at the next node
                best.next = point; //Set the best place to point at the point
            }
            size++; //Update size
        }

    }

    // This is a private inner class, which is a separate class within a class.
    private class ListNode
    {
        private Point data; //The 
        private ListNode next;
        public ListNode(Point p, ListNode n)
        {
            this.data = p; //Set the point 
            this.next = n; //Set the next node to this node
        }

        public ListNode(Point p) //Create a node with a point
        {
            this(p, null); // Don't have the node point to another node
        }        
    }
}
