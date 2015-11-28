package sim.warehouse;

import java.util.ArrayList;
import java.util.Arrays;

import sim.abstraction.CostFactor;
import sim.hr.Employee;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.Wall;
import sim.production.WallType;

public class Warehouse implements CostFactor{

	private int capacity;
	private int utilization;
	private int costs;

	private ArrayList<Employee> employees;

	private ArrayList<Resource> resources;
	private ArrayList<Wall> walls;

	public Warehouse(){
		this(50, 1000);
	}

	public Warehouse(int capacity, int costs){
		this.capacity = capacity;
		this.costs = costs;
	}

	public boolean storeWall(Wall... w){
		int volume = 0;
		for (int i = 0; i < w.length; i++) {
			volume += w[i].getVolume();
		}


		//1. figure out how many space we need to store the resource
		if(utilization + volume > capacity)
			return false;

		//2. ok enough space. store it
		utilization += volume;
		walls.addAll(Arrays.asList(w));

		return true;
	}

	public boolean storeResource(Resource... r){
		int volume = 0;
		for (int i = 0; i < r.length; i++) {
			volume += r[i].getVolume();
		}


		//1. figure out how many space we need to store the resource
		if(utilization + volume > capacity)
			return false;

		//2. ok enough space. store it
		utilization += volume;
		resources.addAll(Arrays.asList(r));

		return true;
	}

	public Wall removeWall(WallType type){

		for (int i = 0; i < walls.size(); i++) {
			if(walls.get(i).getType() == type){
				utilization -= walls.get(i).getVolume();
				return walls.remove(i);
			}
		}

		return null;
	}

	public Wall[] removeWalls(WallType type, int count){
		//1. check if the ecessary count is avaliable
		if(!isInStorage(type, count))
			return null;

		//2. remove the reqested amount from the storage
		Wall[] ret = new Wall[count];
		int c = 0;
		for (int i = 0; i < walls.size(); i++) {
			if(walls.get(i).getType() == type){
				utilization -= walls.get(i).getVolume();
				ret[c++] = walls.remove(i);
			}
		}

		return ret;
	}

	public Resource removeResource(ResourceType type){

		for (int i = 0; i < resources.size(); i++) {
			if(resources.get(i).getType() == type){
				utilization -= resources.get(i).getVolume();
				return resources.remove(i);
			}
		}

		return null;
	}

	public Resource[] removeResource(ResourceType type, int count){
		//1. check if the ecessary count is avaliable
		if(!isInStorage(type, count))
			return null;

		//2. remove the reqested amount from the storage
		Resource[] ret = new Resource[count];
		int c = 0;
		for (int i = 0; i < resources.size(); i++) {
			if(resources.get(i).getType() == type){
				utilization -= resources.get(i).getVolume();
				ret[c++] = resources.remove(i);
			}
		}

		return ret;
	}

	public boolean isInStorage(WallType type){
		return isInStorage(type, 1);
	}

	public boolean isInStorage(WallType type, int count){
		int c = 0;
		for (int i = 0; i < walls.size() && c < count; i++) {
			if(walls.get(i).getType() == type){
				c++;
			}
		}
		return c >= count;
	}

	public boolean isInStorage(ResourceType type){
		return isInStorage(type, 1);
	}

	public boolean isInStorage(ResourceType type, int count){
		int c = 0;
		for (int i = 0; i < resources.size() && c < count; i++) {
			if(resources.get(i).getType() == type){
				c++;
			}
		}
		return c >= count;
	}

	public int getUtilization(){
		return utilization;
	}

	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getCosts() {
		return costs;
	}

	public int getOverallCosts(){
		int costs = 0;
		for (int i = 0; i < employees.size(); i++) {
			costs = employees.get(i).getCosts();
		}

		return getCosts() + costs;
	}

	@Override
	public void setCosts(int costs) {
		this.costs = costs;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

}
