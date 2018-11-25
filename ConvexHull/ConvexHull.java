import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.text.*;

class ConvexHull{

/*
* Given N Numbers of Two Dimensional Coordinate
* Find the Convex Hull
*/

  class MyCanvas extends Canvas {
    private ArrayList<Line> lineList;
    public MyCanvas(ArrayList<Line> lineList) {
      setSize(105,105);
      this.lineList = lineList;
    }
    public void paint(Graphics g) {
      lineList.forEach((line)->
        g.drawLine(line.getNodeOne().getX(),line.getNodeOne().getY(),line.getNodeTwo().getX(),line.getNodeTwo().getY()));
    }
  }

  class Node{
    private int x;
    private int y;
    //Setter and Getter
    public void setX(int x){
      this.x = x;
    }
    public void setY(int y){
      this.y = y;
    }
    public int getX(){
      return this.x;
    }
    public int getY(){
      return this.y;
    }
  }

  class Line{
    private Node node1;
    private Node node2;
    private float slope;
    //Setter and Getter
    public void setNodeOne(Node one){
      this.node1 = one;
    }
    public void setNodeTwo(Node two){
      this.node2 = two;
    }
    public void setSlope(float slope){
      this.slope = slope;
    }
    public Node getNodeOne(){
      return this.node1;
    }
    public Node getNodeTwo(){
      return this.node2;
    }
    public float getSlope(){
      return this.slope;
    }
  }

  //=====================================
  //=====================================
  public static void main(String[] args){
    ConvexHull main = new ConvexHull();
    Node[] nodes = new Node[7];
    ArrayList<Line> lineList = new ArrayList<>();
    main.initNodes(nodes);
    //main.printNodes(nodes); //debug
    main.sortNodes(nodes);
    //main.printNodes(nodes); //debug
    main.findConvexHull(nodes, lineList);
    //main.printLine(lineList); //debug
    main.printResults(lineList);
  }
  //=====================================
  //=====================================

  private void initNodes(Node[] nodes){
    for(int i = 0; i < nodes.length; i++){
      nodes[i] = new Node();
      nodes[i].setX((int)(Math.random()*100+1));
      nodes[i].setY((int)(Math.random()*100+1));
    }
  }

  private void findConvexHull(Node[] nodes, ArrayList<Line> lineList){
    ArrayList<Node> convexList = new ArrayList<>();
    convexList.add(nodes[0]);
    findLowerHull(nodes, convexList);
    //printConvexList(convexList);
    findUpperHull(nodes, convexList);
    //printConvexList(convexList);
    nodeToConvex(convexList, lineList);
    //System.out.println("The First Index from Left:"+firstNode);
  }

  private void findLowerHull(Node[] nodes, ArrayList<Node> convexList){
    //From left to right ==> Lower Hull
    int nextIndex = 0;
    do{
      float minSlope = 101;
      int mainIndex = nextIndex;
      for(int i = nextIndex; i < nodes.length-1; i++){
        float tmpSlope = slope(nodes[mainIndex], nodes[i+1]);
        //System.out.println("minSlope:"+minSlope);
        //System.out.println("tmpSlope:"+tmpSlope);
        if(tmpSlope < minSlope){
          minSlope = tmpSlope;
          nextIndex = i+1;
        }
      }
      //System.out.println("nextIndex:"+nextIndex);
      convexList.add(nodes[nextIndex]);
      //nodes[nextIndex].setConvex(true);
    }while(nextIndex < nodes.length-1);
  }

  private void findUpperHull(Node[] nodes, ArrayList<Node> convexList){
    //From right to left ==> Upper Hull
    int nextIndex = nodes.length-1;
    do{
      float minSlope = 101;
      int mainIndex = nextIndex;
      for(int i = nextIndex; i > 0; i--){
        float tmpSlope = slope(nodes[mainIndex], nodes[i-1]);
        if(tmpSlope < minSlope){
          minSlope = tmpSlope;
          nextIndex = i-1;
        }
      }
      convexList.add(nodes[nextIndex]);
    }while(nextIndex > 0);
  }

  private float slope(Node x, Node y){
    if(y.getX()==x.getX())return 100;
    return (float)(y.getY()-x.getY())/(float)(y.getX()-x.getX());
  }

  private void sortNodes(Node[] nodes){
    //For Andrew's Monotone Chain
    //Sort from left to right and from bottom to top
    for(int i = 0; i < nodes.length; i++){
      for(int j = i+1; j < nodes.length; j++){
        if(nodes[j].getX() < nodes[i].getX()){
          swap(nodes, i, j);
        }else if(nodes[j].getX() == nodes[i].getX()){
          if(nodes[j].getY() < nodes[i].getY()){
            swap(nodes, i, j);
          }
        }
      }
    }
  }

  private void swap(Node[] nodes, int a, int b){
    Node tmp = nodes[a];
    nodes[a] = nodes[b];
    nodes[b] = tmp;
  }

  private void nodeToConvex(ArrayList<Node> convexList, ArrayList<Line> lineList){
    for(int i = 0; i < convexList.size()-1; i++){
      Line line = new Line();
      line.setNodeOne(convexList.get(i));
      line.setNodeTwo(convexList.get(i+1));
      lineList.add(line);
    }
  }

  private double getDistance(Node a, Node b){
    return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
  }

  private void printNodes(Node[] nodes){
    for(int i = 0; i < nodes.length; i++){
      System.out.print("("+nodes[i].getX()+",");
      System.out.print(nodes[i].getY()+")");
      //System.out.println(" onConvexHull:"+nodes[i].onConvexHull());
    }
  }

  private void printConvexList(ArrayList<Node> convexList){
    System.out.print("ConvexList:");
    convexList.forEach((node)->System.out.print("("+node.getX()+","+node.getY()+")"));
    System.out.println();
  }

  private void printLine(ArrayList<Line> lineList){
    lineList.forEach((line)->System.out.println("("+line.getNodeOne().getX()+","+line.getNodeOne().getY()+")->"+"("+line.getNodeTwo().getX()+","+line.getNodeTwo().getY()+")"));
  }

  private void printResults(ArrayList<Line> lineList){
    Frame frame = new Frame("MyCanvas");
    frame.add(new MyCanvas(lineList));
    frame.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    frame.pack();
    frame.setVisible(true);
  }
}
