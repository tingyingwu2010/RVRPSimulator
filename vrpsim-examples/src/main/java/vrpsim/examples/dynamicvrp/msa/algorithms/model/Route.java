package vrpsim.examples.dynamicvrp.msa.algorithms.model;

import java.util.ArrayList;
import java.util.List;

import vrpsim.core.model.behaviour.activities.util.TimeCalculationInformationContainer;
import vrpsim.core.model.network.INode;
import vrpsim.core.model.network.IWay;
import vrpsim.core.model.network.NetworkService;
import vrpsim.core.model.structure.StructureService;
import vrpsim.core.model.structure.customer.ICustomer;
import vrpsim.core.model.structure.util.storage.CanStoreType;
import vrpsim.core.model.util.exceptions.VRPArithmeticException;
import vrpsim.core.simulator.IClock;

public class Route {

	/**
	 * Customers without depot.
	 */
	private List<Stop> stops;

	/**
	 * The vehicle responsible for the {@link Route}.
	 */
	private String vehicleId;

	/**
	 * The size of a route is equal to the number of customers, the depots are
	 * not considered in the route size.
	 * 
	 * @return
	 */
	public int getSizeOfRoute() {
		return stops.size();
	}

	/**
	 * Returns the travel costs of the {@link Route}, which is the sum of
	 * traveled distances for this route. The travel costs consider the way from
	 * and back to the depot.
	 * 
	 * @param networkService
	 * @param structureService
	 * @return
	 */
	public double getTravelCosts(NetworkService networkService, StructureService structureService) {
		double costs = 0.0;
		if (this.getSizeOfRoute() > 0) {

			INode depotNode = (INode) structureService.getDepots().get(0)
					.getVRPSimulationModelStructureElementParameters().getHome();

			INode currentNode = (INode) structureService.getCustomer(this.stops.get(0).getId())
					.getVRPSimulationModelStructureElementParameters().getHome();
			costs += getDistance(depotNode, currentNode);

			for (int i = 1; i < this.stops.size(); i++) {
				INode workingNode = (INode) structureService.getCustomer(this.stops.get(i).getId())
						.getVRPSimulationModelStructureElementParameters().getHome();
				costs += getDistance(workingNode, currentNode);
				currentNode = workingNode;
			}

			costs += getDistance(currentNode, depotNode);
		}
		return costs;

	}

	/**
	 * Returns true if the route is feasible regarding vehicle capacity and time
	 * windows.
	 * 
	 * @param route
	 * @return
	 * @throws VRPArithmeticException
	 */
	public boolean isFeasible(StructureService structureService, IClock clock) throws VRPArithmeticException {
		
		double depotClosingTime = 240.0;
		
		boolean result = true;
		int currentCapacity = 0;
		int maxCapacity = getCapacityOfVehicle(structureService);
		double currentTime = 0;

		INode depotNode = (INode) structureService.getDepots().get(0)
				.getVRPSimulationModelStructureElementParameters().getHome();
		INode currentNode = depotNode;

		for (Stop stop : this.getStops()) {

			currentCapacity += stop.getDemand();
			if (currentCapacity > maxCapacity) {
				result = false;
				break;
			}

			INode workingNode = (INode) structureService.getCustomer(stop.getId())
					.getVRPSimulationModelStructureElementParameters().getHome();
			double workingNodeServiceTime = getServiceTime(structureService.getCustomer(stop.getId()), clock);
			double getDistanceWhichEqualsTime = getDistance(currentNode, workingNode);

			currentTime += getDistanceWhichEqualsTime;

			if (currentTime <= stop.getLdd()) {

				if (currentTime < stop.getEdd()) {
					currentTime = stop.getEdd() + workingNodeServiceTime;
				} else {
					currentTime += workingNodeServiceTime;
				}

			} else {
				result = false;
				break;
			}

			currentNode = workingNode;
		}
		
		double distanceBackToDepot = getDistance(currentNode, depotNode);
		if(currentTime + distanceBackToDepot > depotClosingTime) {
			result = false;
		}
		
		return result;
	}

	private double getServiceTime(ICustomer customer, IClock clock) {
		TimeCalculationInformationContainer container = new TimeCalculationInformationContainer(null, null, null, null,
				new Integer(0));
		return customer.getServiceTime(container, clock).getDoubleValue();

	}

	private int getCapacityOfVehicle(StructureService structure) throws VRPArithmeticException {
		CanStoreType type = structure.getVehicle(this.vehicleId).getAllCanStoreTypes().get(0);
		return structure.getVehicle(this.vehicleId).getFreeCapacity(type).getValue().intValue();
	}

	private double getDistance(INode node1, INode node2) {
		for (IWay way : node1.getWays()) {
			if (way.getTarget().getVRPSimulationModelElementParameters().getId()
					.equals(node2.getVRPSimulationModelElementParameters().getId())) {
				return way.getDistance();
			}
		}
		throw new RuntimeException("No distance between nodes calculateable.");
	}

	/**
	 * Returns the customer ids.
	 * 
	 * @return
	 */
	public List<String> getCustomerIds() {
		List<String> ids = new ArrayList<>();
		this.stops.forEach(stop -> ids.add(stop.getId()));
		return ids;
	}

	/**
	 * Returns true if {@link Route} contains {@link Stop}.
	 * 
	 * @param stop
	 * @return
	 */
	public boolean contains(Stop stop) {
		boolean result = false;
		for (Stop s : this.stops) {
			if (stop.getId().equals(s.getId())) {
				result = true;
				break;
			}
		}
		return result;
	}

	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}
}
