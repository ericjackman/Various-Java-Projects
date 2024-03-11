import java.util.Arrays;
import java.util.List;

public class RoundTripPlanner {
    // user inputs for the source and destination
    private int startCityIndex;
    private int endCityIndex;

    // Graph created using the following vertices and edges
    private WeightedGraph<String> flightNetwork;

    // array of vertices
    private String[] cities;
    // array of weighted edges [source][dest][weight]
    private int[][] connections;

    // forward and return route cities lists and cost of trip
    private List<String> forwardRoute;
    private double forwardRouteCost;
    private List<String> returnRoute;
    private double returnRouteCost;

    /*
     * Constructor:
     * - Assigns class variables
     * - Invokes generateRoundTrip() method
     */
    public RoundTripPlanner(String[] cities, int[][] connections, int startCityIndex, int endCityIndex) {
        this.cities = cities;
        this.connections = connections;
        this.startCityIndex = startCityIndex;
        this.endCityIndex = endCityIndex;

        generateRoundTrip();
    }


    /*
     * Round trip generator:
     * - Creates flight network graph
     * - Updates forward trip path variable and forward trip cost
     * - Performs necessary actions for return trip planning
     * - Updates return trip path variable and return trip cost
     */
    public void generateRoundTrip() {
        flightNetwork = new WeightedGraph<>(cities, connections);  // create weighted graph
        forwardRoute = flightNetwork.getShortestPath(startCityIndex).getPath(endCityIndex);  // find forward route path
        forwardRouteCost = flightNetwork.getShortestPath(startCityIndex).getCost(endCityIndex); // find forward route cost
        // Iterate through path from end to beginning
        for (int i = forwardRoute.size() - 1; i > 0; i--) {
            // Get edge (u, v) from i to i - 1
            int u = flightNetwork.getIndex(forwardRoute.get(i));
            int v = flightNetwork.getIndex(forwardRoute.get(i - 1));
            // Iterate through neighbors of edge
            for (Edge edge : flightNetwork.neighbors.get(u)) {
                if (edge.v == v)
                    ((WeightedEdge) edge).weight = Integer.MAX_VALUE;  // change weight to max
            }
        }
        returnRoute = flightNetwork.getShortestPath(endCityIndex).getPath(startCityIndex);  // find return route path
        returnRouteCost = flightNetwork.getShortestPath(endCityIndex).getCost(startCityIndex);  // find return route cost
    }


    /*
     * Trip viewer:
     * - prints forward trip in the format:
     * "Forward trip from A to B: A –> P –> Q –> R –> B"
     * - prints return trip in the same format:
     * "Return trip from B to A: B –> S –> T –> U –> A"
     * - prints the costs for the forward trip, return trip, and total trip in the format:
     *  "Forward route cost: 200.0"
     *  "Return route cost: 300.0"
     *  "Total trip cost: 500.0"
     */
    public void printRoundTrip() {
        // Print forward path
        System.out.print("Forward trip from " + flightNetwork.getVertex(startCityIndex) + " to " + flightNetwork.getVertex(endCityIndex) + ": ");
        for (int i = 0; i < forwardRoute.size(); i++) {
            System.out.print(forwardRoute.get(i));
            if (i == forwardRoute.size() - 1)
                System.out.print("\n");
            else
                System.out.print(" -> ");
        }
        // Print return path
        System.out.print("Return trip from " + flightNetwork.getVertex(endCityIndex) + " to " + flightNetwork.getVertex(startCityIndex) + ": ");
        for (int i = 0; i < returnRoute.size(); i++) {
            System.out.print(returnRoute.get(i));
            if (i == returnRoute.size() - 1)
                System.out.print("\n");
            else
                System.out.print(" -> ");
        }
        // Print costs
        double totalCost = forwardRouteCost + returnRouteCost;
        System.out.println("Forward route cost: " + forwardRouteCost);
        System.out.println("Return route cost: " + returnRouteCost);
        System.out.println("Total trip cost: " + totalCost);
    }

    // Returns the forwardRoute class variable
    public List<String> getForwardRoute() {
        return forwardRoute;
    }

    // Returns the returnRoute class variable
    public List<String> getReturnRoute() {
        return returnRoute;
    }

    // Returns the forwardRouteCost class variable
    public double getForwardRouteCost() {
        return forwardRouteCost;
    }

    // Returns the returnRouteCost class variable
    public double getReturnRouteCost() {
        return returnRouteCost;
    }
}
